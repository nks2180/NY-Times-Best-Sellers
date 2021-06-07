package com.nytimes.bestsellers.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import com.nytimes.bestsellers.categories.viewstate.CategoryComponentViewState
import com.nytimes.bestsellers.categories.viewstate.CategoryHeaderViewState
import kotlinx.android.synthetic.main.row_best_seller_header.view.*

class HeaderViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup
) : CategoryComponentViewHolder(inflater, parent, CategoryComponentViewState.Type.HEADER) {

    override fun bind(viewState: CategoryComponentViewState) {
        val headerViewState = viewState as CategoryHeaderViewState
        itemView.txtVw_header.text = headerViewState.name
    }
}
