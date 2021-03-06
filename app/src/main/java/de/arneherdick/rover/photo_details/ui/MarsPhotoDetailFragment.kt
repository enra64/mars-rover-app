package de.arneherdick.rover.photo_details.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import de.arneherdick.rover.MarsRoverPhotoActivity
import de.arneherdick.rover.R
import de.arneherdick.rover.databinding.FragmentPhotoDetailsBinding
import de.arneherdick.rover.mars_rover_api.models.MarsPhoto
import de.arneherdick.rover.photo_details.data.MarsPhotoDetailViewModel

class MarsPhotoDetailFragment : Fragment() {
    private var photo: MarsPhoto? = null
    private var needsUpButton: Boolean? = null

    private var _binding: FragmentPhotoDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            try {
                val bundleArgs = MarsPhotoDetailFragmentArgs.fromBundle(it)
                photo = bundleArgs.photo
                needsUpButton = bundleArgs.needsUpButton
            } catch (e: Exception) {
                Log.i("PhotoDetails", "Not showing details, photo not given")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        needsUpButton?.let {
            (activity as MarsRoverPhotoActivity).enableUpButton(it)
        }
    }

    override fun onPause() {
        super.onPause()
        (activity as MarsRoverPhotoActivity).enableUpButton(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoDetailsBinding.inflate(inflater, container, false)
        val rootView = binding.root

        photo?.let {
            binding.viewModel = MarsPhotoDetailViewModel(it)
        }

        binding.noPhotoSelectedPlaceholder.isVisible = photo == null
        binding.photoDetailsScrollview.isVisible = photo != null
        setHasOptionsMenu(photo != null)

        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.photo_details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.share) {
            photo?.let {
                shareUrl(it.imageUrl)
                true
            } ?: false
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun shareUrl(url: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, url)
        startActivity(Intent.createChooser(shareIntent, "Share link using"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
