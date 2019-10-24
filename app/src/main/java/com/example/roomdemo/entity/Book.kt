package com.example.roomdemo.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * @author      ： HuYajun <huyajun0707@gmail.com>
 * @version     ： 1.0
 * @date        ： 2019-10-20 16:49
 * @depiction   ：
 */
@Entity(
    tableName = "tb_book",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = BookType::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("type_id"),
            onDelete = ForeignKey.CASCADE
        )
    )
)
data class Book constructor(
    @PrimaryKey(autoGenerate = true)
    var id: Int
) {
    constructor() : this(0)
    var name: String? = null
    var type_id: Int = 0
    var price: Float = 0f
}