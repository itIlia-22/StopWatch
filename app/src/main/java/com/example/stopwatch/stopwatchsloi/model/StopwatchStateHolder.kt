package com.example.stopwatch.stopwatchsloi.model

import com.example.stopwatch.stopwatchsloi.utils.TimestampMillisecondsFormatter
import com.example.stopwatch.stopwatchsloi.viewmodel.StopwatchState

class StopwatchStateHolder(
    private val stopwatchStateCalculator: StopwatchStateCalculator,
    private val elapsedTimeCalculator: ElapsedTimeCalculator,
    private val timestampMillisecondsFormatter: TimestampMillisecondsFormatter
) {
    var currentState: StopwatchState = StopwatchState.Paused(0)
        private set

    fun start(){
        currentState = stopwatchStateCalculator.calculateRunningState(currentState)
    }
    fun pause(){
        currentState = stopwatchStateCalculator.calculatePausedState(currentState)
    }
    fun stop(){
        currentState = StopwatchState.Paused(0)
    }

   fun getStringTimeRepresentation(): String {
        val elapsedTime = when (val currentState = currentState) {
            is StopwatchState.Paused -> currentState.elapsedTime
            is StopwatchState.Running ->
                elapsedTimeCalculator.calculator(currentState)
        }
        return timestampMillisecondsFormatter.format(elapsedTime)
    }
}