package com.example.swapcampus_mamaabbas.composeapp.ui.i18n

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Currency
import java.util.Locale
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate

@Composable
actual fun currentLanguageTag(): String = LocalConfiguration.current.locales[0].toLanguageTag()

actual fun formatPrice(price: Double, languageTag: String): String {
    val decimals = if (price % 1.0 == 0.0) 0 else 2
    return NumberFormat.getCurrencyInstance(Locale.forLanguageTag(languageTag)).apply {
        currency = Currency.getInstance("EUR")
        minimumFractionDigits = decimals
        maximumFractionDigits = decimals
    }.format(price)
}

actual fun formatDate(date: LocalDate, languageTag: String): String =
    date.toJavaLocalDate().format(
        DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.forLanguageTag(languageTag))
    )
