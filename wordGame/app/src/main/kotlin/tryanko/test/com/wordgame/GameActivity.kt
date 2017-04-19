package tryanko.test.com.wordgame

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.GridLayout
import android.widget.TextView
import org.jetbrains.anko.*
import kotlin.coroutines.experimental.EmptyCoroutineContext.plus

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val player1name = intent.getStringExtra("player1")
        val player2name = intent.getStringExtra("player2")
        val fieldSize = intent.getIntExtra("fieldSize", 4)
        val time = intent.getStringExtra("time")
        val word = intent.getStringExtra("word")
        var startText = ""
        val fieldMatrix = Array(fieldSize+1, {ArrayList<EditText>()})
        verticalLayout {
            linearLayout {
                textView(player1name)
                textView(player2name)
            }
            textView(fieldSize.toString())
            textView(time)
            textView(word)
            var c = 0
            verticalLayout {
                for (i in 0..fieldSize - 1) {
                    linearLayout {
                        for (j in 0..fieldSize - 1) {
                            if(i == fieldSize/2) {
                                startText = word[j].toString()
                            }
                            else
                                startText = ""
                            fieldMatrix[i].add(editText(startText) {
                                if(i == fieldSize/2) {
                                    isEnabled = false
                                    backgroundColor = Color.GRAY
                                } else if (i == fieldSize/2 - 1 || i == fieldSize/2 + 1){
                                    setBackgroundColor(Color.BLUE)
                                } else {
                                    isEnabled = false
                                }
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
                            }.lparams(weight = 1F))
                        }
                    }.lparams { width = matchParent }
                }
            }
//            }

//            gridLayout {
//                leftPadding = 20
//                backgroundColor = Color.LTGRAY
//                id = ViewIDEnum.GAME_FIELD_GRID_LAYOUT_ID.id
//                rowCount = fieldSize
//                columnCount = fieldSize
//                var c = 0
//                for (i in 0..fieldSize - 1) {
//                    for (j in 0..fieldSize - 1) {
//                        if(i == fieldSize/2) {
//                            startText = word[j].toString()
//                        }
//                        else
//                            startText = ""
//                        fieldMatrix[i].add(editText(startText) {
//                            minWidth= 200
//                            minimumWidth= 200
//                            if(i == fieldSize/2) {
//                                isEnabled = false
//                                backgroundColor = Color.GRAY
//                            } else if (i == fieldSize/2 - 1 || i == fieldSize/2 + 1){
////                                backgroundColor = Color.BLUE
//                                setBackgroundColor(Color.BLUE)
//                            } else {
//                                isEnabled = false
//                            }
//                            //Просто для красоты
//                            hint = c++.toString()
//                            //Выделение всего содержимого при клике на поле
//                            setSelectAllOnFocus(true)
//                            textChangedListener {
//                                onTextChanged { charSequence, start, before, count ->
//                                    kotlin.run {
//                                        //Не больше одного символа
//                                        selectAll()
//                                    }
//                                }
//                            }
//                        })
//                    }
//                }
//                fieldMatrix[0][0].clearAnimation()
//                fieldMatrix[0][0].clearComposingText()
//                fieldMatrix[0][0].clearFocus()
////                for (i in 0..fieldSize - 1){
////                    fieldMatrix[fieldSize/2][i].hint = word[i].toString()
////                }
//            }.lparams(width = matchParent)

            linearLayout {
                button("Отмена")
                textView {
                    id = ViewIDEnum.CURRENT_WORD_TEXT_VIEW_ID.id
                }
                button("OK")
            }

            var currentWord = find<TextView>(ViewIDEnum.CURRENT_WORD_TEXT_VIEW_ID.id)
            fieldMatrix[0][0].onClick { currentWord.text = currentWord.text.toString() + fieldMatrix[0][0].text.toString() }
        }

//        val fieldView = find<GridLayout>(ViewIDEnum.GAME_FIELD_GRID_LAYOUT_ID.id)
//        fieldView
//                .setOnTouchListener { v, event ->
//            if(event.action == MotionEvent.ACTION_MOVE) {
//                //TODO: Дополнительные проверки на то какием именно вьюшки должны передаваться
//                event.viewIsTouched(fieldMatrix[0][0])
//                event.viewIsTouched(fieldMatrix[1][1])
//            }
//            true
//        }
    }
}
