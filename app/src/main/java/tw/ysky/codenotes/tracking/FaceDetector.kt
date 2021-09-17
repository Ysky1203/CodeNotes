package tw.ysky.codenotes.tracking

import android.graphics.Rect
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import timber.log.Timber

class FaceDetector {

    private val detector: com.google.mlkit.vision.face.FaceDetector
    private var listener: FaceListener? = null

    init {
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .setMinFaceSize(0.15f)
            .enableTracking()
            .build()

        detector = FaceDetection.getClient(options)
    }

    fun setFaceListener(listener: FaceListener) {
        this.listener = listener
    }

    fun onStop() {
        detector.close()
    }

    fun detect(image: InputImage): Task<MutableList<Face>> {
        return detector.process(image)
            .addOnSuccessListener { faces ->
                val result = ArrayList<Rect>()
                faces.forEach { face ->
                    Timber.d("boundingBox = ${face.boundingBox}")
                    result.add(face.boundingBox)
                }
                listener?.let { it(result) }
            }.addOnFailureListener { e ->
                Timber.d("No Face : $e")
            }

    }

}

typealias FaceListener = (ArrayList<Rect>) -> Unit