package com.example.professional.ui.dashboard

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.professional.R
import com.example.professional.Ticket
import com.example.professional.databinding.FragmentTicketsBinding


class DashboardFragment : Fragment() {

    private var _binding: FragmentTicketsBinding? = null
    private val binding get() = _binding!!
    private lateinit var pref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]

        _binding = FragmentTicketsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val categorySpinner = binding.categorySpinner
        val categories = arrayOf("Выберите категорию", "Билеты категории ABM", "Билеты категории CD", "Билеты по темам (ABM)", "Билеты по темам (CD)", "Билеты по спецтехнике")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter

        pref = requireContext().getSharedPreferences("your_prefs_name", Context.MODE_PRIVATE)
        val position = pref.getInt("position", 0)
        Log.d("pos", "$position")
        categorySpinner.setSelection(position)

        val gridLayoutABM = binding.gridLayoutABM
        val gridLayoutCD = binding.gridLayoutCD
        val gridLayoutSpec = binding.gridLayoutSpec
        val gridLayoutThemesABM = binding.gridLayoutThemesABM
        val gridLayoutThemesCD = binding.gridLayoutThemesCD

        fun addButtonsToGridLayoutABM(gridLayout: GridLayout, ticketCount: Int) {
            gridLayout.removeAllViews()
            for (i in 1..ticketCount) {
                val button = Button(this.context)
                button.text = "Билет $i"
                button.layoutParams = GridLayout.LayoutParams().apply {
                    width = 280
                    height = 120
                    setMargins(16, 16, 16, 16)
                }
                button.setBackgroundResource(R.drawable.gray_background)
                button.textSize = 18f
                button.setOnClickListener {
                    val intent = Intent(this.context, Ticket::class.java)
                    intent.putExtra("ticketType", "ABM")
                    intent.putExtra("ticketNumber", i)
                    startActivity(intent) }
                gridLayout.addView(button)
            }
        }

        fun addButtonsToGridLayoutCD(gridLayout: GridLayout, ticketCount: Int) {
            gridLayout.removeAllViews()
            for (i in 1..ticketCount) {
                val button = Button(this.context)
                button.text = "Билет $i"
                button.layoutParams = GridLayout.LayoutParams().apply {
                    width = 280
                    height = 120
                    setMargins(16, 16, 16, 16)
                }
                button.setBackgroundResource(R.drawable.gray_background)
                button.textSize = 18f
                button.setOnClickListener {
                    val intent = Intent(this.context, Ticket::class.java)
                    intent.putExtra("ticketType", "CD")
                    intent.putExtra("ticketNumber", i)
                    startActivity(intent) }
                gridLayout.addView(button)
            }
        }

        fun addButtonsToGridLayoutThemesABM(gridLayout: GridLayout, themes: List<String>) {
            gridLayout.removeAllViews()
            for ((index, theme) in themes.withIndex()) {
                val button = Button(this.context)
                button.apply {
                    text = theme
                    textSize = 18f
                    setBackgroundResource(R.drawable.gray_background)
                    layoutParams = GridLayout.LayoutParams(
                        GridLayout.spec(GridLayout.UNDEFINED, 1f),
                        GridLayout.spec(GridLayout.UNDEFINED)
                    ).apply {
                        width = ViewGroup.LayoutParams.MATCH_PARENT
                        height = ViewGroup.LayoutParams.WRAP_CONTENT
                        setMargins(16, 16, 16, 16)
                    }
                    setPadding(24, 24, 24, 24)
                    gravity = Gravity.CENTER
                }

                button.setOnClickListener {
                    val intent = Intent(this.context, Ticket::class.java)
                    intent.putExtra("ticketType", "ThemesABM")
                    intent.putExtra("ticketNumber", index + 1)
                    startActivity(intent)
                }
                gridLayout.addView(button)
            }
        }

        fun addButtonsToGridLayoutThemesCD(gridLayout: GridLayout, themes: List<String>) {
            gridLayout.removeAllViews()

            for ((index, theme) in themes.withIndex()) {
                val button = Button(this.context)
                button.apply {
                    text = theme
                    textSize = 18f
                    setBackgroundResource(R.drawable.gray_background)
                    layoutParams = GridLayout.LayoutParams(
                        GridLayout.spec(GridLayout.UNDEFINED, 1f),
                        GridLayout.spec(GridLayout.UNDEFINED)
                    ).apply {
                        width = ViewGroup.LayoutParams.MATCH_PARENT
                        height = ViewGroup.LayoutParams.WRAP_CONTENT
                        setMargins(16, 16, 16, 16)
                    }
                    setPadding(24, 24, 24, 24)
                    gravity = Gravity.CENTER
                }

                button.setOnClickListener {
                    val intent = Intent(this.context, Ticket::class.java)
                    intent.putExtra("ticketType", "ThemesCD")
                    intent.putExtra("ticketNumber", index + 1)
                    startActivity(intent)
                }
                gridLayout.addView(button)
            }
        }

        fun addButtonsToGridLayoutSPEC(gridLayout: GridLayout, ticketCount: Int) {
            gridLayout.removeAllViews()
            for (i in 1..ticketCount) {
                val button = Button(this.context)
                button.text = "Билет $i"
                button.layoutParams = GridLayout.LayoutParams().apply {
                    width = 280
                    height = 120
                    setMargins(16, 16, 16, 16)
                }
                button.setBackgroundResource(R.drawable.gray_background)
                button.textSize = 18f
                button.setOnClickListener {
                    val intent = Intent(this.context, Ticket::class.java)
                    intent.putExtra("ticketType", "SPEC")
                    intent.putExtra("ticketNumber", i)
                    startActivity(intent) }
                gridLayout.addView(button)
            }
        }

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                pref.edit().putInt("position", position).apply();
                when (position) {
                    1 -> {
                        gridLayoutABM.visibility = View.VISIBLE
                        gridLayoutCD.visibility = View.GONE
                        gridLayoutThemesABM.visibility = View.GONE
                        gridLayoutThemesCD.visibility = View.GONE
                        gridLayoutSpec.visibility = View.GONE
                        addButtonsToGridLayoutABM(gridLayoutABM, 40)
                    }
                    2 -> {
                        gridLayoutABM.visibility = View.GONE
                        gridLayoutCD.visibility = View.VISIBLE
                        gridLayoutThemesABM.visibility = View.GONE
                        gridLayoutThemesCD.visibility = View.GONE
                        gridLayoutSpec.visibility = View.GONE
                        addButtonsToGridLayoutCD(gridLayoutCD, 40)
                    }
                    3 -> {
                        gridLayoutABM.visibility = View.GONE
                        gridLayoutCD.visibility = View.GONE
                        gridLayoutThemesABM.visibility = View.VISIBLE
                        gridLayoutThemesCD.visibility = View.GONE
                        gridLayoutSpec.visibility = View.GONE
                        val themes = listOf("Общие положения (25 вопросов)", "Дорожные знаки (126 вопросов)", "Дорожная разметка (40 вопросов)", "Сигналы светофора и регулировщика (39 вопросов)", "Начало движения, маневрирование (113 вопросов)", "Скорость движения (19 вопросов)", "Обгон, опережение, встречный разъезд (36 вопросов)", "Остановка и стоянка (47 вопросов)", "Проезд перекрестков (113 вопросов)", "Пользование внешними световыми приборами и звуковыми сигналами (23 вопроса)", "Неисправности и условия допуска транспортных средств к эксплуатации (26 вопросов)", "Безопасность движения и техника управления автомобилем (59 вопросов)", "Оказание доврачебной медицинской помощи (20 вопросов)", "Общие обязанности водителей (15 вопросов)", "Расположение транспортных средств на проезжей части (24 вопроса)", "Приоритет маршрутных транспортных средств (5 вопросов)", "Буксировка механических транспортных средств (8 вопросов)", "Применение специальных сигналов (9 вопросов)", "Движение по автомагистралям (13 вопросов)", "Учебная езда и дополнительные требования к движению велосипедистов (4 вопроса)", "Движение в жилых зонах (7 вопросов)", "Движение через железнодорожные пути (11 вопросов)", "Пешеходные переходы и места остановок маршрутных транспортных средств (5 вопросов)", "Перевозка людей и грузов (6 вопросов)", "Ответственность водителя (15 вопросов)", "Применение аварийной сигнализации и знака аварийной остановки (9 вопросов)")
                        addButtonsToGridLayoutThemesABM(gridLayoutThemesABM, themes)
                    }
                    4 -> {
                        gridLayoutABM.visibility = View.GONE
                        gridLayoutCD.visibility = View.GONE
                        gridLayoutThemesABM.visibility = View.GONE
                        gridLayoutThemesCD.visibility = View.VISIBLE
                        gridLayoutSpec.visibility = View.GONE
                        val themes = listOf("Общие положения (26 вопросов)", "Дорожные знаки (151 вопросов)", "Дорожная разметка (41 вопросов)", "Сигналы светофора и регулировщика (44 вопросов)", "Начало движения, маневрирование (119 вопросов)", "Скорость движения (27 вопросов)", "Обгон, опережение, встречный разъезд (42 вопросов)", "Остановка и стоянка (71 вопросов)", "Проезд перекрестков (120 вопросов)", "Пользование внешними световыми приборами и звуковыми сигналами (24 вопроса)", "Неисправности и условия допуска транспортных средств к эксплуатации (26 вопросов)", "Безопасность движения и техника управления автомобилем (62 вопросов)", "Оказание доврачебной медицинской помощи (20 вопросов)", "Общие обязанности водителей (15 вопросов)", "Расположение транспортных средств на проезжей части (26 вопроса)", "Приоритет маршрутных транспортных средств (8 вопросов)", "Буксировка механических транспортных средств (10 вопросов)", "Применение специальных сигналов (9 вопросов)", "Движение по автомагистралям (13 вопросов)", "Учебная езда и дополнительные требования к движению велосипедистов (4 вопроса)", "Движение в жилых зонах (10 вопросов)", "Движение через железнодорожные пути (12 вопросов)", "Пешеходные переходы и места остановок маршрутных транспортных средств (5 вопросов)", "Перевозка людей и грузов (10 вопросов)", "Ответственность водителя (15 вопросов)", "Применение аварийной сигнализации и знака аварийной остановки (11 вопросов)")
                        addButtonsToGridLayoutThemesCD(gridLayoutThemesCD, themes)
                    }
                    5 -> {
                        gridLayoutABM.visibility = View.GONE
                        gridLayoutCD.visibility = View.GONE
                        gridLayoutThemesABM.visibility = View.GONE
                        gridLayoutThemesCD.visibility = View.GONE
                        gridLayoutSpec.visibility = View.VISIBLE
                        addButtonsToGridLayoutSPEC(gridLayoutSpec, 50)
                    }
                    else -> {
                        gridLayoutABM.visibility = View.GONE
                        gridLayoutCD.visibility = View.GONE
                        gridLayoutThemesABM.visibility = View.GONE
                        gridLayoutThemesCD.visibility = View.GONE
                        gridLayoutSpec.visibility = View.GONE
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        return root
    }

    private fun openTicketActivityCD(ticketNumber: Int) {
        val intent = Intent(this.context, Ticket::class.java)
        intent.putExtra("ticketType", "ABM")
        intent.putExtra("ticketNumber", ticketNumber)
        startActivity(intent)
    }

    private fun openTicketActivityThemes(ticketNumber: Int) {
        val intent = Intent(this.context, Ticket::class.java)
        intent.putExtra("ticketType", "ABM")
        intent.putExtra("ticketNumber", ticketNumber)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}