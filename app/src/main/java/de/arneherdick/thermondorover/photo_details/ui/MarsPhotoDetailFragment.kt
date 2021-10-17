package de.arneherdick.thermondorover.photo_details.ui

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
import de.arneherdick.thermondorover.R
import de.arneherdick.thermondorover.databinding.FragmentPhotoDetailsBinding
import de.arneherdick.thermondorover.mars_rover_api.models.MarsPhoto
import de.arneherdick.thermondorover.photo_details.data.MarsPhotoDetailViewModel

class MarsPhotoDetailFragment : Fragment() {
    private var photo: MarsPhoto? = null

    private var _binding: FragmentPhotoDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            try {
                photo = MarsPhotoDetailFragmentArgs.fromBundle(it).photo
            } catch (e: Exception) {
                Log.i("MPDF", "Could not get photo from args")
            }
        }
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
