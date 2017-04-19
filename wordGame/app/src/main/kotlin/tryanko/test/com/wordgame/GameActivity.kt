package tryanko.test.com.wordgame

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.EditText
import org.jetbrains.anko.*

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            linearLayout {
                textView(intent.getStringExtra("player1"))
                textView(intent.getStringExtra("player2"))
            }
            val fieldSize = intent.getIntExtra("fieldSize", 4)
            textView(fieldSize.toString())
            val time = intent.getStringExtra("time")
            textView(time)
            textView(intent.getStringExtra("word"))
            gridLayout {
                rowCount = fieldSize
                columnCount = fieldSize
                var c = 0
                val fieldMatrix = Array(fieldSize+1, {ArrayList<EditText>()})
                for (i in 0..fieldSize - 1) {
                    for (j in 0..fieldSize - 1) {
                        fieldMatrix[i].add(editText {
                            //Просто для красоты
                            hint = c++.toString()
                            //Выделение всего содержимого при клике на поле
                            setSelectAllOnFocus(true)
                            textChangedListener {
                                onTextChanged { charSequence, start, before, count ->
                                    kotlin.run {
                                        //Не больше одного символа
                                        selectAll()
                                    }
                                }
                            }
                        })
                    }
                }
            }
        }
    }
}
