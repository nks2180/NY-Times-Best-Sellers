package com.nytimes.bestsellers.categories

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nytimes.bestsellers.BaseActivity
import com.nytimes.bestsellers.R
import com.nytimes.bestsellers.categories.viewstate.CategoriesState
import com.nytimes.bestsellers.categories.viewstate.CategoryComponentViewState
import com.nytimes.bestsellers.common.ErrorType
import kotlinx.android.synthetic.main.activity_categories.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getStateViewModel

class CategoriesActivity : BaseActivity() {

    private val adapter: CategoriesAdapter by inject()

    private val categoriesViewModel by lazy {
        getStateViewModel<CategoriesViewModel>(bundle = intent.extras)
    }

    private val listener = object : CategoriesAdapter.Listener {
        override fun onCategoryTapped(encodedCategory: String, categoryName: String) {
            navigator.goToDetailsPage(encodedCategory, categoryName)
        }
    }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_categories)
        lifecycle.addObserver(categoriesViewModel)
        categoriesViewModel.fetchCategories()
        categoriesViewModel.categories().observe(this, Observer { viewState ->
            when (viewState) {
                is CategoriesState.Loading -> showLoading()
                is CategoriesState.Data -> loadBestSellerCategories(viewState.categories)
                is CategoriesState.Error -> showError(viewState.error)
            }
        })
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
        recyclerView_best_seller_books.visibility = View.GONE
    }

    private fun loadBestSellerCategories(categories: List<CategoryComponentViewState>) {
        progressBar.visibility = View.GONE
        recyclerView_best_seller_books.visibility = View.VISIBLE
        if (categories.isNotEmpty()) {
            recyclerView_best_seller_books.layoutManager = LinearLayoutManager(this)
            adapter.setListener(listener)
            adapter.setItems(categories)
            val dividerItemDecoration = DividerItemDecoration(
                recyclerView_best_seller_books.context,
                RecyclerView.VERTICAL
            )
            recyclerView_best_seller_books.addItemDecoration(dividerItemDecoration)
            recyclerView_best_seller_books.adapter = adapter
        }
    }

    private fun showError(error: ErrorType) {
        progressBar.visibility = View.GONE
        recyclerView_best_seller_books.visibility = View.GONE
        showLongToast(error.value())
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        outState.putInt("Score", userScore) // saving the userScore value
//        super.onSaveInstanceState(outState)
//    }
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        userScore = savedInstanceState.getInt("Score") // restoring the userScore value
//        super.onRestoreInstanceState(savedInstanceState)
//    }
}
