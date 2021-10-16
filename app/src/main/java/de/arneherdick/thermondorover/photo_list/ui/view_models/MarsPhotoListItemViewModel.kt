package de.arneherdick.thermondorover.photo_list.ui.view_models

import de.arneherdick.thermondorover.Utils.formatDate
import de.arneherdick.thermondorover.mars_rover_api.models.MarsPhoto

/**
 * Simple POJO to contain all information for a single mars photo in the photo list
 */
data class MarsPhotoListItemViewModel(
    private val photo: MarsPhoto,
    private val onClickAction: () -> Unit
) {
    val title: String = photo.rover.name
    val subTitle: String = photo.camera.name
    val subSubTitle: String = formatDate(photo.earthDate)

    fun onClick() {
        onClickAction()
    }
}
