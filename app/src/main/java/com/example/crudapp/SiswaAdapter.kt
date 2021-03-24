package com.example.crudapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crudapp.Database.Siswa
import kotlinx.android.synthetic.main.adapter_siswa.view.*

class SiswaAdapter (private val allSiswa: ArrayList<Siswa>, private val listener: OnAdapterListener) : RecyclerView.Adapter<SiswaAdapter.HelmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelmViewHolder {
        return HelmViewHolder(
            LayoutInflater.from(parent.context).inflate( R.layout.adapter_siswa, parent, false)
        )
    }

    override fun getItemCount() = allSiswa.size

    override fun onBindViewHolder(holder: HelmViewHolder, position: Int) {
        val siswa = allSiswa[position]
        holder.view.text_nama.text = siswa.nama
        holder.view.text_nama.setOnClickListener {
            listener.onClick(siswa)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(siswa)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(siswa)
        }
    }

    class HelmViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Siswa>) {
        allSiswa.clear()
        allSiswa.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(siswa: Siswa)
        fun onDelete(siswa: Siswa)
        fun onUpdate(siswa: Siswa)
    }
}