package tw.ysky.codenotes.ui.cam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import tw.ysky.codenotes.R
import tw.ysky.codenotes.camx.CamManager
import tw.ysky.codenotes.databinding.FragmentCamBinding

class CamFragment : Fragment() {

    private lateinit var viewModel: CamViewModel
    private lateinit var binding: FragmentCamBinding
    private var camManager: CamManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        viewModel = ViewModelProvider(this).get(CamViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cam, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        camManager =
            CamManager(requireContext(), viewLifecycleOwner, binding.previewCamera.surfaceProvider)
        camManager?.start()
    }
}