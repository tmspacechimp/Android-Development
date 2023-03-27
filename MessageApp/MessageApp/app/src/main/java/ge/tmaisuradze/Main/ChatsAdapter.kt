package ge.tmaisuradze.Main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ge.tmaisuradze.Chat.ChatActivity
import ge.tmaisuradze.Entities.Chat
import ge.tmaisuradze.R

class ChatsItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    private val username = itemView.findViewById<TextView>(R.id.chats_item_username)
    private val message = itemView.findViewById<TextView>(R.id.chats_item_message)
    private val time = itemView.findViewById<TextView>(R.id.chats_item_time)

    fun bindChat(chat: Chat, presenter: IMainPresenter?) {
        username.text = chat.receiver!!.username
        if (chat.lastMessage?.length!! > 35){
            var tmp = chat.lastMessage.substring(0, 32)
            tmp = "$tmp..."
            message.text = tmp
        }else{
            message.text = chat.lastMessage
        }
        time.text = chat.lastMessageTime

        itemView.setOnClickListener {
            val intent = Intent(itemView.context, ChatActivity::class.java).apply {
                putExtra("user", chat.receiver)
            }
            itemView.context.startActivity(intent)
        }
    }

}

class ChatsAdapter(var list: List<Chat>, val presenter: IMainPresenter?): RecyclerView.Adapter<ChatsItemViewHolder>() {

    fun updateData(data: List<Chat>, index: Int) {
        list = data
        if (index == - 1) {
            notifyItemInserted(0)
        } else if (index == - 2) {
            notifyDataSetChanged()
        } else {
            notifyItemRemoved(index)
            notifyItemInserted(0)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chats_list_item, parent, false)
        return ChatsItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ChatsItemViewHolder, position: Int) {
        holder.bindChat(list[position], presenter)
    }

}
