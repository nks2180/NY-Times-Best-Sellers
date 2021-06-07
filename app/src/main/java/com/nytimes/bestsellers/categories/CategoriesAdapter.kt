package com.nytimes.bestsellers.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nytimes.bestsellers.categories.viewstate.CategoryComponentViewState

class CategoriesAdapter() : RecyclerView.Adapter<CategoryComponentViewHolder>() {

    private var categoryCategories: List<CategoryComponentViewState> = emptyList()
    private var listener: Listener = Listener.NO_OP

    fun setItems(listItems: List<CategoryComponentViewState>) {
        this.categoryCategories = listItems
        notifyDataSetChanged()
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryComponentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (CategoryComponentViewState.Type.from(viewType)) {
            CategoryComponentViewState.Type.HEADER -> HeaderViewHolder(inflater, parent)
            CategoryComponentViewState.Type.CATEGORY -> CategoryCategoryViewHolder(inflater, parent, listener)
        }
    }

    override fun onBindViewHolder(holder: CategoryComponentViewHolder, position: Int) {
        holder.bind(categoryCategories[position])
    }

    override fun getItemCount(): Int {
        return categoryCategories.size
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutId(position)
    }

    private fun getLayoutId(position: Int): Int {
        val productDetailType = categoryCategories[position]
        return productDetailType.type().value()
    }

    interface Listener : CategoryCategoryViewHolder.Listener {

        companion object {
            val NO_OP: Listener = object : Listener {
                override fun onCategoryTapped(encodedCategory: String, categoryName: String) {
                    //no op
                }

            }
        }
    }
}
