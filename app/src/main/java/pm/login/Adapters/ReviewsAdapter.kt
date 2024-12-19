package pm.login.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import pm.login.R
import pm.login.model.Review

class ReviewsAdapter(private val reviews: List<Review>) :
    RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieName: TextView = itemView.findViewById(R.id.textViewMovieName)
        val username: TextView = itemView.findViewById(R.id.textViewUsername)
        val reviewText: TextView = itemView.findViewById(R.id.textViewReviewText)
        val score: TextView = itemView.findViewById(R.id.textViewScore)
        val createdAt: TextView = itemView.findViewById(R.id.textViewCreatedAt)
        val userPhoto: ImageView = itemView.findViewById(R.id.imageViewUserPhoto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]

        holder.movieName.text = review.movieName
        holder.username.text = review.username
        holder.reviewText.text = review.reviewText
        holder.score.text = "Nota: ${review.score}"
        holder.createdAt.text = review.createdAt

        // Usando Picasso para carregar a imagem
        Picasso.get()
            .load(review.photo)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .into(holder.userPhoto)
    }

    override fun getItemCount(): Int = reviews.size
}
