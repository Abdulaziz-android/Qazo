package uz.mnsh.qazo.di.module

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.mnsh.qazo.data.local.AppDatabase
import uz.mnsh.qazo.data.repository.UserRepositoryImpl
import uz.mnsh.qazo.data.repository.PrayerRepositoryImpl
import uz.mnsh.qazo.domain.repository.UserRepository
import uz.mnsh.qazo.domain.repository.PrayerRepository
import uz.mnsh.qazo.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE
        ).allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun providePrayerRepository(db: AppDatabase): UserRepository {
        return UserRepositoryImpl(db.userDao())
    }

    @Provides
    @Singleton
    fun provideStatisticRepository(db: AppDatabase): PrayerRepository {
        return PrayerRepositoryImpl(db.prayerDao())
    }

    @Provides
    @Singleton
    fun provideUserUseCases(userRepository: UserRepository, prayerRepository: PrayerRepository): UserUseCases {
        return UserUseCases(
            addUser = AddUser(userRepository),
            getUser = GetUser(userRepository),
            addPrayer = AddPrayer(prayerRepository),
            getPrayers = GetPrayers(prayerRepository)
        )
    }

}