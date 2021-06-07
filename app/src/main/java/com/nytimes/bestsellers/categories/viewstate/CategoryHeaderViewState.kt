package com.nytimes.bestsellers.categories.viewstate

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryHeaderViewState(val name: String) : CategoryComponentViewState, Parcelable {

    override fun type(): CategoryComponentViewState.Type {
        return CategoryComponentViewState.Type.HEADER
    }
}
