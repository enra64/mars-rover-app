package de.arneherdick.thermondorover.photo_list.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import de.arneherdick.thermondorover.photo_list.data.MarsPhotoRepository
import javax.inject.Inject

/**
 * The ViewModel for our photo list
 */
@HiltViewModel
class MarsPhotoListViewModel @Inject constructor(repository: MarsPhotoRepository) : ViewModel() {
    /**
     * A cached version of the mars rover photo stream. Cached in the scope of this ViewModel.
     */
    val photos = repository.getPhotoStream().cachedIn(viewModelScope)
}
