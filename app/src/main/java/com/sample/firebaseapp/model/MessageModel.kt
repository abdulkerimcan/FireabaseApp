package com.sample.firebaseapp.model

import java.io.Serializable

data class MessageModel(
    var userName: String?,
    var userId: String?,
    var message: String?,
    var messageTime: String?,
    var messageId: String?
)  {
    constructor(): this("","","","","")
}
