package com.pavellukyanov.cinematic.ui.people

import com.pavellukyanov.cinematic.domain.ResourceState
import com.pavellukyanov.cinematic.domain.models.PeopleDetails
import com.pavellukyanov.cinematic.domain.people.GetPeopleDetailsInteractor
import com.pavellukyanov.cinematic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val repo: GetPeopleDetailsInteractor
) : BaseViewModel<PeopleDetails>() {

    fun getPeopleDetails(id: Int) {
        repo.invoke(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { onSetResource(ResourceState.Loading) }
            .subscribe(
                { success ->
                    onSetResource(ResourceState.Success(success))
                },
                { error ->
                    onSetResource(ResourceState.Error(error))
                }
            )
            .untilDestroy()
    }
}