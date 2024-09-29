package com.example.quizapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.databinding.QuestionitemBinding
import com.example.quizapp.model.Question

class QuestionAdapter (
    private val onAnswerSelected: (Int, Int) -> Unit
):
    ListAdapter<Question, QuestionAdapter.QuestionViewHolder>(QuestionDiffCallback()) {

    private val selectedAnswers = mutableMapOf<Int, Int>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = QuestionitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionAdapter.QuestionViewHolder, position: Int) {
        val question = getItem(position)
        question?.let {
            holder.bind(it)
        }

    }

    inner class QuestionViewHolder(private val binding: QuestionitemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(question: Question) {
            binding.textViewQuestionTitle.text = question.questionTitle
            binding.category.text=question.category
            binding.difficultyLevel.text=question.difficultyLevel
            binding.radioGroupOptions.removeAllViews()
            addRadioButton(0,question.option1.toString())
            addRadioButton(1,question.option2.toString())
            addRadioButton(2,question.option3.toString())


            binding.submitMCQ.setOnClickListener {
                val selectedId=binding.radioGroupOptions.checkedRadioButtonId
                if (selectedId!=-1){
                    onAnswerSelected(adapterPosition,selectedId)
                    selectedAnswers[adapterPosition]=selectedId
                    binding.submitMCQ.isEnabled=false
                }
            }
            binding.submitMCQ.isEnabled=selectedAnswers==null
        }
        fun getSelectedAnswers(): Map<Int, Int> = selectedAnswers.toMap()

        private fun addRadioButton(id: Int, text: String) {
            val radioButton = RadioButton(itemView.context).apply {
                this.id = id
                this.text = text
                layoutParams = RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
                )
            }
                binding.radioGroupOptions.addView(radioButton)
        }

    }


    class QuestionDiffCallback : DiffUtil.ItemCallback<Question>() {
        override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem == newItem
        }
    }
}

