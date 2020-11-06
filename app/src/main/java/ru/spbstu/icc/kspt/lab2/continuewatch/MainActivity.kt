package ru.spbstu.icc.kspt.lab2.continuewatch

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var secondsElapsed: Int = 0
    val SEC_TO_NEXT = "lastWroteSeconds"


    override fun onStart() {
        super.onStart()
        backgroundThread.start()
    }



    override fun onStop() {
        super.onStop()
        backgroundThread.interrupt()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    var backgroundThread = Thread {
        while (true) {
            Thread.sleep(1000)
            textSecondsElapsed.post {
                textSecondsElapsed.text = "Seconds elapsed: " + secondsElapsed++
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        if (outState != null) {
            super.onSaveInstanceState(outState, outPersistentState)
        }
        // Save the user's current game state
        outState?.run {
            putInt(SEC_TO_NEXT, secondsElapsed)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                // Restore value of members from saved state
                secondsElapsed = getInt(SEC_TO_NEXT)
            }
        }
            backgroundThread.start()
    }
}
