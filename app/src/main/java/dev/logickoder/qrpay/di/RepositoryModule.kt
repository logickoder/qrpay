package dev.logickoder.qrpay.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.logickoder.qrpay.data.repository.TransactionsRepo
import dev.logickoder.qrpay.data.repository.TransactionsRepoImpl
import dev.logickoder.qrpay.data.repository.UserRepo
import dev.logickoder.qrpay.data.repository.UserRepoImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun transactionsRepo(repo: TransactionsRepoImpl): TransactionsRepo

    @Singleton
    @Binds
    abstract fun userRepo(repo: UserRepoImpl): UserRepo
}
