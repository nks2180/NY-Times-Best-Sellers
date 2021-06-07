package com.nytimes.bestsellers.navigation

import android.app.Activity
import android.content.Intent
import com.nytimes.bestsellers.bestsellerbooks.BestSellerBooksActivity
import com.nytimes.bestsellers.common.AppConstants

class AppNavigator(private val activity: Activity) : Navigator {

    override fun goToDetailsPage(encodedCategory: String, categoryName: String) {
        val intent = Intent(activity, BestSellerBooksActivity::class.java)
        intent.putExtra(AppConstants.ENCODED_CATEGORY_NAME, encodedCategory)
        intent.putExtra(AppConstants.CATEGORY_NAME, categoryName)
        activity.startActivity(intent)
    }
}
