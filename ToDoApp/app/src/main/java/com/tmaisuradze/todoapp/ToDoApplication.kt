package com.tmaisuradze.todoapp

import android.app.Application
import com.tmaisuradze.todoapp.db.ItemsDatabase
import com.tmaisuradze.todoapp.db.ToDoListsDatabase

class ToDoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        this.deleteDatabase("todo-list-db")
        this.deleteDatabase("items-db")
        ItemsDatabase.createDatabase(this)
        ToDoListsDatabase.createDatabase(this)
    }
}