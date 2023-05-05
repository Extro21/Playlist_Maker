package com.practicum.playlistmarket


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView

class SearchActivity : AppCompatActivity() {
    //lateinit var binding: ActivitySearchBinding
    private lateinit var edText: EditText
    lateinit var searchText : String
    //val adapter = SearchAdapter()


    companion object {
        const val SEARCH_QUERY = "SEARCH_QUERY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // binding = ActivitySearchBinding.inflate(layoutInflater)
         setContentView(R.layout.activity_search)
       // setContentView(binding.root)
     //   init()

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.search_toolbar)
        toolbar.setNavigationOnClickListener{
            finish()
        }

        val btClear = findViewById<ImageView>(R.id.btClear)
        edText = findViewById(R.id.editSearch)


        btClear.setOnClickListener {
            edText.setText("")
            val keyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.hideSoftInputFromWindow(edText.windowToken, 0) // скрыть клавиатуру
            edText.clearFocus()
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btClear.visibility = clearButtonVisibility(s)
                searchText = edText.text.toString()

            }

            override fun afterTextChanged(s: Editable?) {

            }

        }

        edText.addTextChangedListener(simpleTextWatcher)

    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_QUERY, searchText)

    }

   override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        edText.setText(savedInstanceState.getString(SEARCH_QUERY, ""))
 }


}