package com.example.quizapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.example.quizapp.R
import com.example.quizapp.adapter.QuestionAdapter
import com.example.quizapp.databinding.FragmentHomeBinding
import com.example.quizapp.model.Question
import com.example.quizapp.ui.viewmodel.MainViewModel
import com.example.quizapp.util.Results
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding:FragmentHomeBinding?=null
    private val binding get() = _binding!!

    private val viewModel:MainViewModel by viewModels()
    private lateinit var adapter: QuestionAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        setAllQuestion()

    }

    private fun setupRecyclerView() {
        adapter= QuestionAdapter{
            questionIndex,selectedAnswer->
            viewModel.selectAnswer(questionIndex, selectedAnswer)
        }

        binding.recyclerViewQuestions.layoutManager=LinearLayoutManager(requireContext())
        binding.recyclerViewQuestions.adapter=adapter

    }

    private fun setAllQuestion() {
        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.response.collect{
              questions->
                  when(questions){
                      is Results.Success ->{
                          Log.d("success",questions.data.toString())
                          binding.progressBar.isVisible = false
                          binding.recyclerViewQuestions.isVisible = true
                          binding.textViewError.isVisible = false
                          adapter.submitList(questions.data)

                      }
                      is Results.Loading->{
                          Log.d("Loading","Loading")
                          showLoading()

                      }
                      is Results.Error->{
                          Log.d("Error",questions.error.toString())
                          showError(questions.error.toString())

                      }
                      else ->Unit

            }
            }
        }
    }
    private fun showLoading() {
        binding.progressBar.isVisible = true
        binding.recyclerViewQuestions.isVisible = false
        binding.textViewError.isVisible = false
    }
    private fun showError(message: String) {
        binding.progressBar.isVisible = false
        binding.recyclerViewQuestions.isVisible = false
        binding.textViewError.isVisible = true
        binding.textViewError.text = message
    }
}