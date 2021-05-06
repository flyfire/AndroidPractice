package com.solarexsoft.androidpractice.nestedscrolling

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingChild
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.ViewCompat

/**
 * <pre>
 *    Author: houruhou
 *    CreatAt: 7:30 AM/5/6/21
 *    Desc:
 * </pre>
 */
class NestedScrollingChildL @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), NestedScrollingChild {
    companion object {
        const val TAG = "NestedScrollingChildL"
    }

    private val helper = NestedScrollingChildHelper(this)
    private var realHeight = 0
    private var lastTouchX = 0
    private var lastTouchY = 0

    init {
        helper.isNestedScrollingEnabled = true
    }

    override fun setNestedScrollingEnabled(enabled: Boolean) {
        Log.d(TAG, "setNestedScrollingEnabled() called with: enabled = $enabled")
        helper.isNestedScrollingEnabled = enabled
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        realHeight = 0
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        for (i in 0..childCount) {
            val view = getChildAt(i)
            val heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightMeasureSpec, MeasureSpec.UNSPECIFIED)
            measureChild(view, widthMeasureSpec, heightMeasureSpec)
            Log.i(TAG, "onMeasure: $view, height = ${view.measuredHeight}")
            realHeight += view.measuredHeight
        }
        Log.i(TAG, "onMeasure: realHeight = $realHeight")
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec))
    }

    override fun isNestedScrollingEnabled(): Boolean {
        Log.d(TAG, "isNestedScrollingEnabled() called")
        return helper.isNestedScrollingEnabled
    }

    override fun startNestedScroll(axes: Int): Boolean {
        Log.d(TAG, "startNestedScroll() called with: axes = $axes")
        return helper.startNestedScroll(axes)
    }

    override fun stopNestedScroll() {
        Log.d(TAG, "stopNestedScroll() called")
        helper.stopNestedScroll()
    }

    override fun hasNestedScrollingParent(): Boolean {
        Log.d(TAG, "hasNestedScrollingParent() called")
        return helper.hasNestedScrollingParent()
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?
    ): Boolean {
        Log.d(
            TAG,
            "dispatchNestedScroll() called with: dxConsumed = $dxConsumed, dyConsumed = $dyConsumed, dxUnconsumed = $dxUnconsumed, dyUnconsumed = $dyUnconsumed, offsetInWindow = $offsetInWindow"
        )
        return helper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow)
    }

    override fun dispatchNestedFling(
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        Log.d(
            TAG,
            "dispatchNestedFling() called with: velocityX = $velocityX, velocityY = $velocityY, consumed = $consumed"
        )
        return helper.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?
    ): Boolean {
        Log.d(
            TAG,
            "dispatchNestedPreScroll() called with: dx = $dx, dy = $dy, consumed = $consumed, offsetInWindow = $offsetInWindow"
        )
        return helper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val consumed = IntArray(2)
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                lastTouchY = (event.rawY + 0.5f).toInt()
                val nestedScrollAxis = ViewCompat.SCROLL_AXIS_NONE
                nestedScrollAxis or ViewCompat.SCROLL_AXIS_HORIZONTAL
                startNestedScroll(nestedScrollAxis)
            }
            MotionEvent.ACTION_MOVE -> {
                val x = (event.rawX + 0.5f).toInt()
                val y = (event.rawY + 0.5f).toInt()
                val dx = lastTouchX - x
                var dy = lastTouchY - y
                lastTouchX = x
                lastTouchY = y

                if (dispatchNestedPreScroll(dx, dy, consumed, null)) {
                    Log.d(TAG, "onTouchEvent() dy = $dy, consumed = $consumed")
                    dy -= consumed[1]
                    if (dy == 0) {
                        Log.d(TAG, "onTouchEvent() dy = 0")
                        return true
                    }
                } else {
                    Log.d(TAG, "onTouchEvent() scrollby")
                    scrollBy(0, dy)
                }
            }
            MotionEvent.ACTION_CANCEL,MotionEvent.ACTION_UP -> {
                stopNestedScroll()
            }
        }
        return true
    }

    override fun scrollTo(x: Int, y: Int) {
        Log.d(TAG, "scrollTo() called with: x = $x, y = $y")
        var realY = y
        if (y < 0) {
            realY = 0
        }
        if (y > realHeight) {
            realY = realHeight
        }
        if (realY != scrollY) {
            Log.d(TAG, "scrollTo() called with: x = $x, realY = $realY")
            super.scrollTo(x, y)
        }
    }
}
 