package com.petsvote.vote.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.petsvote.core.BaseViewModel
import com.petsvote.domain.usecases.resources.GetStringResourcesUseCase
import com.petsvote.domain.usecases.user.IGetUserPetsUseCase
import com.petsvote.domain.usecases.user.IGetUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class BonusVoteViewModel @Inject constructor(
    private val userPetsUserCase: IGetUserPetsUseCase,
    private val getUserUseCase: IGetUserUseCase,
    private val getStringResourcesUseCaseImpl: GetStringResourcesUseCase
) : BaseViewModel() {

    var petImage = MutableStateFlow<String>("")
    var description = MutableStateFlow<String>("")

    fun getUserPet(){

        viewModelScope.launch (Dispatchers.IO) {
            var user = async { getUserUseCase.getUser() }.await()
            userPetsUserCase.getUserPets().collect {
                if(it.isNotEmpty()) {
                    it[0].photos?.get(0)?.let { it1 -> petImage.emit(it1.url) }

                    var desc = when(it.size){
                        1 -> {
                            var textS1 = if(it[0].sex == "FEMALE") getStringResourcesUseCaseImpl.getString("bonus_one_pet_female")
                            else getStringResourcesUseCaseImpl.getString("bonus_one_pet_male")
                            textS1.replace("ddUNAME", "${user.first_name}").replace("ddNAME", "${it[0].name}")
                        }

                        2 ->{
                            var names = "${it[0].name}, ${it[1].name}"
                            var textS1 = getStringResourcesUseCaseImpl.getString("bonus_two_pets")
                            textS1.replace("ddUNAME", "${user.first_name}").replace("ddNAME", "$names")
                        }

                        else -> {
                            var textS1 = getStringResourcesUseCaseImpl.getString("bonus_all_pets", user.first_name ?: "")
                            textS1.replace("ddUNAME", "${user.first_name}")
                        }
                    }
                    description.emit(desc)
                }
            }
        }

    }

    /*
    user.first_name ?: "", it[0].name ?: ""
    user.first_name ?: "", it[0].name ?: ""
     */

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val userPetsUserCase: IGetUserPetsUseCase,
        private val getUserUseCase: IGetUserUseCase,
        private val getStringResourcesUseCaseImpl: GetStringResourcesUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == BonusVoteViewModel::class.java)
            return BonusVoteViewModel(
                userPetsUserCase = userPetsUserCase,
                getUserUseCase = getUserUseCase,
                getStringResourcesUseCaseImpl = getStringResourcesUseCaseImpl
            ) as T
        }

    }

}