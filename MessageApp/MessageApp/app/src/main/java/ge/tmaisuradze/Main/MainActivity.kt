package ge.tmaisuradze.Main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import ge.tmaisuradze.Entities.Chat
import ge.tmaisuradze.Entities.User
import ge.tmaisuradze.LogIn.LoginActivity
import ge.tmaisuradze.R
import ge.tmaisuradze.Search.SearchActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity() : AppCompatActivity(), IMainView, ChatsLoadedListener, CoroutineScope {

    private lateinit var presenter: MainPresenter
    private lateinit var username: String
    private lateinit var viewPager: ViewPager2
    private lateinit var fragments : ArrayList<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = MainPresenter(this)
        val chatsFragment = ChatsFragment()
        chatsFragment.presenter = presenter
        fragments = arrayListOf(chatsFragment, ProfileFragment())
        if (!presenter.isSignedIn()) {
            startActivity(Intent(this, LoginActivity::class.java))
            return
        }
        setContentView(R.layout.activity_main)
        init()
        presenter.getChatsInfo()
    }

    fun init() {
        val nav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        nav.background = null
        nav.menu.getItem(1).isEnabled = false
        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = ViewPagerAdapter(this, fragments)
        nav.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.home_action -> viewPager.currentItem = 0
                R.id.settings_action -> viewPager.currentItem = 1
            }
            true
        }

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position){
                    0 -> nav.selectedItemId = R.id.home_action
                    1 -> {
                        nav.selectedItemId = R.id.settings_action
                        presenter.getProfileInfo()
                        findViewById<BottomAppBar>(R.id.bottom_app_bar).performShow()
                    }
                }
            }
        })
    }

    override fun onFragmentLoaded() {
        val appBar = findViewById<BottomAppBar>(R.id.bottom_app_bar)
        val scrollView = (viewPager.adapter as ViewPagerAdapter).getScrollView()
        scrollView?.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY > oldScrollY){
                appBar.performHide()
            }else{
                appBar.performShow()
            }
        })
        val search = (viewPager.adapter as ViewPagerAdapter).getSearch()
        initSearch(search)
    }

    private fun initSearch(search: EditText){
        val watcher = object : TextWatcher {
            private var name = ""

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s.toString().trim()
                if (text == name) {
                    return
                }
                name = text
                launch {
                    delay(500)

                    if (text != name)
                        return@launch

                    if (name.isEmpty() || name.length > 3){
                        presenter.setChatsInfo(name)
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        }
        search.addTextChangedListener(watcher)
    }

    override fun showChats(chats: List<Chat>, index: Int) {
        (viewPager.adapter as ViewPagerAdapter).setChatsInfo(chats, index)
    }

    override fun setProfileInfo(user: User) {
        username = user.username.toString()
        (viewPager.adapter as ViewPagerAdapter).setProfileInfo(user)
    }

    override fun onSignedOut() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun showInfoFetchError() {
        Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show()
    }

    fun updateClicked(view: View){
        val userInfo = (viewPager.adapter as ViewPagerAdapter).getProfileInfo()
        presenter.updateUserInfo(userInfo, username)
    }

    fun fabClicked(view: View) {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }

    fun signOutClicked(view: View) {
        presenter.signOut()
    }

    override val coroutineContext: CoroutineContext = Dispatchers.Main



}