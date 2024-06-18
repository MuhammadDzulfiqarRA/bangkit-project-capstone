package com.dicoding.abai.ui.activity

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.abai.R
import com.dicoding.abai.adapter.ReadingAdapter
import com.dicoding.abai.databinding.ActivityReadingBinding
import com.dicoding.abai.helper.ConstantsObject
import com.dicoding.abai.response.DataItem
import com.dicoding.abai.response.DataItemStory
import com.dicoding.abai.response.ItemsItem
import com.dicoding.abai.viewmodel.ReadingViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Locale

class ReadingActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityReadingBinding
    private lateinit var textToSpeech: TextToSpeech
    private val readingViewModel by viewModels<ReadingViewModel>()
    private var isSpeaking = false
    private var currentIndex = 0
    private lateinit var fab: FloatingActionButton
    private var totalItems = 0
    private var pausedPosition = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textToSpeech = TextToSpeech(this, this)

        val layoutManager = LinearLayoutManager(this)
        binding.rvReading.layoutManager = layoutManager

        fab = findViewById(R.id.fab)
        fab.setOnClickListener {
            if (isSpeaking) {
                pauseSpeaking()
            } else {
                startSpeaking()
            }
        }

        val storyDataIntent = intent.getParcelableExtra<DataItem>(ConstantsObject.DETAIL_TO_READING)
        if (storyDataIntent != null) {
            storyDataIntent.id?.let { readingViewModel.storyDataReading(it, storyDataIntent.id) }
            binding.tvStoryTitle.text = storyDataIntent.title
        }

        readingViewModel.storyReading.observe(this) {
            setUsersData(it)
        }

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this@ReadingActivity, DetailStoryActivity::class.java ))
        }
    }

    private fun setUsersData(storyDataReading: List<DataItemStory?>?) {
        val adapter = ReadingAdapter()
        adapter.submitList(storyDataReading)
        binding.rvReading.adapter = adapter
        totalItems = storyDataReading?.size ?: 0
        binding.progressBar.max = totalItems
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale("id", "ID"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("ReadingActivity", "Indonesian language not supported")
            }
        } else {
            Log.e("ReadingActivity", "TextToSpeech initialization failed")
        }
    }

    private fun startSpeaking() {
        val visibleItems = (binding.rvReading.adapter as ReadingAdapter).currentList
        if (visibleItems.isEmpty()) {
            Toast.makeText(this, "No text to read", Toast.LENGTH_SHORT).show()
            return
        }

        isSpeaking = true
        fab.setImageResource(R.drawable.ic_pause)
        speakNext(visibleItems)
    }

    private fun pauseSpeaking() {
        if (isSpeaking) {
            textToSpeech.stop()
            isSpeaking = false
            fab.setImageResource(R.drawable.ic_play)
            pausedPosition = currentIndex
        }
    }

    private fun speakNext(items: List<DataItemStory>) {
        if (currentIndex < items.size) {
            val text = items[currentIndex].story // Adjust based on actual text field
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "UTTERANCE_ID")
            textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    runOnUiThread {
                        binding.rvReading.smoothScrollToPosition(currentIndex)
                    }
                }

                override fun onDone(utteranceId: String?) {
                    runOnUiThread {
                        currentIndex++
                        binding.progressBar.progress = currentIndex
                        if (isSpeaking) {
                            speakNext(items)
                        }
                    }
                }

                override fun onError(utteranceId: String?) {
                    runOnUiThread {
                        Toast.makeText(this@ReadingActivity, "Error reading text", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        } else {
            isSpeaking = false
            runOnUiThread {
                fab.setImageResource(R.drawable.ic_play)
                binding.progressBar.progress = totalItems
            }
        }
    }

    override fun onDestroy() {
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        if (isSpeaking) {
            val visibleItems = (binding.rvReading.adapter as ReadingAdapter).currentList
            speakNext(visibleItems.subList(pausedPosition, totalItems))
        }
    }
}
