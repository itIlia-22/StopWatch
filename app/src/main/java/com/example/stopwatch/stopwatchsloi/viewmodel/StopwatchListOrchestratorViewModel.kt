package com.example.stopwatch.stopwatchsloi

import androidx.lifecycle.ViewModel
import com.example.stopwatch.stopwatchsloi.model.StopwatchStateHolder
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StopwatchListOrchestratorViewModel(
    private val stopwatchStateHolder: StopwatchStateHolder,
    private val scope: CoroutineScope
):ViewModel() {
    private var job: Job? = null
    private val mutableTicker = MutableStateFlow("")
    val ticker: StateFlow<String> = mutableTicker

    private fun startJob() {
        scope.launch {
            while (isActive) {
                mutableTicker.value =
                    stopwatchStateHolder.getStringTimeRepresentation()
                delay(20)
            }
        }
    }

    private fun stopJob() {
        scope.coroutineContext.cancelChildren()
        job = null
    }

    private fun clearValue() {
        mutableTicker.value = ""
    }

    fun start() {
        if (job == null) startJob()
        stopwatchStateHolder.start()
    }

    fun pause() {
        stopwatchStateHolder.pause()
        stopJob()
    }

    fun stop() {
        stopwatchStateHolder.stop()
        stopJob()
        clearValue()
    }


}