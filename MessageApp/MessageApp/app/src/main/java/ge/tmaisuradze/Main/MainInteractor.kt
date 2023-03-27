package ge.tmaisuradze.Main

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import ge.tmaisuradze.Entities.Chat
import ge.tmaisuradze.Entities.Message
import ge.tmaisuradze.Entities.User
import java.text.SimpleDateFormat

class MainInteractor(val presenter: IMainPresenter) {

    private val auth = FirebaseAuth.getInstance()
    private val databaseUrl = "https://tmsmessageapp-default-rtdb.europe-west1.firebasedatabase.app/"
    private val database = FirebaseDatabase.getInstance(databaseUrl)
    private val users = database.getReference("users")
    private val messages = database.getReference("messages")
    private val chatsList = mutableListOf<Chat>()
    private val formatter = SimpleDateFormat("HH-mm")

    fun fetchProfileData() {
        users.child(auth.currentUser!!.uid).get().addOnSuccessListener {
            presenter.onProfileInfoFetched(
                User(profession = it.child("profession").getValue<String>(),
                    username = it.child("username")
                    .getValue<String>()))
        }.addOnFailureListener {
            presenter.onInfoFetchError()
        }
    }

    fun updateUserInfo(data: User, previous: String) {
        users
            .orderByChild("username")
            .equalTo(data.username)
            .get()
            .addOnSuccessListener {
                if (it.children.count() > 1) {
                    presenter.onInfoFetchError()
                } else if (it.children.count() > 0 && previous != data.username) {
                    presenter.onInfoFetchError()
                } else {
                    users.child(auth.currentUser!!.uid).setValue(data)
                }
            }
            .addOnFailureListener {
                presenter.onInfoFetchError()
            }
    }

    fun isSignedIn(): Boolean {
        return auth.currentUser != null
    }

    fun signOut() {
        auth.signOut()
        presenter.onSignedOut()
    }

    fun setChatListeners() {
        messages.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue<Message>()
                if (message != null) {
                    if (message.receiverId.equals(auth.currentUser!!.uid)){
                        updateChat(message, message.senderId)
                    } else if(message.senderId.equals(auth.currentUser!!.uid)) {
                        updateChat(message, message.senderId)
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Message Listener", error.message)
                presenter.onInfoFetchError()
            }

        })
    }

    private fun updateChat(message: Message, userId: String?) {
        if (userId != null) {
            users.child(userId).get().addOnSuccessListener {
                val user = it.getValue<User>()
                if (user != null) {
                    user.id = it.key
                    val conv = Chat(user, message.content, formatter.format(message.time))
                    var index = -1
                    for (i in 0 until chatsList.size) {
                        if (chatsList[i].receiver?.id.equals(user.id)){
                            index = i
                            chatsList.removeAt(i)
                            break
                        }
                    }
                    chatsList.add(0, conv)
                    presenter.onChatsInfoFetched(chatsList.toList(), index)
                }
            }
        }
    }

    fun setChatsInfo(filter: String) {
        val newChat = mutableListOf<Chat>()
        for (chat in chatsList){
            if (chat.receiver?.username?.startsWith(filter, ignoreCase = true) == true){
                newChat.add(chat)
            }
        }
        presenter.onChatsInfoFetched(newChat.toList(), -2)
    }
}