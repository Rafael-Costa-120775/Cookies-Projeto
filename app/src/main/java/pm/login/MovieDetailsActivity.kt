package pm.login

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONObject

class MovieDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val title = intent.getStringExtra("movieTitle") ?: return

        val titleTextView = findViewById<TextView>(R.id.movieDetailsTitle)
        val yearTextView = findViewById<TextView>(R.id.movieDetailsYear)
        val genreTextView = findViewById<TextView>(R.id.movieDetailsGenre)
        val plotTextView = findViewById<TextView>(R.id.movieDetailsPlot)
        val posterImageView = findViewById<ImageView>(R.id.movieDetailsPoster)

        val url = "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g35/api/movies_info.php?title=${title}"

        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(url, { response ->
            val movieObject = JSONObject(response)

            titleTextView.text = movieObject.optString("Title", "Título indisponível")
            yearTextView.text = "Ano: ${movieObject.optString("Year", "N/A")}"
            genreTextView.text = "Género: ${movieObject.optString("Genre", "N/A")}"
            plotTextView.text = "Descrição: ${movieObject.optString("Plot", "Descrição indisponível")}"

            val posterUrl = movieObject.optString("Poster", "N/A")
            if (posterUrl != "N/A") {
                Picasso.get().load(posterUrl).into(posterImageView)
            }
        }, { error ->
            Toast.makeText(this, "Erro ao carregar detalhes do filme.", Toast.LENGTH_LONG).show()
        })

        queue.add(stringRequest)
    }
}
