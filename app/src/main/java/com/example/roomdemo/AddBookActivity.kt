package com.example.roomdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.example.roomdemo.dao.DbDataHelper
import android.widget.Toast
import com.example.roomdemo.adapter.BookTypeAdapter
import com.example.roomdemo.entity.Book
import com.example.roomdemo.entity.BookType
import com.example.roomdemo.listener.OnItemButtonClickListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_book.*
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import kotlinx.android.synthetic.main.activity_add_book.btAddBook as btAddBook1


class AddBookActivity : AppCompatActivity(), View.OnClickListener {
    private var bookTypes: MutableList<BookType>? = arrayListOf()
    private var adapter: BookTypeAdapter? = null
    private var typeId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)
        initView()
        initEvent()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btAddBook ->{

            }

        }


    }

    private fun initEvent() {
        btAddBook1.setOnClickListener(View.OnClickListener {
            if (TextUtils.isEmpty(etName.text.toString().trim())) {
                Toast.makeText(this@AddBookActivity, "请填写书刊名!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(etPrice.text.toString().trim())) {
                Toast.makeText(this@AddBookActivity, "请填写价格!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (typeId == 0)
                return@OnClickListener
            val book: Book = Book()
            book.name = etName.text.toString()
            book.price = etPrice.text.toString().toFloat()
            book.type_id = typeId
            insertBook(book)
        })
        btAddBookType.setOnClickListener(View.OnClickListener {
            if (TextUtils.isEmpty(etBookType.text.toString().trim())) {
                Toast.makeText(this@AddBookActivity, "请填写书刊类型!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            val bookType: BookType = BookType()
            bookType.name = etBookType.text.toString()
            insetBookType(bookType)
            getBookTypes()

        })



        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                typeId = bookTypes!!.get(position).id

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        btInsert.setOnClickListener(View.OnClickListener {

            inserTestData()
        })
       btGoLiveData.setOnClickListener(View.OnClickListener {
           startActivity(Intent(this@AddBookActivity,ViewModelActivity::class.java))
       })
        btGoPaging.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@AddBookActivity,PagingActivity::class.java))
        })
    }

    fun initView() {
        getBookTypes()
    }


    fun insertBook(book: Book) {
        Observable.just(0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                DbDataHelper.getInstance(this).dbDataBase.bookDao().insertAll(mutableListOf(book))
            }
            .subscribe()

    }


    fun insetBookType(bookType: BookType) {
        Observable.just(0)
            .subscribeOn(Schedulers.io())
            .doOnNext {
                DbDataHelper.getInstance(this).dbDataBase.bookTypeDao().insertAll(arrayOf(bookType))
            }.subscribe()
    }


    fun deleteBookName(bookType: BookType) {
        Observable.just(0)
            .subscribeOn(Schedulers.io())
            .doOnNext {
                DbDataHelper.getInstance(this@AddBookActivity).dbDataBase.bookTypeDao()
                    .delete(bookType)
            }.subscribe(Consumer {
                getBookTypes()
            })

    }


    fun getBookTypes() {

        DbDataHelper.getInstance(this@AddBookActivity).dbDataBase.bookTypeDao().getAll()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer<Array<BookType>> {
                println("---->onNext" + it.size)
                if (bookTypes!!.size == 0) {
                    bookTypes = it!!.toMutableList()
                    adapter = BookTypeAdapter(this, bookTypes!!)
                    spinner.adapter = adapter
                    adapter!!.setOnItemButtonClickListener(object : OnItemButtonClickListener {
                        override fun onItemButtonClickListener(
                            view: View,
                            any: Any,
                            position: Int
                        ) {
                            println("--->delete")
                            deleteBookName(any as BookType)

                        }
                    })
                } else {
                    println("--->notifyDataSetChanged")
                    bookTypes = it!!.toMutableList()
                    adapter!!.notifyDataSetChanged()
                }


            })
    }

    fun inserTestData() {
        var bookTypesIn: Array<BookType>? = null
        var bookType1 = BookType()
        bookType1.name = "杂志"
        var bookType2 = BookType()
        bookType2.name = "散文"
        var bookType3 = BookType()
        bookType3.name = "小说"
        var bookType4 = BookType()
        bookType4.name = "历史"
        var bookType5 = BookType()
        bookType5.name = "新闻"
        bookTypesIn = arrayOf(bookType1, bookType2, bookType3, bookType4, bookType5)

        var booList: MutableList<Book> = mutableListOf()
        for (i in 0..bookTypesIn!!.size - 1) {

            for (j in 0..9) {
                var book: Book = Book()
                book.name = bookTypesIn!![i]!!.name + j
                book.price = i * 10f + j
                book.type_id = i + 1
                print("--->type_id${book.type_id}")
                booList.add(book)
            }
        }
        println(booList)
        Observable.just(0)
            .subscribeOn(Schedulers.io())
            .doOnNext {
                DbDataHelper.getInstance(this).dbDataBase.bookTypeDao().insertAll(bookTypesIn!!)
            }.observeOn(Schedulers.computation())
            .subscribe(Consumer {
                DbDataHelper.getInstance(this).dbDataBase.bookDao()
                    .insertAll(booList)
            })
    }

}
