package com.tmaisuradze.todoapp.edit_hadlers

import android.os.AsyncTask
import com.tmaisuradze.todoapp.db.ItemsDatabase
import com.tmaisuradze.todoapp.db.ToDoListsDatabase
import com.tmaisuradze.todoapp.edit_hadlers.interfaces.IEditListPresenter
import com.tmaisuradze.todoapp.entity.Item
import com.tmaisuradze.todoapp.entity.ToDoList

class EditListInteractor(private val presenter: IEditListPresenter) {

    fun saveInfoToDatabase(listId: Int, toDoList: ToDoList, items: List<Item>) {
        SaveInfoTask(listId, toDoList, items, presenter).execute()
    }

    fun getListFromDatabase(id: Int) {
        GetListTask(presenter, id).execute()
    }

    fun getItemsFromDatabase(id: Int) {
        GetListItemsTask(presenter, id).execute()
    }

    class GetListItemsTask(val presenter: IEditListPresenter, val id: Int) :
        AsyncTask<Void, Void, List<Item>>() {
        override fun doInBackground(vararg params: Void?): List<Item> {
            val itemsDao = ItemsDatabase.getInstance().itemsDao()
            val items = itemsDao.getListItems(id)
            return items
        }

        override fun onPostExecute(result: List<Item>?) {
            super.onPostExecute(result)
            if (result != null) {
                presenter.onListItemsFetched(result)
            }
        }

    }

    class GetListTask(val presenter: IEditListPresenter, val id: Int) :
        AsyncTask<Void, Void, ToDoList>() {

        override fun doInBackground(vararg params: Void?): ToDoList {
            val listsDao = ToDoListsDatabase.getInstance().listsDao()
            val list = listsDao.getList(id)
            return list
        }

        override fun onPostExecute(result: ToDoList?) {
            super.onPostExecute(result)
            if (result != null) {
                presenter.onListFetched(result)
            }
        }

    }


    class SaveInfoTask(
        val listId: Int,
        val toDoList: ToDoList,
        val items: List<Item>,
        val presenter: IEditListPresenter) : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            val listsDao = ToDoListsDatabase.getInstance().listsDao()
            val itemsDao = ItemsDatabase.getInstance().itemsDao()
            if (listId != -1) {
                listsDao.removeList(listId)
            }
            val listId = listsDao.updateList(toDoList).toInt()
            itemsDao.removeItems(listId)
            for (item in items) {
                val newItem = Item(item.content, listId, item.checked)
                itemsDao.updateItem(newItem)
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            presenter.onInfoSaved()
        }

    }
}