package com.nytimes.bestsellers.categories.viewstate

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryViewState(val name: String,
                             val encodedName: String,
                             val publishDate: String): CategoryComponentViewState, Parcelable {

    override fun type(): CategoryComponentViewState.Type {
        return CategoryComponentViewState.Type.CATEGORY
    }
}
