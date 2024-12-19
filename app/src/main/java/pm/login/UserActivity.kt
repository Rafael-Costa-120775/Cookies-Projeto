package pm.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import pm.login.databinding.ActivityUserBinding
import pm.login.fragments.HomeFragment
import pm.login.fragments.MoviesFragment
import pm.login.fragments.ProfileFragment
import pm.login.fragments.ReviewsFragment

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.nav_reviews -> {
                    replaceFragment(ReviewsFragment())
                    true
                }
                R.id.nav_movies -> {
                    replaceFragment(MoviesFragment())
                    true
                }
                R.id.nav_perfil -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }



        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
