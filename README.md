# SwapCampus

SwapCampus is a classifieds app for students. It was built for the II.3510 Mobile Development course at ISEP (2026-2027).

Labwork 2 team: Eren Tekin and Amine Mouadni.
GitHub repository: https://github.com/erenntekin/SwapCampus_TEKINEren_MOUADNIAmine

The app runs on Android and iOS from the same Kotlin code with Compose Multiplatform. The notes for Labwork 2 are in [LABWORK2.md](LABWORK2.md).

## Demo accounts

Students: alice@isep.fr and bob@isep.fr, password 1234. Administrator: admin@isep.fr, password admin.

## Modules

`:shared` contains the domain and the repositories. `:composeApp` contains the screens, the ViewModels and the navigation in `commonMain`. Its `androidMain` and `iosMain` folders only contain the price and date formats and the iOS entry point. `:androidApp` contains `MainActivity`. `iosApp` contains the Xcode project.

## Run

Android: run the `androidApp` configuration in Android Studio, or `./gradlew :androidApp:installDebug`.

iOS: on a Mac with Xcode 26.4, open `iosApp/iosApp.xcodeproj`, choose an iPhone simulator and press Cmd+R. The first build takes 5 to 15 minutes.

## Tests

Student presentation topic: Testing (unit, JUnit) & automated mobile testing (Espresso, UI Automator).
The exercise for the class is in a separate repository: https://github.com/Tozomoukk/SwapCampus-Testing-UP

| Tests | Folder | Tools | Command |
|---|---|---|---|
| Unit tests (domain, repository) | `shared/src/commonTest` | kotlin.test (JUnit 4 on Android), kotlinx-coroutines-test | `./gradlew :shared:testAndroidHostTest` |
| UI tests ("post a listing" flow) | `androidApp/src/androidTest` | Compose test, Espresso, UI Automator | `./gradlew :androidApp:connectedAndroidTest` |

The unit tests also run on the iOS simulator (Mac): `./gradlew :shared:iosSimulatorArm64Test`.
The UI tests find screens by their English text: start the emulator in English before running them.
Reports: `shared/build/reports/tests/` and `androidApp/build/reports/androidTests/`.
