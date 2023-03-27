package ge.tmaisuradze.Main

import android.widget.EditText
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ge.tmaisuradze.Entities.Chat
import ge.tmaisuradze.Entities.User

class ViewPagerAdapter(activity: FragmentActivity, private val fragments: ArrayList<Fragment>): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun setProfileInfo(user: User) {
        (fragments[1] as ProfileFragment).setData(user)
    }

    fun getProfileInfo(): User{
        return (fragments[1] as ProfileFragment).getInfo()
    }

    fun setChatsInfo(conversations: List<Chat>, index: Int) {
        (fragments[0] as ChatsFragment).setInfo(conversations, index)
    }

    fun getScrollView(): NestedScrollView? {
        return (fragments[0] as ChatsFragment).getScrollView()
    }


    fun getSearch(): EditText {
        return (fragments[0] as ChatsFragment).getSearch()
    }

}
