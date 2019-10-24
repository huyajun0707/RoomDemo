package com.example.roomdemo.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdemo.entity.Book
import com.example.roomdemo.listener.OnItemButtonClickListener
import com.qingmei2.samplepaging.ui.viewholder.BookViewHolder

/**
 * @author      ： HuYajun <huyajun0707@gmail.com>
 * @version     ： 1.0
 * @date        ： 2019-10-22 15:16
 * @depiction   ：
 */
class BookPagingAdapter : PagedListAdapter<Book, RecyclerView.ViewHolder>(diffCallback) {

    var onItemButtonClickListener:OnItemButtonClickListener?=null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BookViewHolder(parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as BookViewHolder
        holder.bindTo(getBookItem(position),onItemButtonClickListener!!,position)

    }

    private fun getBookItem(position: Int): Book? {
        return getItem(position)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean =
                oldItem == newItem
        }

    }
}