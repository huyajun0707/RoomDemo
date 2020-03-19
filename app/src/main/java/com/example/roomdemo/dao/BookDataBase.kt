package com.example.roomdemo.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomdemo.entity.Book
import com.example.roomdemo.entity.BookType

/**
 * @author      ： HuYajun <huyajun0707@gmail.com>
 * @version     ： 1.0
 * @date        ： 2019-10-20 20:34
 * @depiction   ：
 */
@Database(entities = arrayOf(Book::class,BookType::class),version = 2)
abstract class BookDataBase : RoomDatabase() {

    abstract fun bookDao(): BookDao
    abstract fun bookTypeDao(): BookTypeDao
}