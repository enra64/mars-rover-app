package de.arneherdick.thermondorover.photo_list.ui.loading_indicators

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

/**
 * This RecyclerView adapter is used to show a single item in the mars photo list, which only shows
 * up in case of an error
 */
class MarsPhotoLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<MarsPhotoLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: MarsPhotoLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MarsPhotoLoadStateViewHolder {
        return MarsPhotoLoadStateViewHolder.create(parent, retry)
    }
}
