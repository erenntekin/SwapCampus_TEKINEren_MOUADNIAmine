# Labwork 2: SwapCampus on Android and iOS

**Team:** Eren Tekin and Amine Mouadni  
**GitHub repository:** https://github.com/erenntekin/SwapCampus_TEKINEren_MOUADNIAmine

We started from our Labwork 1 app. Its `:composeApp` module was an Android library, so we first turned it into a Compose Multiplatform module, as in the tutorial. Then we followed the labwork steps.

## Step 1: inventory

| Files | What blocks | Solution |
|---|---|---|
| ViewModels, `ListingsUiState` | `R.string` for error messages | Adapt the imports (`Res.string`, `StringResource`) |
| `AppContainer` | Nothing | Move as is |
| `Labels.kt` | `R.string` | Adapt the imports |
| Price and date formats | `java.text`, `java.time`, `java.util.Locale` | expect / actual |
| Language selector | Our app has none | Not needed |
| `ui/components/*`, `ui/screens/*` | `R.string`, Android `stringResource` | Adapt the imports |
| `Theme.kt` | Nothing | Move as is |
| `Routes.kt`, `AppNavHost.kt` | `android.net.Uri`, Android navigation | Multiplatform navigation |
| `strings.xml` (EN and FR) | Android resources | Compose Resources |
| Coil network library | OkHttp only exists on Android | Coil 3 with Ktor |

## Our choices

The theme has no expect / actual. Our colors are fixed, so the same code works on both platforms.

The app follows the language of the phone. Our Labwork 1 app had no language selector, so step 5 did not apply.

In Labwork 1 the login route took a `redirect` argument. With typed routes, a protected screen now shows the login form itself. Once the user is logged in, the screen appears.

We use AGP 9.3.0. Kotlin 2.4.21 supports AGP up to 9.3.1. The minimum Android version is 8.0 because the Android formatter uses `java.time`.

## Step 8: test scenarios

In our app only the owner of a listing can reserve it: the owner types the buyer's name. So scenario 4 tests posting while logged out, and scenario 6 uses Alice instead of Ada.

| # | Scenario | Expected result | Android | iOS |
|---|---|---|---|---|
| 1 | Launch | A brief spinner, then the list. Prices follow the language. | OK | Partly |
| 2 | Search and category filter | The list is filtered. A message appears if nothing matches. | OK | OK |
| 3 | Open a listing, go back | The detail shows the seller and the date. Going back works (arrow, and gesture on iOS). | OK | OK |
| 4 | Post while logged out | A login form appears. After logging in, the post form is shown. | OK | OK |
| 5 | Post: empty title, then price "abc", then valid | Clear error messages. The valid listing appears in the list. | OK | OK |
| 6 | My listings and editing | Only Alice's listings are shown. The edit is saved. | OK | OK |
| 7 | Reserve, cancel, mark as sold, remove | Correct statuses. Confirmation before removal. | Partly | Partly |
| 8 | Profile | Name, email, role, "Member since". Log out works. | Partly | Partly |
| 9 | Change the language | Texts, prices and dates change. | OK | Partly |
| 10 | Dark mode | The dark theme is readable. | OK | OK |

Notes:

- **1.** The spinner exists (`ListingsUiState.Loading`), but the data is in memory, so it only shows for a split second.
- **1 and 9 on iOS.** The texts change, but prices and dates stay in the English format (`€12.50`, `Nov 5, 2026`). Compose Resources reads the preferred language of the phone. Our iOS formatter reads `NSLocale.currentLocale`, and iOS builds it from the languages the app declares. Our Xcode project declares no French localization, so this locale stays English.
- **5.** The price field opens a numeric keyboard. On a real phone you cannot type "abc", so we typed it with the computer keyboard in the emulator and the simulator.
- **7.** The statuses and the removal confirmation work. There is no snackbar: the status tag on the detail screen changes instead. Reserving your own listing is not an error in our app, so there is no business error to show.
- **8.** Name, email, role and statistics are shown, and log out works. "Member since" is missing: `UserAccount.creationDate` exists but the profile screen does not display it.
- **9 on Android.** There is no in-app selector. We changed the system language in the Android settings.

## Step 9: Android and iOS comparison

| What we observe | Android | iOS | Why |
|---|---|---|---|
| Theme colours | Same green palette, light and dark. | Same palette. Dialogs and buttons look like Android (Material 3). | `Theme.kt` is in `commonMain` with fixed colours and no dynamic colour. Compose draws every pixel itself, so iOS does not get native UIKit components. |
| Going back | Arrow in the top bar, plus the system back button or gesture. | Arrow in the top bar, plus a swipe from the left edge. No system back button. | Both trigger the same back event that the navigation library listens to. Each system has its own way to send it. |
| Keyboard and form fields | Gboard. Numeric keyboard for the price, email keyboard for the email. | iOS keyboard. The decimal pad has no Return key, so it is hard to close. | Compose only asks for a keyboard type (`KeyboardType.Decimal`, `Email`). Each system draws its own keyboard. |
| Fonts and spacing | Roboto. | San Francisco. Text looks a little wider. Spacing is the same. | We do not set a font family, so each platform uses its system font. Sizes are in `dp` and `sp`, converted for each screen. |
| Language selector | No selector in the app. System settings, and the app updates. | No selector in the app. Settings app (Language & Region), and the app restarts. | Language selection is done by each OS. The app only reads the current language. |

## Reflection questions

How much code stayed in androidMain and iosMain? With `wc -l`: `commonMain` has 23 files and 1816 lines. `androidMain` has 1 file and 28 lines (formatters). `iosMain` has 2 files and 48 lines (formatters and `MainViewController`). Add 377 lines in `:shared`, all common. About 97% of the code is shared.

To add Spanish, which files change? Only `composeResources/values-es/strings.xml`. It is shared by both platforms, so there is not one file per platform. The formatters already take the language as a parameter. On iOS we would also declare the language in the Xcode project.

Which other component would need an expect / actual for photo sharing? Picking a photo and sharing it. Android uses an `Intent` (photo picker, `ACTION_SEND`) and iOS uses `PHPickerViewController` and `UIActivityViewController`. Reading the image file and asking for permissions would also be platform specific.
