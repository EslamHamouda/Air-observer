package com.example.airobserver.di

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.IOException
import java.security.GeneralSecurityException
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

private const val TAG = "PREF"
@Module
@InstallIn(SingletonComponent::class)
class SharedPref @Inject constructor() {

    @Provides
    @Named("MasterKey")
    fun provideMasterKey(@ApplicationContext context: Context): MasterKey {
        try {
            return MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
        } catch (ex: IOException) {
            Log.e(TAG, "provideMasterKey: ", ex)
            throw RuntimeException(TAG + "provideMasterKey: $ex")
        } catch (ex: GeneralSecurityException) {
            Log.e(TAG, "provideMasterKey: ", ex)
            throw RuntimeException(TAG + "provideMasterKey: $ex")
        }
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context,
        @Named("MasterKey")
        masterKey: MasterKey
    ): SharedPreferences {
        try {
            return EncryptedSharedPreferences.create(
                context,
                "EncryptedShared",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (ex: IOException) {
            Log.e(TAG, "provideSharedPreferences: ", ex)
            throw RuntimeException(TAG + "provideSharedPreferences: $ex")
        } catch (ex: GeneralSecurityException) {
            Log.e(TAG, "provideSharedPreferences: ", ex)
            throw RuntimeException(TAG + "provideSharedPreferences: $ex")
        }
    }

    companion object {
        const val FIRST_RUN = "FIRST_RUN"
    }
}