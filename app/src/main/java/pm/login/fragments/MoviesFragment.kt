package pm.login.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject
import pm.login.R

class MoviesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewMovies)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val url = "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g35/api/movies.php"
        val queue = Volley.newRequestQueue(requireContext())

        val stringRequest = StringRequest(url, { response ->
            val moviesArray = JSONObject(response).getJSONArray("Search")
            val moviesList = convertJsonArrayToMovieList(moviesArray)
            recyclerView.adapter = MoviesAdapter(moviesList)
        }, { error ->
            Toast.makeText(requireContext(), "Erro ao carregar os filmes. Tente novamente.", Toast.LENGTH_LONG).show()
        })

        queue.add(stringRequest)

        return view
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
                poster = jsonObject.optString("Poster", "N/A") // Trata casos onde o poster não está disponível
            )
            moviesList.add(movie)
        }
        return moviesList
    }

    // Modelo de dados para Movie
    data class Movie(
        val title: String,
        val year: String,
        val imdbID: String,
        val type: String,
        val poster: String
    )

    // Adaptador do RecyclerView para exibir os filmes
    class MoviesAdapter(private val moviesList: List<Movie>) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
            return MovieViewHolder(view)
        }

        override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
            val movie = moviesList[position]
            holder.bind(movie)
        }

        override fun getItemCount(): Int = moviesList.size

        class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val titleTextView: TextView = itemView.findViewById(R.id.movieTitle)
            private val yearTextView: TextView = itemView.findViewById(R.id.movieYear)
            private val posterImageView: ImageView = itemView.findViewById(R.id.moviePoster)
            private val noPosterTextView: TextView = itemView.findViewById(R.id.noPosterText)

            fun bind(movie: Movie) {
                titleTextView.text = movie.title
                yearTextView.text = movie.year
                if (movie.poster != "N/A") {
                    noPosterTextView.visibility = View.GONE
                    posterImageView.visibility = View.VISIBLE
                    Picasso.get().load(movie.poster).into(posterImageView)
                } else {
                    posterImageView.visibility = View.GONE
                    noPosterTextView.visibility = View.VISIBLE
                    noPosterTextView.text = "Poster não disponível"
                }
            }
        }
    }
}
