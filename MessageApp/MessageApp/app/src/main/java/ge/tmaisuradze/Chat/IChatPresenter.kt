package ge.tmaisuradze.Chat

import com.google.firebase.database.ChildEventListener
import ge.tmaisuradze.Entities.Message

interface IChatPresenter {

    fun getCurrentUserId(): String?

    fun fetchMessages(user1: String, user2: String)

    fun onMessagesFetched(messages: MutableList<Message>?)

    fun sendMessage(message: Message)

    fun onMessageSent(message: Message)

    fun registerMessagesListener(user1: String, user2: String): ChildEventListener

    fun removeMessagesListener(listener: ChildEventListener)

    fun detachView()

}