package com.example.roomdb_martryatussofia_20

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdb_martryatussofia_20.room.Buku
import kotlinx.android.synthetic.main.adapter_buku.view.*

class BukuAdapter (private val bukuu: ArrayList<Buku>, private val listener: OnAdapterListener)
    : RecyclerView.Adapter<BukuAdapter.BukuViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BukuViewHolder {
        return BukuViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_buku, parent, false)
        )
    }

    override fun getItemCount() = bukuu.size

    override fun onBindViewHolder(holder:BukuViewHolder, position: Int) {
        val buku = bukuu[position]
        holder.view.text_title.text = buku.title
        holder.view.text_title.setOnClickListener{
            listener.onClick(buku)
        }
        holder.view.icon_edit.setOnClickListener{
            listener.onUpdate(buku)
        }
        holder.view.icon_delete.setOnClickListener{
            listener.onDelete(buku)
        }
    }
    class BukuViewHolder (val view : View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Buku>){
        bukuu.clear()
        bukuu.addAll(list)
        notifyDataSetChanged()
    }
    interface OnAdapterListener {
        fun onClick(buku: Buku)
        fun onUpdate(buku: Buku)
        fun onDelete(buku: Buku)
    }
}