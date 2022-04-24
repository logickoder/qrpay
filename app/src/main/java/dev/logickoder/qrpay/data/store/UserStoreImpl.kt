package dev.logickoder.qrpay.data.store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import dev.logickoder.qrpay.data.model.User
import dev.logickoder.qrpay.di.userStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val USER = stringPreferencesKey("user")

/**
 * Stores the user info on the device
 */
class UserStoreImpl @Inject constructor(val context: Context) : DataStoreManager<User> {
    override suspend fun save(data: User) {
        context.userStore.edit { preferences ->
            preferences[USER] = Gson().toJson(data)
        }
    }

    override fun get(): Flow<User?> {
        return context.userStore.data.map { preferences ->
            Gson().fromJson(preferences[USER], User::class.java)
        }
    }
}
