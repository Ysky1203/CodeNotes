package tw.ysky.codenotes.ui.select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import tw.ysky.codenotes.R
import tw.ysky.codenotes.databinding.FragmentSelectBinding
import tw.ysky.codenotes.utils.EventObserver

class SelectFragment : Fragment() {

    private lateinit var viewModel: SelectViewModel
    private lateinit var binding: FragmentSelectBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SelectViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.switchFragment.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(it.hashCode())
        })
    }
}