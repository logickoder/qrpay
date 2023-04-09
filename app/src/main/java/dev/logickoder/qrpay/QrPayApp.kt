package dev.logickoder.qrpay

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dev.logickoder.qrpay.app.data.sync.SyncLauncher
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import javax.inject.Inject


@HiltAndroidApp
class QrPayApp : Application() {

    @Inject
    lateinit var syncLauncher: SyncLauncher

    override fun onCreate() {
        super.onCreate()
        syncLauncher.launch()
        Napier.base(DebugAntilog())
    }
}
