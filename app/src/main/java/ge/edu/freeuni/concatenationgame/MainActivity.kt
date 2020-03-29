package ge.edu.freeuni.concatenationgame

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import ge.edu.freeuni.concatenationgame.gameengine.Engine
import ge.edu.freeuni.concatenationgame.gameengine.ViewImageIdPair
import ge.edu.freeuni.concatenationgame.gameengine.engineOf
import ge.edu.freeuni.concatenationgame.utils.getCircleAnimation

class MainActivity : AppCompatActivity() {

    private val appName: String = "MainActivity"
    private val numOccurrencesToCheckEquality = 2
    private lateinit var constraintLayout: ConstraintLayout
    private val basicBoxImageId: Int = R.drawable.ic_launcher_foreground
    private lateinit var engine: Engine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        constraintLayout = findViewById(R.id.cubesBoxId)
        engine = engineOf(constraintLayout, clickListener, imageIds)
    }

    private val clickListener = View.OnClickListener {
        if (it is ImageView) {
            Log.e(appName, "Clicked ImageView[${it.id}], Image[${engine.getImageId(it.id)}]")
            val viewId: Int = it.id
            val imageId: Int = engine.getImageId(viewId)
            changeImageByAnimation(it, imageId)
            engine.addLastClickedInfo(ViewImageIdPair(viewId, imageId))
            if (numOccurrencesToCheckEquality == engine.numberOfClickedBoxes) {
                checkLastClicks()
            }
        }
    }

    private fun changeImageByAnimation(it: ImageView, imageId: Int) {
        it.setImageResource(imageId)
        val anim: Animator = getCircleAnimation(it)
        anim.start()
    }

    private fun checkLastClicks() {
        val isAllElementsSame: Boolean = engine.isAllElementsSameImage()
        if (isAllElementsSame) {
            engine.makeClickedViewsInvisible()
        } else {
            engine.setClickedViewsOldImage(basicBoxImageId)
        }
        engine.clearLastClickedIcons()
    }

    private val imageIds: List<Int>
        get() {
            return listOf(
                R.drawable.icon_1,
                R.drawable.icon_1,
                R.drawable.icon_2,
                R.drawable.icon_2,
                R.drawable.icon_3,
                R.drawable.icon_3,
                R.drawable.icon_4,
                R.drawable.icon_4,
                R.drawable.icon_5
            )
        }
}
