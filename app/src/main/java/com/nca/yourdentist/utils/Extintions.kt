package com.nca.yourdentist.utils

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.Timestamp
import com.nca.yourdentist.data.models.users.CityArea
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

fun Context.updateBaseContextLocale(locale: Locale): Context {
    Locale.setDefault(locale)
    val config = Configuration(resources.configuration)
    config.setLocale(locale)
    return createConfigurationContext(config)
}

fun String.capitalizeFirstLetter(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase())
            it.titlecase()
        else
            it.toString()
    }
}

fun CityArea.localizedName(context: Context): String {
    val language = context.resources.configuration.locales.get(0).language
    return if (language == "ar") nameAr else nameEn
}

fun Timestamp.toFormattedString(pattern: String = "dd MMM yyyy"): String {
    val locale = Locale.getDefault()
    val formatter = SimpleDateFormat(pattern, locale)
    return formatter.format(this.toDate())
}

fun Timestamp.getExperienceText(): String {
    val now = Date()
    val diffMillis = now.time - this.toDate().time

    val days = TimeUnit.MILLISECONDS.toDays(diffMillis)
    val months = days / 30
    val years = months / 12

    return when {
        days < 30 -> "$days day(s)"
        months < 12 -> "$months month(s)"
        else -> "$years year(s)"
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun Timestamp.toLocalDate(): LocalDate = this.toDate()
    .toInstant()
    .atZone(ZoneId.systemDefault())
    .toLocalDate()