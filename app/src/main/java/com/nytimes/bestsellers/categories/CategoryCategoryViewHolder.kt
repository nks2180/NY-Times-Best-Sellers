package com.nytimes.bestsellers.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import com.nytimes.bestsellers.R
import com.nytimes.bestsellers.categories.viewstate.CategoryViewState
import com.nytimes.bestsellers.categories.viewstate.CategoryComponentViewState
import kotlinx.android.synthetic.main.row_best_seller_category.view.*

class CategoryCategoryViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup,
    private val listener: CategoriesAdapter.Listener
) : CategoryComponentViewHolder(inflater, parent, CategoryComponentViewState.Type.CATEGORY) {

    override fun bind(viewState: CategoryComponentViewState) {
        val categoryViewState = viewState as CategoryViewState
        val categoryName = String.format(
            itemView.context.getString(R.string.str_category_name),
            categoryViewState.name,
            categoryViewState.publishDate
        )
        itemView.txtVw_name.text = categoryName
        itemView.setOnClickListener {
            listener.onCategoryTapped(categoryViewState.encodedName, categoryViewState.name)
        }
    }

    interface Listener {
        fun onCategoryTapped(encodedCategory: String, categoryName: String)
    }
}
