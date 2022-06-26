package com.petsvote.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.petsvote.room.converters.*
import com.petsvote.room.dao.*
import com.petsvote.room.entity.*
import com.petsvote.room.entity.breeds.EntityBreedList
import com.petsvote.room.entity.filter.EntityRatingFilter
import com.petsvote.room.entity.user.EntityUserInfo
import com.petsvote.room.entity.user.EntityUserPet


@Database(
    entities = arrayOf(
        EntityUserInfo::class,
        EntityUserPet::class,
        EntityLocation::class,
        EntityPhoto::class,
        EntityRatingFilter::class,
        EntityBreed::class,
        EntityBreedList::class,
        EntityUserProfile::class,
        EntityPetProfile::class,
        EnityPetImage::class
    ), version = 13, exportSchema = false
)
@TypeConverters(
    PhotoConverter::class,
    UserPetConverter::class,
    LocationConverter::class,
    BreedsConverter::class,
    PetImageConverter::class
)
public abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun ratingFilterDao(): RatingFilterDao
    abstract fun breedsDao(): BreedsDao
    abstract fun imagesDao(): UserProfileDao
    abstract fun petProfileDao(): PetProfileDao

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