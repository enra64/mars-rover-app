package de.arneherdick.thermondorover.photo_list.ui

import androidx.recyclerview.widget.DiffUtil
import de.arneherdick.thermondorover.mars_rover_api.models.MarsPhoto

/**
 * DiffUtil implementation to efficiently replace items in the list
 */
class MarsPhotoDiffUtil : DiffUtil.ItemCallback<MarsPhoto>() {
    override fun areItemsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto) =
        oldItem == newItem
}
