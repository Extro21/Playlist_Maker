package com.practicum.playlistmarket.search.data

enum class SearchStatus(val nameStatus : String) {
    NO_INTERNET("Проблемы со связью\n\nЗагрузка не удалась. Проверьте подключение к интернету"),
    NOTHING_FOUND("Ничего не нашлось")
}