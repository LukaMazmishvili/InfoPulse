package com.example.infopulse.ui.sources

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.infopulse.R
import com.example.infopulse.base.BaseFragment
import com.example.infopulse.databinding.FragmentSourcesBinding
import com.example.infopulse.di.DBModule
import com.example.infopulse.ui.news.NewsFragmentVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SourcesFragment : BaseFragment<FragmentSourcesBinding>(
    FragmentSourcesBinding::inflate
) {

    private val CHANNEL_ID = "your_channel_id"
    private val NOTIFICATION_ACTION = "SEND_ADDED_NOTIFICATION"

    private val buttonClickReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == NOTIFICATION_ACTION) {
                showNotification()
            }
        }
    }

    private val viewModel: SourcesFragmentVM by viewModels()

    private val sourcesAdapter: SourcesAdapter by lazy {
        SourcesAdapter()
    }

    override fun started() {


        createNotificationChannel()

        viewModel.getSources()

        setUpViews()
    }


    private fun showNotification() {
        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_add_alert_24)
            .setContentTitle("InfoPulse")
            .setContentText("Source Added To Favourites !")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(requireContext())) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(1, builder.build())

            lifecycleScope.launch {
                delay(2500) // 2 seconds delay
                cancel(1)   // Cancel the notification with ID 1
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Your Channel Name"
            val descriptionText = "Your Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onStart() {
        super.onStart()

        // Register the BroadcastReceiver to listen for button clicks
        val filter = IntentFilter(NOTIFICATION_ACTION)
        context?.registerReceiver(buttonClickReceiver, filter)
    }

    override fun onStop() {
        super.onStop()

        // Unregister the BroadcastReceiver to avoid memory leaks
        context?.unregisterReceiver(buttonClickReceiver)
    }

    override fun listeners() {
        binding.root.setOnRefreshListener {
            sourcesAdapter.submitList(emptyList())
            observer()
        }

        sourcesAdapter.onAddToFavouritesClick = {
            viewModel.saveSource(it)
            showNotification()
        }
    }

    private fun setUpViews() {
        with(binding) {
            rvSources.adapter = sourcesAdapter
        }
    }

    override fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSourcesState.collect {
                    updateUi(it)
                }
            }
        }
    }

    private fun updateUi(apiState: SourcesFragmentVM.SourcesApiState) {
        sourcesAdapter.submitList(apiState.data)
        binding.root.isRefreshing = apiState.isLoading
        if (apiState.error.isNotEmpty()) {
            Toast.makeText(requireActivity(), apiState.error, Toast.LENGTH_SHORT).show()
        }
    }

}