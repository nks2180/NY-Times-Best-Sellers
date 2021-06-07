package com.nytimes.bestsellers.categories.viewstate

import com.google.common.truth.Truth
import com.nytimes.bestsellers.categories.api.ApiCategories
import com.nytimes.bestsellers.categories.api.ApiCategory
import com.nytimes.bestsellers.common.ErrorType
import org.junit.Before
import org.junit.Test

class CategoriesViewStateConverterTest {

    private val apiCategoryOne = ApiCategory(
        listName = "Category list name 1",
        displayName = "Category display name 1",
        listNameEncoded = "Category encoded list name 1",
        olderPublishedDate = "2020-01-01",
        newestPublishedDate = "2021-01-01",
        updated = "WEEKLY"
    )
    private val apiCategoryTwo = ApiCategory(
        listName = "Category list name 1",
        displayName = "Category display name 1",
        listNameEncoded = "Category encoded list name 1",
        olderPublishedDate = "2020-01-01",
        newestPublishedDate = "2021-01-01",
        updated = "WEEKLY"
    )
    private val apiCategoryThree = ApiCategory(
        listName = "Category list name 1",
        displayName = "Category display name 1",
        listNameEncoded = "Category encoded list name 1",
        olderPublishedDate = "2020-01-01",
        newestPublishedDate = "2021-01-01",
        updated = "MONTHLY"
    )
    private val apiCategoryFour = ApiCategory(
        listName = "Category list name 1",
        displayName = "Category display name 1",
        listNameEncoded = "Category encoded list name 1",
        olderPublishedDate = "2020-01-01",
        newestPublishedDate = "2021-01-01",
        updated = "MONTHLY"
    )

    private val categoryViewStateOne = CategoryViewState(
        name = "Category display name 1",
        encodedName = "Category encoded list name 1",
        publishDate = "2021-01-01",
    )
    private val categoryViewStateTwo = CategoryViewState(
        name = "Category display name 1",
        encodedName = "Category encoded list name 1",
        publishDate = "2021-01-01",
    )
    private val categoryViewStateThree = CategoryViewState(
        name = "Category display name 1",
        encodedName = "Category encoded list name 1",
        publishDate = "2021-01-01",
    )
    private val categoryViewStateFour = CategoryViewState(
        name = "Category display name 1",
        encodedName = "Category encoded list name 1",
        publishDate = "2021-01-01",
    )


    private lateinit var converter: CategoriesViewStateConverter

    @Before
    fun setUp() {
        converter = CategoriesViewStateConverter()
    }

    @Test
    fun `should return no data found when api status is not OK`() {
        val apiBestSellers = ApiCategories(
            status = "ERROR",
            resultCount = 0,
            copyright = "Copyright text",
            bestSellers = emptyList()
        )
        val convertedViewState = converter.apply(apiBestSellers)

        val expectedViewState = CategoriesState.Error(ErrorType.NO_DATA_FOUND)
        Truth.assertThat(convertedViewState).isEqualTo(expectedViewState)
    }

    @Test
    fun `should return no data found when bestSellers list is empty`() {
        val apiBestSellers = ApiCategories(
            status = "OK",
            resultCount = 0,
            copyright = "Copyright text",
            bestSellers = emptyList()
        )
        val convertedViewState = converter.apply(apiBestSellers)

        val expectedViewState = CategoriesState.Error(ErrorType.NO_DATA_FOUND)
        Truth.assertThat(convertedViewState).isEqualTo(expectedViewState)
    }

    @Test
    fun `should correctly convert into categories view state when status is OK`() {
        val apiBestSellers = categoriesStates(apiCategoryOne, apiCategoryTwo, apiCategoryTwo, apiCategoryThree, apiCategoryFour, apiCategoryThree)
        val convertedViewState = converter.apply(apiBestSellers)

        val expectedViewState = listOf(CategoryHeaderViewState("WEEKLY"),
            categoryViewStateTwo,
            categoryViewStateTwo,
            CategoryHeaderViewState("MONTHLY"),
            categoryViewStateThree,
            categoryViewStateFour,
            categoryViewStateThree)
    }

    private fun categoriesStates(vararg apiCategory: ApiCategory): ApiCategories {
        return ApiCategories(
            status = "OK",
            resultCount = 0,
            copyright = "Copyright text",
            bestSellers = apiCategory.toList()
        )
    }
}
