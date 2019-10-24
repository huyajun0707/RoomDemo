package com.example.roomdemo.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.roomdemo.dao.DbDataHelper
import com.example.roomdemo.entity.Book
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * @author      ： HuYajun <huyajun0707@gmail.com>
 * @version     ： 1.0
 * @date        ： 2019-10-22 20:15
 * @depiction   ：
 */

class DataRepository {
    private lateinit var context: Context

    constructor(context: Context) {
        this.context = context

    }


    fun loadPageData(): LiveData<Array<Book>> {

        return DbDataHelper.getInstance(context.applicationContext).dbDataBase.bookDao().getBooksByLiveData()
    }

    fun deleteBook(book: Book) {
        Observable.just(0)
            .subscribeOn(Schedulers.io())
            .doOnNext {
                DbDataHelper.getInstance(context.applicationContext).dbDataBase.bookDao()
                    .delete(book)
            }
            .subscribe()
    }
}