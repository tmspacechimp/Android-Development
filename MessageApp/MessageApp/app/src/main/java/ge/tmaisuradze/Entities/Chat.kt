package ge.tmaisuradze.Entities

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Chat(
    val receiver: User? = null,
    val lastMessage: String? = null,
    val lastMessageTime: String? = null,
) : Comparable<Chat> {

    override fun compareTo(other: Chat): Int {
        return lastMessageTime!!.compareTo(other.lastMessageTime!!)
    }

}