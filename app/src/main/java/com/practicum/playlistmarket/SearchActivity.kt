package com.practicum.playlistmarket

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView

class SearchActivity : AppCompatActivity() {

    private lateinit var edText : EditText
    //var cnt : String =""

    companion object {
        const val SEARCH_QUERY = "SEARCH_QUERY"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_QUERY, edText.text.toString())
    }

//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        val searchValue = savedInstanceState.getString(SEARCH_QUERY,"")
//        edText.setText(searchValue)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)



        val btClear = findViewById<ImageView>(R.id.btClear)
        edText = findViewById(R.id.editSearch)


        btClear.setOnClickListener{
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

                if (savedInstanceState != null) {
                    val searchValue = savedInstanceState.getString(SEARCH_QUERY,"")
                    edText.setText(searchValue)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                //empty
            }

        }

        edText.addTextChangedListener(simpleTextWatcher)


    }

    fun clearButtonVisibility(s : CharSequence?) : Int{
        return if(s.isNullOrEmpty()){
            View.GONE
        } else {
            View.VISIBLE
        }

    }

    fun clickToolBar(view: View) {
        finish()
    }

}