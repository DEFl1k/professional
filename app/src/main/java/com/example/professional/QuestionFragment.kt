package com.example.professional

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class QuestionFragment : Fragment() {
    private var ticketType: String = "ABM"
    private var ticketNumber: Int = 1
    private var questionNumber: Int = 1
    private lateinit var resultText: TextView
    private lateinit var nextButton: Button
    private var correctAnswersCount: Int = 0
    private lateinit var navigationButtons: LinearLayout
    private var buttonList: MutableList<Button> = mutableListOf()
    private var countQuestion = 20

    private lateinit var answers: MutableList<String>
    private lateinit var answeredQuestions: MutableList<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            ticketType = it.getString("ticketType", "ABM")
            ticketNumber = it.getInt("ticketNumber")
            questionNumber = it.getInt("questionNumber")
        }
        when(ticketType){
            "ThemesABM" -> {
                when(ticketNumber) {
                    1 -> countQuestion = 25
                    2 -> countQuestion = 126
                    3 -> countQuestion = 40
                    4 -> countQuestion = 39
                    5 -> countQuestion = 113
                    6 -> countQuestion = 19
                    7 -> countQuestion = 36
                    8 -> countQuestion = 47
                    9 -> countQuestion = 113
                    10 -> countQuestion = 23
                    11 -> countQuestion = 26
                    12 -> countQuestion = 59
                    13 -> countQuestion = 20
                    14 -> countQuestion = 15
                    15 -> countQuestion = 24
                    16 -> countQuestion = 5
                    17 -> countQuestion = 8
                    18 -> countQuestion = 9
                    19 -> countQuestion = 13
                    20 -> countQuestion = 4
                    21 -> countQuestion = 7
                    22 -> countQuestion = 11
                    23 -> countQuestion = 5
                    24 -> countQuestion = 6
                    25 -> countQuestion = 15
                    26 -> countQuestion = 9
                }
            }
            "ThemesCD" -> {
                when(ticketNumber) {
                    1 -> countQuestion = 26
                    2 -> countQuestion = 151
                    3 -> countQuestion = 41
                    4 -> countQuestion = 44
                    5 -> countQuestion = 119
                    6 -> countQuestion = 27
                    7 -> countQuestion = 42
                    8 -> countQuestion = 71
                    9 -> countQuestion = 120
                    10 -> countQuestion = 24
                    11 -> countQuestion = 26
                    12 -> countQuestion = 62
                    13 -> countQuestion = 20
                    14 -> countQuestion = 15
                    15 -> countQuestion = 26
                    16 -> countQuestion = 8
                    17 -> countQuestion = 10
                    18 -> countQuestion = 9
                    19 -> countQuestion = 13
                    20 -> countQuestion = 4
                    21 -> countQuestion = 10
                    22 -> countQuestion = 12
                    23 -> countQuestion = 5
                    24 -> countQuestion = 10
                    25 -> countQuestion = 15
                    26 -> countQuestion = 11
                }
            }
            else -> countQuestion = 20
        }
        answers = MutableList(countQuestion) { "" }
        answeredQuestions = MutableList(countQuestion) { false }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("questionNumber", questionNumber)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        questionNumber = savedInstanceState?.getInt("questionNumber") ?: 1
        updateQuestion()
    }


    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        nextButton = view.findViewById(R.id.nextButton)
        resultText = view.findViewById(R.id.resultText)
        navigationButtons = view.findViewById(R.id.navigationButtons)

        for (i in 1..countQuestion) {
            val button = Button(requireContext())
            button.text = i.toString()
            button.layoutParams = LinearLayout.LayoutParams(
                150,
                150
            ).apply {
                setMargins(5, 8, 5, 50)
            }

            button.textSize = 18f
            button.setPadding(16, 8, 16, 8)
            button.setBackgroundResource(R.drawable.gray_background)

            button.setOnClickListener {
                questionNumber = i
                updateQuestion()
            }

            buttonList.add(button)
            navigationButtons.addView(button)
        }

        nextButton.setOnClickListener {
            if (questionNumber < countQuestion) {
                questionNumber++
                updateQuestion()
            } else {
                resultText.text = "Билет завершен! Верных ответов: $correctAnswersCount из $countQuestion"
                nextButton.visibility = View.GONE
            }
        }

        return view
    }

    @SuppressLint("ResourceType")
    private fun checkAnswer(selectedAnswer: String, question: Question) {
        if (resultText.visibility == View.VISIBLE) {
            return
        }

        val correctAnswer = question.correctAnswer
        if (selectedAnswer == correctAnswer) {
            resultText.text = "Правильно!"
            resultText.setTextColor(resources.getColor(android.R.color.holo_green_dark))
            correctAnswersCount++
            buttonList[questionNumber - 1].setBackgroundResource(R.drawable.green_background)
        } else {
            resultText.text = "Неправильно! Верный ответ: ${correctAnswer}"
            resultText.setTextColor(resources.getColor(android.R.color.holo_red_dark))
            buttonList[questionNumber - 1].setBackgroundResource(R.drawable.red_background)
        }

        resultText.visibility = View.VISIBLE
        nextButton.visibility = View.VISIBLE
        answeredQuestions[questionNumber - 1] = true
    }

    companion object {
        fun newInstance(ticketType: String, ticketNumber: Int, questionNumber: Int) =
            QuestionFragment().apply {
                arguments = Bundle().apply {
                    putString("ticketType", ticketType)
                    putInt("ticketNumber", ticketNumber)
                    putInt("questionNumber", questionNumber)
                }
            }
    }

    private fun updateQuestion() {
        var question = getQuestionDataABM(1,1)
        when (ticketType) {
            "ThemesABM" -> {
                when (ticketNumber){
                    1 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(1, 1)
                            2 -> question = getQuestionDataABM(2, 1)
                            3 -> question = getQuestionDataABM(3, 1)
                            4 -> question = getQuestionDataABM(4, 1)
                            5 -> question = getQuestionDataABM(5, 1)
                            6 -> question = getQuestionDataABM(6, 1)
                            7 -> question = getQuestionDataABM(10, 1)
                            8 -> question = getQuestionDataABM(11, 1)
                            9 -> question = getQuestionDataABM(13, 1)
                            10 -> question = getQuestionDataABM(14, 1)
                            11 -> question = getQuestionDataABM(15, 1)
                            12 -> question = getQuestionDataABM(17, 1)
                            13 -> question = getQuestionDataABM(18, 1)
                            14 -> question = getQuestionDataABM(19, 1)
                            15 -> question = getQuestionDataABM(22, 1)
                            16 -> question = getQuestionDataABM(23, 1)
                            17 -> question = getQuestionDataABM(25, 1)
                            18 -> question = getQuestionDataABM(26, 1)
                            19 -> question = getQuestionDataABM(29, 1)
                            20 -> question = getQuestionDataABM(30, 1)
                            21 -> question = getQuestionDataABM(33, 1)
                            22 -> question = getQuestionDataABM(34, 1)
                            23 -> question = getQuestionDataABM(35, 1)
                            24 -> question = getQuestionDataABM(36, 1)
                            25 -> question = getQuestionDataABM(38, 1)
                            else -> {}
                        }
                    }
                    2 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(1, 2)
                            2 -> question = getQuestionDataABM(2, 2)
                            3 -> question = getQuestionDataABM(3, 2)
                            4 -> question = getQuestionDataABM(4, 2)
                            5 -> question = getQuestionDataABM(5, 2)
                            6 -> question = getQuestionDataABM(6, 2)
                            7 -> question = getQuestionDataABM(7, 2)
                            8 -> question = getQuestionDataABM(8, 2)
                            9 -> question = getQuestionDataABM(9, 2)
                            10 -> question = getQuestionDataABM(10, 2)
                            11 -> question = getQuestionDataABM(11, 2)
                            12 -> question = getQuestionDataABM(12, 2)
                            13 -> question = getQuestionDataABM(13, 2)
                            14 -> question = getQuestionDataABM(14, 2)
                            15 -> question = getQuestionDataABM(15, 2)
                            16 -> question = getQuestionDataABM(16, 2)
                            17 -> question = getQuestionDataABM(17, 2)
                            18 -> question = getQuestionDataABM(18, 2)
                            19 -> question = getQuestionDataABM(19, 2)
                            20 -> question = getQuestionDataABM(20, 2)
                            21 -> question = getQuestionDataABM(21, 2)
                            22 -> question = getQuestionDataABM(22, 2)
                            23 -> question = getQuestionDataABM(23, 2)
                            24 -> question = getQuestionDataABM(24, 2)
                            25 -> question = getQuestionDataABM(25, 2)
                            26 -> question = getQuestionDataABM(26, 2)
                            27 -> question = getQuestionDataABM(27, 2)
                            28 -> question = getQuestionDataABM(28, 2)
                            29 -> question = getQuestionDataABM(29, 2)
                            30 -> question = getQuestionDataABM(30, 2)
                            31 -> question = getQuestionDataABM(31, 2)
                            32 -> question = getQuestionDataABM(32, 2)
                            33 -> question = getQuestionDataABM(33, 2)
                            34 -> question = getQuestionDataABM(34, 2)
                            35 -> question = getQuestionDataABM(35, 2)
                            36 -> question = getQuestionDataABM(36, 2)
                            37 -> question = getQuestionDataABM(37, 2)
                            38 -> question = getQuestionDataABM(38, 2)
                            39 -> question = getQuestionDataABM(39, 2)
                            40 -> question = getQuestionDataABM(40, 2)
                            41 -> question = getQuestionDataABM(1, 3)
                            42 -> question = getQuestionDataABM(2, 3)
                            43 -> question = getQuestionDataABM(3, 3)
                            44 -> question = getQuestionDataABM(4, 3)
                            45 -> question = getQuestionDataABM(5, 3)
                            46 -> question = getQuestionDataABM(6, 3)
                            47 -> question = getQuestionDataABM(7, 3)
                            48 -> question = getQuestionDataABM(8, 3)
                            49 -> question = getQuestionDataABM(9, 3)
                            50 -> question = getQuestionDataABM(10, 3)
                            51 -> question = getQuestionDataABM(11, 3)
                            52 -> question = getQuestionDataABM(12, 3)
                            53 -> question = getQuestionDataABM(13, 3)
                            54 -> question = getQuestionDataABM(14, 3)
                            55 -> question = getQuestionDataABM(15, 3)
                            56 -> question = getQuestionDataABM(16, 3)
                            57 -> question = getQuestionDataABM(17, 3)
                            58 -> question = getQuestionDataABM(18, 3)
                            59 -> question = getQuestionDataABM(19, 3)
                            60 -> question = getQuestionDataABM(20, 3)
                            61 -> question = getQuestionDataABM(21, 3)
                            62 -> question = getQuestionDataABM(22, 3)
                            63 -> question = getQuestionDataABM(23, 3)
                            64 -> question = getQuestionDataABM(24, 3)
                            65 -> question = getQuestionDataABM(25, 3)
                            66 -> question = getQuestionDataABM(26, 3)
                            67 -> question = getQuestionDataABM(27, 3)
                            68 -> question = getQuestionDataABM(28, 3)
                            69 -> question = getQuestionDataABM(29, 3)
                            70 -> question = getQuestionDataABM(30, 3)
                            71 -> question = getQuestionDataABM(31, 3)
                            72 -> question = getQuestionDataABM(32, 3)
                            73 -> question = getQuestionDataABM(33, 3)
                            74 -> question = getQuestionDataABM(34, 3)
                            75 -> question = getQuestionDataABM(35, 3)
                            76 -> question = getQuestionDataABM(36, 3)
                            77 -> question = getQuestionDataABM(37, 3)
                            78 -> question = getQuestionDataABM(38, 3)
                            79 -> question = getQuestionDataABM(39, 3)
                            80 -> question = getQuestionDataABM(40, 3)
                            81 -> question = getQuestionDataABM(1, 4)
                            82 -> question = getQuestionDataABM(2, 4)
                            83 -> question = getQuestionDataABM(3, 4)
                            84 -> question = getQuestionDataABM(4, 4)
                            85 -> question = getQuestionDataABM(5, 4)
                            86 -> question = getQuestionDataABM(6, 4)
                            87 -> question = getQuestionDataABM(7, 4)
                            88 -> question = getQuestionDataABM(8, 4)
                            89 -> question = getQuestionDataABM(9, 4)
                            90 -> question = getQuestionDataABM(10, 4)
                            91 -> question = getQuestionDataABM(11, 4)
                            92 -> question = getQuestionDataABM(12, 4)
                            93 -> question = getQuestionDataABM(13, 4)
                            94 -> question = getQuestionDataABM(14, 4)
                            95 -> question = getQuestionDataABM(15, 4)
                            96 -> question = getQuestionDataABM(16, 4)
                            97 -> question = getQuestionDataABM(17, 4)
                            98 -> question = getQuestionDataABM(18, 4)
                            99 -> question = getQuestionDataABM(19, 4)
                            100 -> question = getQuestionDataABM(20, 4)
                            101 -> question = getQuestionDataABM(21, 4)
                            102 -> question = getQuestionDataABM(22, 4)
                            103 -> question = getQuestionDataABM(23, 4)
                            104 -> question = getQuestionDataABM(24, 4)
                            105 -> question = getQuestionDataABM(25, 4)
                            106 -> question = getQuestionDataABM(26, 4)
                            107 -> question = getQuestionDataABM(27, 4)
                            108 -> question = getQuestionDataABM(28, 4)
                            109 -> question = getQuestionDataABM(29, 4)
                            110 -> question = getQuestionDataABM(30, 4)
                            111 -> question = getQuestionDataABM(31, 4)
                            112 -> question = getQuestionDataABM(32, 4)
                            113 -> question = getQuestionDataABM(33, 4)
                            114 -> question = getQuestionDataABM(34, 4)
                            115 -> question = getQuestionDataABM(36, 4)
                            116 -> question = getQuestionDataABM(37, 4)
                            117 -> question = getQuestionDataABM(38, 4)
                            118 -> question = getQuestionDataABM(39, 4)
                            119 -> question = getQuestionDataABM(40, 4)
                            120 -> question = getQuestionDataABM(3, 5)
                            121 -> question = getQuestionDataABM(1, 10)
                            122 -> question = getQuestionDataABM(19, 10)
                            123 -> question = getQuestionDataABM(20, 12)
                            124 -> question = getQuestionDataABM(13, 15)
                            125 -> question = getQuestionDataABM(15, 15)
                            126 -> question = getQuestionDataABM(23, 15)
                            else -> {}
                        }
                    }
                    3 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(7, 3)
                            2 -> question = getQuestionDataABM(1, 5)
                            3 -> question = getQuestionDataABM(2, 5)
                            4 -> question = getQuestionDataABM(3, 5)
                            5 -> question = getQuestionDataABM(4, 5)
                            6 -> question = getQuestionDataABM(5, 5)
                            7 -> question = getQuestionDataABM(6, 5)
                            8 -> question = getQuestionDataABM(7, 5)
                            9 -> question = getQuestionDataABM(9, 5)
                            10 -> question = getQuestionDataABM(10, 5)
                            11 -> question = getQuestionDataABM(11, 5)
                            12 -> question = getQuestionDataABM(12, 5)
                            13 -> question = getQuestionDataABM(13, 5)
                            14 -> question = getQuestionDataABM(14, 5)
                            15 -> question = getQuestionDataABM(15, 5)
                            16 -> question = getQuestionDataABM(16, 5)
                            17 -> question = getQuestionDataABM(17, 5)
                            18 -> question = getQuestionDataABM(18, 5)
                            19 -> question = getQuestionDataABM(19, 5)
                            20 -> question = getQuestionDataABM(20, 5)
                            21 -> question = getQuestionDataABM(21, 5)
                            22 -> question = getQuestionDataABM(22, 5)
                            23 -> question = getQuestionDataABM(23, 5)
                            24 -> question = getQuestionDataABM(24, 5)
                            25 -> question = getQuestionDataABM(25, 5)
                            26 -> question = getQuestionDataABM(26, 5)
                            27 -> question = getQuestionDataABM(27, 5)
                            28 -> question = getQuestionDataABM(28, 5)
                            29 -> question = getQuestionDataABM(29, 5)
                            30 -> question = getQuestionDataABM(30, 5)
                            31 -> question = getQuestionDataABM(31, 5)
                            32 -> question = getQuestionDataABM(32, 5)
                            33 -> question = getQuestionDataABM(33, 5)
                            34 -> question = getQuestionDataABM(34, 5)
                            35 -> question = getQuestionDataABM(35, 5)
                            36 -> question = getQuestionDataABM(36, 5)
                            37 -> question = getQuestionDataABM(37, 5)
                            38 -> question = getQuestionDataABM(38, 5)
                            39 -> question = getQuestionDataABM(39, 5)
                            40 -> question = getQuestionDataABM(40, 5)
                            else -> {}
                        }
                    }
                    4 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(8, 5)
                            2 -> question = getQuestionDataABM(1, 6)
                            3 -> question = getQuestionDataABM(2, 6)
                            4 -> question = getQuestionDataABM(4, 6)
                            5 -> question = getQuestionDataABM(5, 6)
                            6 -> question = getQuestionDataABM(6, 6)
                            7 -> question = getQuestionDataABM(7, 6)
                            8 -> question = getQuestionDataABM(8, 6)
                            9 -> question = getQuestionDataABM(9, 6)
                            10 -> question = getQuestionDataABM(12, 6)
                            11 -> question = getQuestionDataABM(13, 6)
                            12 -> question = getQuestionDataABM(14, 6)
                            13 -> question = getQuestionDataABM(15, 6)
                            14 -> question = getQuestionDataABM(16, 6)
                            15 -> question = getQuestionDataABM(17, 6)
                            16 -> question = getQuestionDataABM(18, 6)
                            17 -> question = getQuestionDataABM(20, 6)
                            18 -> question = getQuestionDataABM(21, 6)
                            19 -> question = getQuestionDataABM(23, 6)
                            20 -> question = getQuestionDataABM(24, 6)
                            21 -> question = getQuestionDataABM(25, 6)
                            22 -> question = getQuestionDataABM(26, 6)
                            23 -> question = getQuestionDataABM(27, 6)
                            24 -> question = getQuestionDataABM(28, 6)
                            25 -> question = getQuestionDataABM(29, 6)
                            26 -> question = getQuestionDataABM(30, 6)
                            27 -> question = getQuestionDataABM(31, 6)
                            28 -> question = getQuestionDataABM(32, 6)
                            29 -> question = getQuestionDataABM(34, 6)
                            30 -> question = getQuestionDataABM(35, 6)
                            31 -> question = getQuestionDataABM(37, 6)
                            32 -> question = getQuestionDataABM(39, 6)
                            33 -> question = getQuestionDataABM(40, 6)
                            34 -> question = getQuestionDataABM(5, 13)
                            35 -> question = getQuestionDataABM(6, 13)
                            36 -> question = getQuestionDataABM(12, 13)
                            37 -> question = getQuestionDataABM(23, 13)
                            38 -> question = getQuestionDataABM(25, 13)
                            39 -> question = getQuestionDataABM(27, 13)
                            else -> {}
                        }
                    }
                    5 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(1, 7)
                            2 -> question = getQuestionDataABM(2, 7)
                            3 -> question = getQuestionDataABM(3, 7)
                            4 -> question = getQuestionDataABM(4, 7)
                            5 -> question = getQuestionDataABM(5, 7)
                            6 -> question = getQuestionDataABM(6, 7)
                            7 -> question = getQuestionDataABM(7, 7)
                            8 -> question = getQuestionDataABM(8, 7)
                            9 -> question = getQuestionDataABM(9, 7)
                            10 -> question = getQuestionDataABM(10, 7)
                            11 -> question = getQuestionDataABM(11, 7)
                            12 -> question = getQuestionDataABM(12, 7)
                            13 -> question = getQuestionDataABM(13, 7)
                            14 -> question = getQuestionDataABM(14, 7)
                            15 -> question = getQuestionDataABM(18, 7)
                            16 -> question = getQuestionDataABM(19, 7)
                            17 -> question = getQuestionDataABM(21, 7)
                            18 -> question = getQuestionDataABM(22, 7)
                            19 -> question = getQuestionDataABM(23, 7)
                            20 -> question = getQuestionDataABM(24, 7)
                            21 -> question = getQuestionDataABM(25, 7)
                            22 -> question = getQuestionDataABM(26, 7)
                            23 -> question = getQuestionDataABM(27, 7)
                            24 -> question = getQuestionDataABM(29, 7)
                            25 -> question = getQuestionDataABM(30, 7)
                            26 -> question = getQuestionDataABM(31, 7)
                            27 -> question = getQuestionDataABM(32, 7)
                            28 -> question = getQuestionDataABM(33, 7)
                            29 -> question = getQuestionDataABM(36, 7)
                            30 -> question = getQuestionDataABM(37, 7)
                            31 -> question = getQuestionDataABM(38, 7)
                            32 -> question = getQuestionDataABM(1, 8)
                            33 -> question = getQuestionDataABM(2, 8)
                            34 -> question = getQuestionDataABM(3, 8)
                            35 -> question = getQuestionDataABM(4, 8)
                            36 -> question = getQuestionDataABM(5, 8)
                            37 -> question = getQuestionDataABM(6, 8)
                            38 -> question = getQuestionDataABM(7, 8)
                            39 -> question = getQuestionDataABM(8, 8)
                            40 -> question = getQuestionDataABM(9, 8)
                            41 -> question = getQuestionDataABM(10, 8)
                            42 -> question = getQuestionDataABM(11, 8)
                            43 -> question = getQuestionDataABM(12, 8)
                            44 -> question = getQuestionDataABM(13, 8)
                            45 -> question = getQuestionDataABM(14, 8)
                            46 -> question = getQuestionDataABM(15, 8)
                            47 -> question = getQuestionDataABM(16, 8)
                            48 -> question = getQuestionDataABM(17, 8)
                            49 -> question = getQuestionDataABM(18, 8)
                            50 -> question = getQuestionDataABM(19, 8)
                            51 -> question = getQuestionDataABM(20, 8)
                            52 -> question = getQuestionDataABM(21, 8)
                            53 -> question = getQuestionDataABM(22, 8)
                            54 -> question = getQuestionDataABM(23, 8)
                            55 -> question = getQuestionDataABM(24, 8)
                            56 -> question = getQuestionDataABM(25, 8)
                            57 -> question = getQuestionDataABM(26, 8)
                            58 -> question = getQuestionDataABM(27, 8)
                            59 -> question = getQuestionDataABM(28, 8)
                            60 -> question = getQuestionDataABM(29, 8)
                            61 -> question = getQuestionDataABM(30, 8)
                            62 -> question = getQuestionDataABM(31, 8)
                            63 -> question = getQuestionDataABM(32, 8)
                            64 -> question = getQuestionDataABM(33, 8)
                            65 -> question = getQuestionDataABM(34, 8)
                            66 -> question = getQuestionDataABM(35, 8)
                            67 -> question = getQuestionDataABM(36, 8)
                            68 -> question = getQuestionDataABM(37, 8)
                            69 -> question = getQuestionDataABM(38, 8)
                            70 -> question = getQuestionDataABM(39, 8)
                            71 -> question = getQuestionDataABM(40, 8)
                            72 -> question = getQuestionDataABM(1, 9)
                            73 -> question = getQuestionDataABM(2, 9)
                            74 -> question = getQuestionDataABM(3, 9)
                            75 -> question = getQuestionDataABM(4, 9)
                            76 -> question = getQuestionDataABM(5, 9)
                            77 -> question = getQuestionDataABM(6, 9)
                            78 -> question = getQuestionDataABM(7, 9)
                            79 -> question = getQuestionDataABM(8, 9)
                            80 -> question = getQuestionDataABM(9, 9)
                            81 -> question = getQuestionDataABM(10, 9)
                            82 -> question = getQuestionDataABM(11, 9)
                            83 -> question = getQuestionDataABM(12, 9)
                            84 -> question = getQuestionDataABM(13, 9)
                            85 -> question = getQuestionDataABM(14, 9)
                            86 -> question = getQuestionDataABM(15, 9)
                            87 -> question = getQuestionDataABM(16, 9)
                            88 -> question = getQuestionDataABM(17, 9)
                            89 -> question = getQuestionDataABM(18, 9)
                            90 -> question = getQuestionDataABM(19, 9)
                            91 -> question = getQuestionDataABM(20, 9)
                            92 -> question = getQuestionDataABM(21, 9)
                            93 -> question = getQuestionDataABM(22, 9)
                            94 -> question = getQuestionDataABM(23, 9)
                            95 -> question = getQuestionDataABM(24, 9)
                            96 -> question = getQuestionDataABM(25, 9)
                            97 -> question = getQuestionDataABM(26, 9)
                            98 -> question = getQuestionDataABM(27, 9)
                            99 -> question = getQuestionDataABM(28, 9)
                            100 -> question = getQuestionDataABM(29, 9)
                            101 -> question = getQuestionDataABM(30, 9)
                            102 -> question = getQuestionDataABM(31, 9)
                            103 -> question = getQuestionDataABM(32, 9)
                            104 -> question = getQuestionDataABM(33, 9)
                            105 -> question = getQuestionDataABM(34, 9)
                            106 -> question = getQuestionDataABM(35, 9)
                            107 -> question = getQuestionDataABM(36, 9)
                            108 -> question = getQuestionDataABM(37, 9)
                            109 -> question = getQuestionDataABM(38, 9)
                            110 -> question = getQuestionDataABM(39, 9)
                            111 -> question = getQuestionDataABM(40, 9)
                            112 -> question = getQuestionDataABM(37, 10)
                            113 -> question = getQuestionDataABM(6, 13)
                            else -> {}
                        }
                    }
                    6 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(33, 3)
                            2 -> question = getQuestionDataABM(5, 10)
                            3 -> question = getQuestionDataABM(6, 10)
                            4 -> question = getQuestionDataABM(7, 10)
                            5 -> question = getQuestionDataABM(12, 10)
                            6 -> question = getQuestionDataABM(13, 10)
                            7 -> question = getQuestionDataABM(15, 10)
                            8 -> question = getQuestionDataABM(16, 10)
                            9 -> question = getQuestionDataABM(17, 10)
                            10 -> question = getQuestionDataABM(18, 10)
                            11 -> question = getQuestionDataABM(19, 10)
                            12 -> question = getQuestionDataABM(24, 10)
                            13 -> question = getQuestionDataABM(25, 10)
                            14 -> question = getQuestionDataABM(31, 10)
                            15 -> question = getQuestionDataABM(32, 10)
                            16 -> question = getQuestionDataABM(35, 10)
                            17 -> question = getQuestionDataABM(38, 10)
                            18 -> question = getQuestionDataABM(39, 10)
                            19 -> question = getQuestionDataABM(11, 16)
                            else -> {}
                        }
                    }
                    7 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(1, 11)
                            2 -> question = getQuestionDataABM(2, 11)
                            3 -> question = getQuestionDataABM(3, 11)
                            4 -> question = getQuestionDataABM(4, 11)
                            5 -> question = getQuestionDataABM(5, 11)
                            6 -> question = getQuestionDataABM(6, 11)
                            7 -> question = getQuestionDataABM(7, 11)
                            8 -> question = getQuestionDataABM(10, 11)
                            9 -> question = getQuestionDataABM(11, 11)
                            10 -> question = getQuestionDataABM(12, 11)
                            11 -> question = getQuestionDataABM(13, 11)
                            12 -> question = getQuestionDataABM(14, 11)
                            13 -> question = getQuestionDataABM(15, 11)
                            14 -> question = getQuestionDataABM(16, 11)
                            15 -> question = getQuestionDataABM(17, 11)
                            16 -> question = getQuestionDataABM(18, 11)
                            17 -> question = getQuestionDataABM(20, 11)
                            18 -> question = getQuestionDataABM(21, 11)
                            19 -> question = getQuestionDataABM(22, 11)
                            20 -> question = getQuestionDataABM(23, 11)
                            21 -> question = getQuestionDataABM(24, 11)
                            22 -> question = getQuestionDataABM(25, 11)
                            23 -> question = getQuestionDataABM(26, 11)
                            24 -> question = getQuestionDataABM(27, 11)
                            25 -> question = getQuestionDataABM(28, 11)
                            26 -> question = getQuestionDataABM(29, 11)
                            27 -> question = getQuestionDataABM(30, 11)
                            28 -> question = getQuestionDataABM(31, 11)
                            29 -> question = getQuestionDataABM(32, 11)
                            30 -> question = getQuestionDataABM(33, 11)
                            31 -> question = getQuestionDataABM(35, 11)
                            32 -> question = getQuestionDataABM(36, 11)
                            33 -> question = getQuestionDataABM(37, 11)
                            34 -> question = getQuestionDataABM(38, 11)
                            35 -> question = getQuestionDataABM(39, 11)
                            36 -> question = getQuestionDataABM(40, 11)
                            else -> {}
                        }
                    }
                    8 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(36, 3)
                            2 -> question = getQuestionDataABM(5, 4)
                            3 -> question = getQuestionDataABM(29, 4)
                            4 -> question = getQuestionDataABM(35, 4)
                            5 -> question = getQuestionDataABM(37, 4)
                            6 -> question = getQuestionDataABM(40, 4)
                            7 -> question = getQuestionDataABM(1, 12)
                            8 -> question = getQuestionDataABM(2, 12)
                            9 -> question = getQuestionDataABM(3, 12)
                            10 -> question = getQuestionDataABM(4, 12)
                            11 -> question = getQuestionDataABM(5, 12)
                            12 -> question = getQuestionDataABM(6, 12)
                            13 -> question = getQuestionDataABM(7, 12)
                            14 -> question = getQuestionDataABM(8, 12)
                            15 -> question = getQuestionDataABM(9, 12)
                            16 -> question = getQuestionDataABM(10, 12)
                            17 -> question = getQuestionDataABM(11, 12)
                            18 -> question = getQuestionDataABM(12, 12)
                            19 -> question = getQuestionDataABM(13, 12)
                            20 -> question = getQuestionDataABM(14, 12)
                            21 -> question = getQuestionDataABM(15, 12)
                            22 -> question = getQuestionDataABM(16, 12)
                            23 -> question = getQuestionDataABM(17, 12)
                            24 -> question = getQuestionDataABM(18, 12)
                            25 -> question = getQuestionDataABM(19, 12)
                            26 -> question = getQuestionDataABM(20, 12)
                            27 -> question = getQuestionDataABM(21, 12)
                            28 -> question = getQuestionDataABM(22, 12)
                            29 -> question = getQuestionDataABM(23, 12)
                            30 -> question = getQuestionDataABM(24, 12)
                            31 -> question = getQuestionDataABM(25, 12)
                            32 -> question = getQuestionDataABM(26, 12)
                            33 -> question = getQuestionDataABM(27, 12)
                            34 -> question = getQuestionDataABM(28, 12)
                            35 -> question = getQuestionDataABM(29, 12)
                            36 -> question = getQuestionDataABM(30, 12)
                            37 -> question = getQuestionDataABM(31, 12)
                            38 -> question = getQuestionDataABM(32, 12)
                            39 -> question = getQuestionDataABM(33, 12)
                            40 -> question = getQuestionDataABM(34, 12)
                            41 -> question = getQuestionDataABM(35, 12)
                            42 -> question = getQuestionDataABM(36, 12)
                            43 -> question = getQuestionDataABM(37, 12)
                            44 -> question = getQuestionDataABM(38, 12)
                            45 -> question = getQuestionDataABM(39, 12)
                            46 -> question = getQuestionDataABM(40, 12)
                            47 -> question = getQuestionDataABM(23, 16)
                            else -> {}
                        }
                    }
                    9 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(10, 6)
                            2 -> question = getQuestionDataABM(1, 13)
                            3 -> question = getQuestionDataABM(2, 13)
                            4 -> question = getQuestionDataABM(3, 13)
                            5 -> question = getQuestionDataABM(4, 13)
                            6 -> question = getQuestionDataABM(7, 13)
                            7 -> question = getQuestionDataABM(8, 13)
                            8 -> question = getQuestionDataABM(9, 13)
                            9 -> question = getQuestionDataABM(10, 13)
                            10 -> question = getQuestionDataABM(13, 13)
                            11 -> question = getQuestionDataABM(13, 13)
                            12 -> question = getQuestionDataABM(14, 13)
                            13 -> question = getQuestionDataABM(15, 13)
                            14 -> question = getQuestionDataABM(16, 13)
                            15 -> question = getQuestionDataABM(17, 13)
                            16 -> question = getQuestionDataABM(18, 13)
                            17 -> question = getQuestionDataABM(19, 13)
                            18 -> question = getQuestionDataABM(20, 13)
                            19 -> question = getQuestionDataABM(21, 13)
                            20 -> question = getQuestionDataABM(22, 13)
                            21 -> question = getQuestionDataABM(24, 13)
                            22 -> question = getQuestionDataABM(26, 13)
                            23 -> question = getQuestionDataABM(28, 13)
                            24 -> question = getQuestionDataABM(29, 13)
                            25 -> question = getQuestionDataABM(30, 13)
                            26 -> question = getQuestionDataABM(31, 13)
                            27 -> question = getQuestionDataABM(32, 13)
                            28 -> question = getQuestionDataABM(33, 13)
                            29 -> question = getQuestionDataABM(34, 13)
                            30 -> question = getQuestionDataABM(35, 13)
                            31 -> question = getQuestionDataABM(36, 13)
                            32 -> question = getQuestionDataABM(37, 13)
                            33 -> question = getQuestionDataABM(38, 13)
                            34 -> question = getQuestionDataABM(39, 13)
                            35 -> question = getQuestionDataABM(40, 13)
                            36 -> question = getQuestionDataABM(1, 14)
                            37 -> question = getQuestionDataABM(2, 14)
                            38 -> question = getQuestionDataABM(3, 14)
                            39 -> question = getQuestionDataABM(5, 14)
                            40 -> question = getQuestionDataABM(6, 14)
                            41 -> question = getQuestionDataABM(7, 14)
                            42 -> question = getQuestionDataABM(8, 14)
                            43 -> question = getQuestionDataABM(9, 14)
                            44 -> question = getQuestionDataABM(10, 14)
                            45 -> question = getQuestionDataABM(11, 14)
                            46 -> question = getQuestionDataABM(12, 14)
                            47 -> question = getQuestionDataABM(13, 14)
                            48 -> question = getQuestionDataABM(14, 14)
                            49 -> question = getQuestionDataABM(15, 14)
                            50 -> question = getQuestionDataABM(16, 14)
                            51 -> question = getQuestionDataABM(17, 14)
                            52 -> question = getQuestionDataABM(18, 14)
                            53 -> question = getQuestionDataABM(19, 14)
                            54 -> question = getQuestionDataABM(20, 14)
                            55 -> question = getQuestionDataABM(21, 14)
                            56 -> question = getQuestionDataABM(22, 14)
                            57 -> question = getQuestionDataABM(23, 14)
                            58 -> question = getQuestionDataABM(24, 14)
                            59 -> question = getQuestionDataABM(25, 14)
                            60 -> question = getQuestionDataABM(26, 14)
                            61 -> question = getQuestionDataABM(27, 14)
                            62 -> question = getQuestionDataABM(28, 14)
                            63 -> question = getQuestionDataABM(29, 14)
                            64 -> question = getQuestionDataABM(30, 14)
                            65 -> question = getQuestionDataABM(31, 14)
                            66 -> question = getQuestionDataABM(32, 14)
                            67 -> question = getQuestionDataABM(33, 14)
                            68 -> question = getQuestionDataABM(34, 14)
                            69 -> question = getQuestionDataABM(35, 14)
                            70 -> question = getQuestionDataABM(36, 14)
                            71 -> question = getQuestionDataABM(37, 14)
                            72 -> question = getQuestionDataABM(38, 14)
                            73 -> question = getQuestionDataABM(39, 14)
                            74 -> question = getQuestionDataABM(40, 14)
                            75 -> question = getQuestionDataABM(1, 15)
                            76 -> question = getQuestionDataABM(2, 15)
                            77 -> question = getQuestionDataABM(3, 15)
                            78 -> question = getQuestionDataABM(4, 15)
                            79 -> question = getQuestionDataABM(5, 15)
                            80 -> question = getQuestionDataABM(7, 15)
                            81 -> question = getQuestionDataABM(8, 15)
                            82 -> question = getQuestionDataABM(9, 15)
                            83 -> question = getQuestionDataABM(10, 15)
                            84 -> question = getQuestionDataABM(11, 15)
                            85 -> question = getQuestionDataABM(12, 15)
                            86 -> question = getQuestionDataABM(13, 15)
                            87 -> question = getQuestionDataABM(14, 15)
                            88 -> question = getQuestionDataABM(15, 15)
                            89 -> question = getQuestionDataABM(16, 15)
                            90 -> question = getQuestionDataABM(17, 15)
                            91 -> question = getQuestionDataABM(18, 15)
                            92 -> question = getQuestionDataABM(19, 15)
                            93 -> question = getQuestionDataABM(20, 15)
                            94 -> question = getQuestionDataABM(21, 15)
                            95 -> question = getQuestionDataABM(22, 15)
                            96 -> question = getQuestionDataABM(23, 15)
                            97 -> question = getQuestionDataABM(24, 15)
                            98 -> question = getQuestionDataABM(25, 15)
                            99 -> question = getQuestionDataABM(26, 15)
                            100 -> question = getQuestionDataABM(27, 15)
                            101 -> question = getQuestionDataABM(28, 15)
                            102 -> question = getQuestionDataABM(29, 15)
                            103 -> question = getQuestionDataABM(30, 15)
                            104 -> question = getQuestionDataABM(31, 15)
                            105 -> question = getQuestionDataABM(32, 15)
                            106 -> question = getQuestionDataABM(33, 15)
                            107 -> question = getQuestionDataABM(34, 15)
                            108 -> question = getQuestionDataABM(35, 15)
                            109 -> question = getQuestionDataABM(36, 15)
                            110 -> question = getQuestionDataABM(37, 15)
                            111 -> question = getQuestionDataABM(38, 15)
                            112 -> question = getQuestionDataABM(39, 15)
                            113 -> question = getQuestionDataABM(40, 15)
                            else -> {}
                        }
                    }
                    10 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(5, 17)
                            2 -> question = getQuestionDataABM(8, 17)
                            3 -> question = getQuestionDataABM(10, 17)
                            4 -> question = getQuestionDataABM(11, 17)
                            5 -> question = getQuestionDataABM(12, 17)
                            6 -> question = getQuestionDataABM(13, 17)
                            7 -> question = getQuestionDataABM(15, 17)
                            8 -> question = getQuestionDataABM(19, 17)
                            9 -> question = getQuestionDataABM(20, 17)
                            10 -> question = getQuestionDataABM(21, 17)
                            11 -> question = getQuestionDataABM(22, 17)
                            12 -> question = getQuestionDataABM(23, 17)
                            13 -> question = getQuestionDataABM(24, 17)
                            14 -> question = getQuestionDataABM(25, 17)
                            15 -> question = getQuestionDataABM(28, 17)
                            16 -> question = getQuestionDataABM(29, 17)
                            17 -> question = getQuestionDataABM(30, 17)
                            18 -> question = getQuestionDataABM(31, 17)
                            19 -> question = getQuestionDataABM(33, 17)
                            20 -> question = getQuestionDataABM(34, 17)
                            21 -> question = getQuestionDataABM(35, 17)
                            22 -> question = getQuestionDataABM(36, 17)
                            23 -> question = getQuestionDataABM(10, 19)
                            else -> {}
                        }
                    }
                    11 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(1, 18)
                            2 -> question = getQuestionDataABM(2, 18)
                            3 -> question = getQuestionDataABM(3, 18)
                            4 -> question = getQuestionDataABM(4, 18)
                            5 -> question = getQuestionDataABM(5, 18)
                            6 -> question = getQuestionDataABM(6, 18)
                            7 -> question = getQuestionDataABM(7, 18)
                            8 -> question = getQuestionDataABM(8, 18)
                            9 -> question = getQuestionDataABM(9, 18)
                            10 -> question = getQuestionDataABM(10, 18)
                            11 -> question = getQuestionDataABM(12, 18)
                            12 -> question = getQuestionDataABM(13, 18)
                            13 -> question = getQuestionDataABM(14, 18)
                            14 -> question = getQuestionDataABM(15, 18)
                            15 -> question = getQuestionDataABM(16, 18)
                            16 -> question = getQuestionDataABM(22, 18)
                            17 -> question = getQuestionDataABM(24, 18)
                            18 -> question = getQuestionDataABM(25, 18)
                            19 -> question = getQuestionDataABM(26, 18)
                            20 -> question = getQuestionDataABM(28, 18)
                            21 -> question = getQuestionDataABM(29, 18)
                            22 -> question = getQuestionDataABM(31, 18)
                            23 -> question = getQuestionDataABM(32, 18)
                            24 -> question = getQuestionDataABM(35, 18)
                            25 -> question = getQuestionDataABM(37, 18)
                            26 -> question = getQuestionDataABM(39, 18)
                            else -> {}
                        }
                    }
                    12 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(10, 10)
                            2 -> question = getQuestionDataABM(1, 19)
                            3 -> question = getQuestionDataABM(2, 19)
                            4 -> question = getQuestionDataABM(3, 19)
                            5 -> question = getQuestionDataABM(4, 19)
                            6 -> question = getQuestionDataABM(5, 19)
                            7 -> question = getQuestionDataABM(6, 19)
                            8 -> question = getQuestionDataABM(7, 19)
                            9 -> question = getQuestionDataABM(8, 19)
                            10 -> question = getQuestionDataABM(9, 19)
                            in 11..40 -> question = getQuestionDataABM(questionNumber, 19)
                            41 -> question = getQuestionDataABM(1, 20)
                            42 -> question = getQuestionDataABM(5, 20)
                            43 -> question = getQuestionDataABM(9, 20)
                            44 -> question = getQuestionDataABM(11, 20)
                            45 -> question = getQuestionDataABM(14, 20)
                            46 -> question = getQuestionDataABM(17, 20)
                            47 -> question = getQuestionDataABM(18, 20)
                            48 -> question = getQuestionDataABM(20, 20)
                            49 -> question = getQuestionDataABM(22, 20)
                            50 -> question = getQuestionDataABM(23, 20)
                            51 -> question = getQuestionDataABM(26, 20)
                            52 -> question = getQuestionDataABM(27, 20)
                            53 -> question = getQuestionDataABM(28, 20)
                            54 -> question = getQuestionDataABM(30, 20)
                            55 -> question = getQuestionDataABM(31, 20)
                            56 -> question = getQuestionDataABM(33, 20)
                            57 -> question = getQuestionDataABM(35, 20)
                            58 -> question = getQuestionDataABM(36, 20)
                            59 -> question = getQuestionDataABM(39, 20)
                            else -> {}
                        }
                    }
                    13 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(2, 20)
                            2 -> question = getQuestionDataABM(3, 20)
                            3 -> question = getQuestionDataABM(4, 20)
                            4 -> question = getQuestionDataABM(7, 20)
                            5 -> question = getQuestionDataABM(8, 20)
                            6 -> question = getQuestionDataABM(10, 20)
                            7 -> question = getQuestionDataABM(12, 20)
                            8 -> question = getQuestionDataABM(13, 20)
                            9 -> question = getQuestionDataABM(15, 20)
                            10 -> question = getQuestionDataABM(16, 20)
                            11 -> question = getQuestionDataABM(19, 20)
                            12 -> question = getQuestionDataABM(21, 20)
                            13 -> question = getQuestionDataABM(24, 20)
                            14 -> question = getQuestionDataABM(25, 20)
                            15 -> question = getQuestionDataABM(29, 20)
                            16 -> question = getQuestionDataABM(32, 20)
                            17 -> question = getQuestionDataABM(34, 20)
                            18 -> question = getQuestionDataABM(37, 20)
                            19 -> question = getQuestionDataABM(38, 20)
                            20 -> question = getQuestionDataABM(40, 20)
                            else -> {}
                        }
                    }
                    14 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(7, 1)
                            2 -> question = getQuestionDataABM(8, 1)
                            3 -> question = getQuestionDataABM(9, 1)
                            4 -> question = getQuestionDataABM(12, 1)
                            5 -> question = getQuestionDataABM(16, 1)
                            6 -> question = getQuestionDataABM(20, 1)
                            7 -> question = getQuestionDataABM(21, 1)
                            8 -> question = getQuestionDataABM(24, 1)
                            9 -> question = getQuestionDataABM(27, 1)
                            10 -> question = getQuestionDataABM(28, 1)
                            11 -> question = getQuestionDataABM(31, 1)
                            12 -> question = getQuestionDataABM(32, 1)
                            13 -> question = getQuestionDataABM(37, 1)
                            14 -> question = getQuestionDataABM(39, 1)
                            15 -> question = getQuestionDataABM(40, 1)
                            else -> {}
                        }
                    }
                    15 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(2, 10)
                            2 -> question = getQuestionDataABM(3, 10)
                            3 -> question = getQuestionDataABM(4, 10)
                            4 -> question = getQuestionDataABM(8, 10)
                            5 -> question = getQuestionDataABM(9, 10)
                            6 -> question = getQuestionDataABM(11, 10)
                            7 -> question = getQuestionDataABM(14, 10)
                            8 -> question = getQuestionDataABM(20, 10)
                            9 -> question = getQuestionDataABM(21, 10)
                            10 -> question = getQuestionDataABM(22, 10)
                            11 -> question = getQuestionDataABM(23, 10)
                            12 -> question = getQuestionDataABM(26, 10)
                            13 -> question = getQuestionDataABM(27, 10)
                            14 -> question = getQuestionDataABM(28, 10)
                            15 -> question = getQuestionDataABM(29, 10)
                            16 -> question = getQuestionDataABM(30, 10)
                            17 -> question = getQuestionDataABM(33, 10)
                            18 -> question = getQuestionDataABM(34, 10)
                            19 -> question = getQuestionDataABM(36, 10)
                            20 -> question = getQuestionDataABM(40, 10)
                            21 -> question = getQuestionDataABM(8, 11)
                            22 -> question = getQuestionDataABM(9, 11)
                            23 -> question = getQuestionDataABM(19, 11)
                            24 -> question = getQuestionDataABM(34, 11)
                            else -> {}
                        }
                    }
                    16 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(13, 16)
                            2 -> question = getQuestionDataABM(15, 16)
                            3 -> question = getQuestionDataABM(17, 16)
                            4 -> question = getQuestionDataABM(18, 16)
                            5 -> question = getQuestionDataABM(19, 16)
                            else -> {}
                        }
                    }
                    17 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(2, 17)
                            2 -> question = getQuestionDataABM(4, 17)
                            3 -> question = getQuestionDataABM(14, 17)
                            4 -> question = getQuestionDataABM(17, 17)
                            5 -> question = getQuestionDataABM(18, 17)
                            6 -> question = getQuestionDataABM(27, 17)
                            7 -> question = getQuestionDataABM(39, 17)
                            8 -> question = getQuestionDataABM(40, 17)
                            else -> {}
                        }
                    }
                    18 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(3, 6)
                            2 -> question = getQuestionDataABM(11, 6)
                            3 -> question = getQuestionDataABM(19, 6)
                            4 -> question = getQuestionDataABM(22, 6)
                            5 -> question = getQuestionDataABM(33, 6)
                            6 -> question = getQuestionDataABM(36, 6)
                            7 -> question = getQuestionDataABM(38, 6)
                            8 -> question = getQuestionDataABM(12, 14)
                            9 -> question = getQuestionDataABM(6, 15)
                            else -> {}
                        }
                    }
                    19 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(3, 16)
                            2 -> question = getQuestionDataABM(8, 16)
                            3 -> question = getQuestionDataABM(9, 16)
                            4 -> question = getQuestionDataABM(12, 16)
                            5 -> question = getQuestionDataABM(14, 16)
                            6 -> question = getQuestionDataABM(16, 16)
                            7 -> question = getQuestionDataABM(28, 16)
                            8 -> question = getQuestionDataABM(29, 16)
                            9 -> question = getQuestionDataABM(31, 16)
                            10 -> question = getQuestionDataABM(32, 16)
                            11 -> question = getQuestionDataABM(33, 16)
                            12 -> question = getQuestionDataABM(37, 16)
                            13 -> question = getQuestionDataABM(39, 16)
                            else -> {}
                        }
                    }
                    20 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(3, 17)
                            2 -> question = getQuestionDataABM(6, 17)
                            3 -> question = getQuestionDataABM(9, 17)
                            4 -> question = getQuestionDataABM(37, 17)
                            else -> {}
                        }
                    }
                    21 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(1, 16)
                            2 -> question = getQuestionDataABM(4, 16)
                            3 -> question = getQuestionDataABM(7, 16)
                            4 -> question = getQuestionDataABM(11, 16)
                            5 -> question = getQuestionDataABM(21, 16)
                            6 -> question = getQuestionDataABM(22, 16)
                            7 -> question = getQuestionDataABM(36, 16)
                            else -> {}
                        }
                    }
                    22 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(13, 6)
                            2 -> question = getQuestionDataABM(2, 16)
                            3 -> question = getQuestionDataABM(5, 16)
                            4 -> question = getQuestionDataABM(10, 16)
                            5 -> question = getQuestionDataABM(20, 16)
                            6 -> question = getQuestionDataABM(24, 16)
                            7 -> question = getQuestionDataABM(25, 16)
                            8 -> question = getQuestionDataABM(27, 16)
                            9 -> question = getQuestionDataABM(34, 16)
                            10 -> question = getQuestionDataABM(35, 16)
                            11 -> question = getQuestionDataABM(40, 16)
                            else -> {}
                        }
                    }
                    23 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(4, 14)
                            2 -> question = getQuestionDataABM(6, 16)
                            3 -> question = getQuestionDataABM(26, 16)
                            4 -> question = getQuestionDataABM(30, 16)
                            5 -> question = getQuestionDataABM(38, 16)
                            else -> {}
                        }
                    }
                    24 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(1, 17)
                            2 -> question = getQuestionDataABM(7, 17)
                            3 -> question = getQuestionDataABM(16, 17)
                            4 -> question = getQuestionDataABM(26, 17)
                            5 -> question = getQuestionDataABM(32, 17)
                            6 -> question = getQuestionDataABM(38, 17)
                            else -> {}
                        }
                    }
                    25 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(11, 18)
                            2 -> question = getQuestionDataABM(17, 18)
                            3 -> question = getQuestionDataABM(18, 18)
                            4 -> question = getQuestionDataABM(19, 18)
                            5 -> question = getQuestionDataABM(20, 18)
                            6 -> question = getQuestionDataABM(21, 18)
                            7 -> question = getQuestionDataABM(23, 18)
                            8 -> question = getQuestionDataABM(27, 18)
                            9 -> question = getQuestionDataABM(30, 18)
                            10 -> question = getQuestionDataABM(33, 18)
                            11 -> question = getQuestionDataABM(34, 18)
                            12 -> question = getQuestionDataABM(36, 18)
                            13 -> question = getQuestionDataABM(38, 18)
                            14 -> question = getQuestionDataABM(40, 18)
                            15 -> question = getQuestionDataABM(6, 20)
                            else -> {}
                        }
                    }
                    26 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(15, 7)
                            2 -> question = getQuestionDataABM(16, 7)
                            3 -> question = getQuestionDataABM(17, 7)
                            4 -> question = getQuestionDataABM(20, 7)
                            5 -> question = getQuestionDataABM(28, 7)
                            6 -> question = getQuestionDataABM(34, 7)
                            7 -> question = getQuestionDataABM(35, 7)
                            8 -> question = getQuestionDataABM(39, 7)
                            9 -> question = getQuestionDataABM(40, 7)
                            else -> {}
                        }
                    }
                    else ->{}
                }
            }
            "ThemesCD" -> {
                when (ticketNumber){
                    1 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataCD(1, 1)
                            2 -> question = getQuestionDataCD(2, 1)
                            3 -> question = getQuestionDataCD(3, 1)
                            4 -> question = getQuestionDataCD(4, 1)
                            5 -> question = getQuestionDataCD(5, 1)
                            6 -> question = getQuestionDataCD(6, 1)
                            7 -> question = getQuestionDataCD(10, 1)
                            8 -> question = getQuestionDataCD(11, 1)
                            9 -> question = getQuestionDataCD(13, 1)
                            10 -> question = getQuestionDataCD(14, 1)
                            11 -> question = getQuestionDataCD(15, 1)
                            12 -> question = getQuestionDataCD(17, 1)
                            13 -> question = getQuestionDataCD(18, 1)
                            14 -> question = getQuestionDataCD(19, 1)
                            15 -> question = getQuestionDataCD(22, 1)
                            16 -> question = getQuestionDataCD(23, 1)
                            17 -> question = getQuestionDataCD(25, 1)
                            18 -> question = getQuestionDataCD(26, 1)
                            19 -> question = getQuestionDataCD(29, 1)
                            20 -> question = getQuestionDataCD(30, 1)
                            21 -> question = getQuestionDataCD(33, 1)
                            22 -> question = getQuestionDataCD(34, 1)
                            23 -> question = getQuestionDataCD(35, 1)
                            24 -> question = getQuestionDataCD(36, 1)
                            25 -> question = getQuestionDataCD(38, 1)
                            26 -> question = getQuestionDataCD(40, 2)
                            else -> {}
                        }
                    }
                    2 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataCD(1, 2)
                            2 -> question = getQuestionDataCD(2, 2)
                            3 -> question = getQuestionDataCD(3, 2)
                            4 -> question = getQuestionDataCD(4, 2)
                            5 -> question = getQuestionDataCD(5, 2)
                            6 -> question = getQuestionDataCD(6, 2)
                            7 -> question = getQuestionDataCD(7, 2)
                            8 -> question = getQuestionDataCD(8, 2)
                            9 -> question = getQuestionDataCD(9, 2)
                            10 -> question = getQuestionDataCD(10, 2)
                            11 -> question = getQuestionDataCD(11, 2)
                            12 -> question = getQuestionDataCD(12, 2)
                            13 -> question = getQuestionDataCD(13, 2)
                            14 -> question = getQuestionDataCD(14, 2)
                            15 -> question = getQuestionDataCD(15, 2)
                            16 -> question = getQuestionDataCD(16, 2)
                            17 -> question = getQuestionDataCD(17, 2)
                            18 -> question = getQuestionDataCD(18, 2)
                            19 -> question = getQuestionDataCD(19, 2)
                            20 -> question = getQuestionDataCD(20, 2)
                            21 -> question = getQuestionDataCD(21, 2)
                            22 -> question = getQuestionDataCD(22, 2)
                            23 -> question = getQuestionDataCD(23, 2)
                            24 -> question = getQuestionDataCD(24, 2)
                            25 -> question = getQuestionDataCD(25, 2)
                            26 -> question = getQuestionDataCD(26, 2)
                            27 -> question = getQuestionDataCD(27, 2)
                            28 -> question = getQuestionDataCD(28, 2)
                            29 -> question = getQuestionDataCD(29, 2)
                            30 -> question = getQuestionDataCD(30, 2)
                            31 -> question = getQuestionDataCD(31, 2)
                            32 -> question = getQuestionDataCD(32, 2)
                            33 -> question = getQuestionDataCD(33, 2)
                            34 -> question = getQuestionDataCD(34, 2)
                            35 -> question = getQuestionDataCD(35, 2)
                            36 -> question = getQuestionDataCD(36, 2)
                            37 -> question = getQuestionDataCD(37, 2)
                            38 -> question = getQuestionDataCD(38, 2)
                            39 -> question = getQuestionDataCD(39, 2)
                            40 -> question = getQuestionDataCD(1, 3)
                            41 -> question = getQuestionDataCD(2, 3)
                            42 -> question = getQuestionDataCD(3, 3)
                            43 -> question = getQuestionDataCD(4, 3)
                            44 -> question = getQuestionDataCD(5, 3)
                            45 -> question = getQuestionDataCD(6, 3)
                            46 -> question = getQuestionDataCD(8, 3)
                            47 -> question = getQuestionDataCD(9, 3)
                            48 -> question = getQuestionDataCD(10, 3)
                            49 -> question = getQuestionDataCD(11, 3)
                            50 -> question = getQuestionDataCD(12, 3)
                            51 -> question = getQuestionDataCD(13, 3)
                            52 -> question = getQuestionDataCD(14, 3)
                            53 -> question = getQuestionDataCD(15, 3)
                            54 -> question = getQuestionDataCD(16, 3)
                            55 -> question = getQuestionDataCD(17, 3)
                            56 -> question = getQuestionDataCD(18, 3)
                            57 -> question = getQuestionDataCD(19, 3)
                            58 -> question = getQuestionDataCD(20, 3)
                            59 -> question = getQuestionDataCD(21, 3)
                            60 -> question = getQuestionDataCD(22, 3)
                            61 -> question = getQuestionDataCD(23, 3)
                            62 -> question = getQuestionDataCD(24, 3)
                            63 -> question = getQuestionDataCD(25, 3)
                            64 -> question = getQuestionDataCD(26, 3)
                            65 -> question = getQuestionDataCD(27, 3)
                            66 -> question = getQuestionDataCD(28, 3)
                            67 -> question = getQuestionDataCD(29, 3)
                            68 -> question = getQuestionDataCD(30, 3)
                            69 -> question = getQuestionDataCD(31, 3)
                            70 -> question = getQuestionDataCD(32, 3)
                            71 -> question = getQuestionDataCD(33, 3)
                            72 -> question = getQuestionDataCD(34, 3)
                            73 -> question = getQuestionDataCD(35, 3)
                            74 -> question = getQuestionDataCD(36, 3)
                            75 -> question = getQuestionDataCD(37, 3)
                            76 -> question = getQuestionDataCD(38, 3)
                            77 -> question = getQuestionDataCD(39, 3)
                            78 -> question = getQuestionDataCD(40, 3)
                            79 -> question = getQuestionDataCD(1, 4)
                            80 -> question = getQuestionDataCD(2, 4)
                            81 -> question = getQuestionDataCD(3, 4)
                            82 -> question = getQuestionDataCD(4, 4)
                            83 -> question = getQuestionDataCD(5, 4)
                            84 -> question = getQuestionDataCD(6, 4)
                            85 -> question = getQuestionDataCD(7, 4)
                            86 -> question = getQuestionDataCD(8, 4)
                            87 -> question = getQuestionDataCD(9, 4)
                            88 -> question = getQuestionDataCD(10, 4)
                            89 -> question = getQuestionDataCD(11, 4)
                            90 -> question = getQuestionDataCD(12, 4)
                            91 -> question = getQuestionDataCD(13, 4)
                            92 -> question = getQuestionDataCD(14, 4)
                            93 -> question = getQuestionDataCD(15, 4)
                            94 -> question = getQuestionDataCD(16, 4)
                            95 -> question = getQuestionDataCD(17, 4)
                            96 -> question = getQuestionDataCD(18, 4)
                            97 -> question = getQuestionDataCD(19, 4)
                            98 -> question = getQuestionDataCD(20, 4)
                            99 -> question = getQuestionDataCD(21, 4)
                            100 -> question = getQuestionDataCD(22, 4)
                            101 -> question = getQuestionDataCD(23, 4)
                            102 -> question = getQuestionDataCD(24, 4)
                            103 -> question = getQuestionDataCD(25, 4)
                            104 -> question = getQuestionDataCD(26, 4)
                            105 -> question = getQuestionDataCD(27, 4)
                            106 -> question = getQuestionDataCD(28, 4)
                            107 -> question = getQuestionDataCD(29, 4)
                            108 -> question = getQuestionDataCD(30, 4)
                            109 -> question = getQuestionDataCD(31, 4)
                            110 -> question = getQuestionDataCD(32, 4)
                            111 -> question = getQuestionDataCD(33, 4)
                            112 -> question = getQuestionDataCD(34, 4)
                            113 -> question = getQuestionDataCD(35, 4)
                            114 -> question = getQuestionDataCD(36, 4)
                            115 -> question = getQuestionDataCD(37, 4)
                            116 -> question = getQuestionDataCD(38, 4)
                            117 -> question = getQuestionDataCD(39, 4)
                            118 -> question = getQuestionDataCD(40, 4)
                            119 -> question = getQuestionDataCD(3, 5)
                            120 -> question = getQuestionDataCD(1, 10)
                            121 -> question = getQuestionDataCD(19, 10)
                            122 -> question = getQuestionDataCD(19, 12)
                            123 -> question = getQuestionDataCD(20, 12)
                            124 -> question = getQuestionDataCD(24, 12)
                            125 -> question = getQuestionDataCD(35, 12)
                            126 -> question = getQuestionDataCD(25, 13)
                            127 -> question = getQuestionDataCD(1, 15)
                            128 -> question = getQuestionDataCD(3, 15)
                            129 -> question = getQuestionDataCD(5, 15)
                            130 -> question = getQuestionDataCD(7, 15)
                            131 -> question = getQuestionDataCD(8, 15)
                            132 -> question = getQuestionDataCD(9, 15)
                            133 -> question = getQuestionDataCD(10, 15)
                            134 -> question = getQuestionDataCD(12, 15)
                            135 -> question = getQuestionDataCD(13, 15)
                            136 -> question = getQuestionDataCD(15, 15)
                            137 -> question = getQuestionDataCD(16, 15)
                            138 -> question = getQuestionDataCD(23, 15)
                            139 -> question = getQuestionDataCD(25, 15)
                            140 -> question = getQuestionDataCD(26, 15)
                            141 -> question = getQuestionDataCD(27, 15)
                            142 -> question = getQuestionDataCD(28, 15)
                            143 -> question = getQuestionDataCD(29, 15)
                            144 -> question = getQuestionDataCD(30, 15)
                            145 -> question = getQuestionDataCD(31, 15)
                            146 -> question = getQuestionDataCD(32, 15)
                            147 -> question = getQuestionDataCD(33, 15)
                            148 -> question = getQuestionDataCD(34, 15)
                            149 -> question = getQuestionDataCD(35, 15)
                            150 -> question = getQuestionDataCD(39, 15)
                            151 -> question = getQuestionDataCD(28, 16)
                            else -> {}
                        }
                    }
                    3 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataCD(7, 3)
                            2 -> question = getQuestionDataCD(1, 5)
                            3 -> question = getQuestionDataCD(2, 5)
                            4 -> question = getQuestionDataCD(3, 5)
                            5 -> question = getQuestionDataCD(4, 5)
                            6 -> question = getQuestionDataCD(5, 5)
                            7 -> question = getQuestionDataCD(6, 5)
                            8 -> question = getQuestionDataCD(7, 5)
                            9 -> question = getQuestionDataCD(8, 5)
                            10 -> question = getQuestionDataCD(9, 5)
                            11 -> question = getQuestionDataCD(10, 5)
                            12 -> question = getQuestionDataCD(11, 5)
                            13 -> question = getQuestionDataCD(12, 5)
                            14 -> question = getQuestionDataCD(13, 5)
                            15 -> question = getQuestionDataCD(14, 5)
                            16 -> question = getQuestionDataCD(15, 5)
                            17 -> question = getQuestionDataCD(16, 5)
                            18 -> question = getQuestionDataCD(17, 5)
                            19 -> question = getQuestionDataCD(18, 5)
                            20 -> question = getQuestionDataCD(19, 5)
                            21 -> question = getQuestionDataCD(20, 5)
                            22 -> question = getQuestionDataCD(21, 5)
                            23 -> question = getQuestionDataCD(22, 5)
                            24 -> question = getQuestionDataCD(23, 5)
                            25 -> question = getQuestionDataCD(24, 5)
                            26 -> question = getQuestionDataCD(25, 5)
                            27 -> question = getQuestionDataCD(26, 5)
                            28 -> question = getQuestionDataCD(27, 5)
                            29 -> question = getQuestionDataCD(28, 5)
                            30 -> question = getQuestionDataCD(29, 5)
                            31 -> question = getQuestionDataCD(30, 5)
                            32 -> question = getQuestionDataCD(31, 5)
                            33 -> question = getQuestionDataCD(32, 5)
                            34 -> question = getQuestionDataCD(33, 5)
                            35 -> question = getQuestionDataCD(34, 5)
                            36 -> question = getQuestionDataCD(35, 5)
                            37 -> question = getQuestionDataCD(36, 5)
                            38 -> question = getQuestionDataCD(37, 5)
                            39 -> question = getQuestionDataCD(38, 5)
                            40 -> question = getQuestionDataCD(39, 5)
                            41 -> question = getQuestionDataCD(40, 5)
                            else -> {}
                        }
                    }
                    4 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataCD(8, 5)
                            2 -> question = getQuestionDataCD(1, 6)
                            3 -> question = getQuestionDataCD(2, 6)
                            4 -> question = getQuestionDataCD(4, 6)
                            5 -> question = getQuestionDataCD(5, 6)
                            6 -> question = getQuestionDataCD(6, 6)
                            7 -> question = getQuestionDataCD(7, 6)
                            8 -> question = getQuestionDataCD(8, 6)
                            9 -> question = getQuestionDataCD(9, 6)
                            10 -> question = getQuestionDataCD(10, 6)
                            11 -> question = getQuestionDataCD(12, 6)
                            12 -> question = getQuestionDataCD(13, 6)
                            13 -> question = getQuestionDataCD(14, 6)
                            14 -> question = getQuestionDataCD(15, 6)
                            15 -> question = getQuestionDataCD(16, 6)
                            16 -> question = getQuestionDataCD(17, 6)
                            17 -> question = getQuestionDataCD(18, 6)
                            18 -> question = getQuestionDataCD(20, 6)
                            19 -> question = getQuestionDataCD(21, 6)
                            20 -> question = getQuestionDataCD(23, 6)
                            21 -> question = getQuestionDataCD(24, 6)
                            22 -> question = getQuestionDataCD(25, 6)
                            23 -> question = getQuestionDataCD(26, 6)
                            24 -> question = getQuestionDataCD(27, 6)
                            25 -> question = getQuestionDataCD(28, 6)
                            26 -> question = getQuestionDataCD(29, 6)
                            27 -> question = getQuestionDataCD(30, 6)
                            28 -> question = getQuestionDataCD(31, 6)
                            29 -> question = getQuestionDataCD(32, 6)
                            30 -> question = getQuestionDataCD(34, 6)
                            31 -> question = getQuestionDataCD(35, 6)
                            32 -> question = getQuestionDataCD(37, 6)
                            33 -> question = getQuestionDataCD(39, 6)
                            34 -> question = getQuestionDataCD(40, 6)
                            35 -> question = getQuestionDataCD(5, 13)
                            36 -> question = getQuestionDataCD(6, 13)
                            37 -> question = getQuestionDataCD(12, 13)
                            38 -> question = getQuestionDataCD(23, 13)
                            39 -> question = getQuestionDataCD(25, 13)
                            40 -> question = getQuestionDataCD(27, 13)
                            41 -> question = getQuestionDataCD(31, 13)
                            42 -> question = getQuestionDataCD(33, 13)
                            43 -> question = getQuestionDataCD(34, 13)
                            44 -> question = getQuestionDataCD(36, 13)
                            else -> {}
                        }
                    }
                    5 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataCD(11, 2)
                            2 -> question = getQuestionDataCD(37, 3)
                            3 -> question = getQuestionDataCD(39, 3)
                            4 -> question = getQuestionDataCD(40, 3)
                            5 -> question = getQuestionDataCD(1, 7)
                            6 -> question = getQuestionDataCD(2, 7)
                            7 -> question = getQuestionDataCD(3, 7)
                            8 -> question = getQuestionDataCD(4, 7)
                            9 -> question = getQuestionDataCD(5, 7)
                            10 -> question = getQuestionDataCD(6, 7)
                            11 -> question = getQuestionDataCD(7, 7)
                            12 -> question = getQuestionDataCD(8, 7)
                            13 -> question = getQuestionDataCD(9, 7)
                            14 -> question = getQuestionDataCD(10, 7)
                            15 -> question = getQuestionDataCD(11, 7)
                            16 -> question = getQuestionDataCD(12, 7)
                            17 -> question = getQuestionDataCD(13, 7)
                            18 -> question = getQuestionDataCD(14, 7)
                            19 -> question = getQuestionDataCD(18, 7)
                            20 -> question = getQuestionDataCD(19, 7)
                            21 -> question = getQuestionDataCD(21, 7)
                            22 -> question = getQuestionDataCD(22, 7)
                            23 -> question = getQuestionDataCD(23, 7)
                            24 -> question = getQuestionDataCD(24, 7)
                            25 -> question = getQuestionDataCD(25, 7)
                            26 -> question = getQuestionDataCD(26, 7)
                            27 -> question = getQuestionDataCD(32, 7)
                            28 -> question = getQuestionDataCD(29, 7)
                            29 -> question = getQuestionDataCD(30, 7)
                            30 -> question = getQuestionDataCD(31, 7)
                            31 -> question = getQuestionDataCD(32, 7)
                            32 -> question = getQuestionDataCD(33, 7)
                            33 -> question = getQuestionDataCD(36, 7)
                            34 -> question = getQuestionDataCD(37, 7)
                            35 -> question = getQuestionDataCD(38, 7)
                            36 -> question = getQuestionDataCD(1, 8)
                            37 -> question = getQuestionDataCD(2, 8)
                            38 -> question = getQuestionDataCD(3, 8)
                            39 -> question = getQuestionDataCD(4, 8)
                            40 -> question = getQuestionDataCD(5, 8)
                            41 -> question = getQuestionDataCD(6, 8)
                            42 -> question = getQuestionDataCD(7, 8)
                            43 -> question = getQuestionDataCD(8, 8)
                            44 -> question = getQuestionDataCD(9, 8)
                            45 -> question = getQuestionDataCD(10, 8)
                            46 -> question = getQuestionDataCD(11, 8)
                            47 -> question = getQuestionDataCD(12, 8)
                            48 -> question = getQuestionDataCD(13, 8)
                            49 -> question = getQuestionDataCD(14, 8)
                            50 -> question = getQuestionDataCD(15, 8)
                            51 -> question = getQuestionDataCD(16, 8)
                            52 -> question = getQuestionDataCD(17, 8)
                            53 -> question = getQuestionDataCD(18, 8)
                            54 -> question = getQuestionDataCD(19, 8)
                            55 -> question = getQuestionDataCD(20, 8)
                            56 -> question = getQuestionDataCD(21, 8)
                            57 -> question = getQuestionDataCD(22, 8)
                            58 -> question = getQuestionDataCD(23, 8)
                            59 -> question = getQuestionDataCD(24, 8)
                            60 -> question = getQuestionDataCD(25, 8)
                            61 -> question = getQuestionDataCD(26, 8)
                            62 -> question = getQuestionDataCD(27, 8)
                            63 -> question = getQuestionDataCD(28, 8)
                            64 -> question = getQuestionDataCD(29, 8)
                            65 -> question = getQuestionDataCD(30, 8)
                            66 -> question = getQuestionDataCD(31, 8)
                            67 -> question = getQuestionDataCD(32, 8)
                            68 -> question = getQuestionDataCD(33, 8)
                            69 -> question = getQuestionDataCD(34, 8)
                            70 -> question = getQuestionDataCD(35, 8)
                            71 -> question = getQuestionDataCD(36, 8)
                            72 -> question = getQuestionDataCD(37, 8)
                            73 -> question = getQuestionDataCD(38, 8)
                            74 -> question = getQuestionDataCD(39, 8)
                            75 -> question = getQuestionDataCD(40, 8)
                            76 -> question = getQuestionDataCD(1, 9)
                            77 -> question = getQuestionDataCD(2, 9)
                            78 -> question = getQuestionDataCD(3, 9)
                            79 -> question = getQuestionDataCD(4, 9)
                            80 -> question = getQuestionDataCD(5, 9)
                            81 -> question = getQuestionDataCD(6, 9)
                            82 -> question = getQuestionDataCD(7, 9)
                            83 -> question = getQuestionDataCD(8, 9)
                            84 -> question = getQuestionDataCD(9, 9)
                            85 -> question = getQuestionDataCD(10, 9)
                            86 -> question = getQuestionDataCD(11, 9)
                            87 -> question = getQuestionDataCD(12, 9)
                            88 -> question = getQuestionDataCD(13, 9)
                            89 -> question = getQuestionDataCD(14, 9)
                            90 -> question = getQuestionDataCD(15, 9)
                            91 -> question = getQuestionDataCD(16, 9)
                            92 -> question = getQuestionDataCD(17, 9)
                            93 -> question = getQuestionDataCD(18, 9)
                            94 -> question = getQuestionDataCD(19, 9)
                            95 -> question = getQuestionDataCD(20, 9)
                            96 -> question = getQuestionDataCD(21, 9)
                            97 -> question = getQuestionDataCD(22, 9)
                            98 -> question = getQuestionDataCD(23, 9)
                            99 -> question = getQuestionDataCD(24, 9)
                            100 -> question = getQuestionDataCD(25, 9)
                            101 -> question = getQuestionDataCD(26, 9)
                            102 -> question = getQuestionDataCD(27, 9)
                            103 -> question = getQuestionDataCD(28, 9)
                            104 -> question = getQuestionDataCD(29, 9)
                            105 -> question = getQuestionDataCD(30, 9)
                            106 -> question = getQuestionDataCD(31, 9)
                            107 -> question = getQuestionDataCD(32, 9)
                            108 -> question = getQuestionDataCD(33, 9)
                            109 -> question = getQuestionDataCD(34, 9)
                            110 -> question = getQuestionDataCD(35, 9)
                            111 -> question = getQuestionDataCD(36, 9)
                            112 -> question = getQuestionDataCD(37, 9)
                            113 -> question = getQuestionDataCD(38, 9)
                            114 -> question = getQuestionDataCD(39, 9)
                            115 -> question = getQuestionDataCD(40, 9)
                            116 -> question = getQuestionDataCD(10, 10)
                            117 -> question = getQuestionDataCD(37, 10)
                            118 -> question = getQuestionDataCD(9, 16)
                            119 -> question = getQuestionDataCD(37, 16)
                            else -> {}
                        }
                    }
                    6 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataCD(3, 3)
                            2 -> question = getQuestionDataCD(15, 3)
                            3 -> question = getQuestionDataCD(28, 3)
                            4 -> question = getQuestionDataCD(33, 3)
                            5 -> question = getQuestionDataCD(35, 3)
                            6 -> question = getQuestionDataCD(16, 4)
                            7 -> question = getQuestionDataCD(1, 10)
                            8 -> question = getQuestionDataCD(5, 10)
                            9 -> question = getQuestionDataCD(6, 10)
                            10 -> question = getQuestionDataCD(7, 10)
                            11 -> question = getQuestionDataCD(12, 10)
                            12 -> question = getQuestionDataCD(13, 10)
                            13 -> question = getQuestionDataCD(15, 10)
                            14 -> question = getQuestionDataCD(16, 10)
                            15 -> question = getQuestionDataCD(17, 10)
                            16 -> question = getQuestionDataCD(18, 10)
                            17 -> question = getQuestionDataCD(19, 10)
                            18 -> question = getQuestionDataCD(24, 10)
                            19 -> question = getQuestionDataCD(25, 16)
                            20 -> question = getQuestionDataCD(31, 10)
                            21 -> question = getQuestionDataCD(32, 10)
                            22 -> question = getQuestionDataCD(35, 10)
                            23 -> question = getQuestionDataCD(38, 10)
                            24 -> question = getQuestionDataCD(39, 10)
                            25 -> question = getQuestionDataCD(1, 16)
                            26 -> question = getQuestionDataCD(11, 16)
                            27 -> question = getQuestionDataCD(32, 16)
                            else -> {}
                        }
                    }
                    7 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataCD(5, 2)
                            2 -> question = getQuestionDataCD(29, 3)
                            3 -> question = getQuestionDataCD(1, 11)
                            4 -> question = getQuestionDataCD(2, 11)
                            5 -> question = getQuestionDataCD(3, 11)
                            6 -> question = getQuestionDataCD(4, 11)
                            7 -> question = getQuestionDataCD(5, 11)
                            8 -> question = getQuestionDataCD(6, 11)
                            9 -> question = getQuestionDataCD(7, 11)
                            10 -> question = getQuestionDataCD(8, 11)
                            11 -> question = getQuestionDataCD(9, 11)
                            12 -> question = getQuestionDataCD(10, 11)
                            13 -> question = getQuestionDataCD(11, 11)
                            14 -> question = getQuestionDataCD(12, 11)
                            15 -> question = getQuestionDataCD(13, 11)
                            16 -> question = getQuestionDataCD(14, 11)
                            17 -> question = getQuestionDataCD(15, 11)
                            18 -> question = getQuestionDataCD(16, 11)
                            19 -> question = getQuestionDataCD(17, 11)
                            20 -> question = getQuestionDataCD(18, 11)
                            21 -> question = getQuestionDataCD(19, 11)
                            22 -> question = getQuestionDataCD(20, 11)
                            23 -> question = getQuestionDataCD(21, 11)
                            24 -> question = getQuestionDataCD(22, 11)
                            25 -> question = getQuestionDataCD(23, 11)
                            26 -> question = getQuestionDataCD(24, 11)
                            27 -> question = getQuestionDataCD(25, 11)
                            28 -> question = getQuestionDataCD(26, 11)
                            29 -> question = getQuestionDataCD(27, 11)
                            30 -> question = getQuestionDataCD(28, 11)
                            31 -> question = getQuestionDataCD(29, 11)
                            32 -> question = getQuestionDataCD(30, 11)
                            33 -> question = getQuestionDataCD(31, 11)
                            34 -> question = getQuestionDataCD(32, 11)
                            35 -> question = getQuestionDataCD(33, 11)
                            36 -> question = getQuestionDataCD(34, 11)
                            37 -> question = getQuestionDataCD(35, 11)
                            38 -> question = getQuestionDataCD(36, 11)
                            39 -> question = getQuestionDataCD(37, 11)
                            40 -> question = getQuestionDataCD(38, 11)
                            41 -> question = getQuestionDataCD(39, 11)
                            42 -> question = getQuestionDataCD(40, 11)
                            else -> {}
                        }
                    }
                    8 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataCD(1, 3)
                            2 -> question = getQuestionDataCD(2, 3)
                            3 -> question = getQuestionDataCD(5, 3)
                            4 -> question = getQuestionDataCD(10, 3)
                            5 -> question = getQuestionDataCD(13, 3)
                            6 -> question = getQuestionDataCD(19, 3)
                            7 -> question = getQuestionDataCD(20, 3)
                            8 -> question = getQuestionDataCD(24, 3)
                            9 -> question = getQuestionDataCD(27, 3)
                            10 -> question = getQuestionDataCD(30, 3)
                            11 -> question = getQuestionDataCD(34, 3)
                            12 -> question = getQuestionDataCD(36, 3)
                            13 -> question = getQuestionDataCD(38, 3)
                            14 -> question = getQuestionDataCD(5, 4)
                            15 -> question = getQuestionDataCD(12, 4)
                            16 -> question = getQuestionDataCD(22, 4)
                            17 -> question = getQuestionDataCD(29, 4)
                            18 -> question = getQuestionDataCD(35, 4)
                            19 -> question = getQuestionDataCD(37, 4)
                            20 -> question = getQuestionDataCD(40, 4)
                            21 -> question = getQuestionDataCD(3, 5)
                            22 -> question = getQuestionDataCD(14, 5)
                            23 -> question = getQuestionDataCD(16, 5)
                            24 -> question = getQuestionDataCD(23, 5)
                            25 -> question = getQuestionDataCD(38, 5)
                            26 -> question = getQuestionDataCD(1, 12)
                            27 -> question = getQuestionDataCD(2, 12)
                            28 -> question = getQuestionDataCD(3, 12)
                            29 -> question = getQuestionDataCD(4, 12)
                            30 -> question = getQuestionDataCD(5, 12)
                            31 -> question = getQuestionDataCD(6, 12)
                            32 -> question = getQuestionDataCD(7, 12)
                            33 -> question = getQuestionDataCD(8, 12)
                            34 -> question = getQuestionDataCD(9, 12)
                            35 -> question = getQuestionDataCD(10, 12)
                            36 -> question = getQuestionDataCD(11, 12)
                            37 -> question = getQuestionDataCD(12, 12)
                            38 -> question = getQuestionDataCD(13, 12)
                            39 -> question = getQuestionDataCD(14, 12)
                            40 -> question = getQuestionDataCD(15, 12)
                            41 -> question = getQuestionDataCD(16, 12)
                            42 -> question = getQuestionDataCD(17, 12)
                            43 -> question = getQuestionDataCD(18, 12)
                            44 -> question = getQuestionDataCD(19, 12)
                            45 -> question = getQuestionDataCD(20, 12)
                            46 -> question = getQuestionDataCD(21, 12)
                            47 -> question = getQuestionDataCD(22, 12)
                            48 -> question = getQuestionDataCD(23, 12)
                            49 -> question = getQuestionDataCD(24, 12)
                            50 -> question = getQuestionDataCD(25, 12)
                            51 -> question = getQuestionDataCD(26, 12)
                            52 -> question = getQuestionDataCD(27, 12)
                            53 -> question = getQuestionDataCD(28, 12)
                            54 -> question = getQuestionDataCD(29, 12)
                            55 -> question = getQuestionDataCD(30, 12)
                            56 -> question = getQuestionDataCD(31, 12)
                            57 -> question = getQuestionDataCD(32, 12)
                            58 -> question = getQuestionDataCD(33, 12)
                            59 -> question = getQuestionDataCD(34, 12)
                            60 -> question = getQuestionDataCD(35, 12)
                            61 -> question = getQuestionDataCD(36, 12)
                            62 -> question = getQuestionDataCD(37, 12)
                            63 -> question = getQuestionDataCD(38, 12)
                            64 -> question = getQuestionDataCD(39, 12)
                            65 -> question = getQuestionDataCD(40, 12)
                            66 -> question = getQuestionDataCD(3, 16)
                            67 -> question = getQuestionDataCD(8, 16)
                            68 -> question = getQuestionDataCD(16, 16)
                            69 -> question = getQuestionDataCD(20, 16)
                            70 -> question = getQuestionDataCD(23, 16)
                            71 -> question = getQuestionDataCD(28, 16)
                            else -> {}
                        }
                    }
                    9 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataCD(29, 2)
                            2 -> question = getQuestionDataCD(10, 6)
                            3 -> question = getQuestionDataCD(1, 13)
                            4 -> question = getQuestionDataCD(2, 13)
                            5 -> question = getQuestionDataCD(3, 13)
                            6 -> question = getQuestionDataCD(4, 13)
                            7 -> question = getQuestionDataCD(5, 13)
                            8 -> question = getQuestionDataCD(7, 13)
                            9 -> question = getQuestionDataCD(8, 13)
                            10 -> question = getQuestionDataCD(9, 13)
                            11 -> question = getQuestionDataCD(10, 13)
                            12 -> question = getQuestionDataCD(11, 13)
                            13 -> question = getQuestionDataCD(12, 13)
                            14 -> question = getQuestionDataCD(13, 13)
                            15 -> question = getQuestionDataCD(14, 13)
                            16 -> question = getQuestionDataCD(15, 13)
                            17 -> question = getQuestionDataCD(16, 13)
                            18 -> question = getQuestionDataCD(17, 13)
                            19 -> question = getQuestionDataCD(18, 13)
                            20 -> question = getQuestionDataCD(19, 13)
                            21 -> question = getQuestionDataCD(20, 13)
                            22 -> question = getQuestionDataCD(21, 13)
                            23 -> question = getQuestionDataCD(22, 13)
                            24 -> question = getQuestionDataCD(23, 13)
                            25 -> question = getQuestionDataCD(24, 13)
                            26 -> question = getQuestionDataCD(25, 13)
                            27 -> question = getQuestionDataCD(26, 13)
                            28 -> question = getQuestionDataCD(27, 13)
                            29 -> question = getQuestionDataCD(28, 13)
                            30 -> question = getQuestionDataCD(29, 13)
                            31 -> question = getQuestionDataCD(30, 13)
                            32 -> question = getQuestionDataCD(31, 13)
                            33 -> question = getQuestionDataCD(32, 13)
                            34 -> question = getQuestionDataCD(33, 13)
                            35 -> question = getQuestionDataCD(34, 13)
                            36 -> question = getQuestionDataCD(35, 13)
                            37 -> question = getQuestionDataCD(36, 13)
                            38 -> question = getQuestionDataCD(37, 13)
                            39 -> question = getQuestionDataCD(38, 13)
                            40 -> question = getQuestionDataCD(39, 13)
                            41 -> question = getQuestionDataCD(40, 13)
                            42 -> question = getQuestionDataCD(1, 14)
                            43 -> question = getQuestionDataCD(2, 14)
                            44 -> question = getQuestionDataCD(3, 14)
                            45 -> question = getQuestionDataCD(4, 14)
                            46 -> question = getQuestionDataCD(5, 14)
                            47 -> question = getQuestionDataCD(6, 14)
                            48 -> question = getQuestionDataCD(7, 14)
                            49 -> question = getQuestionDataCD(8, 14)
                            50 -> question = getQuestionDataCD(9, 14)
                            51 -> question = getQuestionDataCD(10, 14)
                            52 -> question = getQuestionDataCD(11, 14)
                            53 -> question = getQuestionDataCD(12, 14)
                            54 -> question = getQuestionDataCD(13, 14)
                            55 -> question = getQuestionDataCD(14, 14)
                            56 -> question = getQuestionDataCD(15, 14)
                            57 -> question = getQuestionDataCD(16, 14)
                            58 -> question = getQuestionDataCD(17, 14)
                            59 -> question = getQuestionDataCD(18, 14)
                            60 -> question = getQuestionDataCD(19, 14)
                            61 -> question = getQuestionDataCD(20, 14)
                            62 -> question = getQuestionDataCD(21, 14)
                            63 -> question = getQuestionDataCD(22, 14)
                            64 -> question = getQuestionDataCD(23, 14)
                            65 -> question = getQuestionDataCD(24, 14)
                            66 -> question = getQuestionDataCD(25, 14)
                            67 -> question = getQuestionDataCD(26, 14)
                            68 -> question = getQuestionDataCD(27, 14)
                            69 -> question = getQuestionDataCD(28, 14)
                            70 -> question = getQuestionDataCD(29, 14)
                            71 -> question = getQuestionDataCD(30, 14)
                            72 -> question = getQuestionDataCD(31, 14)
                            73 -> question = getQuestionDataCD(32, 14)
                            74 -> question = getQuestionDataCD(33, 14)
                            75 -> question = getQuestionDataCD(34, 14)
                            76 -> question = getQuestionDataCD(35, 14)
                            77 -> question = getQuestionDataCD(36, 14)
                            78 -> question = getQuestionDataCD(37, 14)
                            79 -> question = getQuestionDataCD(38, 14)
                            80 -> question = getQuestionDataCD(39, 14)
                            81 -> question = getQuestionDataCD(40, 14)
                            82 -> question = getQuestionDataCD(1, 15)
                            83 -> question = getQuestionDataCD(2, 15)
                            84 -> question = getQuestionDataCD(3, 15)
                            85 -> question = getQuestionDataCD(4, 15)
                            86 -> question = getQuestionDataCD(5, 15)
                            87 -> question = getQuestionDataCD(7, 15)
                            88 -> question = getQuestionDataCD(8, 15)
                            89 -> question = getQuestionDataCD(9, 15)
                            90 -> question = getQuestionDataCD(10, 15)
                            91 -> question = getQuestionDataCD(11, 15)
                            92 -> question = getQuestionDataCD(12, 15)
                            93 -> question = getQuestionDataCD(13, 15)
                            94 -> question = getQuestionDataCD(14, 15)
                            95 -> question = getQuestionDataCD(15, 15)
                            96 -> question = getQuestionDataCD(16, 15)
                            97 -> question = getQuestionDataCD(17, 15)
                            98 -> question = getQuestionDataCD(18, 15)
                            99 -> question = getQuestionDataCD(19, 15)
                            100 -> question = getQuestionDataCD(20, 15)
                            101 -> question = getQuestionDataCD(21, 15)
                            102 -> question = getQuestionDataCD(22, 15)
                            103 -> question = getQuestionDataCD(23, 15)
                            104 -> question = getQuestionDataCD(24, 15)
                            105 -> question = getQuestionDataCD(25, 15)
                            106 -> question = getQuestionDataCD(26, 15)
                            107 -> question = getQuestionDataCD(27, 15)
                            108 -> question = getQuestionDataCD(28, 15)
                            109 -> question = getQuestionDataCD(29, 15)
                            110 -> question = getQuestionDataCD(30, 15)
                            111 -> question = getQuestionDataCD(31, 15)
                            112 -> question = getQuestionDataCD(32, 15)
                            113 -> question = getQuestionDataCD(33, 15)
                            114 -> question = getQuestionDataCD(34, 15)
                            115 -> question = getQuestionDataCD(35, 15)
                            116 -> question = getQuestionDataCD(36, 15)
                            117 -> question = getQuestionDataCD(37, 15)
                            118 -> question = getQuestionDataCD(38, 15)
                            119 -> question = getQuestionDataCD(39, 15)
                            120 -> question = getQuestionDataCD(40, 15)
                            else -> {}
                        }
                    }
                    10 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataCD(1, 17)
                            2 -> question = getQuestionDataCD(5, 17)
                            3 -> question = getQuestionDataCD(6, 17)
                            4 -> question = getQuestionDataCD(8, 17)
                            5 -> question = getQuestionDataCD(10, 17)
                            6 -> question = getQuestionDataCD(11, 17)
                            7 -> question = getQuestionDataCD(12, 17)
                            8 -> question = getQuestionDataCD(13, 17)
                            9 -> question = getQuestionDataCD(15, 17)
                            10 -> question = getQuestionDataCD(19, 17)
                            11 -> question = getQuestionDataCD(20, 17)
                            12 -> question = getQuestionDataCD(21, 17)
                            13 -> question = getQuestionDataCD(23, 17)
                            14 -> question = getQuestionDataCD(24, 17)
                            15 -> question = getQuestionDataCD(25, 17)
                            16 -> question = getQuestionDataCD(28, 17)
                            17 -> question = getQuestionDataCD(29, 17)
                            18 -> question = getQuestionDataCD(30, 17)
                            19 -> question = getQuestionDataCD(31, 17)
                            20 -> question = getQuestionDataCD(33, 17)
                            21 -> question = getQuestionDataCD(34, 17)
                            22 -> question = getQuestionDataCD(35, 17)
                            23 -> question = getQuestionDataCD(37, 17)
                            24 -> question = getQuestionDataCD(10, 19)
                            else -> {}
                        }
                    }
                    11 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataCD(1, 18)
                            2 -> question = getQuestionDataCD(2, 18)
                            3 -> question = getQuestionDataCD(3, 18)
                            4 -> question = getQuestionDataCD(4, 18)
                            5 -> question = getQuestionDataCD(5, 18)
                            6 -> question = getQuestionDataCD(6, 18)
                            7 -> question = getQuestionDataCD(7, 18)
                            8 -> question = getQuestionDataCD(8, 18)
                            9 -> question = getQuestionDataCD(9, 18)
                            10 -> question = getQuestionDataCD(10, 18)
                            11 -> question = getQuestionDataCD(12, 18)
                            12 -> question = getQuestionDataCD(13, 18)
                            13 -> question = getQuestionDataCD(14, 18)
                            14 -> question = getQuestionDataCD(15, 18)
                            15 -> question = getQuestionDataCD(16, 18)
                            16 -> question = getQuestionDataCD(22, 18)
                            17 -> question = getQuestionDataCD(24, 18)
                            18 -> question = getQuestionDataCD(25, 18)
                            19 -> question = getQuestionDataCD(26, 18)
                            20 -> question = getQuestionDataCD(28, 18)
                            21 -> question = getQuestionDataCD(29, 18)
                            22 -> question = getQuestionDataCD(31, 18)
                            23 -> question = getQuestionDataCD(32, 18)
                            24 -> question = getQuestionDataCD(35, 18)
                            25 -> question = getQuestionDataCD(37, 18)
                            26 -> question = getQuestionDataCD(39, 18)
                            else -> {}
                        }
                    }
                    12 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataCD(10, 10)
                            2 -> question = getQuestionDataCD(1, 17)
                            3 -> question = getQuestionDataCD(36, 17)
                            4 -> question = getQuestionDataCD(1, 19)
                            5 -> question = getQuestionDataCD(2, 19)
                            6 -> question = getQuestionDataCD(3, 19)
                            7 -> question = getQuestionDataCD(4, 19)
                            8 -> question = getQuestionDataCD(5, 19)
                            9 -> question = getQuestionDataCD(6, 19)
                            10 -> question = getQuestionDataCD(7, 19)
                            11 -> question = getQuestionDataCD(8, 19)
                            12 -> question = getQuestionDataCD(9, 19)
                            13 -> question = getQuestionDataCD(11, 19)
                            14 -> question = getQuestionDataCD(12, 19)
                            15 -> question = getQuestionDataCD(13, 19)
                            16 -> question = getQuestionDataCD(14, 19)
                            17 -> question = getQuestionDataCD(15, 19)
                            18 -> question = getQuestionDataCD(16, 19)
                            19 -> question = getQuestionDataCD(17, 19)
                            20 -> question = getQuestionDataCD(18, 19)
                            21 -> question = getQuestionDataCD(19, 19)
                            22 -> question = getQuestionDataCD(20, 19)
                            23 -> question = getQuestionDataCD(21, 19)
                            24 -> question = getQuestionDataCD(22, 19)
                            25 -> question = getQuestionDataCD(23, 19)
                            26 -> question = getQuestionDataCD(24, 19)
                            27 -> question = getQuestionDataCD(25, 19)
                            28 -> question = getQuestionDataCD(26, 19)
                            29 -> question = getQuestionDataCD(27, 19)
                            30 -> question = getQuestionDataCD(28, 19)
                            31 -> question = getQuestionDataCD(29, 19)
                            32 -> question = getQuestionDataCD(30, 19)
                            33 -> question = getQuestionDataCD(31, 19)
                            34 -> question = getQuestionDataCD(32, 19)
                            35 -> question = getQuestionDataCD(33, 19)
                            36 -> question = getQuestionDataCD(34, 19)
                            37 -> question = getQuestionDataCD(35, 19)
                            38 -> question = getQuestionDataCD(36, 19)
                            39 -> question = getQuestionDataCD(37, 19)
                            40 -> question = getQuestionDataCD(38, 19)
                            41 -> question = getQuestionDataCD(39, 19)
                            42 -> question = getQuestionDataCD(40, 19)
                            43 -> question = getQuestionDataCD(1, 20)
                            44 -> question = getQuestionDataCD(5, 20)
                            45 -> question = getQuestionDataCD(6, 20)
                            46 -> question = getQuestionDataCD(9, 20)
                            47 -> question = getQuestionDataCD(11, 20)
                            48 -> question = getQuestionDataCD(14, 20)
                            49 -> question = getQuestionDataCD(17, 20)
                            50 -> question = getQuestionDataCD(18, 20)
                            51 -> question = getQuestionDataCD(20, 20)
                            52 -> question = getQuestionDataCD(22, 20)
                            53 -> question = getQuestionDataCD(23, 20)
                            54 -> question = getQuestionDataCD(26, 20)
                            55 -> question = getQuestionDataCD(27, 20)
                            56 -> question = getQuestionDataCD(28, 20)
                            57 -> question = getQuestionDataCD(30, 20)
                            58 -> question = getQuestionDataCD(31, 20)
                            59 -> question = getQuestionDataCD(33, 20)
                            60 -> question = getQuestionDataCD(35, 20)
                            61 -> question = getQuestionDataCD(36, 20)
                            62 -> question = getQuestionDataCD(39, 20)
                            else -> {}
                        }
                    }
                    13 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataCD(2, 20)
                            2 -> question = getQuestionDataCD(3, 20)
                            3 -> question = getQuestionDataCD(4, 20)
                            4 -> question = getQuestionDataCD(7, 20)
                            5 -> question = getQuestionDataCD(8, 20)
                            6 -> question = getQuestionDataCD(10, 20)
                            7 -> question = getQuestionDataCD(12, 20)
                            8 -> question = getQuestionDataCD(13, 20)
                            9 -> question = getQuestionDataCD(15, 20)
                            10 -> question = getQuestionDataCD(16, 20)
                            11 -> question = getQuestionDataCD(19, 20)
                            12 -> question = getQuestionDataCD(21, 20)
                            13 -> question = getQuestionDataCD(24, 20)
                            14 -> question = getQuestionDataCD(25, 20)
                            15 -> question = getQuestionDataCD(29, 20)
                            16 -> question = getQuestionDataCD(32, 20)
                            17 -> question = getQuestionDataCD(34, 20)
                            18 -> question = getQuestionDataCD(37, 20)
                            19 -> question = getQuestionDataCD(38, 20)
                            20 -> question = getQuestionDataCD(40, 20)
                            else -> {}
                        }
                    }
                    14 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataCD(7, 1)
                            2 -> question = getQuestionDataCD(8, 1)
                            3 -> question = getQuestionDataCD(9, 1)
                            4 -> question = getQuestionDataCD(12, 1)
                            5 -> question = getQuestionDataCD(16, 1)
                            6 -> question = getQuestionDataCD(20, 1)
                            7 -> question = getQuestionDataCD(21, 1)
                            8 -> question = getQuestionDataCD(24, 1)
                            9 -> question = getQuestionDataCD(27, 1)
                            10 -> question = getQuestionDataCD(28, 1)
                            11 -> question = getQuestionDataCD(31, 1)
                            12 -> question = getQuestionDataCD(32, 1)
                            13 -> question = getQuestionDataCD(37, 1)
                            14 -> question = getQuestionDataCD(39, 1)
                            15 -> question = getQuestionDataCD(40, 1)
                            else -> {}
                        }
                    }
                    15 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataCD(13, 1)
                            2 -> question = getQuestionDataCD(2, 10)
                            3 -> question = getQuestionDataCD(3, 10)
                            4 -> question = getQuestionDataCD(4, 10)
                            5 -> question = getQuestionDataCD(8, 10)
                            6 -> question = getQuestionDataCD(9, 10)
                            7 -> question = getQuestionDataCD(11, 10)
                            8 -> question = getQuestionDataCD(14, 10)
                            9 -> question = getQuestionDataCD(20, 10)
                            10 -> question = getQuestionDataCD(21, 10)
                            11 -> question = getQuestionDataCD(22, 10)
                            12 -> question = getQuestionDataCD(23, 10)
                            13 -> question = getQuestionDataCD(26, 10)
                            14 -> question = getQuestionDataCD(27, 10)
                            15 -> question = getQuestionDataCD(28, 10)
                            16 -> question = getQuestionDataCD(29, 10)
                            17 -> question = getQuestionDataCD(30, 10)
                            18 -> question = getQuestionDataCD(33, 10)
                            19 -> question = getQuestionDataCD(34, 10)
                            20 -> question = getQuestionDataCD(36, 10)
                            21 -> question = getQuestionDataCD(40, 10)
                            22 -> question = getQuestionDataCD(8, 11)
                            23 -> question = getQuestionDataCD(9, 11)
                            24 -> question = getQuestionDataCD(19, 11)
                            25 -> question = getQuestionDataCD(34, 11)
                            26 -> question = getQuestionDataCD(29, 16)
                            else -> {}
                        }
                    }
                    16 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataCD(12, 3)
                            2 -> question = getQuestionDataCD(40, 5)
                            3 -> question = getQuestionDataCD(13, 16)
                            4 -> question = getQuestionDataCD(15, 16)
                            5 -> question = getQuestionDataCD(17, 16)
                            6 -> question = getQuestionDataCD(18, 16)
                            7 -> question = getQuestionDataCD(19, 16)
                            8 -> question = getQuestionDataCD(23, 16)
                            else -> {}
                        }
                    }
                    17 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataCD(21, 3)
                            2 -> question = getQuestionDataCD(4, 4)
                            3 -> question = getQuestionDataCD(2, 17)
                            4 -> question = getQuestionDataCD(4, 17)
                            5 -> question = getQuestionDataCD(14, 17)
                            6 -> question = getQuestionDataCD(17, 17)
                            7 -> question = getQuestionDataCD(18, 17)
                            8 -> question = getQuestionDataCD(27, 17)
                            9 -> question = getQuestionDataCD(39, 17)
                            10 -> question = getQuestionDataCD(40, 17)
                            else -> {}
                        }
                    }
                    18 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataCD(3, 6)
                            2 -> question = getQuestionDataCD(11, 6)
                            3 -> question = getQuestionDataCD(19, 6)
                            4 -> question = getQuestionDataCD(22, 6)
                            5 -> question = getQuestionDataCD(33, 6)
                            6 -> question = getQuestionDataCD(36, 6)
                            7 -> question = getQuestionDataCD(38, 6)
                            8 -> question = getQuestionDataCD(12, 14)
                            9 -> question = getQuestionDataCD(6, 15)
                            else -> {}
                        }
                    }
                    19 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataCD(3, 16)
                            2 -> question = getQuestionDataCD(8, 16)
                            3 -> question = getQuestionDataCD(9, 16)
                            4 -> question = getQuestionDataCD(12, 16)
                            5 -> question = getQuestionDataCD(14, 16)
                            6 -> question = getQuestionDataCD(16, 16)
                            7 -> question = getQuestionDataCD(28, 16)
                            8 -> question = getQuestionDataCD(29, 16)
                            9 -> question = getQuestionDataCD(31, 16)
                            10 -> question = getQuestionDataCD(32, 16)
                            11 -> question = getQuestionDataCD(33, 16)
                            12 -> question = getQuestionDataCD(37, 16)
                            13 -> question = getQuestionDataCD(39, 16)
                            else -> {}
                        }
                    }
                    20 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataCD(4, 16)
                            2 -> question = getQuestionDataCD(12, 16)
                            3 -> question = getQuestionDataCD(31, 16)
                            4 -> question = getQuestionDataCD(3, 17)
                            else -> {}
                        }
                    }
                    21 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataCD(26, 1)
                            2 -> question = getQuestionDataCD(30, 4)
                            3 -> question = getQuestionDataCD(18, 10)
                            4 -> question = getQuestionDataCD(1, 16)
                            5 -> question = getQuestionDataCD(4, 16)
                            6 -> question = getQuestionDataCD(7, 16)
                            7 -> question = getQuestionDataCD(11, 16)
                            8 -> question = getQuestionDataCD(21, 16)
                            9 -> question = getQuestionDataCD(22, 16)
                            10 -> question = getQuestionDataCD(36, 16)
                            else -> {}
                        }
                    }
                    22 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataCD(13, 6)
                            2 -> question = getQuestionDataCD(22, 11)
                            3 -> question = getQuestionDataCD(2, 16)
                            4 -> question = getQuestionDataCD(5, 16)
                            5 -> question = getQuestionDataCD(10, 16)
                            6 -> question = getQuestionDataCD(20, 16)
                            7 -> question = getQuestionDataCD(24, 16)
                            8 -> question = getQuestionDataCD(25, 16)
                            9 -> question = getQuestionDataCD(26, 16)
                            10 -> question = getQuestionDataCD(34, 16)
                            11 -> question = getQuestionDataCD(35, 16)
                            12 -> question = getQuestionDataCD(40, 16)
                            else -> {}
                        }
                    }
                    23 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataCD(4, 14)
                            2 -> question = getQuestionDataCD(6, 16)
                            3 -> question = getQuestionDataCD(26, 16)
                            4 -> question = getQuestionDataCD(30, 16)
                            5 -> question = getQuestionDataCD(38, 16)
                            else -> {}
                        }
                    }
                    24 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(7, 17)
                            2 -> question = getQuestionDataABM(9, 17)
                            3 -> question = getQuestionDataABM(14, 17)
                            4 -> question = getQuestionDataABM(16, 17)
                            5 -> question = getQuestionDataABM(18, 17)
                            6 -> question = getQuestionDataABM(22, 17)
                            7 -> question = getQuestionDataABM(26, 17)
                            8 -> question = getQuestionDataABM(32, 17)
                            9 -> question = getQuestionDataABM(38, 17)
                            10 -> question = getQuestionDataABM(40, 17)
                            else -> {}
                        }
                    }
                    25 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(11, 18)
                            2 -> question = getQuestionDataABM(17, 18)
                            3 -> question = getQuestionDataABM(18, 18)
                            4 -> question = getQuestionDataABM(19, 18)
                            5 -> question = getQuestionDataABM(20, 18)
                            6 -> question = getQuestionDataABM(21, 18)
                            7 -> question = getQuestionDataABM(23, 18)
                            8 -> question = getQuestionDataABM(27, 18)
                            9 -> question = getQuestionDataABM(30, 18)
                            10 -> question = getQuestionDataABM(33, 18)
                            11 -> question = getQuestionDataABM(34, 18)
                            12 -> question = getQuestionDataABM(36, 18)
                            13 -> question = getQuestionDataABM(38, 18)
                            14 -> question = getQuestionDataABM(40, 18)
                            15 -> question = getQuestionDataABM(6, 20)
                            else -> {}
                        }
                    }
                    26 -> {
                        when (questionNumber) {
                            1 -> question = getQuestionDataABM(8, 1)
                            2 -> question = getQuestionDataABM(15, 7)
                            3 -> question = getQuestionDataABM(16, 7)
                            4 -> question = getQuestionDataABM(17, 7)
                            5 -> question = getQuestionDataABM(20, 7)
                            6 -> question = getQuestionDataABM(28, 7)
                            7 -> question = getQuestionDataABM(34, 7)
                            8 -> question = getQuestionDataABM(35, 7)
                            9 -> question = getQuestionDataABM(39, 7)
                            10 -> question = getQuestionDataABM(40, 7)
                            11 -> question = getQuestionDataABM(39, 16)
                            else -> {}
                        }
                    }
                    else ->{}
                }
            }
            "ABM" -> {
                question = getQuestionDataABM(ticketNumber, questionNumber)
            }
            "CD" -> {
                question = getQuestionDataCD(ticketNumber, questionNumber)
            }
            "SPEC" -> {
                question = getQuestionDataSPEC(ticketNumber, questionNumber)
            }
        }

        val questionText = view?.findViewById<TextView>(R.id.questionText)
        val questionImage = view?.findViewById<ImageView>(R.id.questionImage)
        val answersContainer = view?.findViewById<LinearLayout>(R.id.answersContainer)
        val ticketQuestion = view?.findViewById<TextView>(R.id.ticketQuestion)

        questionText?.text = question.text
        ticketQuestion?.text = "Вопрос ${questionNumber} из $countQuestion"

        if (question.imageResId != null) {
            questionImage?.setImageResource(question.imageResId!!)
            questionImage?.visibility = View.VISIBLE
        } else {
            questionImage?.visibility = View.GONE
        }

        answersContainer?.removeAllViews()

        for (answer in question.answers) {
            val answerButton = Button(requireContext()).apply {
                text = answer
                textSize = 16f
                setPadding(16, 8, 16, 8)
                setBackgroundResource(R.drawable.button_selector)
                setTextColor(resources.getColor(android.R.color.black))
                isEnabled = answers[questionNumber - 1].isEmpty()

                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 8, 0, 8)
                }

                setOnClickListener {
                    answers[questionNumber - 1] = answer
                    checkAnswer(answer, question)
                    highlightSelectedButton(this, answersContainer)
                    if (answersContainer != null) {
                        for (i in 0 until answersContainer.childCount) {
                            val button = answersContainer.getChildAt(i) as Button
                            button.isEnabled = false
                        }
                    }
                }
            }

            answersContainer?.addView(answerButton)

            if (answers[questionNumber - 1] == answer) {
                answerButton.setBackgroundResource(R.drawable.selected_button)
            }
        }

        resultText.visibility = View.GONE
        nextButton.visibility = View.GONE

        nextButton.text = if (questionNumber < countQuestion) "Следующий вопрос" else "Завершить"

        val selectedAnswer = answers[questionNumber - 1]
        if (selectedAnswer.isNotEmpty()) {
            resultText.visibility = View.VISIBLE
            if (selectedAnswer == question.correctAnswer) {
                resultText.text = "Правильно!"
                resultText.setTextColor(resources.getColor(android.R.color.holo_green_dark))
            } else {
                resultText.text = "Неправильно! Верный ответ: ${question.correctAnswer}"
                resultText.setTextColor(resources.getColor(android.R.color.holo_red_dark))
            }
            nextButton.visibility = View.VISIBLE
        }
    }


    private fun highlightSelectedButton(selectedButton: Button, container: LinearLayout?) {
        for (i in 0 until container?.childCount!!) {
            val button = container.getChildAt(i) as Button
            if (button == selectedButton) {
                button.setBackgroundResource(R.drawable.selected_button)
            } else {
                button.setBackgroundResource(R.drawable.button_selector)
            }
        }
    }
}
