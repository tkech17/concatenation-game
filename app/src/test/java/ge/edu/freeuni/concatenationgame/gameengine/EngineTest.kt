package ge.edu.freeuni.concatenationgame.gameengine

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import org.junit.Assert.*
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

        engine = engineOf(constraintLayout, getDrawableIds())

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

        engine = engineOf(constraintLayout, getDrawableIds())
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
        engine = engineOf(constraintLayout, getDrawableIds())
        val viewImageIdPair = ViewImageIdPair(1, 1)

        engine.addLastClickedInfo(viewImageIdPair)
        engine.addLastClickedInfo(viewImageIdPair)

        assertEquals(1, engine.numberOfClickedBoxes)
    }

    @Test
    fun should_beSameElements() {
        val imageViews: List<ImageViewMock> = (0..3).map {
            imageViewMockOf(
                it
            )
        }
        val constraintLayout: ConstraintLayout = getMockConstraintLayout(imageViews)
        engine = engineOf(constraintLayout, getDrawableIds())
        val viewImageIdPair = ViewImageIdPair(1, 1)

        engine.addLastClickedInfo(viewImageIdPair)
        engine.addLastClickedInfo(viewImageIdPair)
        engine.addLastClickedInfo(viewImageIdPair)

        assertTrue(engine.isAllElementsSameImage())
    }

    @Test
    fun should_notBeSameElements() {
        val imageViews: List<ImageViewMock> = (0..3).map {
            imageViewMockOf(
                it
            )
        }
        val constraintLayout: ConstraintLayout = getMockConstraintLayout(imageViews)
        engine = engineOf(constraintLayout, getDrawableIds())
        val viewImageIdPair = ViewImageIdPair(1, 1)

        engine.addLastClickedInfo(viewImageIdPair)
        engine.addLastClickedInfo(viewImageIdPair)
        engine.addLastClickedInfo(viewImageIdPair)
        engine.addLastClickedInfo(
            ViewImageIdPair(
                viewImageIdPair.viewId + 1,
                viewImageIdPair.ImageId + 1
            )
        )

        assertFalse(engine.isAllElementsSameImage())
    }

    @Test
    fun should_clearLastClickedInfo() {
        val imageViews: List<ImageViewMock> = (0..3).map {
            imageViewMockOf(
                it
            )
        }
        val constraintLayout: ConstraintLayout = getMockConstraintLayout(imageViews)
        engine = engineOf(constraintLayout, getDrawableIds())
        val viewImageIdPair = ViewImageIdPair(1, 1)
        engine.addLastClickedInfo(viewImageIdPair)
        engine.addLastClickedInfo(viewImageIdPair)
        engine.addLastClickedInfo(viewImageIdPair)

        engine.clearLastClickedIcons()

        assertEquals(0, engine.numberOfClickedBoxes)
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
        engine = engineOf(constraintLayout, imageIds)

        val actualImageIds: List<Int> = imageViews.map { engine.getImageId(it.id) }.sorted()
        val expectedImageIdsSorted: List<Int> = imageIds.sorted()

        assertEquals(expectedImageIdsSorted, actualImageIds)
    }

    @Test
    fun should_setVisibilityToInvisibleToClickedImageViews() {
        val imageViews: List<ImageViewMock> = (0..3).map { imageViewMockOf(it) }
        val constraintLayout: ConstraintLayout = getMockConstraintLayout(imageViews)
        val imageIds: List<Int> = getDrawableIds()
        engine = engineOf(constraintLayout, imageIds)
        engine.addLastClickedInfo(ViewImageIdPair(imageViews[0].id, 1))
        engine.addLastClickedInfo(ViewImageIdPair(imageViews[1].id, 1))

        engine.makeClickedViewsInvisible()

        assertEquals(View.INVISIBLE, imageViews[0].visibility)
        assertEquals(View.INVISIBLE, imageViews[1].visibility)
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