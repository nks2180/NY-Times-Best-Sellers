package com.nytimes.bestsellers.navigation

import android.app.Activity
import org.koin.dsl.module

val androidNavigatorModule = module {
    factory { (activity: Activity) -> AppNavigator(activity) as Navigator }

}
