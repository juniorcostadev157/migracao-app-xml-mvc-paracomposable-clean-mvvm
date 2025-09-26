package com.junior.migracaosevenmvvmcompose.data.datasource.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.junior.migracaosevenmvvmcompose.core.contants.Constants
import com.junior.migracaosevenmvvmcompose.data.model.UserSession
import com.junior.migracaosevenmvvmcompose.domain.repository.UsersLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore by preferencesDataStore(Constants.DataStore.PREFERENCES_NAME)

@Singleton
class UsersPreference @Inject constructor( context: Context): UsersLocalDataSource{

    private val dataStore = context.dataStore
    companion object {
       val USER_SESSION = stringPreferencesKey("user_session")
    }

    private val gson = Gson()


    override suspend fun saveUserSession(session: UserSession) {
        dataStore.edit { preferences ->
           preferences[USER_SESSION] = gson.toJson(session)
       }
    }

    override fun getUserSession(): Flow<UserSession?> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences())
                else throw exception
            }
            .map { preferences ->
                preferences[USER_SESSION]?.let { gson.fromJson(it, UserSession::class.java) }
            }
    }

    override suspend fun clear(){
        dataStore.edit { it.clear() }
    }




}