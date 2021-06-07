package com.nytimes.bestsellers.categories.viewstate

import androidx.annotation.LayoutRes
import com.nytimes.bestsellers.R

interface CategoryComponentViewState {

    fun type(): Type

    enum class Type constructor(@LayoutRes private val layoutId: Int) {
        HEADER(R.layout.row_best_seller_header),
        CATEGORY(R.layout.row_best_seller_category);

        companion object {
            fun from(value: Int): Type = Type.values()[value]
        }

        fun layoutId(): Int {
            return layoutId
        }

        fun value(): Int = ordinal
    }
}
