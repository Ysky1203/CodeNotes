package tw.ysky.codenotes.camx

import android.annotation.SuppressLint
import android.content.Context
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber
import tw.ysky.codenotes.utils.Const
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CamManager(
    private var context: Context,
    private var activity: LifecycleOwner,
    private var surfaceProvider: Preview.SurfaceProvider?
) {


    private val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var imageCapture: ImageCapture? = null
    private var cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    private var cameraProvider: ProcessCameraProvider? = null

    private var frameListener: FrameListener? = null

    private lateinit var preview: Preview

    fun setFrameListener(frameListener: FrameListener) {
        this.frameListener = frameListener
    }

    @SuppressLint("RestrictedApi")
    fun start() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        // Preview
        preview = Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(surfaceProvider)
            }

        imageCapture = ImageCapture.Builder()
            .build()

        val imageAnalyzer =
            ImageAnalysis.Builder().setTargetResolution(Size(Const.CAM_WIDTH, Const.CAM_HEIGHT))
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, LuminosityAnalyzer { image, rotation ->
                        frameListener?.let { it -> it(image, rotation) }
                    })
                }

        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()

            try {

                cameraProvider?.unbindAll()

                cameraProvider?.bindToLifecycle(
                    activity, cameraSelector, preview, imageCapture, imageAnalyzer
                )

            } catch (exc: Exception) {
                Timber.e("Use case binding failed")
            }

        }, ContextCompat.getMainExecutor(context))
    }
}

typealias FrameListener = (image: ByteArray, rotation: Int) -> Unit

