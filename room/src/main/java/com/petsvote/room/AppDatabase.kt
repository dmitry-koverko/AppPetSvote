package com.petsvote.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.petsvote.room.converters.LocationConverter
import com.petsvote.room.converters.PhotoConverter
import com.petsvote.room.converters.UserPetConverter
import com.petsvote.room.dao.UserDao
import com.petsvote.room.entity.EntityLocation
import com.petsvote.room.entity.EntityPhoto
import com.petsvote.room.entity.user.EntityUserInfo
import com.petsvote.room.entity.user.EntityUserPet


@Database(
    entities = arrayOf(
        EntityUserInfo::class,
        EntityUserPet::class,
        EntityLocation::class,
        EntityPhoto::class
    ), version = 1, exportSchema = false
)
@TypeConverters(PhotoConverter::class, UserPetConverter::class, LocationConverter::class)
public abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}