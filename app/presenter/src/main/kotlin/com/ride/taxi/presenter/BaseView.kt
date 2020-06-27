package com.ride.taxi.presenter

/**
 * @author lusinabrian on 27/06/20.
 * @Notes Base View that will act as a base for any class that is to take the role of the BaseView
 * in the Model-BaseView-Presenter pattern
 */
interface BaseView<in T : BasePresenter> {

    /**
     * Sets the presenter for the view
     * @param presenter [T] Presenter which implements the [BasePresenter]
     */
    fun setPresenter(presenter: T)
}
