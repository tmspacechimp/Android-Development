package ge.tmaisuradze.Main

import ge.tmaisuradze.Entities.Chat
import ge.tmaisuradze.Entities.User

interface IMainPresenter {
    fun getProfileInfo()
    fun onProfileInfoFetched(user: User)
    fun isSignedIn(): Boolean
    fun signOut()
    fun onSignedOut()
    fun onChatsInfoFetched(chats: List<Chat>, index: Int)
    fun updateUserInfo(data: User, previous: String)
    fun getChatsInfo()
    fun setChatsInfo(filter: String)
    fun onInfoFetchError()
}