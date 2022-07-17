package com.petsvote.ui

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import java.math.RoundingMode
import java.text.DecimalFormat

class SearchBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var count = 0
    var editType: SearchBarType = SearchBarType.TEXT

    private var mOnTextSearchBar: OnTextSearchBar? = null
    var edit: EditText
    var editable = true
        set(value) {
            field = value
            edit.isFocusable = value
            //edit.inputType =InputType.TYPE_NULL
            edit.setTextColor(ContextCompat.getColor(context, android.R.color.black))
        }

    var textSearch = ""
        set(value) {
            field = value
            edit.setText(value)
        }

    var textHint = ""
        set(value) {
            field = value
            edit.hint = value
        }
    init {
        var inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.search_bar, this, true)

        edit =  findViewById<EditText>(R.id.edit);
        edit.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var d= ""
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var d =""
            }
            override fun afterTextChanged(p0: Editable?) {
                var t = p0.toString()
                if(editType == SearchBarType.PET_ID){
                    var inputlength = t.length

                    if (count <= inputlength && inputlength == 4){

                        edit.setText(edit.text.toString() + " ")

                        var pos = edit.text.length
                        edit.setSelection(pos);

                    } else if (count >= inputlength && (inputlength == 4)) {
                        edit.setText(edit.getText().toString()
                            .substring(0, edit.getText()
                                .toString().length - 1));

                        var pos = edit.text.length
                        edit.setSelection(pos);
                    }
                    count = edit.text.toString().length
                }
                mOnTextSearchBar?.onText(edit.text.toString())
            }

        })

        findViewById<BesieLayout>(R.id.clear).setOnClickListener {
            if(editable) {
                mOnTextSearchBar?.onClear()
                textSearch = ""
            }
            else textSearch = ""
        }
    }

    fun setOnTextSearchBar(onTextSearchBar: OnTextSearchBar){
        mOnTextSearchBar = onTextSearchBar
    }

    interface OnTextSearchBar{
        fun onText(text: String)
        fun onClear()
    }

    enum class SearchBarType{
        TEXT,
        PET_ID
    }
}
