package com.qingmei2.samplepaging.viewmodel

import android.app.Application
import android.nfc.tech.MifareUltralight
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.roomdemo.dao.DbDataHelper
import com.example.roomdemo.entity.Book
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CommonViewModel(app: Application) : AndroidViewModel(app) {



    fun getRefreshLiveData(): LiveData<PagedList<Book>> =
            LivePagedListBuilder(DbDataHelper.getInstance(getApplication()).dbDataBase.bookDao().getAllBook(), PagedList.Config.Builder()
                    .setPageSize(PAGE_SIZE)                         //配置分页加载的数量
                    .setEnablePlaceholders(ENABLE_PLACEHOLDERS)     //配置是否启动PlaceHolders
                    .setInitialLoadSizeHint(PAGE_SIZE)              //初始化加载的数量
                    .build()).build()

    companion object {

        private const val PAGE_SIZE = 5

        private const val ENABLE_PLACEHOLDERS = false
    }


    fun deleteBook(book:Book){
        Observable.just(0)
            .subscribeOn(Schedulers.io())
            .doOnNext {
                DbDataHelper.getInstance(getApplication()).dbDataBase.bookDao().delete(book)
            }
            .subscribe()
    }
}