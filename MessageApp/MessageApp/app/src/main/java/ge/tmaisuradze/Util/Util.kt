package ge.tmaisuradze.Util

class Util {

    fun addMailSuffix(username: String): String {
        return username.plus("@freeuni.edu.ge")
    }

    fun messageKey(user1: String, user2: String): String {
        if (user1 < user2) {
            return "${user1}_${user2}"
        }
        return "${user2}_${user1}"
    }
}