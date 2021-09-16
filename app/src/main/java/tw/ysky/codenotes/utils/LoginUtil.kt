package tw.ysky.codenotes.utils

object LoginUtil {

    fun validateLogin(account: String, password: String): Boolean {
        if (account == "" || password == "") {
            return false
        }

        if (account.length < 4 || password.length < 4) {
            return false
        }
        return true
    }
}
