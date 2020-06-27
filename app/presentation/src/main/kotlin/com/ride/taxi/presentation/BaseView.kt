package com.ride.taxi.presentation

/**
 * @author lusinabrian on 27/06/20.
 * @Notes Base View that will act as a base for any class that is to take the role of the BaseView
 * in the Model-BaseView-Presenter pattern
 */
interface BaseView<in T> {

    fun setPresenter(presenter: T)
}
