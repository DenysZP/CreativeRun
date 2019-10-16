package com.dm.creativerun.manager

import android.app.DownloadManager
import android.app.DownloadManager.ACTION_DOWNLOAD_COMPLETE
import android.app.DownloadManager.EXTRA_DOWNLOAD_ID
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.dm.creativerun.R
import java.io.File

class ImageDownloadManager(private val context: Context) : LifecycleObserver {

    private var downloadId: Long = -1
    private val onDownloadCompleteReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val id = intent.getLongExtra(EXTRA_DOWNLOAD_ID, -1)
            if (id == downloadId) {
                Toast.makeText(
                    context,
                    context.getString(R.string.download_complete),
                    Toast.LENGTH_SHORT
                ).show()
                downloadId = -1
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun registerReceiver() {
        context.registerReceiver(
            onDownloadCompleteReceiver,
            IntentFilter(ACTION_DOWNLOAD_COMPLETE)
        )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun unregisterReceiver() {
        context.unregisterReceiver(onDownloadCompleteReceiver)
    }

    fun downloadImage(imageUrl: String) {
        if (downloadId > 0) {
            Toast.makeText(
                context,
                context.getString(R.string.previous_download_in_progress),
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        val file = File(context.getExternalFilesDir(null), context.getString(R.string.app_name))

        val request =
            DownloadManager.Request(Uri.parse(imageUrl))
                .setTitle(imageUrl)
                .setDescription(context.getString(R.string.image_downloading))
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setDestinationUri(Uri.fromFile(file))
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)
        val downloadManager = context.getSystemService(DOWNLOAD_SERVICE) as? DownloadManager
        downloadId = downloadManager?.enqueue(request) ?: -1
    }
}