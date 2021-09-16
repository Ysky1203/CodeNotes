package tw.ysky.codenotes.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import tw.ysky.codenotes.R
import tw.ysky.codenotes.utils.Event
import tw.ysky.codenotes.utils.LoginUtil

class LoginViewModel : ViewModel() {

    val account = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _goListFragment = MutableLiveData<Event<Any>>()
    val goListFragment: LiveData<Event<Any>> = _goListFragment

    fun onLoginClick() {
        if (!LoginUtil.validateLogin(account.value.toString(), password.value.toString())) {
            return
        }
        Timber.d("Login : (${account.value}, ${password.value})")

        loginSuccess()
    }

    private fun loginFailed() {
        _goListFragment.value = Event(0)
    }

    private fun loginSuccess() {
        _goListFragment.value = Event(R.id.action_loginFragment_to_selectFragment)
    }
}