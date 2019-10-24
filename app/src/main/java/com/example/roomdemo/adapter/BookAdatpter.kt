package com.example.roomdemo.adapter

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.roomdemo.R
import com.example.roomdemo.entity.Book
import com.example.roomdemo.listener.OnItemButtonClickListener
import kotlinx.android.synthetic.main.item_book.view.*

/**
 * @author      ： HuYajun <huyajun0707@gmail.com>
 * @version     ： 1.0
 * @date        ： 2019-10-20 15:46
 * @depiction   ：
 */
class BookAdatpter(layoutResId: Int, data: List<Book>?) :
    BaseQuickAdapter<Book, BaseViewHolder>(layoutResId, data) {

     var onItemButtonClickListener: OnItemButtonClickListener? = null



    override fun convert(holder: BaseViewHolder, item: Book) {
        val adapterPosition = holder.adapterPosition
        holder.convertView.findViewById<TextView>(R.id.tvName).text = data[adapterPosition].name
        holder.convertView.findViewById<ImageView>(R.id.ivDelete)
            .setOnClickListener(View.OnClickListener {
                onItemButtonClickListener!!.onItemButtonClickListener(it,data[adapterPosition],adapterPosition)
            })

    }
}