package com.petsvote.room.entity.breeds

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.petsvote.room.converters.BreedsConverter
import com.petsvote.room.entity.EntityBreed
import org.jetbrains.annotations.NotNull

@Entity
data class EntityBreedList(
    @NotNull
    @PrimaryKey(autoGenerate = true)
    var idPet: Int? = 1,

    @TypeConverters(BreedsConverter::class)
    var breeds: List<EntityBreed> = listOf()
)