package com.tmaisuradze.todoapp.main_handlers.interfaces

import com.tmaisuradze.todoapp.entity.ToDoList

interface ListListener {
    fun onListItemClicked(toDoList: ToDoList)
}