package com.tmaisuradze.todoapp.main_handlers.interfaces

import com.tmaisuradze.todoapp.entity.Item
import com.tmaisuradze.todoapp.entity.ToDoList

interface IMainView {
    abstract fun showPinnedLists(pinnedToDoLists: List<ToDoList>, pinnedItems: List<List<Item>>)
    abstract fun showUnpinnedLists(unpinnedToDoLists: List<ToDoList>, unpinnedItems: List<List<Item>>)
}