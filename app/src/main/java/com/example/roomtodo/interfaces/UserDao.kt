package com.example.roomtodo.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.roomtodo.models.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAllUser(): List<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT EXISTS (SELECT * FROM user WHERE userName = :userName and password = :pass)")
    suspend fun getUser(userName: String, pass: String): Boolean

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOneUser(user: User)

    @Insert
    suspend fun insertAll(vararg users: User)

    @Delete
    suspend fun deleteUser(user: User)
}
