package com.mahidol.snakeclassification

import android.content.Context
import android.os.Build
import java.util.*
import android.annotation.SuppressLint


@Suppress("DEPRECATION")
class LocaleHelper {
    private val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"
    fun onAttach(context: Context):Context{
        val lang = getPersistedData(context,Locale.getDefault().language)
        return setLocal(context,lang)
    }
    fun onAttach(context: Context,defaultLanguage:String):Context{
        val lang = getPersistedData(context,defaultLanguage)
        return setLocal(context,lang)
    }
    @SuppressLint("ObsoleteSdkInt")
    fun setLocal(context: Context, lang: String): Context {
        persist(context,lang)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return updateResource(context,lang)
        return updateResourceLegacy(context,lang)
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun updateResourceLegacy(context: Context, lang: String): Context {

        val locale = Locale(lang)
        Locale.setDefault(locale)
        val resources = context.resources
        val config = resources.configuration
        config.locale = locale
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            config.setLayoutDirection(locale)
        resources.updateConfiguration(config,resources.displayMetrics)
        return context
    }

    private fun updateResource(context: Context, lang: String): Context {

        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)

        return context.createConfigurationContext(config)
    }

    private fun persist(context: Context, lang: String) {
        val sp = context.getSharedPreferences(SELECTED_LANGUAGE,  Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(SELECTED_LANGUAGE,lang)
        editor.apply()
    }

    fun getPersistedData(context: Context,language:String):String{
        val sp = context.getSharedPreferences(SELECTED_LANGUAGE,  Context.MODE_PRIVATE)
        return sp.getString(SELECTED_LANGUAGE,language)!!

    }
}