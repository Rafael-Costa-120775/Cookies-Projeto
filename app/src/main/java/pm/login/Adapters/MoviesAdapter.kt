package pm.login.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import pm.login.MovieDetailsActivity
import pm.login.R
import pm.login.model.Movie

class MoviesAdapter(private val moviesList: List<Movie>) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.movieTitle)
        val yearTextView: TextView = itemView.findViewById(R.id.movieYear)
        val posterImageView: ImageView = itemView.findViewById(R.id.moviePoster)
        val noPosterTextView: TextView = itemView.findViewById(R.id.noPosterText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = moviesList[position]
        val context = holder.itemView.context
        holder.titleTextView.text = movie.title
        holder.yearTextView.text = movie.year
        if (movie.poster != "N/A") {
            holder.noPosterTextView.visibility = View.GONE
            holder.posterImageView.visibility = View.VISIBLE
            Picasso.get().load(movie.poster).into(holder.posterImageView)
        } else {
            holder.posterImageView.visibility = View.GONE
            holder.noPosterTextView.visibility = View.VISIBLE
            holder.noPosterTextView.text = "Poster não disponível"
        }

        holder.itemView.setOnClickListener {
            Toast.makeText(context, "detalhe: ${movie.title}", Toast.LENGTH_SHORT).show()

            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra("movieTitle", movie.title)
            intent.putExtra("movieId", movie.imdbID)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = moviesList.size


}
