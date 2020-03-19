package com.example.roomdemo.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.roomdemo.entity.Book
import io.reactivex.Flowable

/**
 * @author      ： HuYajun <huyajun0707@gmail.com>
 * @version     ： 1.0
 * @date        ： 2019-10-21 15:47
 * @depiction   ：
 */

@Dao
interface BookDao {
    @Insert
    fun insertAll(items: MutableList<Book>)

    @Update
    fun update(item: Book)

    @Delete
    fun delete(item: Book)

    @Query("select * from tb_book")
    fun getAll(): Array<Book>

    @Query("select * from tb_book limit :page*12 , :page*12+12")
    fun getBooksByPage(page: Int): Flowable<Array<Book>>

    @Query("select count(*) from tb_book")
    fun getTotalCount(): Flowable<Integer>


    @Query("SELECT * FROM tb_book")
    fun getAllBook(): DataSource.Factory<Int, Book>


    @Query("select * from tb_book")
    fun getBooksByLiveData(): LiveData<Array<Book>>

    @Query("select * from tb_book where name = :name")
    fun findBookByName(name: String): Array<Book>


}