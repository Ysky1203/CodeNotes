package tw.ysky.codenotes.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import timber.log.Timber
import tw.ysky.codenotes.api.ApiHelper
import tw.ysky.codenotes.api.ApiRepo
import tw.ysky.codenotes.api.Result
import tw.ysky.codenotes.data.PostItem

class ListViewModel: ViewModel() {

    init {
        viewModelScope.launch {
            val results: Result<List<PostItem>> = ApiRepo(ApiHelper().getService()).getListData()

            when (results) {
                is Result.Error -> Timber.e("Get data from server failed.")
                is Result.Success -> results.data.forEach { Timber.d("id = ${it.id}, userId = ${it.userId}") }
            }
        }
    }

}