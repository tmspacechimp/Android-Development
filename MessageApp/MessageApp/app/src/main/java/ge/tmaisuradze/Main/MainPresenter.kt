package ge.tmaisuradze.Main

import ge.tmaisuradze.Entities.Chat
import ge.tmaisuradze.Entities.User

class MainPresenter(var view: IMainView?): IMainPresenter {

    private val interactor = MainInteractor(this)

    override fun getProfileInfo() {
        return interactor.fetchProfileData()
    }

    override fun onProfileInfoFetched(user: User) {
        view?.setProfileInfo(user)
    }

    override fun isSignedIn(): Boolean {
        return interactor.isSignedIn()
    }

    override fun signOut() {
        interactor.signOut()
    }

    override fun onSignedOut() {
        view?.onSignedOut()
    }

    override fun onChatsInfoFetched(chats: List<Chat>, index: Int) {
        view?.showChats(chats, index)
    }

    override fun updateUserInfo(data: User, previous: String) {
        interactor.updateUserInfo(data, previous)
    }

    override fun getChatsInfo() {
        interactor.setChatListeners()
    }

    override fun setChatsInfo(filter: String) {
        interactor.setChatsInfo(filter)
    }

    override fun onInfoFetchError() {
        view?.showInfoFetchError()
    }

    fun detachView(){
        view = null
    }
}