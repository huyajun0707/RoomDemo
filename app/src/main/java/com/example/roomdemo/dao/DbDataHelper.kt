package com.example.roomdemo.dao

import android.content.Context
import androidx.room.Room

/**
 * @author      ： HuYajun <huyajun0707@gmail.com>
 * @version     ： 1.0
 * @date        ： 2019-10-20 20:37
 * @depiction   ：
 */

class DbDataHelper constructor(context: Context){
    val dbDataBase = Room.databaseBuilder(context, BookDataBase::class.java,
        "database_book")
//        .allowMainThreadQueries()
        .build()!!

    companion object {
        @Volatile
        var INSTANCE: DbDataHelper? = null

        fun getInstance(context: Context): DbDataHelper {
            if (INSTANCE == null) {
                synchronized(DbDataHelper::class) {
                    if (INSTANCE == null) {
                        INSTANCE = DbDataHelper(context.applicationContext)
                    }
                }
            }
            return INSTANCE!!
        }
    }


}