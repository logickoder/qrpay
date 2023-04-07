package dev.logickoder.qrpay

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dev.logickoder.qrpay.app.data.sync.SyncLauncher
import javax.inject.Inject

@HiltAndroidApp
class QrPayApp : Application() {

    @Inject
    lateinit var syncLauncher: SyncLauncher

    override fun onCreate() {
        super.onCreate()
        syncLauncher.launch()
    }
}
