package com.ride.taxi.presenter

/**
 * @author lusinabrian on 27/06/20.
 * @Notes Interface to act as a base for any class that is to take the role of the IPresenter in the
 * Model-BaseView-IPresenter pattern.
 */
interface BasePresenter<in V : BaseView> {
    /**
     * On attach is used to set the view the presenter will be interacting with
     * @param view [V] Attaches View that will interact with the presenter
     */
    fun onAttach(view: V)

    /**
     * On detach is used to detach the presenter from the view
     */
    fun onDetach()
}
