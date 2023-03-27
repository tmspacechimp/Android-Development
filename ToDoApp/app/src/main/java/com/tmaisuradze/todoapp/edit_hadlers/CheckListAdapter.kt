package com.tmaisuradze.todoapp.edit_hadlers

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.tmaisuradze.todoapp.R
import com.tmaisuradze.todoapp.edit_hadlers.interfaces.CheckListListener
import com.tmaisuradze.todoapp.entity.Item

class CheckListAdapter(private val listListener: CheckListListener) :
    RecyclerView.Adapter<CheckListAdapter.CheckListViewHolder>() {

    var items = mutableListOf<Item>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckListAdapter.CheckListViewHolder {
        return CheckListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.todo_edit_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CheckListAdapter.CheckListViewHolder, position: Int) {
        val item = items[position]
        holder.bindItem(item)
        holder.checkBox.setOnClickListener {
            listListener.onCheckBoxClicked(item, position)
        }
        holder.contentText.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                val newItem = Item(holder.contentText.text.toString(), item.listId, item.checked)
                items[position] = newItem
                return@OnKeyListener true
            }
            false
        })
        holder.removeButton.setOnClickListener{
            listListener.onRemoveButtonClicked(item, position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class CheckListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(item: Item) {
            checkBox.isChecked = item.checked
            contentText.setText(item.content)
            if (checkBox.isChecked){
                contentText.focusable = View.NOT_FOCUSABLE
                removeButton.visibility = View.GONE
            }else{
                contentText.focusable = View.FOCUSABLE
                removeButton.visibility = View.VISIBLE
            }
        }

        val checkBox: CheckBox = view.findViewById(R.id.todo_edit_checkbox)
        val contentText: EditText = view.findViewById(R.id.todo_edit_text)
        val removeButton: ImageView = view.findViewById(R.id.remove_todo_item)

    }


}