package dev.logickoder.qrpay.app.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.logickoder.qrpay.app.data.local.QrPayDatabase
import dev.logickoder.qrpay.R
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun appDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            QrPayDatabase::class.java,
            context.getString(R.string.app_name)
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun transactionsDao(database: QrPayDatabase) = database.transactionsDao()
}
