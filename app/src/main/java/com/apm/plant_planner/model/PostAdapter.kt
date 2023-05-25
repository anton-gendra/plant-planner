package com.apm.plant_planner.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apm.plant_planner.R
import org.w3c.dom.Text
import java.io.File

class PostAdapter(
    private val context: Context,
    private val posts:List<Post>
)

    : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private val titles = mutableListOf<String>()
    private val users = mutableListOf<String>()
    private val positions = mutableListOf<String>()
    private val images = mutableListOf<Bitmap>()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemTitle: TextView
        var itemUser: TextView
        var itemPosition: TextView

        init {
            itemImage=itemView.findViewById(R.id.item_image)
            itemTitle=itemView.findViewById(R.id.item_title)
            itemUser=itemView.findViewById(R.id.item_userValue)
            itemPosition=itemView.findViewById(R.id.item_PositionValue)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.card_view, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // check if plant name already exists
        for (p in posts) {
            titles.add(p.title)
            users.add(p.author.toString())
            positions.add(p.location.toString())
            val imageByteArray = Base64.decode(p.bitmap, Base64.DEFAULT)
            val bitmap2 = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
            if(bitmap2 == null) {
                continue
            } else {
                images.add(bitmap2)
            }
        }
        holder.itemTitle.text=titles[position]
        holder.itemUser.text=users[position]
        holder.itemPosition.text=positions[position]
        holder.itemImage.setImageBitmap(images[position])
    }


}