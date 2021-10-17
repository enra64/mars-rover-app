package de.arneherdick.rover.photo_list.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import de.arneherdick.rover.R
import de.arneherdick.rover.databinding.FragmentPhotoListBinding
import de.arneherdick.rover.mars_rover_api.models.MarsPhoto
import de.arneherdick.rover.photo_details.ui.MarsPhotoDetailFragmentArgs
import de.arneherdick.rover.photo_list.ui.loading_indicators.MarsPhotoLoadStateAdapter
import de.arneherdick.rover.photo_list.ui.view_models.MarsPhotoListViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * This fragment displays a list of mars photos.
 */
@AndroidEntryPoint
class MarsPhotoListFragment : Fragment() {
    private var _binding: FragmentPhotoListBinding? = null
    private val marsPhotoListViewModel: MarsPhotoListViewModel by viewModels()

    /**
     * This property is only valid between onCreateView and onDestroyView.
     */
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MarsPhotoAdapter(this::onPhotoClicked)

        // enable showing error messages if the loading does not complete
        binding.photoList.adapter = adapter.withLoadStateHeaderAndFooter(
            MarsPhotoLoadStateAdapter { adapter.retry() },
            MarsPhotoLoadStateAdapter { adapter.retry() }
        )

        // allow force refresh
        binding.retryButton.setOnClickListener {
            adapter.refresh()
        }

        // start showing photos from the paging source
        viewLifecycleOwner.lifecycleScope.launch {
            marsPhotoListViewModel.photos.collect(adapter::submitData)
        }

        // hide refresh indicator & scroll up if refresh loading has finished
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collect { loadState ->
                val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
                // show empty list
                binding.noPhotos.isVisible = isListEmpty
                // Only show the list if refresh succeeds.
                binding.photoList.isVisible = !isListEmpty
                // Show loading spinner during initial load or refresh.
                binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                // Show the retry state if initial load or refresh fails.
                binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

                // Toast on any error
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                    ?: loadState.refresh as? LoadState.Error
                errorState?.let { error ->
                    context?.let {
                        val text = error.error.localizedMessage ?: getString(R.string.unknown_error)
                        Toast.makeText(it, text, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    /**
     * Either navigate to the detail fragment, or update the already visible detail fragment for
     * tablets.
     */
    private fun onPhotoClicked(photo: MarsPhoto) {
        // Leaving this not using view binding as it relies on if the view is visible the current
        // layout configuration (layout, layout-sw600dp)
        val detailPaneNavContainer: View? =
            view?.findViewById(R.id.item_detail_nav_container)

        if (detailPaneNavContainer != null) {
            detailPaneNavContainer.findNavController().navigate(
                R.id.fragment_item_detail, MarsPhotoDetailFragmentArgs(photo, false).toBundle()
            )
        } else {
            requireView().findNavController().navigate(
                MarsPhotoListFragmentDirections.showPhotoDetail(photo, true)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
