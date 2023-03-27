package com.tmaisuradze.todoapp.main_handlers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tmaisuradze.todoapp.R
import com.tmaisuradze.todoapp.entity.Item
import com.tmaisuradze.todoapp.entity.ToDoList
import com.tmaisuradze.todoapp.main_handlers.interfaces.ListListener

class ListAdapter(
    val listListener: ListListener,
    var toDoLists: List<ToDoList>,
    var items: List<List<Item>>
) :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.todo_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListAdapter.ListViewHolder, position: Int) {
        val list = toDoLists[position]
        val listItems = items[position]
        holder.bindList(list, listItems)
        holder.itemView.setOnClickListener {
            listListener.onListItemClicked(list)
        }
    }

    fun updateData(toDoLists: List<ToDoList>, items: List<List<Item>>) {
        this.toDoLists = toDoLists
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return toDoLists.size
    }

    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindList(toDoList: ToDoList, listItems: List<Item>) {
            header.text = toDoList.name

            for (i in 0..2) {
                items[i].visibility = View.GONE
                if (i >= listItems.size)
                    continue
                val item = listItems[i]
                items[i].isChecked = item.checked
                items[i].text = item.content
                items[i].visibility = View.VISIBLE
            }

            if (listItems.size > 3) {
                more.visibility = View.VISIBLE
                itemCount.visibility = View.VISIBLE
                val str = "+ ${listItems.size} checked items"
                itemCount.text = str
            } else {
                more.visibility = View.GONE
                itemCount.visibility = View.GONE
            }
        }

        private val header = view.findViewById<TextView>(R.id.todo_item_title)
        private val items = listOf<CheckBox>(
            view.findViewById(R.id.first_item),
            view.findViewById(R.id.second_item), view.findViewById(R.id.third_item)
        )
        private val more = view.findViewById<TextView>(R.id.todo_item_unchecked_extras)
        private val itemCount = view.findViewById<TextView>(R.id.todo_item_checked_count)
    }

}