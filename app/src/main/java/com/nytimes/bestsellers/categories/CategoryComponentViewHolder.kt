package com.nytimes.bestsellers.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nytimes.bestsellers.categories.viewstate.CategoryComponentViewState

abstract class CategoryComponentViewHolder(inflater: LayoutInflater,
                                           parent: ViewGroup,
                                           type: CategoryComponentViewState.Type)

    : RecyclerView.ViewHolder(inflater.inflate(type.layoutId(), parent, false)) {

    abstract fun bind(viewState: CategoryComponentViewState)

    open fun unbind() {
        // no op
    }

}
