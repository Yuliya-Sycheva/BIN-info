package com.example.bin_info.external.data.impl

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.bin_info.R
import com.example.bin_info.external.domain.ExternalRepository

class ExternalRepositoryImpl(private val context: Context) : ExternalRepository {
    override fun openMap(latitude: String, longitude: String) {
        val geoUri = "geo:$latitude,$longitude?q=$latitude,$longitude"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(geoUri)).setFlags(FLAG_ACTIVITY_NEW_TASK)

        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            Toast.makeText(context, R.string.no_maps_app_found, Toast.LENGTH_SHORT).show()
        }

    }

    override fun openPhone(phone: String) {
        val phoneUri = "tel:$phone"
        val intent =
            Intent(Intent.ACTION_CALL, Uri.parse(phoneUri)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            context.startActivity(intent)
        } else {
            if (context is Activity) {
                ActivityCompat.requestPermissions(
                    context,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    REQUEST_PHONE_CALL_PERMISSION
                )
            } else {
                Log.e("ExternalRepositoryImpl", "problem")
            }
        }

        if (intent.resolveActivity(context.packageManager) == null) {
            Log.e("ExternalRepositoryImpl", "No app found to handle the intent")
            Toast.makeText(context, R.string.no_maps_app_found, Toast.LENGTH_SHORT).show()
        } else {
            Log.d("ExternalRepositoryImpl", "Intent resolved, opening activity")
            context.startActivity(intent)
        }
    }

    override fun openUrl(url: String) {
        val validUrl = if (!url.startsWith("http://") && !url.startsWith("https://")) {
            "https://$url"
        } else {
            url
        }

        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse(validUrl)).setFlags(FLAG_ACTIVITY_NEW_TASK)

        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            Toast.makeText(context, R.string.no_browser_app_found, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val REQUEST_PHONE_CALL_PERMISSION = 1
    }
}



