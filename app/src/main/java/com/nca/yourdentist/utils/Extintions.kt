package com.nca.yourdentist.utils

import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import androidx.annotation.RequiresApi
import com.google.firebase.Timestamp
import com.nca.yourdentist.data.models.users.CityArea
import java.io.File
import java.io.FileOutputStream
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
    val locale = Locale.ENGLISH
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

fun Uri.getFileFromUri(context: Context): File {
    val contentResolver = context.contentResolver

    if (this.scheme == "file") {
        return File(this.path!!)
    }

    val fileName = this.getFileName(context)
    val tempFile = File(context.cacheDir, fileName)

    contentResolver.openInputStream(this)?.use { inputStream ->
        tempFile.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }
    }

    return tempFile
}

fun Uri.getFileName(context: Context): String {
    var name: String? = null
    if (this.scheme == "content") {
        val cursor = context.contentResolver.query(this, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                name = it.getString(it.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
            }
        }
    }
    return name ?: "${System.currentTimeMillis()}.jpg"
}

fun ByteArray.convertToImageFile(context: Context): File {
    val outputFile = File(context.cacheDir, "output_image.jpg")
    FileOutputStream(outputFile).use { fos ->
        fos.write(this)
        fos.flush()
    }
    return outputFile
}