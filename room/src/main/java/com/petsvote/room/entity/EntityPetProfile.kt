package com.petsvote.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.petsvote.room.converters.PetImageConverter
import kotlinx.serialization.Serializable
import org.jetbrains.annotations.NotNull

@Entity
@Serializable
data class EntityPetProfile(
    @PrimaryKey
    var id: Int? = null,

    @ColumnInfo(name = "image",typeAffinity = ColumnInfo.BLOB)
    val image: ByteArray? = byteArrayOf(),

    @NotNull
    @TypeConverters(PetImageConverter::class)
    val imagesCrop: List<EnityPetImage> = emptyList(),

    val name: String? = "",
    val kindId: Int? = -1,
    val kindTitle: String? = "",
    val breedId: Int? = -1,
    val breedTitle: String? = "",
    var birthday: String,
    var sex: Int,
    var inst: String? = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EntityPetProfile

        if (id != other.id) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false
        if (imagesCrop != other.imagesCrop) return false
        if (name != other.name) return false
        if (kindId != other.kindId) return false
        if (kindTitle != other.kindTitle) return false
        if (breedId != other.breedId) return false
        if (breedTitle != other.breedTitle) return false
        if (birthday != other.birthday) return false
        if (sex != other.sex) return false
        if (inst != other.inst) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (image?.contentHashCode() ?: 0)
        result = 31 * result + imagesCrop.hashCode()
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (kindId ?: 0)
        result = 31 * result + (kindTitle?.hashCode() ?: 0)
        result = 31 * result + (breedId ?: 0)
        result = 31 * result + (breedTitle?.hashCode() ?: 0)
        result = 31 * result + birthday.hashCode()
        result = 31 * result + sex
        result = 31 * result + (inst?.hashCode() ?: 0)
        return result
    }

}