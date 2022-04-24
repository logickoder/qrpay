package dev.logickoder.qrpay.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.logickoder.qrpay.data.model.Transaction
import dev.logickoder.qrpay.data.model.User
import dev.logickoder.qrpay.data.store.DataStoreManager
import dev.logickoder.qrpay.data.store.TransactionsStoreImpl
import dev.logickoder.qrpay.data.store.UserStoreImpl
import javax.inject.Singleton

val Context.userStore: DataStore<Preferences> by preferencesDataStore(
    name = "user"
)

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {

    @Singleton
    @Binds
    abstract fun transactionsStore(store: TransactionsStoreImpl): DataStoreManager<List<Transaction>>

    companion object {
        @Singleton
        @Provides
        fun userStore(@ApplicationContext context: Context): DataStoreManager<User> {
            return UserStoreImpl(context)
        }
    }
}
