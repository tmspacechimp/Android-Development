package com.tmaisuradze.todoapp.main_handlers

import com.tmaisuradze.todoapp.entity.Item
import com.tmaisuradze.todoapp.entity.ToDoList
import com.tmaisuradze.todoapp.main_handlers.interfaces.IMainPresenter
import com.tmaisuradze.todoapp.main_handlers.interfaces.IMainView


class MainPresenter(var view: IMainView?) : IMainPresenter {
    private val interactor = MainInteractor(this)
    private lateinit var toDoLists: List<ToDoList>

    fun getLists(filterString: String) {
        interactor.getListsFromDatabase(filterString)
    }

    override fun onListsFetched(toDoLists: List<ToDoList>) {
        this.toDoLists = toDoLists
        interactor.getItemListFromDatabase(toDoLists)
    }

    override fun onItemListFetched(items: List<List<Item>>) {
        val pinnedLists = mutableListOf<ToDoList>()
        val pinnedItems = mutableListOf<List<Item>>()
        val unpinnedLists = mutableListOf<ToDoList>()
        val unpinnedItems = mutableListOf<List<Item>>()

        for ((index, list) in toDoLists.withIndex()) {
            val item = items[index]
            if (list.pinned) {
                pinnedLists.add(list)
                pinnedItems.add(item)
            } else {
                unpinnedLists.add(list)
                unpinnedItems.add(item)
            }
        }

        view?.showPinnedLists(pinnedLists, pinnedItems)
        view?.showUnpinnedLists(unpinnedLists, unpinnedItems)
    }


    fun detachView() {
        view = null
    }

}
