package pm.login.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import pm.login.Adapters.ReviewsAdapter
import pm.login.databinding.FragmentReviewsBinding
import pm.login.model.Review

class ReviewsFragment : Fragment() {

    private var _binding: FragmentReviewsBinding? = null
    private val binding get() = _binding!!
    private val reviewsList = mutableListOf<Review>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReviewsBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewReviews.layoutManager = LinearLayoutManager(requireContext())
        val adapter = ReviewsAdapter(reviewsList)
        binding.recyclerViewReviews.adapter = adapter

        fetchReviewsData { reviews ->
            reviewsList.clear()
            reviewsList.addAll(reviews)
            adapter.notifyDataSetChanged()
        }
    }


    private fun fetchReviewsData(onSuccess: (List<Review>) -> Unit) {
        val url = "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g35/api/reviews.php"
        val requestQueue = Volley.newRequestQueue(requireContext())

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val reviewsJsonArray = response.getJSONArray("reviews")
                val reviews = mutableListOf<Review>()

                for (i in 0 until reviewsJsonArray.length()) {
                    val reviewJson = reviewsJsonArray.getJSONObject(i)
                    reviews.add(
                        Review(
                            movieName = reviewJson.optString("movie_name", "Unknown"),
                            userId = reviewJson.getInt("user_id"),
                            username = reviewJson.getString("username"),
                            photo = reviewJson.getString("photo"),
                            reviewText = reviewJson.getString("review_text"),
                            score = reviewJson.getDouble("score"),
                            createdAt = reviewJson.getString("created_at")
                        )
                    )
                }
                onSuccess(reviews)
            },
            { error ->
                Toast.makeText(requireContext(), "Erro ao carregar reviews: $error", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(jsonObjectRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
