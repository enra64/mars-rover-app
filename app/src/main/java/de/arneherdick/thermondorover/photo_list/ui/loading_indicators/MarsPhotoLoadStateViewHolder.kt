package de.arneherdick.thermondorover.photo_list.ui.loading_indicators

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import de.arneherdick.thermondorover.R
import de.arneherdick.thermondorover.databinding.PhotoListLoadStateFooterViewItemBinding

/**
 * The PhotoLoadStateViewHolder is used to display the error message footer in the photo list.
 */
class MarsPhotoLoadStateViewHolder(
    private val binding: PhotoListLoadStateFooterViewItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.retryButton.setOnClickListener { retry() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsg.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): MarsPhotoLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.photo_list_load_state_footer_view_item, parent, false)
            val binding = PhotoListLoadStateFooterViewItemBinding.bind(view)
            return MarsPhotoLoadStateViewHolder(binding, retry)
        }
    }
}
