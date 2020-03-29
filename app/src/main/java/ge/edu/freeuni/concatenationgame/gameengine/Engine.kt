package ge.edu.freeuni.concatenationgame.gameengine

/**
 * Engine Class Which has State Of The Game
 */
interface Engine {

    /**
     *
     * @param imageViewId id of clicked ImageView
     * @return image resource id of the imageView
     */
    fun getImageId(imageViewId: Int): Int

    /**
     * @param viewImageIdPair clicked imageViewId and it's temp imageId
     *
     * saves click event info
     */
    fun addLastClickedInfo(viewImageIdPair: ViewImageIdPair)

    /**
     * number of current saved clicked boxes
     */
    val numberOfClickedBoxes: Int

    /**
     * @return true if all clicked elements have same image
     */
    fun isAllElementsSameImage(): Boolean

    /**
     * makes saved clicked ImageViews Disappear
     */
    fun makeClickedViewsInvisible()

    /**
     * @param basicBoxImageId old basic image id
     * sets all clicked imageVies old image
     */
    fun setClickedViewsOldImage(basicBoxImageId: Int)

    /**
     * clears saved clicked ImageViews
     */
    fun clearLastClickedIcons()

}

data class ViewImageIdPair(val viewId: Int, val ImageId: Int)