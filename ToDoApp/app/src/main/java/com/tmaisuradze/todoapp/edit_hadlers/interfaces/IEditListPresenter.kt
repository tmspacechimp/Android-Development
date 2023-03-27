package com.tmaisuradze.todoapp.edit_hadlers.interfaces

import com.tmaisuradze.todoapp.entity.Item
import com.tmaisuradze.todoapp.entity.ToDoList

interface IEditListPresenter {
    abstract fun saveInfo(listId: Int, name: String, pinned: Boolean, checkedItems: MutableList<Item>, uncheckedItems: MutableList<Item>)
    abstract fun onInfoSaved()
    abstract fun getList(id: Int)
    abstract fun onListFetched(toDoList: ToDoList)
    abstract fun onListItemsFetched(items: List<Item>)
}