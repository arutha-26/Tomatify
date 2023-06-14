package com.capstone.tomatifyapp.ui.predict

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.capstone.tomatifyapp.databinding.ActivityResultPredictBinding
import com.capstone.tomatifyapp.model.Predict

class ResultPredictActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultPredictBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultPredictBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Prediction Result"


        val responseJson = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_RESULT, Predict::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_RESULT)
        }

        Log.e("responseJson", "${responseJson}")
        if(responseJson != null){
            displayPredictionResult(responseJson)
        }

    }

    private fun displayPredictionResult(responsePredict: Predict) {
        binding.tvPredictResult.text = responsePredict.name
        binding.tvPredictDescription.text = responsePredict.description
        binding.tvPrevention.text = responsePredict.prevention
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object{
        const val EXTRA_RESULT = "extra_result"
    }
}
