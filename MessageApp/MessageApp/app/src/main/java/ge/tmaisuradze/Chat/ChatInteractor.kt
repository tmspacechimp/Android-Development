package ge.tmaisuradze.Chat

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import ge.tmaisuradze.Entities.Message
import ge.tmaisuradze.Util.Util
import java.text.SimpleDateFormat
import java.util.*

class ChatInteractor(private val presenter: IChatPresenter) {

    private val databaseUrl = "https://tmsmessageapp-default-rtdb.europe-west1.firebasedatabase.app/"
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance(databaseUrl)
    private val messages = database.getReference("messages")
    private val util = Util()
    private val formatter = SimpleDateFormat("HH-mm")

    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    fun fetchMessages(user1: String, user2: String) {
        messages
            .orderByChild("key")
            .equalTo(util.messageKey(user1, user2))
            .get()
            .addOnSuccessListener { onSuccess(it) }
            .addOnFailureListener { onFailure(it) }
    }

    fun registerMessagesListener(user1: String, user2: String): ChildEventListener {
        val registerTime = Date()
        val listener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(Message::class.java) as Message

                if (message.time != null && formatter.parse(message.time).after(registerTime)) {
                    presenter.onMessageSent(snapshot.getValue(Message::class.java) as Message)
                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        }

        messages
            .orderByChild("key")
            .equalTo(util.messageKey(user1, user2))
            .addChildEventListener(listener)

        return listener
    }

    private fun onFailure(ex: Exception) {
        Log.e(TAG, "Couldn't fetch messages", ex)
        presenter.onMessagesFetched(null)
    }

    fun sendMessage(message: Message) {
        Log.i(TAG, "Sending message")
        messages.push().key?.let {
            messages.child(it).setValue(message)
        }
    }

    fun removeMessagesListener(listener: ChildEventListener) {
        messages.removeEventListener(listener)
    }

    private fun onSuccess(dataSnapshot: DataSnapshot) {
        Log.i(TAG, "Successfully fetched ${dataSnapshot.childrenCount} messages")

        val messages = mutableListOf<Message>()
        dataSnapshot.children.forEach {
            val message = it.getValue(Message::class.java) as Message
            message.id = it.key
            messages.add(message)
        }
        messages.sort()
        presenter.onMessagesFetched(messages)
    }

    companion object {
        const val TAG = "Chat Interactor"
    }
}