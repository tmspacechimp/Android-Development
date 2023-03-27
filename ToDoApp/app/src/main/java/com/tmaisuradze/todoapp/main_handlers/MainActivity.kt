package com.tmaisuradze.todoapp.main_handlers

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.tmaisuradze.todoapp.R
import com.tmaisuradze.todoapp.edit_hadlers.EditListActivity
import com.tmaisuradze.todoapp.entity.Item
import com.tmaisuradze.todoapp.entity.ToDoList
import com.tmaisuradze.todoapp.main_handlers.interfaces.IMainView
import com.tmaisuradze.todoapp.main_handlers.interfaces.ListListener

class MainActivity : AppCompatActivity(), IMainView, ListListener {

    private lateinit var presenter: MainPresenter
    private lateinit var rvListsPinned: RecyclerView
    private lateinit var rvListsUnpinned: RecyclerView
    private var pinnedAdapter = ListAdapter(this, emptyList(), emptyList())
    private var unpinnedAdapter = ListAdapter(this, emptyList(), emptyList())
    private lateinit var searchBar: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        presenter = MainPresenter(this)
        presenter.getLists("%%")
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    private fun initView() {
        rvListsPinned = findViewById(R.id.pinned_rv)
        rvListsPinned.adapter = pinnedAdapter
        rvListsUnpinned = findViewById(R.id.other_rv)
        rvListsUnpinned.adapter = unpinnedAdapter
        val verticalSpacing = resources.getDimensionPixelSize(R.dimen.big_margin)
        val horizontalSpacing = resources.getDimensionPixelSize(R.dimen.small_font_size)
        rvListsPinned.addItemDecoration(RVDecoration(verticalSpacing, horizontalSpacing))
        rvListsUnpinned.addItemDecoration(RVDecoration(verticalSpacing, horizontalSpacing))

        searchBar = findViewById(R.id.search_edit_text)
        searchBar.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                presenter.getLists("%${searchBar.text.toString()}%")
                return@OnKeyListener true
            }
            false
        })
    }


    override fun showPinnedLists(pinnedToDoLists: List<ToDoList>, pinnedItems: List<List<Item>>) {
        if (pinnedToDoLists.isEmpty()){
            findViewById<TextView>(R.id.pinned_text).visibility = View.GONE
            findViewById<TextView>(R.id.other_text).visibility = View.GONE
            rvListsPinned.visibility = View.GONE
        }else{
            findViewById<TextView>(R.id.pinned_text).visibility = View.VISIBLE
            findViewById<TextView>(R.id.other_text).visibility = View.VISIBLE
            rvListsPinned.visibility = View.VISIBLE
        }
        pinnedAdapter.updateData(pinnedToDoLists, pinnedItems)
    }

    override fun showUnpinnedLists(unpinnedToDoLists: List<ToDoList>, unpinnedItems: List<List<Item>>) {
        unpinnedAdapter.updateData(unpinnedToDoLists, unpinnedItems)
    }

    override fun onListItemClicked(toDoList: ToDoList) {
        startDetails(toDoList.id)
    }

    fun startDetails(id: Int){
        val intent = Intent(Intent(this, EditListActivity::class.java))
        intent.putExtra(EditListActivity.LIST_ID_EXTRA, id)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==1){
            presenter.getLists("%%")
        }
    }

    fun addList(view: View) {
        startDetails(-1)
    }

}