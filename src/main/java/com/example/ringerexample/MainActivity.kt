package com.example.ringerexample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.textView

class MainActivity : AppCompatActivity() {

    private val volumeChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val ringerMode = audioManager.ringerMode

            textView.text = (textView.text?.toString() ?: "") + when (ringerMode) {
                AudioManager.RINGER_MODE_SILENT -> "$ringerMode: RINGER_MODE_SILENT\n"
                AudioManager.RINGER_MODE_VIBRATE -> "$ringerMode: RINGER_MODE_VIBRATE\n"
                AudioManager.RINGER_MODE_NORMAL -> "$ringerMode: RINGER_MODE_NORMAL\n"
                else -> ""
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        val intentFilter = IntentFilter()
        intentFilter.addAction(AudioManager.RINGER_MODE_CHANGED_ACTION)
        registerReceiver(volumeChangeReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(volumeChangeReceiver)
    }
}
