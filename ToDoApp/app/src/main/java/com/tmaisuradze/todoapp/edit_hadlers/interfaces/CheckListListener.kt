package com.tmaisuradze.todoapp.edit_hadlers.interfaces

import com.tmaisuradze.todoapp.entity.Item

interface CheckListListener {
    fun onCheckBoxClicked(item: Item, pos: Int)
    abstract fun onRemoveButtonClicked(item: Item, pos: Int)
}