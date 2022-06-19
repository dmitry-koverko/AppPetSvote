package com.petsvote.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class EntityImage(
    @PrimaryKey
    var id: Int? = null,
    val type: Int? = null,

    @ColumnInfo(name = "image",typeAffinity = ColumnInfo.BLOB)
    val image: ByteArray? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EntityImage

        if (id != other.id) return false
        if (type != other.type) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (type ?: 0)
        result = 31 * result + (image?.contentHashCode() ?: 0)
        return result
    }
}
