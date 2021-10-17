package de.arneherdick.rover.photo_details.data

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import de.arneherdick.rover.R
import de.arneherdick.rover.Utils.formatDate
import de.arneherdick.rover.mars_rover_api.models.MarsPhoto

/**
 * A POJO class that holds all information about a specific photo to be displayed by the
 * MarsPhotoDetailFragment
 */
data class MarsPhotoDetailViewModel(
    private val photo: MarsPhoto
) {
    val photoId = photo.id
    val sol = photo.sol
    val earthDate: String = formatDate(photo.earthDate)
    val camera = photo.camera
    val rover = photo.rover
    val launchDate: String = formatDate(rover.launchDate)
    val landingDate: String = formatDate(rover.landingDate)

    val url: String = photo.imageUrl

    companion object {
        @JvmStatic @BindingAdapter("bind:imageUrl")
        fun loadImage(view: ImageView, imageUrl: String?) {
            val safeUrl = imageUrl?.replace("http://", "https://")

            Log.d("DetailVM", "Loading photo $safeUrl")
            Picasso.get().load(safeUrl).placeholder(R.drawable.mars_rover_placeholder).into(view)
        }
    }
}
