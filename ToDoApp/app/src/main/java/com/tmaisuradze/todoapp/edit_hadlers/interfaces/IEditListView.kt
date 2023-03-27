package com.tmaisuradze.todoapp.edit_hadlers.interfaces

import com.tmaisuradze.todoapp.entity.Item
import com.tmaisuradze.todoapp.entity.ToDoList

interface IEditListView {
    fun showList(toDoList: ToDoList)
    abstract fun showCheckedItems(checkedItems: MutableList<Item>)
    abstract fun showUncheckedItems(uncheckedItems: MutableList<Item>)
    fun exit()
}