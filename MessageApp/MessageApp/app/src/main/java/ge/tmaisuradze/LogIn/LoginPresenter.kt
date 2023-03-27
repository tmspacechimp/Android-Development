package ge.tmaisuradze.LogIn

class LoginPresenter(var view: ILoginView?): ILoginPresenter{

    private val interactor = LoginInteractor(this)

    fun validateInputs(username: String, password: String) {
        if ((username.isEmpty()) or (password.isEmpty())){
            view?.showBadInputError()
        } else {
            interactor.validateInputs(username, password)
        }
    }

    override fun onInputsValidated(isValid: Boolean) {
        if (isValid) {
            view?.showSuccessPage()
        } else {
            view?.showAuthorizationError()
        }
    }

    override fun detachView(){
        view = null
    }
}