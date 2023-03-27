package ge.tmaisuradze.Registration

import ge.tmaisuradze.Entities.User

interface IRegisterPresenter {

    fun validateUsername(username: String)

    fun registerUser(username: String, password: String, profession: String)

    fun onUsernameValidated(username: String?, isValid: Boolean)

    fun onUserRegistered(user: User?, errorMessage: String?)

    fun detachView()
}