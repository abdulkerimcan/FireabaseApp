package com.sample.firebaseapp.dashboard

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>()
    private var userName: String? = null
    private var email: String? = null

    fun setUserName(userName: String?) {
        this.userName = userName
    }

    fun getUserName(): String? {
        return userName
    }

    fun setEmail(email: String?) {
        this.email = email
    }
    fun getEmail(): String? {
        return email
    }
}