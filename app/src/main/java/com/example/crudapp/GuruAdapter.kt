package com.example.crudapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crudapp.Database.Guru
import kotlinx.android.synthetic.main.adapter_user.view.*

class GuruAdapter (private val allGuru: ArrayList<Guru>, private val listener: OnAdapterListener) : RecyclerView.Adapter<GuruAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate( R.layout.adapter_user, parent, false)
        )
    }

    override fun getItemCount() = allGuru.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val guru = allGuru[position]
        holder.view.text_jabatan.text = guru.jabatan
        holder.view.text_jabatan.setOnClickListener {
            listener.onClick(guru)
        }
        holder.view.icon_deleteGuru.setOnClickListener {
            listener.onDelete(guru)
        }
        holder.view.icon_editGuru.setOnClickListener {
            listener.onUpdate(guru)
        }
    }

    class UserViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Guru>) {
        allGuru.clear()
        allGuru.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(guru: Guru)
        fun onDelete(guru: Guru)
        fun onUpdate(guru: Guru)
    }

}