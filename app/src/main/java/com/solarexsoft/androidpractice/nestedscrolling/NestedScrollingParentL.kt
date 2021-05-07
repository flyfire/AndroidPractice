package com.solarexsoft.androidpractice.nestedscrolling

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingParent
import androidx.core.view.NestedScrollingParentHelper

/**
 * <pre>
 *    Author: houruhou
 *    CreatAt: 7:37 AM/5/7/21
 *    Desc:
 * </pre>
 */
class NestedScrollingParentL @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), NestedScrollingParent {
    companion object {
        const val TAG = "NestedScrollingParentL"
    }

    private val helper = NestedScrollingParentHelper(this)
    private var realHeight = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        realHeight = 0
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            val heightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.UNSPECIFIED)
            measureChild(view, widthMeasureSpec, heightMeasureSpec)
            realHeight += view.measuredHeight
        }
        Log.d(TAG, "onMeasure() realHeight = $realHeight")
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec))
    }

    override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int): Boolean {
        Log.d(TAG, "onStartNestedScroll() called with: child = $child, target = $target, nestedScrollAxes = $nestedScrollAxes")
        return true
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int) {
        Log.d(TAG, "onNestedScrollAccepted() called with: child = $child, target = $target, axes = $axes")
        helper.onNestedScrollAccepted(child, target, axes)
    }

    override fun onStopNestedScroll(child: View) {
        Log.d(TAG, "onStopNestedScroll() called with: child = $child")
        helper.onStopNestedScroll(child)
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        Log.d(TAG, "onNestedScroll() called with: target = $target, dxConsumed = $dxConsumed, dyConsumed = $dyConsumed, dxUnconsumed = $dxUnconsumed, dyUnconsumed = $dyUnconsumed")
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        val show = showImg(dy)
        val hide = hideImg(dy)
        if (show || hide) {
            consumed[1] = dy
            scrollBy(0, dy)
            Log.d(TAG, "onNestedPreScroll() parent scroll = $scrollY")
        }
        Log.d(TAG, "onNestedPreScroll() called with: target = $target, dx = $dx, dy = $dy, consumed = $consumed, scrollY = $scrollY")
    }

    private fun showImg(dy: Int): Boolean {
        val view = getChildAt(0)
        // dy < 0 手指向下滑动 scrollY > 0 图片还有区域在父布局外
        if (dy < 0 && scrollY > 0 && !view.canScrollVertically(-1)) {
            return true
        }
        return false
    }

    private fun hideImg(dy: Int): Boolean {
        val view = getChildAt(0)
        // dy > 0 手指向上滑动 scrollY < view.height 图片还没完全滑出去
        if (dy > 0 && scrollY < view.height) {
            return true
        }
        return false
    }

    override fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        Log.d(TAG, "onNestedFling() called with: target = $target, velocityX = $velocityX, velocityY = $velocityY, consumed = $consumed")
        return true
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        Log.d(TAG, "onNestedPreFling() called with: target = $target, velocityX = $velocityX, velocityY = $velocityY")
        return true
    }

    override fun scrollTo(x: Int, y: Int) {
        val view = getChildAt(0)
        var realY = 0
        if (y < 0) {
            realY = 0
        }
        if (y > view.height) {
            realY = view.height
        }
        if (realY != scrollY) {
            super.scrollTo(x, realY)
        }
    }
}
 