package ge.tmaisuradze.Main

import ge.tmaisuradze.Entities.Chat
import ge.tmaisuradze.Entities.User

interface IMainView {
    fun showChats(chats: List<Chat>, index: Int)
    fun setProfileInfo(user: User)
    fun onSignedOut()
    fun showInfoFetchError()
}