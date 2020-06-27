package com.ride.taxi.presentation

/**
 * @author lusinabrian on 27/06/20.
 * @Notes Interface to act as a base for any class that is to take the role of the IPresenter in the
 * Model-BaseView-IPresenter pattern.
 */
interface BasePresenter {
    /**
     * On attach is used to set the view the presenter will be interacting with
     */
    fun onAttach()

    /**
     * On detach is used to detach the presenter from the view
     */
    fun onDetach()
}
