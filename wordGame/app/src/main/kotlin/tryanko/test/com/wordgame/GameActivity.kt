package tryanko.test.com.wordgame

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
                            if(isMiddleRow(fieldSize, i)) {
                                startText = word[j].toString()
                            }
                            else
                                startText = ""
                            fieldMatrix[i].add(editText(startText) {
                                if(isMiddleRow(fieldSize, i)) {
                                    permanentEditTextStyle()
                                } else if (isNearlyMiddleRow(fieldSize, i)){
                                    availableToChangeEditTextStyle()
                                } else {
                                    unavailableEditTextStyle()
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
//                        setOnTouchListener{ v, event ->
//                            if(event.action == MotionEvent.ACTION_DOWN)
//                                toast("ds")
//                            if(event.action == MotionEvent.ACTION_MOVE) {
//                                //TODO: Дополнительные проверки на то какием именно вьюшки должны передаваться
//                                for (i in 0..fieldSize - 1){
//                                    for (j in 0..fieldSize - 1){
//                                        event.viewIsTouched(fieldMatrix[i][j])
//                                    }
//                                }
//                            }
//                            true
//                        }
                    }.lparams {
                        fieldLayoutStyle()
                    }
                }
            }
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

    //Стиль для основного игрвоого поля
    private fun LinearLayout.LayoutParams.fieldLayoutStyle() {
        width = matchParent
        leftMargin = 30
        rightMargin = 30
    }

    //Стиль для пустых ячеек, недоступных для изменения
    private fun EditText.unavailableEditTextStyle() {
        isEnabled = false
    }
    //Стиль для пустых ячеек, значение которых можно изменить на этом ходу
    private fun EditText.availableToChangeEditTextStyle() {
        setBackgroundColor(Color.BLUE)
    }
    //Стиль для завершенных ячеек, недоступных для изменения
    //Можно использовать для составления новых слов
    private fun EditText.permanentEditTextStyle() {
        isEnabled = false
        backgroundColor = Color.GRAY
    }

    private fun isNearlyMiddleRow(fieldSize: Int, i: Int) = i == fieldSize / 2 - 1 || i == fieldSize / 2 + 1

    private fun isMiddleRow(fieldSize: Int, i: Int) = i == fieldSize / 2

    /**
     * Проверка касания соответствующего отображаемого элемента
     * @param view объект типа [View] содержащий в себе границы
     */
    fun MotionEvent.viewIsTouched(view: View){
        if(x >= view.left && x <= view.right) {
            if(y >= view.top && x <= view.bottom) {
                Log.d("Target", "yeah!")
                view.setBackgroundColor(Color.GREEN)
            }
        }
    }
}
