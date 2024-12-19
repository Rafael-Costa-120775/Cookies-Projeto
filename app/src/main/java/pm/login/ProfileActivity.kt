package pm.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class ProfileActivity : AppCompatActivity() {


    private lateinit var profileImageView: ImageView
    private lateinit var bannerImageView: ImageView
    private lateinit var descriptionTextView: TextView
    private lateinit var logoutButton: Button

    private val profileImageUrl = "https://example.com/user_profile_image.jpg"
    private val bannerImageUrl = "https://example.com/user_banner_image.jpg"
    private val userDescription = "teste"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Inicializar Views
        profileImageView = findViewById(R.id.profileImageView)
        bannerImageView = findViewById(R.id.bannerImageView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        logoutButton = findViewById(R.id.logoutButton)

        loadUserData()
        logoutButton.setOnClickListener {
            performLogout()
        }
    }

    private fun loadUserData() {
        Picasso.get()
            .load(profileImageUrl)
            .placeholder(R.drawable.ic_banner_placeholder)
            .error(R.drawable.ic_banner_placeholder)
            .into(profileImageView)

        Picasso.get()
            .load(bannerImageUrl)
            .placeholder(R.drawable.ic_banner_placeholder)
            .error(R.drawable.ic_banner_placeholder)
            .into(bannerImageView)

        // Carregar descrição
        descriptionTextView.text = userDescription
    }

    // Logout
    private fun performLogout() {
        val sharedPreferences = getSharedPreferences("pmLogin", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finishAffinity()
    }

}
