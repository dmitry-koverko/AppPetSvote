package com.petsvote.user

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.petsvote.core.BaseViewModel
import com.petsvote.domain.entity.user.SaveUserParams
import com.petsvote.domain.entity.user.UserPet
import com.petsvote.domain.usecases.configuration.*
import com.petsvote.domain.usecases.configuration.impl.GetImagesUseCase
import com.petsvote.domain.usecases.user.IGetUserPetsUseCase
import com.petsvote.domain.usecases.user.IGetUserUseCase
import com.petsvote.domain.usecases.user.impl.ISaveUserUseCase
import com.petsvote.domain.usecases.user.impl.SaveUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(
    private val getAddPhotosSettingsUseCase: IGetAddPhotosSettingsUseCase,
    private val setAddPhotosSettingsUseCase: ISetAddPhotosSettingsUseCase,
    private val getUserUseCase: IGetUserUseCase,
    private val getImagesUseCase: IGetImagesUseCase,
    private val setImageCropUseCase: ISetImageCropUseCase,
    private val saveUserProfileUseCase: ISaveUserUseCase
) : BaseViewModel() {

    var isAddPhoto = MutableStateFlow<Boolean>(true)
    var image = MutableStateFlow<ByteArray>(byteArrayOf())
    var imageUrl = MutableStateFlow<String?>("")
    var imageCrop = MutableStateFlow<Bitmap?>(null)
    var country = MutableStateFlow<String?>(null)
    var city = MutableStateFlow<String?>(null)
    var firstName = MutableStateFlow<String?>(null)
    var lastname = MutableStateFlow<String?>(null)

    fun getConfiguration() {

        viewModelScope.launch(Dispatchers.IO) {
            isAddPhoto.emit(getAddPhotosSettingsUseCase.getAddPhotosSettings())
        }

        viewModelScope.launch (Dispatchers.IO) {

            var jobUser = async { getUserUseCase.getUser() }.await()

            getImagesUseCase.getImage().collect {
                if(it.imageCrop?.isNotEmpty() == true){
                    val imageStream = ByteArrayInputStream(it.imageCrop)
                    val theImage = BitmapFactory.decodeStream(imageStream)
                    imageCrop.emit(theImage)
                }else {
                    imageUrl.emit(jobUser.avatar)
                }

                if(it.locationCountryId != null){
                    country.emit(it.locationCountryTitle)
                }else if(jobUser.location?.country_id != null && jobUser.location?.country_id != -1){
                    country.emit(jobUser.location?.country)
                }

                if(it.locationCityId == -1) city.emit(null)
                else if(it.locationCityId != null){
                    city.emit(it.locationCityTitle)
                }else if(jobUser.location?.city_id != null && jobUser.location?.city_id != -1){
                    city.emit(jobUser.location?.city)
                }
                firstName.emit(jobUser.first_name)
                lastname.emit(jobUser.last_name)
            }

        }
    }

    fun setAddPhotoDialog(){
        viewModelScope.launch (Dispatchers.IO) {
            setAddPhotosSettingsUseCase.setAddPhotosSettings()
        }
    }

    fun saveUserInfo(saveUserParams: SaveUserParams){
        viewModelScope.launch (Dispatchers.IO) {
            saveUserProfileUseCase.saveUser(saveUserParams)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val getAddPhotosSettingsUseCase: IGetAddPhotosSettingsUseCase,
        private val setAddPhotosSettingsUseCase: ISetAddPhotosSettingsUseCase,
        private val getUserUseCase: IGetUserUseCase,
        private val getImagesUseCase: IGetImagesUseCase,
        private val setImageCropUseCase: ISetImageCropUseCase,
        private val saveUserProfileUseCase: ISaveUserUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == UserProfileViewModel::class.java)
            return UserProfileViewModel(
                getAddPhotosSettingsUseCase = getAddPhotosSettingsUseCase,
                setAddPhotosSettingsUseCase = setAddPhotosSettingsUseCase,
                getUserUseCase = getUserUseCase,
                getImagesUseCase = getImagesUseCase,
                setImageCropUseCase = setImageCropUseCase,
                saveUserProfileUseCase = saveUserProfileUseCase
            ) as T
        }

    }

}