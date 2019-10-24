package com.example.roomdemo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomdemo.entity.Book
import com.example.roomdemo.entity.BookType
import io.reactivex.Flowable

/**
 * @author      ： HuYajun <huyajun0707@gmail.com>
 * @version     ： 1.0
 * @date        ： 2019-10-20 20:32
 * @depiction   ：
 */

@Dao
interface BookTypeDao {
    @Insert
    fun insertAll(items:Array<BookType>)

    @Update
    fun update(item: BookType)

    @Delete
    fun delete(item: BookType)

    @Query("select * from tb_book_type")
    fun getAll(): Flowable<Array<BookType>>

    @Query("select name from tb_book_type")
    fun getAllBookTypeNames():Flowable<Array<String>>
}