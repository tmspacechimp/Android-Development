package ge.tmaisuradze.Search

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ge.tmaisuradze.Chat.ChatActivity
import ge.tmaisuradze.Entities.User
import ge.tmaisuradze.R

class SearchAdapter (val presenter: ISearchPresenter) : RecyclerView.Adapter<SearchViewHolder>() {

    var users = listOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_users_list_item, parent, false)
        return SearchViewHolder(view, presenter)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bindUser(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }
}

class SearchViewHolder(private val view: View, val presenter: ISearchPresenter) : RecyclerView.ViewHolder(view) {

    private val name = view.findViewById<TextView>(R.id.search_item_username)
    private val profession = view.findViewById<TextView>(R.id.search_item_profession)

    fun bindUser(user: User) {
        name.text = user.username
        profession.text = user.profession
        view.setOnClickListener {
            val chatIntent = Intent(view.context, ChatActivity::class.java).apply {
                putExtra("user", user)
            }
            view.context.startActivity(chatIntent)
        }
    }
}