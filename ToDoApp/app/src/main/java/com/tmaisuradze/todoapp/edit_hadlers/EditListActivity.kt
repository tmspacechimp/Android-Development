package com.tmaisuradze.todoapp.edit_hadlers

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.tmaisuradze.todoapp.R
import com.tmaisuradze.todoapp.edit_hadlers.interfaces.CheckListListener
import com.tmaisuradze.todoapp.edit_hadlers.interfaces.IEditListView
import com.tmaisuradze.todoapp.entity.Item
import com.tmaisuradze.todoapp.entity.ToDoList

class EditListActivity : AppCompatActivity(), IEditListView, CheckListListener {

    private var isPinned = false
    private lateinit var presenter: EditListPresenter
    private lateinit var rvChecked: RecyclerView
    private lateinit var rvUnchecked: RecyclerView
    private var checkedAdapter = CheckListAdapter(this)
    private var uncheckedAdapter = CheckListAdapter(this)
    private lateinit var listName: EditText
    private var checkedItems = mutableListOf<Item>()
    private var uncheckedItems = mutableListOf<Item>()
    private var listId = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_edit)
        presenter = EditListPresenter(this)
        initView()
        val id = intent.getIntExtra(LIST_ID_EXTRA, 0)
        if (id != -1){
            presenter.getList(id)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun initView() {
        rvChecked = findViewById(R.id.checked_rv)
        rvChecked.adapter = checkedAdapter
        rvUnchecked = findViewById(R.id.unchecked_rv)
        rvUnchecked.adapter = uncheckedAdapter
        listName = findViewById(R.id.editText)
    }

    fun togglePin(view: View) {
        val pinView = view as ImageView

        if (isPinned){
            pinView.setImageResource(R.drawable.ic_pin)
            isPinned = false
        }else{
            pinView.setImageResource(R.drawable.ic_pinned)
            isPinned = true
        }
    }

    override fun onBackPressed() {
        goBack(null)
    }

    fun goBack(view: View?){
        presenter.saveInfo(listId, listName.text.toString(), isPinned, checkedItems, uncheckedItems)
    }

    companion object{
        const val LIST_ID_EXTRA = "listId"
    }

    override fun onCheckBoxClicked(item: Item, pos: Int) {
        if(item.checked){
            checkedItems.removeAt(pos)
            val newItem = Item(item.content, item.listId, false)
            uncheckedItems.add(newItem)

        }else{
            uncheckedItems.removeAt(pos)
            val newItem = Item(item.content, item.listId, true)
            checkedItems.add(newItem)
        }
        checkedAdapter.items = checkedItems
        uncheckedAdapter.items = uncheckedItems
        uncheckedAdapter.notifyDataSetChanged()
        checkedAdapter.notifyDataSetChanged()
    }

    override fun onRemoveButtonClicked(item: Item, pos: Int) {
        if(item.checked){
            checkedItems.removeAt(pos)
        }else{
            uncheckedItems.removeAt(pos)
        }
        checkedAdapter.items = checkedItems
        uncheckedAdapter.items = uncheckedItems
        uncheckedAdapter.notifyDataSetChanged()
        checkedAdapter.notifyDataSetChanged()
    }

    fun addItem(view: View) {
        val item = Item("",-1, false)
        uncheckedItems.add(item)
        uncheckedAdapter.items = uncheckedItems
        uncheckedAdapter.notifyDataSetChanged()
    }

    override fun exit() {
        finish()
    }

    override fun showList(toDoList: ToDoList) {
        listName.setText(toDoList.name)
        isPinned = !toDoList.pinned
        listId = toDoList.id
        togglePin(findViewById(R.id.pinned_icon))
    }

    override fun showCheckedItems(checkedItems: MutableList<Item>) {
        this.checkedItems = checkedItems
        checkedAdapter.items = checkedItems
        checkedAdapter.notifyDataSetChanged()
    }

    override fun showUncheckedItems(uncheckedItems: MutableList<Item>) {
        this.uncheckedItems = uncheckedItems
        uncheckedAdapter.items = uncheckedItems
        uncheckedAdapter.notifyDataSetChanged()
    }

}
