package com.aabidi.moviesviewer.core.ui

import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * Base Fragment to inherit from.
 * All common code and abstraction goes here.
 */
abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    protected val contextNonNull: Context
        get() = requireContext()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) loadDependencies()
    }

    /**
     * Override this value to provide a fragment tag.
     * This will be used in Fragment Transactions.
     */
    abstract val fragmentTag: String

    /**
     * Override this method to load Dependency Modules.
     *
     * Only called once in the Persistent Lifecycle.
     */
    open fun loadDependencies() {}

}
