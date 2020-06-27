package com.ride.taxi.di

import com.ride.taxi.presenter.maps.MapsContract
import com.ride.taxi.presenter.maps.MapsPresenterImpl
import org.koin.dsl.module

val presenterModule = module {
    single { MapsPresenterImpl<MapsContract.View>() }
    single<MapsContract.Presenter<MapsContract.View>> { MapsPresenterImpl() }
}
