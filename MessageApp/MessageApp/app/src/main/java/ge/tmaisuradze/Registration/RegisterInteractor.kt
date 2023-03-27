package ge.tmaisuradze.Registration

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import ge.tmaisuradze.Entities.User
import ge.tmaisuradze.Util.Util


class RegisterInteractor(private val presenter: IRegisterPresenter) {
    private val databaseUrl = "https://tmsmessageapp-default-rtdb.europe-west1.firebasedatabase.app/"
    private val auth = FirebaseAuth.getInstance()
    private val users = FirebaseDatabase.getInstance(databaseUrl).getReference("users")
    private val util = Util()

    fun validateUsername(username: String) {
        Log.i(TAG, "Validating username: $username")

        users.orderByChild("username")
            .equalTo(username)
            .get()
            .addOnSuccessListener {
                Log.i(TAG, "Validated username: $username")
                presenter.onUsernameValidated(username, it.children.count() == 0)
            }
            .addOnFailureListener {
                Log.e(TAG, "Couldn't validate user", it)
                presenter.onUsernameValidated(null, false)
            }
    }

    fun registerUser(username: String, password: String, profession: String) {
        Log.i(TAG, "Registering user: username: $username, profession: $profession")

        auth.createUserWithEmailAndPassword(util.addMailSuffix(username), password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.i(TAG, "Successfully registered user: username: $username, profession: $profession")
                    addUser(username, profession)
                } else {
                    Log.e(TAG, "Couldn't register user", it.exception)
                    presenter.onUserRegistered(null, it.exception?.message)
                }
            }
    }

    private fun addUser(username: String, profession: String) {
        val user = User(username = username, profession = profession)
        users.child(auth.currentUser?.uid!!).setValue(user)

        presenter.onUserRegistered(user, null)
    }

    companion object {
        const val TAG = "Register Interactor"
    }
}