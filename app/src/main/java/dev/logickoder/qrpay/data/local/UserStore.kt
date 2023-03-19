package dev.logickoder.qrpay.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.logickoder.qrpay.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject


/**
 * Stores the user info on the device
 */
class UserStore @Inject constructor(@ApplicationContext private val context: Context) {
    /**
     * save the user into the local storage
     */
    suspend fun save(user: User) {
        context.userStore.edit { preferences ->
            preferences[USER] = Json.encodeToString(user)
        }
    }

    /**
     * retrieves the user saved on the device or null if no user is present
     */
    fun get(): Flow<User?> = context.userStore.data.map { preferences ->
        preferences[USER]?.let {
            Json.decodeFromString(it)
        }
    }

    /**
     * Delete the user from the local store
     */
    suspend fun clear() {
        context.userStore.edit { preferences -> preferences.clear() }
    }

    companion object {
        private val USER = stringPreferencesKey("user")

        private val Context.userStore: DataStore<Preferences> by preferencesDataStore(
            name = "user"
        )
    }
}
