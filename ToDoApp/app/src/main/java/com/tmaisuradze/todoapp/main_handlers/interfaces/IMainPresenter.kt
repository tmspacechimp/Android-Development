package com.tmaisuradze.todoapp.main_handlers.interfaces

import com.tmaisuradze.todoapp.entity.Item
import com.tmaisuradze.todoapp.entity.ToDoList

interface IMainPresenter {
    abstract fun onListsFetched(result: List<ToDoList>)
    abstract fun onItemListFetched(items: List<List<Item>>)
}