package ge.tmaisuradze.Search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ge.tmaisuradze.Entities.User
import ge.tmaisuradze.Main.MainActivity
import ge.tmaisuradze.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SearchActivity : AppCompatActivity(), ISearchView, CoroutineScope {

    private lateinit var presenter: SearchPresenter
    private lateinit var adapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        init()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    private fun init() {
        presenter = SearchPresenter(this)
        adapter = SearchAdapter(presenter)

        initToolbar()
        initUsers()
    }

    private fun initToolbar() {
        initSearch()
    }

    private fun initUsers() {
        findViewById<RecyclerView>(R.id.search_users_rv).adapter = adapter
        presenter.fetchAllUsers()
    }

    private fun initSearch() {

        val search = findViewById<EditText>(R.id.search_edit_text)

        val watcher = object : TextWatcher {
            private var name = ""

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s.toString().trim()
                if (text == name) {
                    return
                }
                name = text
                launch {
                    delay(300)
                    if (text != name)
                        return@launch
                    if (name.isEmpty()) {
                        presenter.fetchAllUsers()
                    } else if (name.length > 3) {
                        presenter.fetchUsers(name)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        }

        search.addTextChangedListener(watcher)
    }

    override fun showUsers(users: List<User>?) {
        if (users == null) {
            Toast.makeText(this, "Couldn't fetch users", Toast.LENGTH_SHORT).show()
            return
        }
        adapter.users = users
        adapter.notifyDataSetChanged()
    }

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    fun searchBackClicked(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}
