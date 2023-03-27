package com.tmaisuradze.todoapp.main_handlers

import android.os.AsyncTask
import com.tmaisuradze.todoapp.db.ItemsDatabase
import com.tmaisuradze.todoapp.db.ToDoListsDatabase
import com.tmaisuradze.todoapp.entity.Item
import com.tmaisuradze.todoapp.entity.ToDoList
import com.tmaisuradze.todoapp.main_handlers.interfaces.IMainPresenter

class MainInteractor(private val presenter: IMainPresenter) {
    fun getListsFromDatabase(filterString: String) {
        GetListsTask(presenter, filterString).execute()
    }

    fun getItemListFromDatabase(toDoLists: List<ToDoList>) {
        GetItemsTask(presenter, toDoLists).execute()
    }

    class GetListsTask(private val presenter: IMainPresenter, private val filterString: String) : AsyncTask<Void, Void, List<ToDoList>>() {
        override fun doInBackground(vararg params: Void?): List<ToDoList> {
            val listsDao = ToDoListsDatabase.getInstance().listsDao()
            val list = listsDao.getLists(filterString)
            return list
        }

        override fun onPostExecute(result: List<ToDoList>?) {
            super.onPostExecute(result)
            if (result != null) {
                presenter.onListsFetched(result)
            }
        }

    }

    class GetItemsTask(val presenter: IMainPresenter, val toDoLists: List<ToDoList>) :
        AsyncTask<Void, Void, List<List<Item>>>() {
        override fun doInBackground(vararg params: Void?): List<List<Item>> {
            val itemsDao = ItemsDatabase.getInstance().itemsDao()
            val items = mutableListOf<List<Item>>()
            for (list in toDoLists) {
                val list = itemsDao.getListItems(list.id)
                items.add(list)
            }
            return items
        }

        override fun onPostExecute(result: List<List<Item>>?) {
            super.onPostExecute(result)
            if (result != null) {
                presenter.onItemListFetched(result)
            }
        }

    }
}