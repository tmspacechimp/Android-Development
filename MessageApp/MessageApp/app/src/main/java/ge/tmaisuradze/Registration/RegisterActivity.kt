package ge.tmaisuradze.Registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import ge.tmaisuradze.Entities.User
import ge.tmaisuradze.LogIn.LoginActivity
import ge.tmaisuradze.Main.MainActivity
import ge.tmaisuradze.R

class RegisterActivity : AppCompatActivity(), IRegisterView {

    private lateinit var presenter: IRegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
    }

    private fun init() {
        presenter = RegisterPresenter(this)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    fun pressToSignInClicked(view: View) {
        val intent = Intent(this, LoginActivity::class.java).apply {}
        startActivity(intent)
        finish()
    }

    override fun onUsernameValidated(username: String?, isValid: Boolean) {
        if (!isValid) {
            Toast.makeText(this, "Username is taken", Toast.LENGTH_SHORT).show()
                return
        }
        if (username == null) {
            Toast.makeText(this, "Can't validate username", Toast.LENGTH_SHORT).show()
            return
        }
        val password = findViewById<EditText>(R.id.register_password)
        val profession = findViewById<EditText>(R.id.register_profession)
        presenter.registerUser(username, password.text.toString(), profession.text.toString())
    }

    override fun onUserRegistered(user: User?, errorMessage: String?) {
        if (user == null) {
            Toast.makeText(this, "Couldn't register user: $errorMessage", Toast.LENGTH_SHORT).show()
            return
        }
        showSuccessPage()
    }

    override fun showSuccessPage() {
        val intent = Intent(this, MainActivity::class.java).apply {}
        startActivity(intent)
        finish()
    }

    fun registerClicked(view: View) {
        val username = findViewById<EditText>(R.id.register_username)
            .text.toString()
        val password = findViewById<EditText>(R.id.register_password)
            .text.toString()
        if (username.length < 4) {
            Toast.makeText(this, "Username should be at least 4 characters long",
                Toast.LENGTH_SHORT).show()
        } else if(password.length < 6) {
            Toast.makeText(this, "Password should be at least 6 characters long",
                Toast.LENGTH_SHORT).show()
        } else {
            presenter.validateUsername(username)
        }
    }


}