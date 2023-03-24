package com.emberstudio.exodus.player

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor() : ViewModel() {
    private val youtubeUrl = "https://youtu.be/1wkPMUZ9vX4"

    private val videoList = buildList {
        add("https://storage.googleapis.com/exoplayer-test-media-0/play.mp3")
        add("https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4")
        add("https://www.youtube.com/api/manifest/dash/id/bf5bb2419360daf1/source/youtube?as=fmp4_audio_clear,fmp4_sd_hd_clear&sparams=ip,ipbits,expire,source,id,as&ip=0.0.0.0&ipbits=0&expire=19000000000&signature=51AF5F39AB0CEC3E5497CD9C900EBFEAECCCB5C7.8506521BFC350652163895D4C26DEE124209AA9E&key=ik0")
    }


    fun getVideoLink(selection: Int) : String{
        if(selection < 0) return ""
        if(selection >= videoList.size) return ""
        return videoList[selection]
    }
}
