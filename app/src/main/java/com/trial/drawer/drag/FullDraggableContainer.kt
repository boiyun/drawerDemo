package com.trial.drawer.drag

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.core.math.MathUtils
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import java.util.*

/**
 * <pre>
 * @author : Trial
 * @time   : 11/15/21
 * @desc   :
 * @version: 1.0
</pre> *
 */
class FullDraggableContainer @JvmOverloads constructor(context: Context , attrs: AttributeSet? = null , defStyleAttr: Int = 0) : FrameLayout(context , attrs , defStyleAttr) , FullDraggableHelper.Callback {
    private val helper: FullDraggableHelper
    private var drawerLayout: DrawerLayout? = null
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ensureDrawerLayout()
    }

    private fun ensureDrawerLayout() {
        val parent = parent
        check(parent is DrawerLayout) { "This $this must be added to a DrawerLayout" }
        drawerLayout = parent
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return helper.onInterceptTouchEvent(event)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return helper.onTouchEvent(event)
    }

    override val drawerMainContainer: View
        get() = this

    override fun isDrawerOpen(gravity: Int): Boolean {
        return drawerLayout!!.isDrawerOpen(gravity)
    }

    override fun hasEnabledDrawer(gravity: Int): Boolean {
        return (drawerLayout!!.getDrawerLockMode(gravity) == DrawerLayout.LOCK_MODE_UNLOCKED
                && findDrawerWithGravity(gravity) != null)
    }

    override fun offsetDrawer(gravity: Int , offset: Float) {
        setDrawerToOffset(gravity , offset)
        drawerLayout!!.invalidate()
    }

    override fun smoothOpenDrawer(gravity: Int) {
        drawerLayout!!.openDrawer(gravity , true)
    }

    override fun smoothCloseDrawer(gravity: Int) {
        drawerLayout!!.closeDrawer(gravity , true)
    }

    override fun onDrawerDragging() {
        val drawerListeners = drawerListeners
        if (drawerListeners != null) {
            val listenerCount = drawerListeners.size
            for (i in listenerCount - 1 downTo 0) {
                drawerListeners[i].onDrawerStateChanged(DrawerLayout.STATE_DRAGGING)
            }
        }
    }// throw to let developer know the api is changed

    // noinspection unchecked
    protected val drawerListeners: List<DrawerListener>?
        protected get() = try {
            val field = DrawerLayout::class.java.getDeclaredField("mListeners")
            field.isAccessible = true
            // noinspection unchecked
            field[drawerLayout] as List<DrawerListener>
        } catch (e: Exception) {
            // throw to let developer know the api is changed
            throw RuntimeException(e)
        }

    protected fun setDrawerToOffset(gravity: Int , offset: Float) {
        val drawerView = findDrawerWithGravity(gravity)
        val slideOffsetPercent = MathUtils.clamp(offset / Objects.requireNonNull(drawerView)!!.width , 0f , 1f)
        try {
            val method = DrawerLayout::class.java.getDeclaredMethod("moveDrawerToOffset" , View::class.java , Float::class.javaPrimitiveType)
            method.isAccessible = true
            method.invoke(drawerLayout , drawerView , slideOffsetPercent)
            drawerView!!.visibility = VISIBLE
        } catch (e: Exception) {
            // throw to let developer know the api is changed
            throw RuntimeException(e)
        }
    }

    // Copied from DrawerLayout
    private fun findDrawerWithGravity(gravity: Int): View? {
        val absHorizontalGravity = GravityCompat.getAbsoluteGravity(gravity , ViewCompat.getLayoutDirection(drawerLayout!!)) and Gravity.HORIZONTAL_GRAVITY_MASK
        val childCount = drawerLayout!!.childCount
        for (i in 0 until childCount) {
            val child = drawerLayout!!.getChildAt(i)
            val childAbsGravity = getDrawerViewAbsoluteGravity(child)
            if (childAbsGravity and Gravity.HORIZONTAL_GRAVITY_MASK == absHorizontalGravity) {
                return child
            }
        }
        return null
    }

    // Copied from DrawerLayout
    private fun getDrawerViewAbsoluteGravity(drawerView: View): Int {
        val gravity = (drawerView.layoutParams as DrawerLayout.LayoutParams).gravity
        return GravityCompat.getAbsoluteGravity(gravity , ViewCompat.getLayoutDirection(drawerLayout!!))
    }

    init {
        helper = FullDraggableHelper(context , this)
    }
}