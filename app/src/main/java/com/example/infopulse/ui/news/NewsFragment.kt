package com.example.infopulse.ui.news

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
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.infopulse.R
import com.example.infopulse.base.BaseFragment
import com.example.infopulse.databinding.FragmentNewsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : BaseFragment<FragmentNewsBinding>(
    FragmentNewsBinding::inflate
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

    private val viewModel: NewsFragmentVM by viewModels()

    private val articlesAdapter: ArticlesAdapter by lazy {
        ArticlesAdapter()
    }

    override fun started() {


        createNotificationChannel()

        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavBar).visibility =
            View.VISIBLE

        setUpViews()
    }

    override fun listeners() {
        binding.root.setOnRefreshListener {
            observer()
        }

        articlesAdapter.onItemClick = {
            findNavController().navigate(
                NewsFragmentDirections.actionNewsFragmentToArticlesFragment(
                    it
                )
            )
        }

        articlesAdapter.saveItemClicked = {
            viewModel.saveArticle(it)
            val intent = Intent("SEND_ADDED_NOTIFICATION")
            requireActivity().sendBroadcast(intent)
        }
    }

    private fun showNotification() {
        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_add_alert_24)
            .setContentTitle("Your Notification Title")
            .setContentText("Your Notification Message")
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

    private fun setUpViews() {
        with(binding) {
            rvArticles.adapter = articlesAdapter
        }
    }

    override fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.getArticlesState.collect {
                    updateUi(it)
                }
            }
        }
    }

    private fun updateUi(apiState: NewsFragmentVM.ArticlesApiState) {
        articlesAdapter.submitList(apiState.data)
        binding.root.isRefreshing = apiState.isLoading

        Log.d("LoadingCallback", "getArticles: ${apiState.isLoading}")
        if (apiState.error.isNotEmpty()) {
            Toast.makeText(requireActivity(), apiState.error, Toast.LENGTH_SHORT).show()
        }
    }

}