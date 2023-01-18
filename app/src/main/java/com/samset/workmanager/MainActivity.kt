package com.samset.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.work.*
import com.samset.workmanager.work.MyWorkManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG: String = MainActivity::class.java.simpleName

    private lateinit var workManager: WorkManager
    private val PERIODIC_REQUEST_TAG: String = "periodic_request"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        workManager = WorkManager.getInstance(this)
        btnstart.setOnClickListener(this)
        btnstop.setOnClickListener(this)

    }

    private fun setupOneTimeWorker() {
        OneTimeWorkRequestBuilder<MyWorkManager>().build().also {
            workManager.enqueue(it)
        }
    }

    private fun setupPeriodicWorker() {

        /*val work = PeriodicWorkRequest.Builder(
            MyWorkManager::class.java,
            PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS,
            TimeUnit.MINUTES,
            PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS,
            TimeUnit.MINUTES
        )*/

        PeriodicWorkRequestBuilder<MyWorkManager>(
            18, TimeUnit.MINUTES,
            15, TimeUnit.MINUTES
        ).build().also {
            workManager.enqueue(it)
        }

    }


    private fun isScheduleWork(tag: String): Boolean {
        val statuses = workManager.getWorkInfosByTag(tag)
        if (statuses.get().isEmpty()) return false
        var running = false

        for (state in statuses.get()) {

            running =
                state.state == WorkInfo.State.RUNNING || state.state == WorkInfo.State.ENQUEUED
        }
        return running

    }


    override fun onClick(view: View?) {
        if (view == btnstart) {
            setupOneTimeWorker()
        } else if (view == btnstop) {
            if (!isScheduleWork(PERIODIC_REQUEST_TAG)) {
                setupPeriodicWorker()
            }
            Log.d(TAG, "onClick: DONE>>>>>")
        }

    }
}