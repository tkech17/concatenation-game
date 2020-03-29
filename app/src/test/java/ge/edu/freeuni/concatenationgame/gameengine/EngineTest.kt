package ge.edu.freeuni.concatenationgame.gameengine

import android.content.Context
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import org.junit.Assert.assertEquals
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import org.mockito.Mockito

@FixMethodOrder(MethodSorters.JVM)
class EngineTest {

    private lateinit var engine: Engine

    @Test
    fun lastClickedIcons_should_beZero_when_EngineCreated() {
        val imageViews: List<ImageViewMock> = (0..3).map {
            imageViewMockOf(
                it
            )
        }
        val constraintLayout: ConstraintLayout = getMockConstraintLayout(imageViews)

        engine = engineOf(constraintLayout, getDrawableIds(),10)

        assertEquals(0, engine.numberOfClickedBoxes)
    }

    @Test
    fun should_IncreaseLastClickedIconsByOne_when_addedClickEventOnImageView() {
        val imageViews: List<ImageViewMock> = (0..3).map {
            imageViewMockOf(
                it
            )
        }
        val constraintLayout: ConstraintLayout = getMockConstraintLayout(imageViews)

        engine = engineOf(constraintLayout, getDrawableIds(),10)
        engine.addLastClickedInfo(ViewImageIdPair(1, 1))

        assertEquals(1, engine.numberOfClickedBoxes)
    }

    @Test
    fun should_notIncreaseLastClickedIconsByOne_when_ThatImageViewWasAlreadyInLastClickedViews() {
        val imageViews: List<ImageViewMock> = (0..3).map {
            imageViewMockOf(
                it
            )
        }
        val constraintLayout: ConstraintLayout = getMockConstraintLayout(imageViews)
        engine = engineOf(constraintLayout, getDrawableIds(),10)
        val viewImageIdPair = ViewImageIdPair(1, 1)

        engine.addLastClickedInfo(viewImageIdPair)
        engine.addLastClickedInfo(viewImageIdPair)

        assertEquals(1, engine.numberOfClickedBoxes)
    }

    @Test
    fun should_UseAllImageIds() {
        val imageViews: List<ImageViewMock> = (0..3).map {
            imageViewMockOf(
                it
            )
        }
        val constraintLayout: ConstraintLayout = getMockConstraintLayout(imageViews)
        val imageIds: List<Int> = getDrawableIds()
        engine = engineOf(constraintLayout, imageIds,10)

        val actualImageIds: List<Int> = imageViews.map { engine.getImageId(it.id) }.sorted()
        val expectedImageIdsSorted: List<Int> = imageIds.sorted()

        assertEquals(expectedImageIdsSorted, actualImageIds)
    }

    @Test
    fun numFlips_should_beZero_when_startingGame() {
        val imageViews: List<ImageViewMock> = (0..3).map { imageViewMockOf(it) }
        val constraintLayout: ConstraintLayout = getMockConstraintLayout(imageViews)
        val imageIds: List<Int> = getDrawableIds()
        engine = engineOf(constraintLayout, imageIds,10)

        assertEquals(0, engine.getNumFlips())
    }

    @Test
    fun should_increaseNumFlips_after_Clicking() {
        val imageViews: List<ImageViewMock> = (0..3).map { imageViewMockOf(it) }
        val constraintLayout: ConstraintLayout = getMockConstraintLayout(imageViews)
        val imageIds: List<Int> = getDrawableIds()
        engine = engineOf(constraintLayout, imageIds,10)

        engine.addLastClickedInfo(ViewImageIdPair(1, 1))
        engine.addLastClickedInfo(ViewImageIdPair(2, 2))

        assertEquals(2, engine.getNumFlips())
    }

    @Test
    fun should_notIncreaseNumFlips_after_ClickingAlreadyClickedCard() {
        val imageViews: List<ImageViewMock> = (0..3).map { imageViewMockOf(it) }
        val constraintLayout: ConstraintLayout = getMockConstraintLayout(imageViews)
        val imageIds: List<Int> = getDrawableIds()
        engine = engineOf(constraintLayout, imageIds,10)

        engine.addLastClickedInfo(ViewImageIdPair(1, 1))
        engine.addLastClickedInfo(ViewImageIdPair(1, 1))
        engine.addLastClickedInfo(ViewImageIdPair(2, 2))

        assertEquals(2, engine.getNumFlips())
    }

    @Test
    fun numCardsLeft_should_beTwelve_when_gameStarts() {
        val imageViews: List<ImageViewMock> = (0..3).map { imageViewMockOf(it) }
        val constraintLayout: ConstraintLayout = getMockConstraintLayout(imageViews)
        val imageIds: List<Int> = getDrawableIds()

        engine = engineOf(constraintLayout, imageIds,10)

        assertEquals(12, engine.getNumCardsLeft())
    }

    private fun getMockConstraintLayout(imageViews: List<ImageViewMock>): ConstraintLayout {
        val constraintLayout: ConstraintLayout = Mockito.mock(ConstraintLayout::class.java)
        Mockito.`when`(constraintLayout.childCount).thenReturn(imageViews.size)
        Mockito.`when`(constraintLayout.getChildAt(0)).thenReturn(imageViews[0])
        Mockito.`when`(constraintLayout.getChildAt(1)).thenReturn(imageViews[1])
        Mockito.`when`(constraintLayout.getChildAt(2)).thenReturn(imageViews[2])
        Mockito.`when`(constraintLayout.getChildAt(3)).thenReturn(imageViews[3])
        return constraintLayout
    }


    private fun getDrawableIds(): List<Int> {
        return listOf(1, 2, 3, 4)
    }

}

private class ImageViewMock(context: Context?) : ImageView(context) {

    private var id: Int = -1
    private var visibility: Int = -1

    override fun getId(): Int {
        return id
    }

    override fun setId(id: Int) {
        this.id = id
    }

    override fun setOnClickListener(l: OnClickListener?) {

    }

    override fun setVisibility(visibility: Int) {
        this.visibility = visibility
    }

    override fun getVisibility(): Int {
        return visibility
    }

}

private fun imageViewMockOf(id: Int): ImageViewMock {
    val imageView = ImageViewMock(null)
    imageView.id = id
    return imageView
}
