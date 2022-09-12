package com.petsvote.rating

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.petsvote.domain.entity.user.UserPet
import kotlinx.coroutines.flow.*

class RatingEmptyViewModel(): ViewModel() {

    var ratingList = MutableStateFlow<List<EmptyObject>>(emptyList())
    val _ratingList: SharedFlow<List<EmptyObject>> = ratingList.asSharedFlow()

    val listObjects = mutableListOf<EmptyObject>()

    fun getRatingMore(lastPosition: Int){

    }

    fun generateList(){
        for (i in 0..10000){
            listObjects.add(EmptyObject(i, "Name $i"))
        }
    }

}