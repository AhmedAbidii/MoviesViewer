package com.aabidi.moviesviewer.core.extensions

import java.text.SimpleDateFormat
import java.util.*

private const val READABLE_DATE_FORMAT = "MMMM d, yyyy"

private val readableDateFormatter = SimpleDateFormat(READABLE_DATE_FORMAT, Locale.ENGLISH)

fun Date.toReadableDate(): String = readableDateFormatter.format(this)

fun Date.yearAsString(): String = Calendar.getInstance().apply { time = this@yearAsString }
    .get(Calendar.YEAR).toString()
