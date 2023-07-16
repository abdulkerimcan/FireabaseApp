package com.sample.firebaseapp.profile


import android.app.Application
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport.Session.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.sample.firebaseapp.helpers.FirebaseHelper
import com.sample.firebaseapp.model.UserModel

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>()
    private var selectedUser: UserModel? = null
    private var currentUserId: String? = null
    private var storage = Firebase.storage
    private var databaseReference: DatabaseReference = Firebase.database.reference
    init {
        currentUserId =  Firebase.auth.currentUser?.uid
    }

    fun setUser(selectedUser: UserModel?) {
        this.selectedUser = selectedUser
    }

    fun getUser(): UserModel? {
        return selectedUser
    }


    fun isCurrentUser(): Boolean {
        return currentUserId == getUser()?.userId
    }
    fun uploadPicture(id: String,uri: Uri) {

        storage.getReference("images").child(id).putFile(uri).addOnSuccessListener {task ->
                task.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                    selectedUser?.imageUrl = it.toString()
                    val map = mapOf(
                        "name" to selectedUser?.name,
                        "surName" to selectedUser?.surName,
                        "userId" to selectedUser?.userId,
                        "imageUrl" to selectedUser?.imageUrl,
                        "email" to selectedUser?.email
                    )
                    databaseReference.child("Users").child(id).updateChildren(map).addOnSuccessListener {
                        Toast.makeText(context,"Succes",Toast.LENGTH_SHORT).show()
                    }
                }
            }


    }
}