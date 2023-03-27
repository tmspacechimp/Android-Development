package ge.tmaisuradze.Search

import ge.tmaisuradze.Entities.User

interface ISearchPresenter {

    fun fetchAllUsers()

    fun fetchUsers(name: String)

    fun onUsersFetched(users: List<User>?)

    fun detachView()

}