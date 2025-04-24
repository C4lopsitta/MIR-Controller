package dev.robaldo.mir

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

internal object PREFERENCES {
    val ADDRESS = stringPreferencesKey("MIR_ADDRESS")
    val USERNAME = stringPreferencesKey("MIR_USERNAME")
    val PASSWORD = stringPreferencesKey("MIR_PASSWORD")
}

object AppPreferences {
    lateinit var prefs: DataStore<Preferences>;

    suspend fun getAddress(): String {
        return prefs.data.map {
            it[PREFERENCES.ADDRESS] ?: ""
        }.flowOn(Dispatchers.IO).first()
    }

    suspend fun setAddress(address: String) {
        withContext(Dispatchers.IO) {
            try {
                prefs.edit {
                    it[PREFERENCES.ADDRESS] = address
                }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                e.printStackTrace()
            }
        }
    }

    suspend fun getUsername(): String {
        return prefs.data.map {
            it[PREFERENCES.USERNAME] ?: ""
        }.flowOn(Dispatchers.IO).first()
    }

    suspend fun setUsername(username: String) {
        withContext(Dispatchers.IO) {
            try {
                prefs.edit {
                    it[PREFERENCES.USERNAME] = username
                }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                e.printStackTrace()
            }
        }
    }

    suspend fun getPassword(): String {
        return prefs.data.map {
            it[PREFERENCES.PASSWORD] ?: ""
        }.flowOn(Dispatchers.IO).first()
    }

    suspend fun setPassword(password: String) {
        withContext(Dispatchers.IO) {
            try {
                prefs.edit {
                    it[PREFERENCES.PASSWORD] = password
                }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                e.printStackTrace()
            }
        }
    }
}
