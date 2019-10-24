package com.example.roomdemo.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author      ： HuYajun <huyajun0707@gmail.com>
 * @version     ： 1.0
 * @date        ： 2019-10-20 16:54
 * @depiction   ：
 */

@Entity(tableName = "tb_book_type")
data class BookType(
    @PrimaryKey(autoGenerate = true)
    var id: Int
) {
    constructor() : this(0)

    var name: String? = null

}