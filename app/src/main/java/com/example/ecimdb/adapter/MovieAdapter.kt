package com.example.ecimdb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecimdb.R
import com.example.ecimdb.model.MovieModel

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var mvnList: ArrayList<MovieModel> = ArrayList()
    private var onClickItem: ((MovieModel) -> Unit)? = null
    private var onClickDeleteItem: ((MovieModel) -> Unit)? = null

    fun addItems(items: ArrayList<MovieModel>) {
        this.mvnList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (MovieModel) -> Unit) {
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (MovieModel) -> Unit) {
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_movie, parent, false)
    )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val mvn = mvnList[position]
        holder.bindView(mvn)
        holder.itemView.setOnClickListener { onClickItem?.invoke(mvn) }
        holder.btnDelete.setOnClickListener { onClickDeleteItem?.invoke(mvn) }
    }

    override fun getItemCount(): Int {
        return mvnList.size
    }

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var id = view.findViewById<TextView>(R.id.tvId)
        private var name = view.findViewById<TextView>(R.id.tvTitle)
        private var note = view.findViewById<TextView>(R.id.tvNote)
        var btnDelete = view.findViewById<Button>(R.id.btnDelete)

        fun bindView(mvn: MovieModel) {
            id.text = mvn.id.toString()
            name.text = mvn.name
            note.text = mvn.note
        }
    }
}