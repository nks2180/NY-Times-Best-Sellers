package com.nytimes.bestsellers

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.nytimes.bestsellers.navigation.Navigator
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

abstract class BaseActivity : AppCompatActivity() {

    val navigator: Navigator by inject { parametersOf(this) }

    fun showShortToast(@StringRes message: Int) {
        Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(@StringRes message: Int) {
        Toast.makeText(this, getString(message), Toast.LENGTH_LONG).show()
    }

}
