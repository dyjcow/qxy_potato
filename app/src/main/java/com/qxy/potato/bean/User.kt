package com.qxy.potato.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author     ：Dyj
 * @date       ：Created in 2022/8/2 10:58
 * @description：This is a data class
 * @modified By：
 * @version:     1.0
 */
@Entity()
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int?,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?
)
