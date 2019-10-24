package com.example.roomdemo.viewmodel

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.roomdemo.dao.DbDataHelper
import com.example.roomdemo.entity.Book
import com.example.roomdemo.repository.DataRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.function.Consumer

/**
 * @author      ： HuYajun <huyajun0707@gmail.com>
 * @version     ： 1.0
 * @date        ： 2019-10-22 10:40
 * @depiction   ：
 */

public class BookViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var dataRepository: DataRepository


    init {
        dataRepository = DataRepository(getApplication())


    }

    fun getAllBooks(): LiveData<Array<Book>> {
        return dataRepository.loadPageData()!!
    }



    fun deleteBook(book: Book) {
        dataRepository.deleteBook(book)
    }


}
