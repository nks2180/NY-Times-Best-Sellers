package com.nytimes.bestsellers.common

import androidx.annotation.StringRes
import com.nytimes.bestsellers.R

enum class ErrorType(@StringRes val errorCauseString: Int) {

    NO_INTERNET_CONNECTION(R.string.no_internet_connection_text),
    SERVER(R.string.server_error),
    UNKNOWN(R.string.server_error),
    NO_DATA_FOUND(R.string.server_error);

    fun value(): Int {
        return errorCauseString
    }
}
