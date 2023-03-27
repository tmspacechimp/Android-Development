package ge.tmaisuradze.Chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ge.tmaisuradze.Entities.Message
import ge.tmaisuradze.R

class MessagesAdapter(private val userId: String) : RecyclerView.Adapter<MessageViewHolder>() {

    var messages = mutableListOf<Message>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = if (viewType == SENT) {
            LayoutInflater.from(parent.context).inflate(R.layout.sent_message, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.received_message, parent, false)
        }
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bindMessage(messages[position])
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return if (message.senderId.equals(userId)) {
            SENT
        } else {
            RECEIVED
        }
    }

    fun add(message: Message) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }



    companion object {
        const val SENT = 0
        const val RECEIVED = 1
    }
}

class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val time = view.findViewById<TextView>(R.id.message_time)
    private val text = view.findViewById<TextView>(R.id.message_content)

    fun bindMessage(message: Message) {
        time.text = message.time!!
        text.text = message.content
    }
}