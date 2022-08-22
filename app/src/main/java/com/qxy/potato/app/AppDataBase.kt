package com.qxy.potato.app

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.qxy.potato.annotation.Dao
import com.qxy.potato.bean.User


/**
 * @author     ：Dyj
 * @date       ：Created in 2022/8/2 11:21
 * @description：This is AppDataBse of the Project
 * @modified By：
 * @version:     1.0
 */
@Database(entities = [User::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): Dao
    companion object{

        private var instance:AppDatabase ?= null

        fun getDatabase(context:Context):AppDatabase{
            instance?.let {
                return it
            }
            return Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java,"potato_database")
                .build().apply {
                    instance = this
                }
        }
    }
}
