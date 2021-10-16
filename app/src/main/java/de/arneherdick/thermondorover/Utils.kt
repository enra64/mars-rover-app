package de.arneherdick.thermondorover

import java.text.DateFormat
import java.util.Date

object Utils {
    fun formatDate(date: Date): String {
        return DateFormat.getDateInstance().format(date)
    }
}
