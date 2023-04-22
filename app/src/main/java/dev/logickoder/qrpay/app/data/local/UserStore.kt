package dev.logickoder.qrpay.app.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.logickoder.qrpay.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Stores the user info on the device
 */
@Singleton
class UserStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    /**
     * save the user into the local storage
     */
    suspend fun saveUser(user: User) {
        context.userStore.edit { preferences ->
            preferences[USER] = Json.encodeToString(user)
        }
    }

    /**
     * retrieves the user saved on the device or null if no user is present
     */
    fun getUser(): Flow<User?> = context.userStore.data.map { preferences ->
        preferences[USER]?.let {
            Json.decodeFromString(it)
        }
    }

    /**
     * saves the bearer token to the device
     */
    suspend fun saveToken(token: String) {
        context.userStore.edit { preferences ->
            preferences[TOKEN] = token
        }
    }

    /**
     * retrieves the token saved on the device or null if no token is present
     */
    fun getToken(): Flow<String?> = context.userStore.data.map { it[TOKEN] }

    /**
     * Delete the user from the local store
     */
    suspend fun clear() {
        context.userStore.edit { preferences -> preferences.clear() }
    }

    companion object {
        private val USER = stringPreferencesKey("user")
        private val TOKEN = stringPreferencesKey("token")

        private val Context.userStore: DataStore<Preferences> by preferencesDataStore(
            name = "user"
        )
    }
}
