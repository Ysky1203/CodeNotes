package tw.ysky.codenotes.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import timber.log.Timber
import tw.ysky.codenotes.api.ApiHelper
import tw.ysky.codenotes.api.ApiRepo
import tw.ysky.codenotes.api.Result
import tw.ysky.codenotes.data.PostItem

class ListViewModel: ViewModel() {

    private var _postList: MutableList<PostItem> = ArrayList()
    val postList = MutableLiveData<List<PostItem>>().apply {
        MutableLiveData<List<PostItem>>().also {
            viewModelScope.launch {
                val results: Result<List<PostItem>> =
                    ApiRepo(ApiHelper().getService()).getListData()

                when (results) {
                    is Result.Error -> {
                        Timber.e("Get data from server failed.")
                    }
                    is Result.Success -> {
                        results.data.forEach {
                            _postList.add(it)
                        }
                    }
                }
                postValue(_postList)
            }.start()
        }
    }
}