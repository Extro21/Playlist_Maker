package com.practicum.playlistmarket.player.data.repository

sealed class PlayerState(
    val isPlayButtonEnabled: Boolean,
    val buttonText: String,
    val progress: String
) {
    class Default : PlayerState(false, "PLAY", "00:00")
    class Prepared : PlayerState(true, "PLAY", "00:00")

//    class Playing(progress: String) :
//        PlayerState(true, "PAUSE", progress = progress)

    class Playing(progress: String) :
        PlayerState(true, "PAUSE", progress = progress)

    class Paused(progress: String) :
            PlayerState(true, "PLAY", progress)
}
