package ge.tmaisuradze.Search

import ge.tmaisuradze.Entities.User

interface ISearchView {

    fun showUsers(users: List<User>?)

}