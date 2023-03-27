package ge.tmaisuradze.Search

import ge.tmaisuradze.Entities.User

class SearchPresenter(private var view: ISearchView?): ISearchPresenter {

    private val interactor = SearchInteractor(this)

    override fun fetchAllUsers() {
        interactor.fetchAllUsers()
    }

    override fun fetchUsers(name: String) {
        interactor.fetchUsers(name)
    }

    override fun onUsersFetched(users: List<User>?) {
        view?.showUsers(users)
    }

    override fun detachView() {
        view = null
    }
}