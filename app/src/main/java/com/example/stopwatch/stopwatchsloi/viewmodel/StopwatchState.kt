package com.example.stopwatch.stopwatchsloi.viewmodel

sealed class StopwatchState {

    data class Paused(
         val elapsedTime: Long //секундомер пауза 1 состояние
    ): StopwatchState()
    data class Running(
        val startTime: Long,//секундомер работает 2 состояние
        val elapsedTime: Long
    ): StopwatchState()
}