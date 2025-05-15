package com.example.professional

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Ticket : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ticket)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val ticketType = intent.getStringExtra("ticketType").toString();

        val ticketNumber = intent.getIntExtra("ticketNumber", 1)
        val ticketTitle = findViewById<TextView>(R.id.ticketTitle)
        ticketTitle.text = "Билет №${ticketNumber}"

        val fragment = QuestionFragment.newInstance(ticketType, ticketNumber, 1)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()

        val back = findViewById<Button>(R.id.back)
        back.setOnClickListener {
            finish()
        }
    }

}