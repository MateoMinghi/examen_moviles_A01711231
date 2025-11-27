package com.app.examen_tc2007b.data.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CovidPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences("covid_prefs", Context.MODE_PRIVATE)

    fun saveLastCountry(country: String) {
        prefs.edit().putString("last_country", country).apply()
    }

    fun getLastCountry(): String? {
        return prefs.getString("last_country", null)
    }

    fun saveLastJson(json: String) {
        prefs.edit().putString("last_json", json).apply()
    }

    fun getLastJson(): String? {
        return prefs.getString("last_json", null)
    }

    fun getMockJson(): String {
        return try {
            context.assets.open("mock_covid_data.json").bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            "[]"
        }
    }
}
