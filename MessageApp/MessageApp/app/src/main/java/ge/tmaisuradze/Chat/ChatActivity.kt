package ge.tmaisuradze.Chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.ChildEventListener
import ge.tmaisuradze.Entities.Message
import ge.tmaisuradze.Entities.User
import ge.tmaisuradze.Main.MainActivity
import ge.tmaisuradze.R
import ge.tmaisuradze.Search.SearchActivity
import ge.tmaisuradze.Util.Util
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ChatActivity : AppCompatActivity(), IChatView {

    private lateinit var reciever: User
    private lateinit var currentId: String
    private lateinit var presenter: IChatPresenter
    private lateinit var messagesAdapter: MessagesAdapter
    private lateinit var listener: ChildEventListener
    private lateinit var messageInput: TextInputEditText
    private val util = Util()
    private val formatter = SimpleDateFormat("HH-mm")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        init()
    }

    private fun init() {
        val userSerializable = intent.getSerializableExtra("user")
        if (userSerializable == null || userSerializable !is User) {
            Toast.makeText(this, "Couldn't load user", Toast.LENGTH_SHORT).show()
            return
        }
        reciever = userSerializable
        presenter = ChatPresenter(this)
        val id = presenter.getCurrentUserId()
        if (id == null) {
            Toast.makeText(this, "Can't load current user", Toast.LENGTH_SHORT).show()
            return
        }
        currentId = id
        messagesAdapter = MessagesAdapter(currentId)
        findViewById<TextView>(R.id.chat_username).text = reciever.username
        findViewById<TextView>(R.id.chat_profession).text = reciever.profession

        messageInput = findViewById<TextInputEditText>(R.id.chat_text_input_layout)
        loadMessages()

    }

    override fun onStart() {
        listener = presenter.registerMessagesListener(currentId, reciever.id!!)
        super.onStart()
    }

    override fun onStop() {
        presenter.removeMessagesListener(listener)
        super.onStop()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun showMessages(messages: MutableList<Message>?) {
        if (messages != null) {
            messagesAdapter.messages = messages
            messagesAdapter.notifyDataSetChanged()
        } else {
            Toast.makeText(this, "Couldn't fetch messages", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initMessageInput() {
        val layout = findViewById<TextInputLayout>(R.id.chat_text_input_layout)
        val input = findViewById<TextInputEditText>(R.id.chat_message_input)
        val current = Date()
        layout.setEndIconOnClickListener {
            val message = Message(
                senderId = currentId,
                receiverId = reciever.id,
                key = util.messageKey(currentId, reciever.id!!),
                time = formatter.format(Date()),
                content = messageInput.text.toString()
            )
            messageInput.setText("")
            presenter.sendMessage(message)
        }
    }

    private fun loadMessages() {
        findViewById<RecyclerView>(R.id.message_rv).adapter = messagesAdapter
        presenter.fetchMessages(currentId, reciever.id!!)
    }

    fun EditText.onSubmit(func: () -> Unit) {
        setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                func()
            }
            true
        }
    }

    override fun onMessageSent(message: Message) {
        messagesAdapter.add(message)
    }

    fun backButtonClicked() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}