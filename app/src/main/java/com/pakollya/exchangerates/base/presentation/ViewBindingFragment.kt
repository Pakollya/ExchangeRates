package com.pakollya.exchangerates.base.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.viewbinding.ViewBinding

typealias ViewBindingCreator<VB> = (LayoutInflater, ViewGroup?, Boolean) -> VB

class ViewBindingStore<VB : ViewBinding>(
    private val viewBindingCreator: ViewBindingCreator<VB>
) {

    var viewBinding: VB? = null
        private set

    fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View = viewBindingCreator.invoke(
        inflater,
        container,
        false
    ).also {
        viewBinding = it
    }.root

    fun destroyViewBinding() {
        viewBinding = null
    }

}

abstract class ViewBindingFragment<VB : ViewBinding>(
    private val viewBindingCreator: ViewBindingCreator<VB>
) : Fragment() {

    private val viewBindingStore by lazy(LazyThreadSafetyMode.NONE) {
        ViewBindingStore(viewBindingCreator)
    }

    protected val viewBinding: VB?
        get() = viewBindingStore.viewBinding

    private val backPressedDispatcher = object :OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            this@ViewBindingFragment.onBackPressed()
        }
    }

    open fun onBackPressed() {
        findNavController(this).navigateUp()
    }

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = viewBindingStore.createViewBinding(
        inflater,
        container
    )

    @CallSuper
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        viewBinding?.let {
            onViewBindingCreated(
                it,
                savedInstanceState
            )
        }
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).apply {
            onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressedDispatcher)
        }
    }

    open fun onViewBindingCreated(
        binding: VB,
        savedInstanceState: Bundle?
    ) {
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        viewBindingStore.destroyViewBinding()
        onViewBindingDestroyed()
        backPressedDispatcher.remove()
    }

    @CallSuper
    open fun onViewBindingDestroyed() {
    }
}