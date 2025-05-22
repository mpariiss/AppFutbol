package com.example.appfutbol.adapter




import android.content.Context
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.appfutbol.R
import com.example.appfutbol.db.DBHelper
import com.example.appfutbol.model.Match

class MatchAdapter(private val matches: List<Match>, private val context: Context) :
    RecyclerView.Adapter<MatchAdapter.MatchViewHolder>() {

    class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTeams: TextView = view.findViewById(R.id.tv_teams)
        val tvDate: TextView = view.findViewById(R.id.tv_date)
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

        holder.itemView.setOnClickListener {
            // Agregar el partido a favoritos cuando se haga clic
            DBHelper(context).addFavorite(match)
            Toast.makeText(context, "Agregado a favoritos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = matches.size
}
