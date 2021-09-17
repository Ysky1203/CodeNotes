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
    private var width = 1280
    private var height = 720

    private lateinit var preview: Preview

    fun setFrameListener(frameListener: FrameListener) {
        this.frameListener = frameListener
    }

    fun setResolution(width: Int, height: Int) {
        this.width = width
        this.height = height
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

        val imageAnalyzer = ImageAnalysis.Builder().setTargetResolution(Size(width, height))
            .build()
            .also {
                it.setAnalyzer(cameraExecutor, LuminosityAnalyzer { image ->
                    frameListener?.let { it -> it(image) }
                })
            }

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            cameraProvider = cameraProviderFuture.get()

            try {
                // Unbind use cases before rebinding
                cameraProvider?.unbindAll()

                // Bind use cases to camera
                cameraProvider?.bindToLifecycle(
                    activity, cameraSelector, preview, imageCapture, imageAnalyzer
                )

            } catch (exc: Exception) {
                Timber.e("Use case binding failed")
            }

        }, ContextCompat.getMainExecutor(context))
    }
}

typealias FrameListener = (imgBuffer: ByteArray) -> Unit