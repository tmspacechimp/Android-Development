package ge.tmaisuradze.Chat

import com.google.firebase.database.ChildEventListener
import ge.tmaisuradze.Entities.Message

class ChatPresenter(private var view: IChatView?) : IChatPresenter  {

    private val interactor = ChatInteractor(this)

    override fun getCurrentUserId(): String? {
        return interactor.getCurrentUserId()
    }

    override fun fetchMessages(user1: String, user2: String) {
        interactor.fetchMessages(user1, user2)
    }

    override fun onMessagesFetched(messages: MutableList<Message>?) {
        view?.showMessages(messages)
    }

    override fun sendMessage(message: Message) {
        interactor.sendMessage(message)
    }

    override fun onMessageSent(message: Message) {
        view?.onMessageSent(message)
    }

    override fun registerMessagesListener(user1: String, user2: String): ChildEventListener {
        return interactor.registerMessagesListener(user1, user2)
    }

    override fun removeMessagesListener(listener: ChildEventListener) {
        interactor.removeMessagesListener(listener)
    }

    override fun detachView(){
        view = null
    }
}