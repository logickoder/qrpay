package dev.logickoder.qrpay.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.logickoder.qrpay.data.sync.SyncLauncher
import dev.logickoder.qrpay.data.sync.SyncLauncherImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun syncLauncher(launcher: SyncLauncherImpl): SyncLauncher
}
