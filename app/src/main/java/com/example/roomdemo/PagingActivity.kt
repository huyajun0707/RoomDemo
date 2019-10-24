package com.example.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.roomdemo.adapter.BookPagingAdapter
import com.example.roomdemo.entity.Book
import com.example.roomdemo.listener.OnItemButtonClickListener
import com.qingmei2.samplepaging.viewmodel.CommonViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_paging.*
import java.util.concurrent.TimeUnit

class PagingActivity : AppCompatActivity() {

    private lateinit var mAdapter: BookPagingAdapter

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(CommonViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging)
        mAdapter = BookPagingAdapter()
        recyclerView.adapter = mAdapter

        binds()
        mAdapter.onItemButtonClickListener = object :OnItemButtonClickListener{
            override fun onItemButtonClickListener(view: View, any: Any, position: Int) {

               viewModel.deleteBook(any as Book)
            }
        }



    }

    private fun binds() {
        // 模拟下拉刷新
        mSwipeRefreshLayout.setOnRefreshListener {
            mSwipeRefreshLayout.isRefreshing = true

            Observable.just(0)
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    mSwipeRefreshLayout.isRefreshing = false
                    mAdapter.submitList(null)
                    viewModel.getRefreshLiveData()
                        .observe(this, Observer { mAdapter.submitList(it) })
                }
                .subscribe()
        }
    }
}
