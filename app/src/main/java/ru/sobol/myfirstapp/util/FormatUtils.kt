package ru.sobol.myfirstapp.util

import java.text.DecimalFormat

object FormatUtils {
    fun formatCount(count: Int): String {
        return when {
            count >= 1_000_000 -> {
                val millions = count / 1_000_000.0
                // Если число миллионов целое, показываем без десятых
                if (millions % 1.0 == 0.0) {
                    "${millions.toInt()}M"
                } else {
                    // Оставляем один знак после запятой
                    DecimalFormat(".").format(millions) + "M"
                }
            }
            count >= 10_000 -> {
                // После 10К сотни не показываем
                "${count / 1000}K"
            }
            count >= 1_000 -> {
                val thousands = count / 1000.0
                if (thousands % 1.0 == 0.0) {
                    "${thousands.toInt()}K"
                } else {
                    DecimalFormat(".").format(thousands) + "K"
                }
            }
            else -> count.toString()
        }
    }
}