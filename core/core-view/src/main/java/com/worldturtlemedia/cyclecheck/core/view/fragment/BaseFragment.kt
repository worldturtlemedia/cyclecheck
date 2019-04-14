package com.worldturtlemedia.cyclecheck.core.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.lifecycle.LifecycleOwner
import com.worldturtlemedia.cyclecheck.core.di.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment() {

    @LayoutRes abstract fun getLayoutRes(): Int

    private val layoutRedID by lazy { getLayoutRes() }

    @MenuRes open fun getMenuRes(): Int? = null

    @Inject lateinit var viewModelFactory: ViewModelFactory

    val owner: LifecycleOwner
        get() = viewLifecycleOwner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(layoutRedID, container, false)

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        getMenuRes()?.let { inflater?.inflate(it, menu) }
    }

    @CallSuper
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        beforeSetup()

        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(getMenuRes() !== null)

        setupViews()
        preSubscribeViewModel()
        subscribeViewModel()
    }

    protected open fun beforeSetup() {}

    protected open fun setupViews() {}

    protected open fun preSubscribeViewModel() {}

    protected open fun subscribeViewModel() {}
}
