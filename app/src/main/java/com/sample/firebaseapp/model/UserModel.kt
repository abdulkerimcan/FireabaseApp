package com.sample.firebaseapp.model

import android.os.Parcel
import android.os.Parcelable

data class UserModel(
    var name: String?,
    var surName: String?,
    var userId: String?,
    var imageUrl: String?,
    var email: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    constructor() : this("", "", "", "", "")

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(surName)
        dest.writeString(userId)
        dest.writeString(imageUrl)
        dest.writeString(email)
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }
}
