package com.example.professional

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class VideoFragment : Fragment() {

    private var code: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            code = it.getString("lesson").toString()
        }
    }

    @SuppressLint("SetJavaScriptEnabled", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lesson, container, false)
        val lessonContainer = view.findViewById<LinearLayout>(R.id.lessonContainer)
        val lessons = getLessonsData(code)


        if (lessons.text.isNotEmpty() && lessons.urls.isNotEmpty()) {
            for (i in lessons.text.indices) {
                // Создаем общий контейнер для текста и видео
                val lessonItemContainer = LinearLayout(requireContext()).apply {
                    orientation = LinearLayout.VERTICAL
                    background = resources.getDrawable(R.drawable.selected_button, null)
                    // Устанавливаем внутренние отступы
                    setPadding(16, 16, 16, 16)
                    // Устанавливаем внешний отступ между контейнерами
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(0, 16, 0, 16) // Отступы между контейнерами
                    }
                }

                // Текстовый элемент
                val textView = TextView(requireContext()).apply {
                    text = lessons.text[i]
                    textSize = 20f
                    setTextColor(Color.BLACK)
                    // Устанавливаем внутренний отступ для текста
                    setPadding(0, 0, 0, 8) // Можно настроить отступы
                }

                // Контейнер для WebView
                val webView = WebView(requireContext()).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    settings.allowFileAccess = true
                    settings.allowContentAccess = true

                    webChromeClient = object : WebChromeClient() {
                        private var customView: View? = null
                        private var customViewCallback: CustomViewCallback? = null
                        override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                            customView = view
                            customViewCallback = callback

                            activity?.window?.decorView?.systemUiVisibility =
                                View.SYSTEM_UI_FLAG_FULLSCREEN or
                                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

                            (activity?.window?.decorView as FrameLayout).addView(customView)
                        }

                        override fun onHideCustomView() {
                            (activity?.window?.decorView as FrameLayout).removeView(customView)
                            customView = null
                            activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                            customViewCallback?.onCustomViewHidden()
                        }
                    }

                    loadUrl(lessons.urls[i])
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1000
                    ).apply {
                        setMargins(0, 8, 0, 16) // Отступы для WebView
                    }
                }

                // Добавляем текст и WebView в общий контейнер
                lessonItemContainer.addView(textView)
                lessonItemContainer.addView(webView)

                // Добавляем в основной контейнер
                lessonContainer.addView(lessonItemContainer)
            }
        }
        return view
    }


    companion object {
        fun newInstance(lessoncode: String) =
            VideoFragment().apply {
                arguments = Bundle().apply {
                    putString("lesson", lessoncode)
                }
            }
    }
}
