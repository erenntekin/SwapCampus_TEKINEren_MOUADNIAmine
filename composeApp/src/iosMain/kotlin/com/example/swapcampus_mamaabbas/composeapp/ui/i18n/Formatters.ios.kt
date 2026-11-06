package com.example.swapcampus_mamaabbas.composeapp.ui.i18n

import androidx.compose.runtime.Composable
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import platform.Foundation.NSCalendar
import platform.Foundation.NSDateComponents
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSDateFormatterMediumStyle
import platform.Foundation.NSDateFormatterNoStyle
import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterCurrencyStyle
import platform.Foundation.currentLocale
import platform.Foundation.localeIdentifier

@Composable
actual fun currentLanguageTag(): String = NSLocale.currentLocale.localeIdentifier

actual fun formatPrice(price: Double, languageTag: String): String {
    val decimals = if (price % 1.0 == 0.0) 0uL else 2uL
    val formatter = NSNumberFormatter()
    formatter.numberStyle = NSNumberFormatterCurrencyStyle
    formatter.currencyCode = "EUR"
    formatter.locale = NSLocale(languageTag)
    formatter.minimumFractionDigits = decimals
    formatter.maximumFractionDigits = decimals
    return formatter.stringFromNumber(NSNumber(price)) ?: price.toString()
}

actual fun formatDate(date: LocalDate, languageTag: String): String {
    val components = NSDateComponents()
    components.year = date.year.toLong()
    components.month = date.month.number.toLong()
    components.day = date.day.toLong()
    val nsDate = NSCalendar.currentCalendar.dateFromComponents(components) ?: return date.toString()
    val formatter = NSDateFormatter()
    formatter.dateStyle = NSDateFormatterMediumStyle
    formatter.timeStyle = NSDateFormatterNoStyle
    formatter.locale = NSLocale(languageTag)
    return formatter.stringFromDate(nsDate)
}
