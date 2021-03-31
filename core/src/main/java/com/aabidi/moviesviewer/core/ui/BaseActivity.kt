package com.aabidi.moviesviewer.core.ui

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

/**
 * Base Activity to inherit from.
 * All common code and abstraction layer goes in here.
 */
abstract class BaseActivity(@LayoutRes layoutId: Int) : AppCompatActivity(layoutId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            addRootFragment()
        }

        this.fragment?.let {
            setupFragment(it)
        }
    }

    /**
     * Replace the fragment present in provided container.
     *
     * @param containerId The container to replace.
     * @param fragment    The fragment to place.
     */
    private fun replaceFragment(
        @IdRes containerId: Int, fragment: BaseFragment,
        addToBackStack: Boolean
    ) {
        supportFragmentManager.commit {
            replace(containerId, fragment, fragment.fragmentTag)
            if (addToBackStack) addToBackStack(null)
        }
    }

    /**
     * Override this value to provide a root fragment.
     *
     * @return The BaseFragment instance to inflate.
     */
    protected open var fragment: BaseFragment? = null

    /**
     * Override this value to provide the fragment container ID.
     *
     * @return An IdRes representing the container to place the root fragment.
     */
    @IdRes
    protected open val fragmentContainer = NO_LAYOUT

    private fun addRootFragment() {
        if (fragmentContainer == NO_LAYOUT) return
        val fragment = fragment ?: return

        replaceFragment(fragmentContainer, fragment, false)
    }

    /**
     * Override this method to configure a Fragment after add.
     */
    protected open fun setupFragment(fragment: BaseFragment) {}

    companion object {
        private const val NO_LAYOUT = 0
    }
}
