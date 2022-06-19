package com.petsvote.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class EntityUserProfile(
    @PrimaryKey
    var id: Int? = null,
    val type: Int? = null,

    @ColumnInfo(name = "image",typeAffinity = ColumnInfo.BLOB)
    val image: ByteArray? = byteArrayOf(),

    @ColumnInfo(name = "imageCrop",typeAffinity = ColumnInfo.BLOB)
    val imageCrop: ByteArray? = byteArrayOf(),

    val locationCountryTitle: String? = "",
    val locationCountryId: Int? = -1,
    val locationCityTitle: String? = "",
    val locationCityId: Int? = -1
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EntityUserProfile

        if (id != other.id) return false
        if (type != other.type) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false
        if (imageCrop != null) {
            if (other.imageCrop == null) return false
            if (!imageCrop.contentEquals(other.imageCrop)) return false
        } else if (other.imageCrop != null) return false
        if (locationCountryTitle != other.locationCountryTitle) return false
        if (locationCountryId != other.locationCountryId) return false
        if (locationCityTitle != other.locationCityTitle) return false
        if (locationCityId != other.locationCityId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (type ?: 0)
        result = 31 * result + (image?.contentHashCode() ?: 0)
        result = 31 * result + (imageCrop?.contentHashCode() ?: 0)
        result = 31 * result + (locationCountryTitle?.hashCode() ?: 0)
        result = 31 * result + (locationCountryId ?: 0)
        result = 31 * result + (locationCityTitle?.hashCode() ?: 0)
        result = 31 * result + (locationCityId ?: 0)
        return result
    }

}
