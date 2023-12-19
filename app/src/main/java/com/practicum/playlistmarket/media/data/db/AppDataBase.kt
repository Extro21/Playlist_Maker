package com.practicum.playlistmarket.media.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practicum.playlistmarket.media.data.db.dao.PlayListDao
import com.practicum.playlistmarket.media.data.db.dao.TrackDao
import com.practicum.playlistmarket.media.data.db.entity.PlayListEntity
import com.practicum.playlistmarket.media.data.db.entity.TrackEntity
import com.practicum.playlistmarket.media.data.db.entity.TrackPlayListEntity
import com.practicum.playlistmarket.media.data.db.entity.TrackPlaylist


@Database(
    version = 1,
    entities = [TrackEntity::class, PlayListEntity::class, TrackPlaylist::class, TrackPlayListEntity::class],
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun trackDao() : TrackDao
    abstract fun playListDao() : PlayListDao

//    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//        override fun migrate(database: SupportSQLiteDatabase) {
//            database.execSQL("ALTER TABLE")
//        }
//    }

}