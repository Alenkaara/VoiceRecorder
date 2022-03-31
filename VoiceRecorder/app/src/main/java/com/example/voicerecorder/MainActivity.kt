package com.example.voicerecorder

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {


    lateinit var mr : MediaRecorder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<View>(R.id.buStart)
        val button2 = findViewById<View>(R.id.buStop)
        val button3 = findViewById<View>(R.id.buPlay)



        var path = Environment.getExternalStorageDirectory().toString()+"/myrec.3gp"
        mr = MediaRecorder()

        button.isEnabled = false
        button2.isEnabled = false

        if(ActivityCompat.checkSelfPermission( this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions( this, arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE), 111)

        button.isEnabled = true


        // Start Recording
        button.setOnClickListener {
            mr.setAudioSource(MediaRecorder.AudioSource.MIC)
            mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            mr.setOutputFile(path)
            mr.prepare()
            mr.start()
            button2.isEnabled = true
            button.isEnabled = false
        }

        //Stop Recording
        button2.setOnClickListener {
            mr.stop()
            button.isEnabled = true
            button2.isEnabled = false
        }

        //Play Recording
        button3.setOnClickListener {
            var mp = MediaPlayer()
            mp.setDataSource(path)
            mp.prepare()
            mp.start()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray

    ) {
        val button = findViewById<View>(R.id.buStart)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==111 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
            button.isEnabled = true
    }
}