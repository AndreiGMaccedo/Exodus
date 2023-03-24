package com.emberstudio.exodus.utils

import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes

inline fun getMediaItem(rawUrl: String) : MediaItem{
    return if (rawUrl.contains("youtube")) {
        MediaItem.Builder()
            .setUri(rawUrl)
            .setMimeType(MimeTypes.APPLICATION_MPD)
            .build()
    } else {
        MediaItem.Builder().setUri(rawUrl).build()
    }
}