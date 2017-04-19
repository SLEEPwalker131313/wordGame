package tryanko.test.com.wordgame

import android.R
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import org.jetbrains.anko.*

class GameSettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            linearLayout {
                editText("Player 1") {
                    id = ViewIDEnum.PLAYER_1_EDIT_TEXT_ID.id
                    setSelectAllOnFocus(true)
                }.lparams{
                    weight = 1F
                }
                editText("Player 2") {
                    id = ViewIDEnum.PLAYER_2_EDIT_TEXT_ID.id
                    setSelectAllOnFocus(true)
                }.lparams{
                    weight = 1F
                }
            }
            textView("Размер поля: ")
                    .lparams {
                        gravity = left
                        width = wrapContent
                    }
            val fieldSizeAdapter = ArrayAdapter<String>(applicationContext, R.layout.simple_spinner_item, arrayOf("4x4", "5x5", "6x6", "7x7"))
            val fieldSizeSpinner = spinner {
                adapter = fieldSizeAdapter
            }
            textView("Ограничение по времени: ")
                    .lparams {
                        gravity = left
                        width = wrapContent
                    }
            val timeAdapter = ArrayAdapter<String>(applicationContext, R.layout.simple_spinner_item, arrayOf("нет", "1 минута", "2 минуты"))
            val timeSpinner = spinner {
                adapter = timeAdapter
            }
            textView("Слово: ")
                    .lparams {
                        gravity = left
                        width = wrapContent
                    }
            editText("Случайное"){
                id = ViewIDEnum.START_WORD_EDIT_TEXT_ID.id
                setSelectAllOnFocus(true)
            }.lparams {
                        gravity = left
                        width = wrapContent
                    }
            val player1EditText = find<EditText>(ViewIDEnum.PLAYER_1_EDIT_TEXT_ID.id)
            val player2EditText = find<EditText>(ViewIDEnum.PLAYER_2_EDIT_TEXT_ID.id)
            val startWordEditText = find<EditText>(ViewIDEnum.START_WORD_EDIT_TEXT_ID.id)
            button("Начать игру"){
                onClick {
                    startActivity<GameActivity>("player1" to player1EditText.text.toString(),
                            "player2" to player2EditText.text.toString(),
                            "fieldSize" to (fieldSizeSpinner.selectedItemPosition+4),
                            "time" to (timeSpinner.selectedItem.toString()),
                            "word" to startWordEditText.text.toString()
                    )
                }
            }.lparams {
                        width = matchParent
                    }
        }
    }
}
