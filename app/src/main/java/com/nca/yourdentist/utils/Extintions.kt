package com.nca.yourdentist.utils

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

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