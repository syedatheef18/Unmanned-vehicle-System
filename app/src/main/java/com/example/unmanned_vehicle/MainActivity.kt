package com.example.unmanned_vehicle


import android.R.attr.button
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.math.log


class MainActivity : AppCompatActivity() {

    lateinit var acc:Button
    lateinit var rev:Button
    lateinit var left:Button
    lateinit var right:Button
    lateinit var stop:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = Firebase.database
        val acce = database.getReference("acc")
        val reve = database.getReference("rev")
        val leftt = database.getReference("left")
        val rightt = database.getReference("right")

        acc=findViewById(R.id.acc)
        rev=findViewById(R.id.rev)
        left=findViewById(R.id.left)
        right=findViewById(R.id.right)
        stop=findViewById(R.id.stop)

        left.setOnTouchListener(object : OnTouchListener {
            private var mHandler: Handler? = null
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (mHandler != null) return true
                        mHandler = Handler()
                        mHandler!!.postDelayed(mAction, 500)
                    }
                    MotionEvent.ACTION_UP -> {
                        if (mHandler == null) return true
                        mHandler!!.removeCallbacks(mAction)
                        mHandler = null
                        leftt.setValue(0)
                    }
                }
                return false
            }

            var mAction: Runnable = object : Runnable {
                override fun run() {
                    leftt.setValue(1)
                    mHandler?.postDelayed(this, 500)
                }
            }
        })

        rev.setOnClickListener {
            acce.setValue(0)
            reve.setValue(1)
        }

        acc.setOnClickListener {
            reve.setValue(0)
            acce.setValue(1)
        }

        right.setOnTouchListener(object : View.OnTouchListener {
            private var mHandler: Handler? = null
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (mHandler != null) return true
                        mHandler = Handler()
                        mHandler!!.postDelayed(mAction, 500)
                    }
                    MotionEvent.ACTION_UP -> {
                        if (mHandler == null) return true
                        mHandler!!.removeCallbacks(mAction)
                        mHandler = null
                        rightt.setValue(0)
                    }
                }
                return false
            }

            var mAction: Runnable = object : Runnable {
                override fun run() {
                    rightt.setValue(1)
                    mHandler?.postDelayed(this, 500)
                }
            }
        })

        stop.setOnClickListener {
            acce.setValue(0)
            rightt.setValue(0)
            leftt.setValue(0)
            reve.setValue(0)
        }

    }
}