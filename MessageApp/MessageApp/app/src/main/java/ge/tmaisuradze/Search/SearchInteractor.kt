package ge.tmaisuradze.Search

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.tmaisuradze.Entities.User

class SearchInteractor(private val presenter: ISearchPresenter) {

    private val databaseUrl = "https://tmsmessageapp-default-rtdb.europe-west1.firebasedatabase.app/"
    private val database = FirebaseDatabase.getInstance(databaseUrl)
    private val users = database.getReference("users")

    fun fetchAllUsers() {
        users.get()
            .addOnSuccessListener { onSuccess(it) }
            .addOnFailureListener { onFailure(it) }
    }

    fun fetchUsers(name: String) {
        users
            .orderByChild("username")
            .equalTo(name)
            .get()
            .addOnSuccessListener { onSuccess(it) }
            .addOnFailureListener { onFailure(it) }
    }

    private fun onSuccess(dataSnapshot: DataSnapshot) {
        Log.i(TAG, "Fetched ${dataSnapshot.childrenCount} users")

        val users = mutableListOf<User>()
        dataSnapshot.children.forEach {
            val user = it.getValue(User::class.java) as User
            user.id = it.key
            users.add(user)
        }

        presenter.onUsersFetched(users)
    }

    private fun onFailure(ex: Exception) {
        Log.e(TAG, "Couldn't fetch users", ex)
        presenter.onUsersFetched(null)
    }

    companion object {
        const val TAG = "Search Interactor"
    }
}