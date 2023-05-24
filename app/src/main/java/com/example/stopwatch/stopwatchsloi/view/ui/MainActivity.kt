package com.example.stopwatch.stopwatchsloi.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.stopwatch.R
import com.example.stopwatch.stopwatchsloi.*
import com.example.stopwatch.stopwatchsloi.model.*
import com.example.stopwatch.stopwatchsloi.utils.TimestampMillisecondsFormatter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val timestampProvider = object : TimestampProvider {
        override fun getMilliseconds(): Long {
            return System.currentTimeMillis()
        }
    }

    private val viewModel = StopwatchListOrchestratorViewModel(
        StopwatchStateHolder(
            StopwatchStateCalculator(
                timestampProvider,
                ElapsedTimeCalculator(timestampProvider)
            ),
            ElapsedTimeCalculator(timestampProvider),
            TimestampMillisecondsFormatter()
        ),
        CoroutineScope(
            Dispatchers.Main
                    + SupervisorJob()
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.text_time)
        CoroutineScope(
            Dispatchers.Main
                    + SupervisorJob()
        ).launch {
            viewModel.ticker.collect {
                textView.text = it
            }
        }
        findViewById<Button>(R.id.button_start).setOnClickListener {
            viewModel.start()
        }
        findViewById<Button>(R.id.button_pause).setOnClickListener {
            viewModel.pause()
        }
        findViewById<Button>(R.id.button_stop).setOnClickListener {
            viewModel.stop()
        }
    }
}