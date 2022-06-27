package com.petsvote.data.repository

import com.petsvote.data.mappers.*
import com.petsvote.domain.entity.configuration.UserProfile
import com.petsvote.domain.entity.user.RegisterUserParams
import com.petsvote.domain.entity.user.SaveUserParams
import com.petsvote.domain.entity.user.UserInfo
import com.petsvote.domain.entity.user.UserPet
import com.petsvote.domain.entity.user.location.City
import com.petsvote.domain.entity.user.location.Country
import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.usecases.configuration.GetLocaleLanguageCodeUseCase
import com.petsvote.retrofit.api.ApiInstagram
import com.petsvote.retrofit.api.UserApi
import com.petsvote.retrofit.entity.InstagramResponse
import com.petsvote.retrofit.entity.user.Location
import com.petsvote.retrofit.entity.user.Register
import com.petsvote.retrofit.entity.user.User
import com.petsvote.retrofit.entity.user.location.Cities
import com.petsvote.retrofit.entity.user.location.Countries
import com.petsvote.room.dao.UserDao
import com.petsvote.room.dao.UserProfileDao
import com.petsvote.room.entity.EntityUserProfile
import kotlinx.coroutines.flow.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val instagramApi: ApiInstagram,
    private val userApi: UserApi,
    private val userDao: UserDao,
    private val imagesDao: UserProfileDao,
    private val getLocaleLanguageCodeUseCase: GetLocaleLanguageCodeUseCase
) : IUserRepository {

    override suspend fun registerUser(params: RegisterUserParams): UserInfo? {
        return checkResult<Register>(userApi.registerUser(code = params.code))?.toUserInfoUC()
    }

    override suspend fun getUser(): UserInfo? {
        val user = checkResult<User>(
            userApi.getCurrentUser(userDao.getToken(), getLocaleLanguageCodeUseCase.getLanguage())
        )
            ?.toUserInfoUC()
        if (user != null) {
            var token = userDao.getToken()
            userDao.update(user.toLocalUser(token))
        }
        return user
    }

    override suspend fun getCurrentUser(): UserInfo {
        return userDao.getUser().toUserInfo()
    }

    override suspend fun getCountUserPets(): Int {
        return userDao.getUser().pet.size
    }

    override suspend fun getUserPets(): Flow<List<UserPet>> =
        flow {
            run {
                userDao.getUserFlow().collect {
                    var listPets: List<UserPet> = it.pet.toUserPets()
                    emit(listPets)
                }
            }
        }

    override suspend fun getSimpleUserPets(): List<UserPet> {
        return userDao.getUser().pet.toUserPets()
    }

    override suspend fun checkLoginUser(): Boolean {
        val user = userDao.getUser()
        return user != null
    }


    override suspend fun saveUserToLocal(user: UserInfo) {
        userDao.insert(user.toLocalUser())
    }

    override suspend fun getToken(): String {
        return userDao.getToken()
    }

    override suspend fun setImage(bytes: ByteArray) {
        imagesDao.updateImage(bytes)
    }

    override suspend fun getImage(): Flow<UserProfile> = flow {
        run {
            imagesDao.getImageFlow().collect {
                it?.toUserProfile()?.let { it1 -> emit(it1) }
            }
        }

    }

    override suspend fun getUserProfile(): UserProfile? {
        return imagesDao.getUserProfile()?.toUserProfile()
    }

    override suspend fun setImageCrop(bytes: ByteArray) {
        imagesDao.updateImageCrop(bytes)
    }

    override suspend fun setCountry(title: String, id: Int) {
        imagesDao.updateLocationCountryId(id, title)

    }

    override suspend fun setCity(title: String, id: Int, region: String) {
        imagesDao.updateLocationCityId(id, title, region)
    }

    override suspend fun getCountryList(): List<Country> {
        return checkResult<Countries>(
            userApi.getCountries(getLocaleLanguageCodeUseCase.getLanguage(), null)
        )?.countries?.toCountry() ?: listOf()
    }

    override suspend fun getCitiesList(): List<City> {
        var user = imagesDao.getUserProfile()
        var countryId = user?.locationCountryId
        return checkResult<Cities>(
            userApi.getCities(
                getLocaleLanguageCodeUseCase.getLanguage(),
                null,
                countryId,
                null,
                null
            )
        )?.cities?.toCity() ?: listOf()
    }

    override suspend fun setEmptyUserProfile() {
        imagesDao.update(
            EntityUserProfile(
                id = 1, null, byteArrayOf(), byteArrayOf(), null, null, null, null, null
            ))
    }

    override suspend fun saveUser(params: SaveUserParams) {

        var userProfile = imagesDao.getUserProfile()
        var user = userDao.getUser().toUserInfo()
        var mp: MultipartBody.Part? = null
        var locationStr: String? = null

        if(userProfile?.locationCountryId != null){
            var location = Location(
                city_id = userProfile.locationCityId ?: 1,
                country_id = userProfile.locationCountryId ?: 1,
                country = userProfile.locationCountryTitle?: "",
                city = userProfile.locationCityTitle ?: ""
            )
            locationStr = Json.encodeToString(location)
        }else if(user.location != null)
            locationStr = Json.encodeToString(user.location)

        userProfile?.let {
            if(userProfile.imageCrop?.isNotEmpty() == true){
                val reqFile: RequestBody = RequestBody.create(
                    "image/*".toMediaTypeOrNull(),
                    userProfile.imageCrop!!
                )

                mp = MultipartBody.Part.createFormData(
                    "photo_data",
                    null,  // filename, this is optional
                    reqFile
                )
            }
        }

        checkResult<User>(userApi.saveUserData(userDao.getToken(), mp, params.first_name, params.last_name, null))
    }

    override suspend fun getUsernameInsta(id: Long): String {
        var ua = "Mozilla/5.0 (Linux; Android 8.0.0; SM-A520F Build/R16NW; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/66.0.3359.158 Mobile Safari/537.36 Instagram 46.0.0.15.96 Android (26/8.0.0; 480dpi; 1080x1920; samsung; SM-A520F; a5y17lte; samsungexynos7880; pt_BR; 109556226)"
        return checkResult<InstagramResponse>(instagramApi.getUsername(ua, id))?.user?.username ?: ""
    }


}