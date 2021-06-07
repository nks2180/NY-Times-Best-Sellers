package com.nytimes.bestsellers.bestsellerbooks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nytimes.bestsellers.R
import com.nytimes.bestsellers.bestsellerbooks.viewstate.BestSellerBookViewState
import kotlinx.android.synthetic.main.row_bestseller_books.view.*

class BestSellersAdapter : RecyclerView.Adapter<BestSellersAdapter.ViewHolder>() {

    private var bestSellers: List<BestSellerBookViewState> = emptyList()

    fun setItems(listItems: List<BestSellerBookViewState>) {
        this.bestSellers = listItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_bestseller_books, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bestSellers[position])
    }

    override fun getItemCount(): Int {
        return bestSellers.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(viewState: BestSellerBookViewState) {
            itemView.txtvw_title.text = viewState.title
            itemView.txtvw_description.text = viewState.description
            itemView.txtvw_author.text = "Authored by ${viewState.author}"
            itemView.txtvw_price.text = viewState.price
        }
    }
}
