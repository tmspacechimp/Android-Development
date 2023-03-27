package com.tmaisuradze.todoapp.edit_hadlers

import com.tmaisuradze.todoapp.edit_hadlers.interfaces.IEditListPresenter
import com.tmaisuradze.todoapp.edit_hadlers.interfaces.IEditListView
import com.tmaisuradze.todoapp.entity.Item
import com.tmaisuradze.todoapp.entity.ToDoList
import java.util.*

class EditListPresenter(val view: IEditListView?): IEditListPresenter {

    private val interactor = EditListInteractor(this)

    override fun saveInfo(
        listId: Int,
        name: String,
        pinned: Boolean,
        checkedItems: MutableList<Item>,
        uncheckedItems: MutableList<Item>
    ) {
        val list = ToDoList(name, Calendar.getInstance().timeInMillis, pinned)
        val items = checkedItems + uncheckedItems
        interactor.saveInfoToDatabase(listId, list, items)
    }

    override fun onInfoSaved() {
        view?.exit()
    }

    override fun getList(id: Int) {
        interactor.getListFromDatabase(id)
    }

    override fun onListFetched(toDoList: ToDoList) {
        view?.showList(toDoList)
        interactor.getItemsFromDatabase(toDoList.id)
    }

    override fun onListItemsFetched(items: List<Item>) {
        val checkedItems = mutableListOf<Item>()
        val uncheckedItems = mutableListOf<Item>()

        for (item in items){
            if(item.checked){
                checkedItems.add(item)
            }else{
                uncheckedItems.add(item)
            }
        }
        view?.showCheckedItems(checkedItems)
        view?.showUncheckedItems(uncheckedItems)
    }

}