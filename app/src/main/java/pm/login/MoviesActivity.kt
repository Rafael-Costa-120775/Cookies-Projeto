package pm.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONArray
import org.json.JSONObject
import pm.login.Adapters.MoviesAdapter
import pm.login.model.Movie

class MoviesActivity : AppCompatActivity() {

    // Criando o handler
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewMovies)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val url = "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g35/api/movies.php"
        val queue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(url, { response ->
            val moviesArray = JSONObject(response).getJSONArray("search")
            val moviesList = convertJsonArrayToMovieList(moviesArray)

            // Usando o handler para atualizar o RecyclerView
            handler.post {
                recyclerView.adapter = MoviesAdapter(moviesList)
            }
        }, { error ->
            handler.post {
                Toast.makeText(this, "Erro ao carregar os filmes. Tente novamente.", Toast.LENGTH_LONG).show()
            }
        })

        queue.add(stringRequest)

        // menu inferior
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.selectedItemId = R.id.nav_movies

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, UserActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_movies -> true
                else -> false
            }
        }
    }

    private fun convertJsonArrayToMovieList(jsonArray: JSONArray): List<Movie> {
        val moviesList = mutableListOf<Movie>()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val movie = Movie(
                title = jsonObject.getString("Title"),
                year = jsonObject.getString("Year"),
                imdbID = jsonObject.getString("imdbID"),
                type = jsonObject.getString("Type"),
                poster = jsonObject.optString("Poster", "N/A")
            )
            moviesList.add(movie)
        }
        return moviesList
    }
}
