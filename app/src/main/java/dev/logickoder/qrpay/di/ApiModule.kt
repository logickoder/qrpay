package dev.logickoder.qrpay.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.logickoder.qrpay.data.api.QrPayApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun qrPayApi(retrofit: Retrofit.Builder): QrPayApi {
        return retrofit
            .baseUrl(QrPayApi.BASE_URL)
            .build()
            .create(QrPayApi::class.java)
    }
}
