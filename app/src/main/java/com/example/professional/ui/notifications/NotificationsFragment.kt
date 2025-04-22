package com.example.professional.ui.notifications

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.professional.R
import com.example.professional.Ticket
import com.example.professional.VideLesson
import com.example.professional.databinding.FragmentNotificationsBinding


class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this)[NotificationsViewModel::class.java]

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.lessoncarA.setOnClickListener {
            val intent = Intent(requireContext(), VideLesson::class.java)
            intent.putExtra("lesson", "A")
            startActivity(intent)
        }

        binding.lessoncarB.setOnClickListener {
            val intent = Intent(requireContext(), VideLesson::class.java)
            intent.putExtra("lesson", "B")
            startActivity(intent)
        }

        binding.lessoncarC.setOnClickListener {
            val intent = Intent(requireContext(), VideLesson::class.java)
            intent.putExtra("lesson", "C")
            startActivity(intent)
        }

        binding.lessoncarCE.setOnClickListener {
            val intent = Intent(requireContext(), VideLesson::class.java)
            intent.putExtra("lesson", "CE")
            startActivity(intent)
        }

        binding.lessoncarD.setOnClickListener {
            val intent = Intent(requireContext(), VideLesson::class.java)
            intent.putExtra("lesson", "D")
            startActivity(intent)
        }

        binding.lessoncarJCB.setOnClickListener {
            val intent = Intent(requireContext(), VideLesson::class.java)
            intent.putExtra("lesson", "JCB")
            startActivity(intent)
        }

        binding.lessoncarBE.setOnClickListener {
            val intent = Intent(requireContext(), VideLesson::class.java)
            intent.putExtra("lesson", "BE")
            startActivity(intent)
        }

        binding.lessoncarA1.setOnClickListener {
            val intent = Intent(requireContext(), VideLesson::class.java)
            intent.putExtra("lesson", "A1")
            startActivity(intent)
        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}