package com.calcar.common.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.calcar.common.datastore.GarageInformationPreferences
import com.calcar.common.datastore.UserPreferences
import com.calcar.common.datastore.serializers.GarageInformationPreferencesSerializer
import com.calcar.common.datastore.serializers.UserPreferencesSerializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataStoreModule = module {
    single {
        UserPreferencesSerializer()
    }
    single {
        GarageInformationPreferencesSerializer()
    }
    single<DataStore<UserPreferences>>(named("UserDataStore")) {
        DataStoreFactory.create(
            serializer = get<UserPreferencesSerializer>(),
            scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        ) {
            androidApplication().dataStoreFile("user_preferences.pb")
        }
    }
    single<DataStore<GarageInformationPreferences>>(named("GarageDataStore")) {
        DataStoreFactory.create(
            serializer = get<GarageInformationPreferencesSerializer>(),
            scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        ) {
            androidApplication().dataStoreFile("garage_information_preferences.pb")
        }
    }
}