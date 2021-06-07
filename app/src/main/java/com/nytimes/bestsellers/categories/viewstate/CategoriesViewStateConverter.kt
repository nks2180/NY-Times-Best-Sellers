package com.nytimes.bestsellers.categories.viewstate

import com.nytimes.bestsellers.common.ErrorType
import com.nytimes.bestsellers.common.rx.scheduler.Converter
import com.nytimes.bestsellers.categories.api.ApiCategories

class CategoriesViewStateConverter : Converter<ApiCategories, CategoriesState> {

    override fun apply(apiCategories: ApiCategories): CategoriesState {
        if (apiCategories.status == "OK" && !apiCategories.bestSellers.isNullOrEmpty()) {
            val bestSellersMap = apiCategories.bestSellers.groupBy { it.updated }
            val viewState = mutableListOf<CategoryComponentViewState>()
            for (apiBestSellers in bestSellersMap) {
                viewState.add(CategoryHeaderViewState(apiBestSellers.key.capitalize()))
                for (apiSeller in apiBestSellers.value) {
                    val category = CategoryViewState(
                        name = apiSeller.displayName,
                        encodedName = apiSeller.listNameEncoded,
                        publishDate = apiSeller.newestPublishedDate
                    )
                    viewState.add(category)
                }
            }
            return CategoriesState.Data(viewState)
        }
        return CategoriesState.Error(ErrorType.NO_DATA_FOUND)
    }
}
