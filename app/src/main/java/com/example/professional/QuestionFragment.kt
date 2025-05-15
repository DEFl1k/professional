package com.example.professional

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

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
    private var isEnd: Boolean = false

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
            if(isEnd) return@setOnClickListener
            if (questionNumber < countQuestion) {
                questionNumber++
                updateQuestion()
            } else {
                isEnd = true
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
        if(isEnd) return
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
        val question: Question = when (ticketType) {
            "ThemesABM", "ThemesCD" -> {
                val themeMap = loadThemeMap(requireContext(), if (ticketType == "ThemesABM") "themesabm" else "themescd")
                val theme = themeMap[ticketNumber.toString()]
                val ref = theme?.get(questionNumber.toString())

                if (ref != null) {
                    val fileName = when (ticketType) {
                        "ThemesABM" -> "getquestiondataabm"
                        "ThemesCD" -> "getquestiondatacd"
                        else -> ""
                    }
                    loadQuestionFromJson(requireContext(), fileName, ref.Ticket, ref.Question)
                } else null
            }

            "ABM" -> loadQuestionFromJson(requireContext(), "getquestiondataabm", ticketNumber, questionNumber)
            "CD" -> loadQuestionFromJson(requireContext(), "getquestiondatacd", ticketNumber, questionNumber)
            "SPEC" -> loadQuestionFromJson(requireContext(), "getquestiondataspec", ticketNumber, questionNumber)
            else -> null
        } ?: return

        val questionText = view?.findViewById<TextView>(R.id.questionText)
        val questionImage = view?.findViewById<ImageView>(R.id.questionImage)
        val answersContainer = view?.findViewById<LinearLayout>(R.id.answersContainer)
        val ticketQuestion = view?.findViewById<TextView>(R.id.ticketQuestion)

        questionText?.text = question.Text
        ticketQuestion?.text = "Вопрос ${questionNumber} из $countQuestion"

        if (!question.Image.isNullOrBlank()) {
            val imageName = question.Image.removeSuffix(".png")
            val imageResId = resources.getIdentifier(imageName, "drawable", requireContext().packageName)
            if (imageResId != 0) {
                questionImage?.setImageResource(imageResId)
                questionImage?.visibility = View.VISIBLE
            } else {
                questionImage?.visibility = View.GONE
            }
        } else {
            questionImage?.visibility = View.GONE
        }

        answersContainer?.removeAllViews()

        for (answer in question.Options) {
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
        if (selectedAnswer != "") {
            resultText.visibility = View.VISIBLE
            if (selectedAnswer == question.correctAnswer) {
                resultText.text = "Правильно!"
                resultText.setTextColor(resources.getColor(android.R.color.holo_green_dark))
            } else {
                resultText.text = "Неправильно! Верный ответ: ${question.correctAnswer}"
                resultText.setTextColor(resources.getColor(android.R.color.holo_red_dark))
            }
            nextButton.visibility = if (!isEnd) View.VISIBLE else View.GONE
            if (isEnd && questionNumber == countQuestion) {
                resultText.text = "Билет завершен! Верных ответов: $correctAnswersCount из $countQuestion"
            }
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

    private fun loadThemeMap(context: Context, fileName: String): Map<String, Map<String, TicketRef>> {
        val resId = context.resources.getIdentifier(fileName, "raw", context.packageName)
        val json = context.resources.openRawResource(resId).bufferedReader().use { it.readText() }

        val type = object : TypeToken<Map<String, Map<String, TicketRef>>>() {}.type
        return Gson().fromJson(json, type)
    }

    private fun loadQuestionFromJson(
        context: Context,
        fileName: String,
        ticketNumber: Int,
        questionNumber: Int
    ): Question? {
        val resId = context.resources.getIdentifier(fileName, "raw", context.packageName)
        val json = context.resources.openRawResource(resId).bufferedReader().use { it.readText() }

        val type = object : TypeToken<List<Ticket>>() {}.type
        val tickets: List<Ticket> = Gson().fromJson(json, type)

        val ticket = tickets.find { it.TicketNumber == ticketNumber }
        return ticket?.Questions?.find { it.Number == questionNumber }
    }

    data class TicketRef(
        val Ticket: Int,
        val Question: Int
    )

    data class Question(
        val Number: Int,
        val Text: String,
        val Options: List<String>,
        val CorrectIndex: Int,
        val Image: String?
    ) {
        val correctAnswer: String get() = Options[CorrectIndex]
    }

    data class Ticket(
        val TicketNumber: Int,
        val Questions: List<Question>
    )
}
