package com.example.swapcampus_mamaabbas.composeapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swapcampus_mamaabbas.shared.domain.Available
import com.example.swapcampus_mamaabbas.shared.domain.Category
import com.example.swapcampus_mamaabbas.shared.domain.ListingStatus
import com.example.swapcampus_mamaabbas.shared.domain.Reserved
import com.example.swapcampus_mamaabbas.shared.domain.Sold


private val LightColors = lightColorScheme(
    primary = Color(0xFF1E6B52),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB4EFD2),
    onPrimaryContainer = Color(0xFF002116),
    secondary = Color(0xFF4C6359),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFCFE9DB),
    onSecondaryContainer = Color(0xFF082018),
    tertiary = Color(0xFF8A5100),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFDCBE),
    onTertiaryContainer = Color(0xFF2C1600),
    background = Color(0xFFF8FAF6),
    onBackground = Color(0xFF191C1A),
    surface = Color(0xFFF8FAF6),
    onSurface = Color(0xFF191C1A),
    surfaceVariant = Color(0xFFDCE5DE),
    onSurfaceVariant = Color(0xFF404943),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF2F5F0),
    surfaceContainer = Color(0xFFECF0EA),
    surfaceContainerHigh = Color(0xFFE6EAE4),
    surfaceContainerHighest = Color(0xFFE1E4DF),
    outline = Color(0xFF707973),
    outlineVariant = Color(0xFFC0C9C2),
    error = Color(0xFFBA1A1A),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF98D5B8),
    onPrimary = Color(0xFF003827),
    primaryContainer = Color(0xFF00513A),
    onPrimaryContainer = Color(0xFFB4EFD2),
    secondary = Color(0xFFB3CCBF),
    onSecondary = Color(0xFF1E352C),
    secondaryContainer = Color(0xFF354B42),
    onSecondaryContainer = Color(0xFFCFE9DB),
    tertiary = Color(0xFFFFB870),
    onTertiary = Color(0xFF4A2800),
    tertiaryContainer = Color(0xFF6A3C00),
    onTertiaryContainer = Color(0xFFFFDCBE),
    background = Color(0xFF101412),
    onBackground = Color(0xFFE0E3DF),
    surface = Color(0xFF101412),
    onSurface = Color(0xFFE0E3DF),
    surfaceVariant = Color(0xFF404943),
    onSurfaceVariant = Color(0xFFC0C9C2),
    surfaceContainerLowest = Color(0xFF0B0F0D),
    surfaceContainerLow = Color(0xFF191C1A),
    surfaceContainer = Color(0xFF1D201E),
    surfaceContainerHigh = Color(0xFF272B28),
    surfaceContainerHighest = Color(0xFF323533),
    outline = Color(0xFF8A938C),
    outlineVariant = Color(0xFF404943),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
)

private val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp),
)

private val BaseTypography = Typography()

private val AppTypography = BaseTypography.copy(
    displaySmall = BaseTypography.displaySmall.copy(fontWeight = FontWeight.Bold, letterSpacing = (-0.5).sp),
    headlineLarge = BaseTypography.headlineLarge.copy(fontWeight = FontWeight.Bold, letterSpacing = (-0.5).sp),
    headlineMedium = BaseTypography.headlineMedium.copy(fontWeight = FontWeight.Bold, letterSpacing = (-0.25).sp),
    headlineSmall = BaseTypography.headlineSmall.copy(fontWeight = FontWeight.Bold),
    titleLarge = BaseTypography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
    titleMedium = BaseTypography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
    titleSmall = BaseTypography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
    labelLarge = BaseTypography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
)

class ToneColors(val container: Color, val content: Color)

@Composable
fun statusColors(status: ListingStatus): ToneColors {
    val dark = isSystemInDarkTheme()
    return when (status) {
        Available ->
            if (dark) ToneColors(Color(0xFF00513A), Color(0xFFB4EFD2))
            else ToneColors(Color(0xFFD5F5E3), Color(0xFF0B5A40))
        is Reserved ->
            if (dark) ToneColors(Color(0xFF5A3A00), Color(0xFFFFD08A))
            else ToneColors(Color(0xFFFFE8C2), Color(0xFF7A4600))
        is Sold ->
            if (dark) ToneColors(Color(0xFF3A3D3B), Color(0xFFD5D9D6))
            else ToneColors(Color(0xFFE4E7E4), Color(0xFF3F4441))
    }
}

@Composable
fun categoryColors(category: Category): ToneColors {
    val dark = isSystemInDarkTheme()
    return when (category) {
        Category.BOOKS ->
            if (dark) ToneColors(Color(0xFF2C2F6B), Color(0xFFC0C1FF)) else ToneColors(Color(0xFFE2E1FF), Color(0xFF3A3D8F))
        Category.COURSES ->
            if (dark) ToneColors(Color(0xFF00393C), Color(0xFF8ED6DA)) else ToneColors(Color(0xFFCDEFF0), Color(0xFF00585C))
        Category.ELECTRONICS ->
            if (dark) ToneColors(Color(0xFF12305B), Color(0xFFAAC7FF)) else ToneColors(Color(0xFFD9E5FF), Color(0xFF1B4A8A))
        Category.SPORTS_LEISURE ->
            if (dark) ToneColors(Color(0xFF5A2800), Color(0xFFFFB68A)) else ToneColors(Color(0xFFFFDECB), Color(0xFF8A3F00))
        Category.FURNITURE ->
            if (dark) ToneColors(Color(0xFF3D3207), Color(0xFFDCC890)) else ToneColors(Color(0xFFF0E6CB), Color(0xFF5C4A12))
        Category.CLOTHING ->
            if (dark) ToneColors(Color(0xFF5E0F3A), Color(0xFFFFAFD3)) else ToneColors(Color(0xFFFFDBEA), Color(0xFF8B2058))
        Category.OTHER ->
            if (dark) ToneColors(Color(0xFF323533), Color(0xFFC4C7C3)) else ToneColors(Color(0xFFE3E6E2), Color(0xFF444845))
    }
}

@Composable
fun SwapCampusTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = AppTypography,
        shapes = AppShapes,
        content = content,
    )
}
