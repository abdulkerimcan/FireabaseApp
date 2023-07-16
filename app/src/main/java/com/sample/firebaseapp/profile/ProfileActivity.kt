package com.sample.firebaseapp.profile

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport.Session.User
import com.sample.firebaseapp.R
import com.sample.firebaseapp.dashboard.DashboardViewModel
import com.sample.firebaseapp.databinding.ActivityDashBoardBinding
import com.sample.firebaseapp.databinding.ActivityProfileBinding
import com.sample.firebaseapp.model.UserModel
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    var sImage:String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)

        intent?.let {
            if (it.hasExtra("user")) {
                viewModel.setUser(it.getParcelableExtra<UserModel>("user"))
            }
        }

        setUI()
        setImage()
        setContentView(binding.root)
    }

    private fun setUI() {


        val user = viewModel.getUser()
        user?.let {
            binding.nameText.text = it.name
            binding.emailText.text = it.email
            Picasso.get().load(it.imageUrl).into(binding.profileImageView)

        }
    }

    private fun setImage() {
        if (viewModel.isCurrentUser()) {
            binding.profileImageView.setOnClickListener {
                var myFileIntent = Intent(Intent.ACTION_GET_CONTENT)
                myFileIntent.setType("image/*")
                ActivityResultLauncher.launch(myFileIntent)
            }
        }
    }
    private  val ActivityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) {result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val uri = result.data?.data
            try {
                if(uri != null) {
                    val user = viewModel.getUser()
                    user?.userId?.let {

                        viewModel.uploadPicture(it,uri)
                    }
                }
            }catch(exception: Exception) {
                Toast.makeText(this,exception.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}