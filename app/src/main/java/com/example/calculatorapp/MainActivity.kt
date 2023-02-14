package com.example.calculatorapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calculatorapp.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var addOperation = false
    private var addDecimal = true
    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun numberAction(view: View){

        if(view is Button){
            if(view.text == "."){
                if (addDecimal){
                    binding.workings.append((view as Button).text)
                    addDecimal = false
                }

            }
            else
                binding.workings.append((view as Button).text)

            addOperation = true
        }

    }

    fun operatorAction(view:View){
        if(view is Button && addOperation){
            binding.workings.append((view as Button).text)
            addOperation = false
            addDecimal = true
        }
    }

    fun clearAllAction(view: View){
        binding.workings.text = ""
        binding.results.text = ""
    }

    fun backspaceAction(view: View){
        val length = binding.workings.length()
        if (length>0){
            binding.workings.text = binding.workings.text.subSequence(0, length - 1)
        }

    }

    fun resultAction(view: View){

        resultsCalc()

    }


    private fun resultsCalc(){
        val text = binding.workings.text.toString()
        expression = ExpressionBuilder(text).build()
        try{
            val result = expression.evaluate()
            binding.workings.text = ""
            binding.results.text = "=" + result.toString()
        }catch(ex : java.lang.ArithmeticException){
            Log.e("ERROR", ex.toString())
            binding.results.text = "ERROR"
        }

    }


}