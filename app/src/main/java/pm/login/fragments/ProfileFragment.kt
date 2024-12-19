package pm.login.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import pm.login.MainActivity
import pm.login.R

class ProfileFragment : Fragment() {

    // URLs simuladas (substitua com dados reais)
    private val profileImageUrl = "https://example.com/user_profile_image.jpg"
    private val bannerImageUrl = "https://example.com/user_banner_image.jpg"
    private val userDescription = "Olá! Eu sou um desenvolvedor apaixonado por criar apps incríveis."

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar o layout do fragmento
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Inicializar Views
        val profileImageView: ImageView = view.findViewById(R.id.profileImageView)
        val bannerImageView: ImageView = view.findViewById(R.id.bannerImageView)
        val descriptionTextView: TextView = view.findViewById(R.id.descriptionTextView)
        val logoutButton: Button = view.findViewById(R.id.logoutButton)

        loadUserData(profileImageView, bannerImageView, descriptionTextView)

        logoutButton.setOnClickListener {
            //Limpa as cenas do login
            val sharedPreferences = requireContext().getSharedPreferences("pmLogin", Context.MODE_PRIVATE)
            sharedPreferences.edit().clear().apply()

            // Redirecionar para MainActivity
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            requireActivity().finishAffinity()
        }


        return view
    }

    private fun loadUserData(
        profileImageView: ImageView,
        bannerImageView: ImageView,
        descriptionTextView: TextView
    ) {
        // Carregar imagem de perfil
        Picasso.get()
            .load(profileImageUrl)
            .placeholder(R.drawable.ic_banner_placeholder)
            .into(profileImageView)

        // Carregar banner
        Picasso.get()
            .load(bannerImageUrl)
            .placeholder(R.drawable.ic_banner_placeholder)
            .into(bannerImageView)

        // Carregar descrição
        descriptionTextView.text = userDescription
    }
}
