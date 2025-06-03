package com.example.appfutbol.adapter




import android.content.Context
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.appfutbol.R
import com.example.appfutbol.db.DBHelper
import com.example.appfutbol.model.Match

class MatchAdapter(
    private val matches: List<Match>,
    private val context: Context,
    private val onFavoriteChanged: ((Match, Boolean) -> Unit)? = null
) : RecyclerView.Adapter<MatchAdapter.MatchViewHolder>() {

    private val dbHelper = DBHelper(context)
    private val favoriteMatches = dbHelper.getFavorites().map { it.id }.toMutableSet()

    inner class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTeams: TextView = view.findViewById(R.id.tv_teams)
        val tvDate: TextView = view.findViewById(R.id.tv_date)
        val ivFavorite: ImageView = view.findViewById(R.id.iv_favorite)  // Añadido para el icono
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_match, parent, false)
        return MatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val match = matches[position]
        holder.tvTeams.text = "${match.homeTeam.name} vs ${match.awayTeam.name}"
        holder.tvDate.text = match.utcDate

        val isFavorite = favoriteMatches.contains(match.id)


        holder.ivFavorite.setImageResource(
            if (isFavorite) R.drawable.ic_favorite_check else R.drawable.ic_favorite
        )


        holder.ivFavorite.setOnClickListener {
            if (isFavorite) {
                dbHelper.removeFavorite(match.id)
                favoriteMatches.remove(match.id)
                holder.ivFavorite.setImageResource(R.drawable.ic_favorite)
                Toast.makeText(context, "Eliminado de favoritos", Toast.LENGTH_SHORT).show()
                onFavoriteChanged?.invoke(match, false)  // Notificamos que lo quitaron
            } else {
                dbHelper.addFavorite(match)
                favoriteMatches.add(match.id)
                holder.ivFavorite.setImageResource(R.drawable.ic_favorite_check)
                Toast.makeText(context, "Agregado a favoritos", Toast.LENGTH_SHORT).show()
                onFavoriteChanged?.invoke(match, true)   // Notificamos que lo agregaron
            }
        }

        // Quitamos click del item completo (opcional)
        holder.itemView.setOnClickListener(null)
    }

    override fun getItemCount() = matches.size
}

