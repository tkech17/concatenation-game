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
     * makes saved clicked ImageViews Disappear
     */

    /**
     * @return Number Of Flips
     */
    fun getNumFlips(): Int

    /**
     * @return number of cards left
     */
    fun getNumCardsLeft(): Int

    /**
     * @param imageViewId clicked ImageView id
     * @return true if imageViewId is in clicked ImageViews List
     */
    fun isViewClickedAlready(imageViewId: Int): Boolean

    /**
     * @param basicBoxImageId old image
     * clears images if matched, else draws old image on image views
     */
    fun clearImagesIfMatchedOrElseSetOldImagesAndDrop(basicBoxImageId: Int)
}

data class ViewImageIdPair(val viewId: Int, val ImageId: Int)