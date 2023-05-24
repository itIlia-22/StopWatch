package com.example.stopwatch.stopwatchsloi.model

import com.example.stopwatch.stopwatchsloi.viewmodel.StopwatchState

class ElapsedTimeCalculator(
    private val timestampProvider: TimestampProvider
) {

    fun calculator(state: StopwatchState.Running): Long {
        val currentTime = timestampProvider.getMilliseconds()
        val timePassedSinceStart =
            if (currentTime > state.startTime) currentTime - state.elapsedTime else 0
        return timePassedSinceStart + state.elapsedTime
    }
}