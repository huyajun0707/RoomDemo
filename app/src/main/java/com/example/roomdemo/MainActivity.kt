package com.example.roomdemo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.roomdemo.adapter.BookAdatpter
import com.example.roomdemo.dao.DbDataHelper
import com.example.roomdemo.entity.Book
import com.example.roomdemo.listener.OnItemButtonClickListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.android.synthetic.main.activity_paging.*
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.concurrent.TimeUnit
import io.reactivex.Observer as Observer1

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener,
    BaseQuickAdapter.RequestLoadMoreListener, OnItemButtonClickListener {

    private var index: Int = 0
    private var totalCount: Int = 0
    private var totalPage: Int = 0

    private lateinit var bookAdatper: BookAdatpter
    private var books: MutableList<Book> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initEvent()

    }

    private fun initEvent() {
        ivAdd.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, AddBookActivity::class.java))
        })

    }


    private fun initView() {
        val gridLayoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = gridLayoutManager
//        DbDataHelper.getInstance(this).dbDataBase.bookDao().getBooksByPage(index).doOnNext {
//            books = it.toMutableList()
//        }
        bookAdatper = BookAdatpter(R.layout.item_book, books)
        bookAdatper.setOnLoadMoreListener(this, recyclerView)
        recyclerView.adapter = bookAdatper
        swrlayout.setOnRefreshListener(this)
        bookAdatper.onItemButtonClickListener = this
        onRefresh()

    }

    override fun onRefresh() {
        index = 0
        getData(true)
    }


    override fun onLoadMoreRequested() {
        index++
        if (index <= totalPage) {


            getData(false)

        } else {
            bookAdatper.loadMoreEnd()
        }

    }


    private fun getData(isRefresh: Boolean) {
        swrlayout.isRefreshing = false
        bookAdatper.loadMoreComplete()
        bookAdatper.loadMoreEnd()

        DbDataHelper.getInstance(this).dbDataBase.bookDao().getTotalCount()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer<Integer> {
                totalCount = it!!.toInt()
                totalPage = getTotalPage(totalCount)

                DbDataHelper.getInstance(this@MainActivity).dbDataBase.bookDao()
                    .getBooksByPage(index)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(Consumer<Array<Book>>{
                        println("---->" + it!!.size)
                        if (isRefresh) {
                            bookAdatper.setNewData(it.toList())

                        } else {
                            bookAdatper.addData(it.toList())

                        }
                    })
            })


    }

    override fun onItemButtonClickListener(view: View, any: Any, position: Int) {
        var book: Book = any as Book
        Observable.just(0)
            .subscribeOn(Schedulers.io())
            .doOnNext {
                DbDataHelper.getInstance(this).dbDataBase.bookDao().delete(book)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                onRefresh()
            })


    }


    private fun getTotalPage(totalCount: Int): Int {
        if (totalCount % 12 == 0) {
            totalPage = totalCount / 12
        } else {
            totalPage = totalCount / 12 + 1
        }
        return totalPage

    }


}
