package app.com.mobileassignment.utils

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View

fun Activity.getRootView(): View {
    return findViewById<View>(android.R.id.content)
}

fun Context.convertDpToPx(dp: Float): Float {
    return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            this.resources.displayMetrics
    )
}

fun getScreenHeight(activity: Activity): Int {
    val metrics = DisplayMetrics()
    activity.windowManager.defaultDisplay.getMetrics(metrics)
    return metrics.heightPixels
}

fun Activity.isKeyboardOpen(): Boolean {
    val screenHeight = getScreenHeight(this)
    val heightDiff = screenHeight - getRootView().height
    val marginOfError = Math.round(this.convertDpToPx(100F))
    return heightDiff > marginOfError
}

