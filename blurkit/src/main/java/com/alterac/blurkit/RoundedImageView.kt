package com.alterac.blurkit

import android.content.Context
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.widget.ImageView

class RoundedImageView : ImageView {

    private var mCornerRadius = 0f

    private var rectF: RectF? = null
    private var porterDuffXfermode: PorterDuffXfermode? = null

    constructor(context: Context) : super(context, null)
    constructor(context: Context, attributes: AttributeSet) : super(context, attributes)

    init {
        rectF = RectF()
        porterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    }

    override fun onDraw(canvas: Canvas) {
        val myDrawable = drawable
        if (myDrawable != null && myDrawable is BitmapDrawable && mCornerRadius > 0) {
            val paint = myDrawable.paint

            rectF!!.set(myDrawable.bounds)

            val prevCount = canvas.saveLayer(rectF, null, Canvas.ALL_SAVE_FLAG)

            paint.isAntiAlias = true
            paint.color = DEFAULT_COLOR

            canvas.drawARGB(0, 0, 0, 0)
            canvas.drawRoundRect(rectF!!, mCornerRadius, mCornerRadius, paint)

            val prevMode = paint.xfermode
            paint.xfermode = porterDuffXfermode
            super.onDraw(canvas)

            paint.xfermode = prevMode
            canvas.restoreToCount(prevCount)
        } else {
            super.onDraw(canvas)
        }
    }

    fun setCornerRadius(cornerRadius: Float) {
        this.mCornerRadius = cornerRadius
    }

    companion object {
        const val DEFAULT_COLOR = -0x1000000
    }
}