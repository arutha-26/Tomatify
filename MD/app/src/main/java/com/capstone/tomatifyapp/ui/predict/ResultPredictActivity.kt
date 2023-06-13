package com.capstone.tomatifyapp.ui.predict

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.capstone.tomatifyapp.databinding.ActivityResultPredictBinding
import com.capstone.tomatifyapp.model.Predict
import com.capstone.tomatifyapp.model.ResponsePredict
import com.google.gson.Gson

class ResultPredictActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultPredictBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultPredictBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Prediction Result"

        val name = intent.getStringExtra("name")
        val description = intent.getStringExtra("description")
        val prevention = intent.getStringExtra("prevention")

        if (name != null && description != null && prevention != null) {
            val responsePredict = ResponsePredict(
                error = false,
                message = "",
                listPredict = listOf(
                    Predict(name = name, description = description, prevention = prevention)
                )
            )
            displayPredictionResult(responsePredict)
        } else {
            Toast.makeText(applicationContext, "Invalid prediction data", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun displayPredictionResult(responsePredict: ResponsePredict) {
        if (responsePredict.error) {
            // Error occurred, show error message
            Toast.makeText(this, responsePredict.message, Toast.LENGTH_SHORT).show()
        } else {
            val listPredict = responsePredict.listPredict
            if (listPredict.isNullOrEmpty()) {
                Toast.makeText(this, "No prediction result", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("ListPredict", listPredict.toString())
                // Display prediction result
                val predict = listPredict[0]
                with(binding) {
                    tvPredictResult.text = predict.name
                    tvPredictDescription.text = predict.description
                    tvPrevention.text = predict.prevention
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
