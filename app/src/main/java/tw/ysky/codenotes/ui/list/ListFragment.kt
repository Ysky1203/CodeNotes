package tw.ysky.codenotes.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
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
        val layoutManager = LinearLayoutManager(requireContext())
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        binding.viewModel = vm
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewPostList.layoutManager = layoutManager

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        subscribeObservers()
    }

    private fun subscribeObservers() {
        vm.postList.observe(viewLifecycleOwner, { list ->
            binding.viewPostList.adapter = ListAdapter(requireContext(), list)
        })
    }
}