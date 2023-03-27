package ge.tmaisuradze.LogIn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import ge.tmaisuradze.Main.MainActivity
import ge.tmaisuradze.R
import ge.tmaisuradze.Registration.RegisterActivity

class LoginActivity : AppCompatActivity(), ILoginView {

    private lateinit var presenter : LoginPresenter
    private lateinit var username: EditText
    private lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init() {
        presenter = LoginPresenter(this)
        username = findViewById(R.id.login_username)
        password =  findViewById(R.id.login_password)
    }

    fun pressToSignUpClicked(view: View) {
        val intent = Intent(this, RegisterActivity::class.java).apply {}
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun showAuthorizationError() {
        Toast.makeText(this,"Couldn't sign in", Toast.LENGTH_SHORT).show()
        password.setText("")
    }

    override fun showSuccessPage() {
        val intent = Intent(this, MainActivity::class.java).apply {}
        startActivity(intent)
        finish()
    }

    override fun showBadInputError() {
        Toast.makeText(this,"Fields can't be empty", Toast.LENGTH_SHORT).show()
        password.setText("")
    }

    fun singInClicked(view: View) {
        presenter.validateInputs(username.text.toString(), password.text.toString())
    }
}