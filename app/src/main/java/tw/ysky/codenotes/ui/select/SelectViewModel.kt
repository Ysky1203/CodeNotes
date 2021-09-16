package tw.ysky.codenotes.ui.select

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tw.ysky.codenotes.R
import tw.ysky.codenotes.utils.Event

class SelectViewModel:ViewModel() {

    private val _goListFragment = MutableLiveData<Event<Any>>()
    val goListFragment: LiveData<Event<Any>> = _goListFragment

    fun goListFragment(){
        _goListFragment.value = Event(R.id.action_selectFragment_to_listFragment)
    }

    fun goCameraFragment(){

    }
}