package tw.ysky.codenotes.ui.cam

import android.content.Context
import android.graphics.*
import android.view.View

class CanvasView(context: Context) : View(context) {

    private var barcodeRect: ArrayList<Rect> = ArrayList()
    private var camWidth = 0
    private var camHeight = 0

    fun setCameraSize(camWidth: Int, camHeight: Int) {
        this.camWidth = camWidth
        this.camHeight = camHeight
    }

    override fun onDraw(canvas: Canvas) {

        drawRectFromMLKit(canvas)

        invalidate()
    }

    private fun drawRectFromMLKit(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.rgb(147, 0, 20)
        paint.strokeWidth = 5.0F

//        val scale: Float = (canvas.width).toFloat() / (camWidth).toFloat()
        val scale: Float = 1.0F

        barcodeRect.forEach {
            val ptsList: ArrayList<PointF> = ArrayList()

            val xMin: Float = it.left * scale
            val xMax: Float = it.right * scale
            val yMin: Float = it.bottom * scale
            val yMax: Float = it.top * scale

            ptsList.add(PointF(xMin, yMin))
            ptsList.add(PointF(xMin, yMax))
            ptsList.add(PointF(xMax, yMax))
            ptsList.add(PointF(xMax, yMin))

            for (i in 0 until ptsList.size) {
                canvas.drawLine(
                    ptsList[i].x,
                    ptsList[i].y,
                    ptsList[(i + 1) % 4].x,
                    ptsList[(i + 1) % 4].y,
                    paint
                )
            }
        }
    }

    fun setMLRect(results: ArrayList<Rect>?) {
        barcodeRect.clear()
        results?.forEach {
            barcodeRect.add(it)
        }
    }
}