package ge.tmaisuradze.Entities

import com.google.firebase.database.IgnoreExtraProperties
import java.text.SimpleDateFormat
import java.util.*

@IgnoreExtraProperties
data class Message(
    var id: String? = null,
    val senderId: String? = null,
    val receiverId: String? = null,
    val key: String? = null,
    val time: String? = null,
    val content: String? = null
) : Comparable<Message> {

    override fun compareTo(other: Message): Int {
        val formatter = SimpleDateFormat("HH-mm")
        return formatter
            .parse(time)!!.compareTo(formatter.parse(other.time))
    }

}