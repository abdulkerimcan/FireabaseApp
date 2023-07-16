package com.sample.firebaseapp.dashboard

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sample.firebaseapp.chat.ui.GroupChatActivity
import com.sample.firebaseapp.databinding.ActivityDashBoardBinding
import com.sample.firebaseapp.ui.register.RegisterActivity
import java.io.ByteArrayOutputStream

class DashBoardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashBoardBinding
    private val viewModel: DashboardViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashBoardBinding.inflate(layoutInflater)

        intent?.let {
            if (it.hasExtra("userName")) {
                viewModel.setUserName(it.extras?.getString("userName"))
            }

            if (it.hasExtra("userEmail")) {
                viewModel.setEmail(it.extras?.getString("userEmail"))
            }
        }

        setUI()
        setContentView(binding.root)
    }

    private fun setUI() {
        binding.logoutButton.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this@DashBoardActivity, RegisterActivity::class.java))
        }
        binding.chatButton.setOnClickListener {
            startActivity(Intent(this@DashBoardActivity, GroupChatActivity::class.java))
        }
        setLoggedInUI()
    }

    private fun setLoggedInUI() {
        binding.logoutButton.visibility = View.VISIBLE
        binding.logoutButton.isEnabled = true
        binding.userNameTextViewValue.text = viewModel.getUserName()
        binding.userEmailTextViewValue.text = viewModel.getEmail()
    }


}