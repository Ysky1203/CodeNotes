package tw.ysky.codenotes.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import tw.ysky.codenotes.R
import tw.ysky.codenotes.BR
import tw.ysky.codenotes.data.PostItem

class ListAdapter(private var context: Context, private var list: List<PostItem>) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    inner class ViewHolder(var dataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(dataBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_list_cell,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.dataBinding
        Timber.d("listdata = ${list[position]}")
        binding.setVariable(BR.item, list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }


}