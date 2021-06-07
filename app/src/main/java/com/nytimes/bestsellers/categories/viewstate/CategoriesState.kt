package com.nytimes.bestsellers.categories.viewstate

import android.os.Parcelable
import com.nytimes.bestsellers.common.ErrorType
import kotlinx.android.parcel.Parcelize

sealed class CategoriesState {

    data class Data(val categories: List<CategoryComponentViewState>): CategoriesState()

    data class Error(val error: ErrorType) : CategoriesState()

    object Loading: CategoriesState()
}
