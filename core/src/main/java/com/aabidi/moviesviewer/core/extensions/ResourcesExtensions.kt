package com.aabidi.moviesviewer.core.extensions

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.colorFrom(@ColorRes colorRes: Int) = ContextCompat.getColor(context as Context, colorRes)

