package br.com.creativedevmind.padv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdvogadoListaAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<AdvogadoListaAdapter.AdvogadoViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var advogados = emptyList<Advogado>() // Cached copy of words
    inner class AdvogadoViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val advogadoItemView: TextView = itemView.findViewById(R.id.tvProduto)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            AdvogadoViewHolder {
        val itemView = inflater.inflate(R.layout.advogado_item, parent, false)
        return AdvogadoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdvogadoViewHolder, position: Int) {
        val current = advogados[position]
        holder.advogadoItemView.text = current.nome
    }

    override fun getItemCount() = advogados.size
}