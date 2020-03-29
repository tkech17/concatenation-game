package ge.edu.freeuni.concatenationgame.utils


import android.animation.Animator
import android.view.ViewAnimationUtils
import android.widget.ImageView
import kotlin.math.hypot

fun getCircleAnimation(imageView: ImageView): Animator {
    val cx = imageView.width / 2
    val cy = imageView.height / 2
    val finalRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()
    return ViewAnimationUtils.createCircularReveal(imageView, cx, cy, 0f, finalRadius)
}