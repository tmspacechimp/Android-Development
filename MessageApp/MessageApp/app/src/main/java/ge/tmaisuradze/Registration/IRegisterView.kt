package ge.tmaisuradze.Registration

import ge.tmaisuradze.Entities.User

interface IRegisterView {

    fun onUsernameValidated(username: String?, isValid: Boolean)

    fun onUserRegistered(user: User?, errorMessage: String?)

    fun showSuccessPage()
}