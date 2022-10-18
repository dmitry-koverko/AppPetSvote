package com.petsvote.app.notification

import android.content.Context
import com.google.firebase.messaging.RemoteMessage
import com.petsvote.app.R
import com.petsvote.app.notification.dto.PetInfoNotification
import com.petsvote.app.notification.dto.PetNotification
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun RemoteMessage.isVoteNotify(): Boolean{
    return this.data.containsKey(REMOTE_MESSAGE_ARGS)
}

fun RemoteMessage.toLocalBigNotifyMessage(context: Context): PetInfoNotification? {
    val dataMessage = this.data
    if(dataMessage.containsKey(REMOTE_MESSAGE_ARGS)){
        val args = dataMessage[REMOTE_MESSAGE_ARGS]
        val argsJson  = Json.decodeFromString<PetInfoNotification>(args ?: "")
        return PetInfoNotification(
            argsJson.title,
            context.getString(R.string.notify_vote_description, argsJson.vote),
            argsJson.vote,
            argsJson.pet_id,
            argsJson.image
        )
    }else return null
}

fun RemoteMessage.toLocalNotifyMessage(): PetNotification? {
    return PetNotification(
        this.data[REMOTE_MESSAGE_TITLE] ?: "",
        this.data[REMOTE_MESSAGE_BODY] ?: ""
    )
}

const val REMOTE_MESSAGE_ARGS = "loc-args"
const val REMOTE_MESSAGE_TITLE = "title"
const val REMOTE_MESSAGE_VOTE = "vote"
const val REMOTE_MESSAGE_PET_ID = "pet_id"
const val REMOTE_MESSAGE_IMAGE = "image"
const val REMOTE_MESSAGE_BODY = "body"