package com.tmaisuradze.todoapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDoList(
    @ColumnInfo val name: String,
    @ColumnInfo val modificationDate: Long,
    @ColumnInfo val pinned: Boolean
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}