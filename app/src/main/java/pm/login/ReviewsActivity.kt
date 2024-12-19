package pm.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONArray
import org.json.JSONObject
import pm.login.Adapters.ReviewsAdapter
import pm.login.model.Review

class ReviewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewReviews)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val url = "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g35/api/reviews.php"
        val queue = Volley.newRequestQueue(this)

        // Requisição para obter as críticas
        val stringRequest = StringRequest(url, { response ->
            val reviewsArray = JSONObject(response).getJSONArray("reviews")
            val reviewsList = convertJsonArrayToReviewList(reviewsArray) // Conversão
            recyclerView.adapter = ReviewsAdapter(reviewsList)
        }, { error ->
            //erro
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        })

        queue.add(stringRequest)

        // menu inferior
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.selectedItemId = R.id.nav_reviews

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, UserActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_reviews -> true
                else -> false
            }
        }
    }

    // Função para converter JSONArray em lista de Reviews
    private fun convertJsonArrayToReviewList(jsonArray: JSONArray): List<Review> {
        val reviewsList = mutableListOf<Review>()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val review = Review(
                movieName = jsonObject.getString("movieName"),
                userId = jsonObject.getInt("userId"),
                username = jsonObject.getString("username"),
                photo = jsonObject.getString("photo"),
                reviewText = jsonObject.getString("reviewText"),
                score = jsonObject.getDouble("score"),
                createdAt = jsonObject.getString("createdAt")
            )
            reviewsList.add(review)
        }
        return reviewsList
    }
}
