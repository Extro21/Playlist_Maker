package com.practicum.playlistmarket.media.domain.module


data class PlayList(
     val playListId : Int,
     val name : String,
     val description : String?,
     val uri : String?,
     var quantityTracks : Int = 0,
 )
