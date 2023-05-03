package com.example.roomtodo.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class TodoModel(
    @PrimaryKey(autoGenerate = true) val tid: Int = 0,
    @ColumnInfo(name = "username") val user: String,
    @ColumnInfo(name = "task") val task: String,
    @ColumnInfo(name = "checked") val check: Boolean
)
