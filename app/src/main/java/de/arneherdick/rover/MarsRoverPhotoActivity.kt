package de.arneherdick.rover

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import de.arneherdick.rover.databinding.ActivityMarsRoverPhotosBinding

@AndroidEntryPoint
class MarsRoverPhotoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMarsRoverPhotosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_item_detail)
        return navController.navigateUp()
    }

    fun enableUpButton(enable: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(enable)
    }
}
