package ge.tmaisuradze.Main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import ge.tmaisuradze.Entities.User
import ge.tmaisuradze.R

class ProfileFragment : Fragment() {

    private lateinit var username: EditText
    private lateinit var profession: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        username = view.findViewById(R.id.edit_username)
        profession = view.findViewById(R.id.edit_profession)
        return view
    }

    fun setData(user: User) {
        username.setText(user.username)
        profession.setText(user.profession)
    }

    fun getInfo(): User{
        return User(username = username.text.toString(),
            profession = profession.text.toString())
    }

}