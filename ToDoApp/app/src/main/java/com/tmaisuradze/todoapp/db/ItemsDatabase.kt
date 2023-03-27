package com.tmaisuradze.todoapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tmaisuradze.todoapp.dao.ItemsDao
import com.tmaisuradze.todoapp.entity.Item

@Database(entities = [Item::class], version = 2)
abstract class ItemsDatabase(): RoomDatabase() {
    abstract fun itemsDao(): ItemsDao

    companion object{
        private const val dbName = "todo-item-db"
        private lateinit var INSTANCE: ItemsDatabase

        fun getInstance(): ItemsDatabase{
            return INSTANCE
        }

        fun createDatabase(context: Context){
            INSTANCE = Room.databaseBuilder(context, ItemsDatabase::class.java, dbName).build()
        }
    }
}