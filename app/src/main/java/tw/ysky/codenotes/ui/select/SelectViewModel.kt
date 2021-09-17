package tw.ysky.codenotes.ui.select

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tw.ysky.codenotes.R
import tw.ysky.codenotes.utils.Event

enum class SelectType {
    LIST,
    CAMERA
}

class SelectViewModel : ViewModel() {


    private val _switchFragment = MutableLiveData<Event<Any>>()
    val switchFragment: LiveData<Event<Any>> = _switchFragment

    fun switchFragment(id: SelectType) {
        when (id) {
            SelectType.LIST -> _switchFragment.value =
                Event(R.id.action_selectFragment_to_listFragment)
            SelectType.CAMERA -> _switchFragment.value =
                Event(R.id.action_selectFragment_to_camFragment)
        }
    }

}
