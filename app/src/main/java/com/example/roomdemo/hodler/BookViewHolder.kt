package com.qingmei2.samplepaging.ui.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.roomdemo.R
import com.example.roomdemo.entity.Book
import com.example.roomdemo.listener.OnItemButtonClickListener
import java.text.FieldPosition

class BookViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)) {

    private val nameView = itemView.findViewById<TextView>(R.id.tvName)
    private val ivDelete = itemView.findViewById<ImageView>(R.id.ivDelete)
    var book: Book? = null

    /**
     * Items might be null if they are not paged in yet. PagedListAdapter will re-bind the
     * ViewHolder when Item is loaded.
     */
    fun bindTo(book: Book?,onItemButtonClickListener: OnItemButtonClickListener,position:Int) {
        this.book = book
        nameView.text = book?.name
        ivDelete.setOnClickListener(View.OnClickListener {
            onItemButtonClickListener!!.onItemButtonClickListener(ivDelete,book!!,position)
        })
    }
}