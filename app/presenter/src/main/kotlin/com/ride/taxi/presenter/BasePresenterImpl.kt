package com.ride.taxi.presenter

/**
 * @author lusinabrian on 27/06/20.
 * @Notes Base presenter implementation
 */
open class BasePresenterImpl<V : BaseView> : BasePresenter<V> {
    /**
     * Gets the base view
     * @return [BaseView]
     */
    lateinit var baseView: V
        private set

    override fun onAttach(view: V) {
        this.baseView = view
    }

    @Suppress("EmptyFunctionBlock")
    override fun onDetach() {}
}
