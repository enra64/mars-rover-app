package de.arneherdick.thermondorover.photo_list.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import de.arneherdick.thermondorover.databinding.PhotoListItemBinding
import de.arneherdick.thermondorover.mars_rover_api.models.MarsPhoto
import de.arneherdick.thermondorover.photo_list.ui.view_models.MarsPhotoListItemViewModel

/**
 * An implementation of PagingDataAdapter, i.e., a RecyclerView adapter, that shows mars photos.
 */
class MarsPhotoAdapter(
    private val onClickListener: (MarsPhoto) -> Unit
) : PagingDataAdapter<MarsPhoto, MarsPhotoListItemViewHolder>(MarsPhotoDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsPhotoListItemViewHolder {
        val binding =
            PhotoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarsPhotoListItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holderMarsPhotoListItem: MarsPhotoListItemViewHolder,
        position: Int
    ) {
        // we can enforce non-null values here because enablePlaceholders is false
        // in the paging config
        val photo = getItem(position)!!
        holderMarsPhotoListItem.binding.viewModel =
            MarsPhotoListItemViewModel(photo, onClickAction = { onClickListener(photo) })
    }
}
