package tryanko.test.com.wordgame

import android.content.Intent
import android.graphics.Color
import android.media.JetPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Gravity
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
        var isPlayer1Turn = true
        var gameIsOver = false
        val fieldMatrix = Array(fieldSize+1, {ArrayList<EditText>()})
        //TODO: Попробовать вернуть пример с координатами в лог при таче корневого вью
        //TODO: (возможно получится отловить коотдинаты и так добиться плавных переходов между разными EditText)
        //TODO: Доделать базу. Добавить очки и использование в игре слова
        verticalLayout {
            linearLayout {
                textView(player1name){
                    id = ViewIDEnum.PLAYER_1_NAME_TEXT_VIEW_ID.id
                    gravity = Gravity.CENTER
                }.lparams { weight = 1F }
                textView("--|--"){
                    gravity = Gravity.CENTER
                }.lparams { weight = 1F }
                textView(player2name){
                    id = ViewIDEnum.PLAYER_2_NAME_TEXT_VIEW_ID.id
                    gravity = Gravity.CENTER
                }.lparams { weight = 1F }
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
                                //TODO: Добавить логику
                                //TODO: Не работает с disabled, найти другое решение
                                //TODO: Работает с пределах одного вью, нам же нужен переход от одного к другому
                                setOnTouchListener { v, event ->
                                    if(event.action == MotionEvent.ACTION_DOWN)
                                        toast("Hi!")
                                    if(event.action == MotionEvent.ACTION_MOVE)
                                        backgroundColor = Color.GREEN
                                    true
                                }
                            }.lparams(weight = 1F))
                        }
                    }.lparams {
                        fieldLayoutStyle()
                    }
                }
            }
            linearLayout {
                button("Отмена"){
                    id = ViewIDEnum.BTN_ROLLBACK_BUTTON_ID.id
                }
                textView {
                    id = ViewIDEnum.CURRENT_WORD_TEXT_VIEW_ID.id
                }
                button("OK"){
                    id = ViewIDEnum.BTN_OK_BUTTON_ID.id
                }

                editText("ds"){
                    setOnTouchListener { v, event ->
                        if(event.action == MotionEvent.ACTION_DOWN)
                            toast("touched!")
                        true
                    }
                }

            }

            var currentWord = find<TextView>(ViewIDEnum.CURRENT_WORD_TEXT_VIEW_ID.id)
            fieldMatrix[0][0].onClick { currentWord.text = currentWord.text.toString() + fieldMatrix[0][0].text.toString() }
        }

        find<Button>(ViewIDEnum.BTN_OK_BUTTON_ID.id).onClick {
            isPlayer1Turn = !isPlayer1Turn
            selectCurrentPlayer(isPlayer1Turn)

        }

//        while(gameIsOver){
//            selectCurrentPlayer(isPlayer1Turn)
//            gameIsOver = false
//        }

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

    //Применить стили в зависимости от того чей ход
    fun selectCurrentPlayer(isPlayer1Turn: Boolean){
        val player1nameTextView = find<TextView>(ViewIDEnum.PLAYER_1_NAME_TEXT_VIEW_ID.id)
        val player2nameTextView = find<TextView>(ViewIDEnum.PLAYER_2_NAME_TEXT_VIEW_ID.id)
        if(isPlayer1Turn){
            activePlayerTextViewStyle(player1nameTextView)
            nonActivePlayerTextViewStyle(player2nameTextView)
        } else{
            activePlayerTextViewStyle(player2nameTextView)
            nonActivePlayerTextViewStyle(player1nameTextView)
        }
    }

    private fun nonActivePlayerTextViewStyle(playerNameTextView: TextView) {
        playerNameTextView.backgroundColor = Color.WHITE
    }

    private fun activePlayerTextViewStyle(playerNameTextView: TextView) {
        playerNameTextView.backgroundColor = Color.RED
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

    private fun isNearlyMiddleRow(fieldSize: Int, i: Int) =
            i == fieldSize / 2 - 1 || i == fieldSize / 2 + 1

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
