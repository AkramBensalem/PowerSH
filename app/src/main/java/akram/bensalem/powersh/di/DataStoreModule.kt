package akram.bensalem.powersh.di

import akram.bensalem.powersh.repository.DataStoreRepository
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun providesDataStore(
        @ApplicationContext context: Context
    ) = DataStoreRepository(context = context)
}