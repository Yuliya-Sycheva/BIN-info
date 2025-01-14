package com.example.bin_info.common.util

object Functions {
    fun formatCoordinates(latitude: String?, longitude: String?): String {
        return if (latitude != null && longitude != null) {
            "Lat: $latitude, Lon: $longitude"
        } else {
            "-"
        }
    }
}