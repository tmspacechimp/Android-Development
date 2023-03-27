package ge.tmaisuradze.LogIn

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import ge.tmaisuradze.Util.Util

class LoginInteractor(private val presenter: ILoginPresenter) {

    private val auth = FirebaseAuth.getInstance()
    private val util = Util()

    fun validateInputs(username: String, password: String) {
        val mail = util.addMailSuffix(username)
        auth.signInWithEmailAndPassword(mail, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Successfully signed in")
                    val user = auth.currentUser
                    presenter.onInputsValidated(true)
                }}.addOnFailureListener {
                Log.w(TAG, "Failed to sign in", it)
                presenter.onInputsValidated(false)
            }
    }

    companion object {
        const val TAG = "Login Interactor"
    }
}