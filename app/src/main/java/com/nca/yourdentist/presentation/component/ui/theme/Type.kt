package com.nca.yourdentist.presentation.component.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.nca.yourdentist.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val bodyFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Poppins"),
        fontProvider = provider,
    )
)

val displayFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Poppins"),
        fontProvider = provider,
    )
)

// Default Material 3 typography values
val baseline = Typography()

// Define typography styles
val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp
    ),
    displayMedium = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 45.sp
    ),
    displaySmall = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 36.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    titleLarge = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),
    titleSmall = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 10.sp
    )
)
//val AppTypography = Typography(
//    displayLarge = baseline.displayLarge.copy(fontFamily = displayFontFamily),
//    displayMedium = baseline.displayMedium.copy(fontFamily = displayFontFamily),
//    displaySmall = baseline.displaySmall.copy(fontFamily = displayFontFamily),
//    headlineLarge = baseline.headlineLarge.copy(fontFamily = displayFontFamily),
//    headlineMedium = baseline.headlineMedium.copy(fontFamily = displayFontFamily),
//    headlineSmall = baseline.headlineSmall.copy(fontFamily = displayFontFamily),
//    titleLarge = baseline.titleLarge.copy(fontFamily = displayFontFamily),
//    titleMedium = baseline.titleMedium.copy(fontFamily = displayFontFamily),
//    titleSmall = baseline.titleSmall.copy(fontFamily = displayFontFamily),
//    bodyLarge = baseline.bodyLarge.copy(fontFamily = bodyFontFamily),
//    bodyMedium = baseline.bodyMedium.copy(fontFamily = bodyFontFamily),
//    bodySmall = baseline.bodySmall.copy(fontFamily = bodyFontFamily),
//    labelLarge = baseline.labelLarge.copy(fontFamily = bodyFontFamily),
//    labelMedium = baseline.labelMedium.copy(fontFamily = bodyFontFamily),
//    labelSmall = baseline.labelSmall.copy(fontFamily = bodyFontFamily),
//)