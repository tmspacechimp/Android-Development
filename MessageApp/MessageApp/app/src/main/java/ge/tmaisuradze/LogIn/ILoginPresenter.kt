package ge.tmaisuradze.LogIn

interface ILoginPresenter {
    fun onInputsValidated(isValid: Boolean)
    fun detachView()
}