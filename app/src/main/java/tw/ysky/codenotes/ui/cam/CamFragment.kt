package tw.ysky.codenotes.ui.cam

import android.graphics.*
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.mlkit.vision.common.InputImage
import timber.log.Timber
import tw.ysky.codenotes.R
import tw.ysky.codenotes.camx.CamManager
import tw.ysky.codenotes.camx.FrameListener
import tw.ysky.codenotes.databinding.FragmentCamBinding
import tw.ysky.codenotes.tracking.FaceDetector
import tw.ysky.codenotes.tracking.FaceListener
import tw.ysky.codenotes.utils.Const
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*

class CamFragment : Fragment() {

    private lateinit var viewModel: CamViewModel
    private lateinit var binding: FragmentCamBinding

    private var camManager: CamManager? = null
    private var faceDetector: FaceDetector? = null
    private lateinit var canvasView: CanvasView

    private val frameListener: FrameListener = { image, rotation ->
        Timber.d("image length = ${image.size}")
        faceDetector?.detect(
            InputImage.fromByteArray(
                image,
                Const.CAM_WIDTH,
                Const.CAM_HEIGHT,
                0,
                InputImage.IMAGE_FORMAT_NV21
            )
        )
        val rgba = convertNV21ToRGBA(
            image, Const.CAM_WIDTH,
            Const.CAM_HEIGHT,
        )
        saveImage("", convertToBitmap(rgba, Const.CAM_WIDTH, Const.CAM_HEIGHT), "hello.jpg")
    }

    fun convertToBitmap(rawImage: ByteArray, width: Int, height: Int): Bitmap {
        return BitmapFactory.decodeByteArray(rawImage, 0, rawImage.size)
    }

    fun convertNV21ToRGBA(imgNV21: ByteArray, width: Int, height: Int): ByteArray {
        val inputImage = imgNV21.clone()
        val out = ByteArrayOutputStream()

        val yuvData = YuvImage(inputImage, ImageFormat.NV21, width, height, null)
        yuvData.compressToJpeg(Rect(0, 0, width, height), 100, out)
        val rgbData = out.toByteArray()
        //val outMap = BitmapFactory.decodeByteArray(rgbData, 0, rgbData.size)

        return rgbData
    }

    fun saveImage(path: String, finalBitmap: Bitmap, fileName: String?) {
        val root: String =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
        val myDir = File("$root/$path")
        myDir.mkdirs()

        val generator = Random()
        var n = 10000
        n = generator.nextInt(n)
        val fn: String = fileName ?: "img-$n.jpg"

        val file = File(myDir, fn)
        if (file.exists()) {
            file.delete()
        }
        try {
            val out = FileOutputStream(file)
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val faceListener: FaceListener = { list ->
        canvasView.setMLRect(list)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
//        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        viewModel = ViewModelProvider(this).get(CamViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cam, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        camManager =
            CamManager(requireContext(), viewLifecycleOwner, binding.previewCamera.surfaceProvider)
        camManager?.setFrameListener(frameListener)
        camManager?.start()

        faceDetector = FaceDetector().apply {
            setFaceListener(faceListener)
        }

        initCanvasView()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onStop() {
        super.onStop()
//        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    private fun initCanvasView() {
        canvasView = CanvasView(requireContext())
        canvasView.setCameraSize(Const.CAM_WIDTH, Const.CAM_HEIGHT)
        binding.cameraCanvas.addView(canvasView)
    }

}