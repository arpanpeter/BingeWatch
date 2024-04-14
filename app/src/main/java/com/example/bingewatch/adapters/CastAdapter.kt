package com.example.bingewatch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.bingewatch.R
import com.example.bingewatch.models.Cast
//import com.example.newsprojectpractice.R

class CastAdapter(private var castList: List<Cast>) : RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cast, parent, false)
        return CastViewHolder(view)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val cast = castList[position]
        holder.bind(cast)
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    fun updateData(newCastList: List<Cast>) {
        castList = newCastList
        notifyDataSetChanged()
    }

    class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val profileImage: ImageView = itemView.findViewById(R.id.image_cast)
        private val nameTextView: TextView = itemView.findViewById(R.id.cast_name)
      //  private val characterTextView: TextView = itemView.findViewById(R.id.character_text_view)

        fun bind(cast: Cast) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w500${cast.profilePath}")
                .transform(CircleCrop())
                .placeholder(R.drawable.movie_icon) // Placeholder image resource
                .into(profileImage)

            nameTextView.text = cast.name
        }
    }
}
