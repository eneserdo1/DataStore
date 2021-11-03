package com.app.datastore.data

import android.content.Context
import androidx.datastore.preferences.clear
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserManager(context: Context) {

    private val dataStore = context.createDataStore(name = "user_prefs")


    companion object{
        val PROP_USER_NAME = preferencesKey<String>("USER_NAME")
        val PROP_USER_SURNAME = preferencesKey<String>("USER_SURNAME")
        val PROP_USER_AGE = preferencesKey<Int>("USER_AGE")
    }

    suspend fun saveUser(name:String,surname:String,age:Int){
        dataStore.edit {
            it[PROP_USER_NAME] = name
            it[PROP_USER_SURNAME] = surname
            it[PROP_USER_AGE] = age
        }
    }

    suspend fun clearUser(){
        dataStore.edit {
            it.clear()
        }
    }

    val userAgeFlow : Flow<Int> = dataStore.data.map {
        it[PROP_USER_AGE] ?:0
    }

    val userSurnameFlow : Flow<String> = dataStore.data.map {
        it[PROP_USER_SURNAME] ?:""
    }

    val userNameFlow : Flow<String> = dataStore.data.map {
        it[PROP_USER_NAME] ?:""
    }
}