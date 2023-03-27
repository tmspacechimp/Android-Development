package ge.tmaisuradze.Chat

import ge.tmaisuradze.Entities.Message

interface IChatView {

    fun showMessages(messages: MutableList<Message>?)

    fun onMessageSent(message: Message)
}