package com.tmaisuradze.todoapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tmaisuradze.todoapp.dao.ToDoListsDao
import com.tmaisuradze.todoapp.entity.ToDoList

@Database(entities = [ToDoList::class], version = 1)
abstract class ToDoListsDatabase(): RoomDatabase() {
    abstract fun listsDao(): ToDoListsDao

    companion object{
        private const val dbName = "todo-list-db"
        private lateinit var INSTANCE: ToDoListsDatabase

        fun getInstance(): ToDoListsDatabase{
            return INSTANCE
        }

        fun createDatabase(context: Context){
            INSTANCE = Room.databaseBuilder(context, ToDoListsDatabase::class.java, dbName).build()
        }
    }
}