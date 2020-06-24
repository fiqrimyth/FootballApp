package id.fiqridhan.footballapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.fiqridhan.footballapp.R
import id.fiqridhan.footballapp.model.League
import kotlinx.android.synthetic.main.item_club.view.*

class ClubAdapter (private val context: Context?, private val myClub:List<League>, private val listener: (League) -> Unit)
    :RecyclerView.Adapter<ClubViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_club, parent, false)
        return ClubViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClubViewHolder, position: Int) {
        holder.bindItem(myClub[position],listener)
    }

    override fun getItemCount(): Int = myClub.size

}
class ClubViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private var view : View = view

    fun bindItem(myClub: League,  listener: (League) -> Unit){
        view.tv_name_club.text = myClub.teamName
        view.tv_description.text = myClub.teamDescription
        Glide.with(itemView.context).load(myClub.teamBadge).into(view.img_club)
        itemView.setOnClickListener { listener(myClub) }
    }
}