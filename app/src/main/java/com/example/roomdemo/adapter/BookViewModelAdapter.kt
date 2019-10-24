package com.example.roomdemo.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.roomdemo.R
import com.example.roomdemo.entity.Book
import com.example.roomdemo.listener.OnItemButtonClickListener
import com.example.roomdemo.viewmodel.BookViewModel

import java.util.ArrayList

class BookViewModelAdapter internal constructor(val bookViewModel: BookViewModel?) :
    RecyclerView.Adapter<BookViewModelAdapter.MyViewHolder>() {
    private var allBooks: Array<Book> = arrayOf()

    fun setAllBooks(allBooks: Array<Book>) {
        this.allBooks = allBooks
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView: View
        itemView = layoutInflater.inflate(R.layout.item_book, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val book = allBooks[position]
        holder.tvName.text = book.name
        holder.ivDelete.setOnClickListener {
            bookViewModel?.deleteBook(book)
        }

    }

    override fun getItemCount(): Int {
        return allBooks.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView
        var ivDelete: ImageView

        init {
            tvName = itemView.findViewById(R.id.tvName)
            ivDelete = itemView.findViewById(R.id.ivDelete)
        }
    }
}
