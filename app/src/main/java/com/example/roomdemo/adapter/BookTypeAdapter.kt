package com.example.roomdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

import com.example.roomdemo.R
import com.example.roomdemo.entity.BookType
import com.example.roomdemo.listener.OnItemButtonClickListener

/**
 * @author ： HuYajun <huyajun0707></huyajun0707>@gmail.com>
 * @version ： 1.0
 * @date ： 2019-10-23 10:40
 * @depiction ：
 */
class BookTypeAdapter(private val mContext: Context, private val mList: MutableList<BookType>) :
    BaseAdapter() {

    private var onItemButtonClickListener: OnItemButtonClickListener? = null

    fun setOnItemButtonClickListener(onItemButtonClickListener: OnItemButtonClickListener) {
        this.onItemButtonClickListener = onItemButtonClickListener
    }

    override fun getCount(): Int {
        return mList.size
    }

    override fun getItem(position: Int): Any {
        return mList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        val _LayoutInflater = LayoutInflater.from(mContext)
        convertView = _LayoutInflater.inflate(R.layout.item_spinner, null)
        if (convertView != null) {
            val tvTypeName = convertView.findViewById<TextView>(R.id.tvTypeName)
            val ivDelete = convertView.findViewById<ImageView>(R.id.ivDelete)
            tvTypeName.text = mList[position].name
            ivDelete.setOnClickListener {
                if (onItemButtonClickListener != null)
                    onItemButtonClickListener!!.onItemButtonClickListener(
                        ivDelete,
                        mList[position],
                        position
                    )
            }
        }
        return convertView
    }
}