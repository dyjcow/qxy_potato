package com.qxy.potato.annotation

import androidx.room.*
import androidx.room.Dao
import com.qxy.potato.bean.User

/**
 * @author     ：Dyj
 * @date       ：Created in 2022/8/2 11:10
 * @description：This is interface of Dao
 * @modified By：
 * @version:     1.0
 */
@Dao
interface Dao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)
}