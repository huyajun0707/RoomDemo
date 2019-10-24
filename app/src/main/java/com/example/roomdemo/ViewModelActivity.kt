package com.example.roomdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.roomdemo.adapter.BookAdatpter
import com.example.roomdemo.adapter.BookViewModelAdapter
import com.example.roomdemo.dao.DbDataHelper
import com.example.roomdemo.entity.Book
import com.example.roomdemo.listener.OnItemButtonClickListener
import com.example.roomdemo.viewmodel.BookViewModel
import com.qingmei2.samplepaging.viewmodel.CommonViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class ViewModelActivity : AppCompatActivity() {

    private var viewModel: BookViewModel? = null

    private lateinit var bookAdatper: BookViewModelAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_model)
        initView()

    }

    private fun initView() {
        viewModel = ViewModelProviders.of(this).get(BookViewModel::class.java)
        val gridLayoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = gridLayoutManager
        bookAdatper = BookViewModelAdapter(viewModel)
        recyclerView.adapter = bookAdatper

        viewModel!!.getAllBooks().observe(this, object : Observer<Array<Book>> {
            override fun onChanged(t: Array<Book>?) {
                bookAdatper.setAllBooks(t!!)
                bookAdatper.notifyDataSetChanged()
            }
        })


    }




}
