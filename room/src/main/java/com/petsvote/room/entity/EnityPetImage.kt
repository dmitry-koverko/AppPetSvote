package com.petsvote.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class EnityPetImage(
    @PrimaryKey
    val id: Int? = null,

    @ColumnInfo(name = "imagePet",typeAffinity = ColumnInfo.BLOB)
    val imagePet: ByteArray = byteArrayOf(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EnityPetImage

        if (!imagePet.contentEquals(other.imagePet)) return false

        return true
    }

    override fun hashCode(): Int {
        return imagePet.contentHashCode()
    }
}