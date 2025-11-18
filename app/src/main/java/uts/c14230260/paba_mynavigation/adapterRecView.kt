package uts.c14230260.paba_mynavigation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class adapterRecView(private val listBahan: ArrayList<dcBahan>) : RecyclerView.Adapter<adapterRecView.ListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.bahan_recycler, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ListViewHolder,
        position: Int
    ) {
        val bahan = listBahan[position]
        holder._namaBahan.setText(bahan.nama)
        holder._kategoriBahan.setText(bahan.kategori)
        Picasso.get()
            .load(bahan.linkGambar)
            .resize(200, 200)
            .centerCrop()
            .into(holder._gambarBahan)
        holder._btnAddToCart.setOnClickListener {
            // Add to cart bahan
        }
    }

    override fun getItemCount(): Int {
        return listBahan.size
    }

    class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val _namaBahan = view.findViewById<TextView>(R.id.tvBahan)
        val _kategoriBahan = view.findViewById<TextView>(R.id.tvKategori)
        val _gambarBahan = view.findViewById<ImageView>(R.id.ivGambarBahan)
        val _btnAddToCart = view.findViewById<Button>(R.id.btnAddToCart)
    }

    // Tambah fungsionalitas ke add to cart button(?)
}