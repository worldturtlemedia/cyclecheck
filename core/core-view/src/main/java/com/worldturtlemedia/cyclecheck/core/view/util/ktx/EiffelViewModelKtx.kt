package com.worldturtlemedia.cyclecheck.core.view.util.ktx

import com.etiennelenhart.eiffel.state.Action
import com.etiennelenhart.eiffel.state.State
import com.etiennelenhart.eiffel.viewmodel.EiffelViewModel

val <S : State, A : Action> EiffelViewModel<S, A>.currentState: S
    get() = state.value!!
