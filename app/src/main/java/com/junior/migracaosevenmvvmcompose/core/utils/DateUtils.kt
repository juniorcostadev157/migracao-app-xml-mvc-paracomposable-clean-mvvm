package com.junior.migracaosevenmvvmcompose.core.utils

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

object DateUtils {

    fun formatTimestamp(
        timestamp: Timestamp?,
        patterns: String = "dd/MM/yyyy HH:mm",
        locale: Locale = Locale.getDefault()
        ): String{
        return timestamp?.toDate()?.let { date->
            SimpleDateFormat(patterns, locale).format(date)
        } ?: "Date Invalid"
    }
}
