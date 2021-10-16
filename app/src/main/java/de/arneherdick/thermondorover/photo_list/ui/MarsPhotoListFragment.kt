package de.arneherdick.thermondorover.photo_list.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import de.arneherdick.thermondorover.R
import de.arneherdick.thermondorover.databinding.FragmentPhotoListBinding
import de.arneherdick.thermondorover.mars_rover_api.models.MarsPhoto
import de.arneherdick.thermondorover.photo_details.ui.MarsPhotoDetailFragmentArgs
import de.arneherdick.thermondorover.photo_list.ui.loading_indicators.MarsPhotoLoadStateAdapter
import de.arneherdick.thermondorover.photo_list.ui.view_models.MarsPhotoListViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * This fragment displays a list of mars photos.
 */
@AndroidEntryPoint
class MarsPhotoListFragment : Fragment() {
    private var _binding: FragmentPhotoListBinding? = null
    private val marsPhotoListViewModel: MarsPhotoListViewModel by viewModels()
    private val adapter = MarsPhotoAdapter(this::onPhotoClicked)

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

        // enable showing error messages if the loading does not complete
        binding.photoList.adapter = adapter.withLoadStateHeaderAndFooter(
            MarsPhotoLoadStateAdapter { adapter.retry() },
            MarsPhotoLoadStateAdapter { adapter.retry() }
        )

        // start showing photos from the paging source
        viewLifecycleOwner.lifecycleScope.launch {
            marsPhotoListViewModel.photos.collect(adapter::submitData)
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
                R.id.fragment_item_detail, MarsPhotoDetailFragmentArgs(photo).toBundle()
            )
        } else {
            requireView().findNavController().navigate(
                MarsPhotoListFragmentDirections.showPhotoDetail(photo)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
