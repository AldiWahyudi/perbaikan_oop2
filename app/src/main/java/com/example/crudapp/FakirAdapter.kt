package com.example.crudapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crudapp.Database.Fakir
import kotlinx.android.synthetic.main.adapter_fakir.view.*

class FakirAdapter (private val allFakir: ArrayList<Fakir>, private val listener: OnAdapterListener) : RecyclerView.Adapter<FakirAdapter.FakirViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FakirViewHolder {
        return FakirViewHolder(
            LayoutInflater.from(parent.context).inflate( R.layout.adapter_fakir, parent, false)
        )
    }

    override fun getItemCount() = allFakir.size

    override fun onBindViewHolder(holder: FakirViewHolder, position: Int) {
        val fakirm = allFakir[position]
        holder.view.text_nama.text = fakirm.nama
        holder.view.text_nama.setOnClickListener {
            listener.onClick(fakirm)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(fakirm)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(fakirm)
        }
    }

    class FakirViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Fakir>) {
        allFakir.clear()
        allFakir.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(fakir: Fakir)
        fun onDelete(fakir: Fakir)
        fun onUpdate(fakir: Fakir)
    }
}