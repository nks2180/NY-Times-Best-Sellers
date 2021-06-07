package com.nytimes.bestsellers.bestsellerbooks

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nytimes.bestsellers.BaseActivity
import com.nytimes.bestsellers.R
import com.nytimes.bestsellers.bestsellerbooks.viewstate.BestSellerBookViewState
import com.nytimes.bestsellers.bestsellerbooks.viewstate.BestSellerBooksViewState
import com.nytimes.bestsellers.common.AppConstants
import com.nytimes.bestsellers.common.ErrorType
import kotlinx.android.synthetic.main.activity_best_seller_books.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getStateViewModel


class BestSellerBooksActivity : BaseActivity() {

    private val adapter: BestSellersAdapter by inject()

    private val bestSellerBooksViewModel by lazy {
        getStateViewModel<BestSellerBooksViewModel> (bundle = intent.extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_best_seller_books)
        lifecycle.addObserver(bestSellerBooksViewModel)

        val bundle = intent.extras
        bundle?.let {
            if (it.containsKey(AppConstants.ENCODED_CATEGORY_NAME)) {
                val category = bundle.getString(AppConstants.ENCODED_CATEGORY_NAME)
                bestSellerBooksViewModel.fetchBestSellerBooks(category!!)
            }
            if (it.containsKey(AppConstants.CATEGORY_NAME)) {
                txt_toolbarTitle.text = bundle.getString(AppConstants.CATEGORY_NAME)
            }
        }
        bestSellerBooksViewModel.bestSellers().observe(this, Observer { viewState ->
            when (viewState) {
                is BestSellerBooksViewState.Loading -> showLoading()
                is BestSellerBooksViewState.Data -> loadBestSellerBooks(viewState.bestSellers)
                is BestSellerBooksViewState.Error -> showError(viewState.error)
            }
        })
        imgVwLeftIcon.setOnClickListener { onBackPressed() }
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
        recyclerView_best_seller_books.visibility = View.GONE
    }

    private fun loadBestSellerBooks(bestSellers: List<BestSellerBookViewState>) {
        progressBar.visibility = View.GONE
        recyclerView_best_seller_books.visibility = View.VISIBLE
        if (bestSellers.isNotEmpty()) {
            recyclerView_best_seller_books.layoutManager = LinearLayoutManager(this)
            adapter.setItems(bestSellers)
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

}
