package ge.edu.freeuni.concatenationgame.gameengine

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout

class EngineImpl(
    constraintLayout: ConstraintLayout,
    clickListener: View.OnClickListener,
    imageIds: List<Int>,
    numOccurrencesToCheckEquality: Int
) : Engine {

    private val connections: MutableMap<Int, Int> = mutableMapOf()
    private val imageViews: MutableMap<Int, ImageView> = mutableMapOf()
    private val lastClickedIcons: MutableList<ViewImageIdPair> = mutableListOf()
    private var numOccurrencesToCheckEquality: Int = 0
    private var numFlips: Int = 0
    private var numCardsLeft: Int = 12

    init {
        this.numOccurrencesToCheckEquality = numOccurrencesToCheckEquality
        loadAllImageViews(constraintLayout)
        setAllImageViewsOnClickListener(clickListener)
        connectImageViewsToImages(imageIds)
    }

    private fun loadAllImageViews(constraintLayout: ConstraintLayout) {
        val imageViewsList: List<ImageView> = getAllImageViews(constraintLayout)
        val imageViewsMap: Map<Int, ImageView> = imageViewsList.associateBy({ it.id }, { it })
        this.imageViews.putAll(imageViewsMap)
    }

    private fun getAllImageViews(constraintLayout: ConstraintLayout): List<ImageView> {
        val viewsList: MutableList<ImageView> = mutableListOf()
        for (i in 0 until constraintLayout.childCount) {
            val imageView: View? = constraintLayout.getChildAt(i)
            if (imageView is ImageView) {
                viewsList.add(imageView)
            }
        }
        return viewsList
    }

    private fun setAllImageViewsOnClickListener(clickListener: View.OnClickListener) {
        imageViews.values.forEach { imageView -> imageView.setOnClickListener(clickListener) }
    }

    private fun connectImageViewsToImages(imageIds: List<Int>) {
        val imageViews: List<ImageView> = imageViews.values.toList()
        val shuffledIdSuffixes: List<Int> = imageIds.shuffled()

        for (i in imageViews.indices) {
            val imageId: Int = shuffledIdSuffixes[i]
            val view: ImageView = imageViews[i]
            val viewId: Int = view.id
            connections[viewId] = imageId
        }
    }


    override fun getImageId(imageViewId: Int): Int {
        return connections[imageViewId]!!
    }

    override fun addLastClickedInfo(viewImageIdPair: ViewImageIdPair) {
        if (lastClickedIcons.isEmpty() || lastClickedIcons.last().viewId != viewImageIdPair.viewId) {
            lastClickedIcons.add(viewImageIdPair)
            numFlips++
        }
    }

    override val numberOfClickedBoxes: Int
        get() = lastClickedIcons.size

    override fun getNumFlips(): Int {
        return numFlips
    }

    override fun getNumCardsLeft(): Int {
        return numCardsLeft
    }

    override fun isViewClickedAlready(imageViewId: Int): Boolean {
        return lastClickedIcons.any { it.viewId == imageViewId }
    }


    private fun isFirstPartOfClickedOccurrencesSame(): Boolean {
        val count = lastClickedIcons.size
        val firstElementImageId = lastClickedIcons.first().ImageId
        return count == lastClickedIcons.asSequence()
            .filterIndexed { index, _ -> index < numOccurrencesToCheckEquality }
            .map { it.ImageId }
            .count { it == firstElementImageId }
    }

    override fun clearImagesIfMatchedOrElseSetOldImagesAndDrop(basicBoxImageId: Int) {
        val isAllElementsSame: Boolean = isFirstPartOfClickedOccurrencesSame()
        if (isAllElementsSame) {
            makeClickedViewsInvisible()
        } else {
            setClickedViewsOldImage(basicBoxImageId)
        }
        lastClickedIcons.clear()
    }

    private fun makeClickedViewsInvisible() {
        numCardsLeft -= lastClickedIcons.size
        for (lastClickedIcon in lastClickedIcons) {
            val imageView: ImageView = imageViews[lastClickedIcon.viewId]!!
            imageView.visibility = View.INVISIBLE
        }
    }

    private fun setClickedViewsOldImage(basicBoxImageId: Int) {
        for (lastClickedIcon in lastClickedIcons) {
            val imageView: ImageView = imageViews[lastClickedIcon.viewId]!!
            imageView.setImageResource(basicBoxImageId)
        }

    }

}

fun engineOf(
    constraintLayout: ConstraintLayout,
    clickListener: View.OnClickListener,
    imageIds: List<Int>,
    numOccurrencesToCheckEquality: Int
): EngineImpl {
    return EngineImpl(constraintLayout, clickListener, imageIds, numOccurrencesToCheckEquality)
}

fun engineOf(
    constraintLayout: ConstraintLayout,
    imageIds: List<Int>,
    numOccurrencesToCheckEquality: Int
): EngineImpl {
    return engineOf(
        constraintLayout,
        View.OnClickListener { },
        imageIds,
        numOccurrencesToCheckEquality
    )
}