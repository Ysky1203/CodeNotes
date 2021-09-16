package tw.ysky.codenotes.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import tw.ysky.codenotes.R
import tw.ysky.codenotes.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private val vm: ListViewModel by viewModels()
    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        binding.viewModel = vm
        binding.lifecycleOwner = viewLifecycleOwner
//        binding.OrderListView.layoutManager = layoutManager

        return binding.root
    }
}