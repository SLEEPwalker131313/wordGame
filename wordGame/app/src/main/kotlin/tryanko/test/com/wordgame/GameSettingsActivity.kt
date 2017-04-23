package tryanko.test.com.wordgame

import android.R
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import org.jetbrains.anko.*
import org.jetbrains.anko.db.update

class GameSettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            linearLayout {
                //Разобраться с весами чтобы блоки не плавали
                editText(StringConstantEnum.DEFAULT_PLAYER_1_NAME_STRING_CONSTANT.text) {
                    id = ViewIDEnum.PLAYER_1_EDIT_TEXT_ID.id
                    setSelectAllOnFocus(true)
                }.lparams{
                    weight = 1F
                }
                editText(StringConstantEnum.DEFAULT_PLAYER_2_NAME_STRING_CONSTANT.text) {
                    id = ViewIDEnum.PLAYER_2_EDIT_TEXT_ID.id
                    setSelectAllOnFocus(true)
                }.lparams{
                    weight = 1F
                }
            }
            textView(StringConstantEnum.FIELD_SIZE_STRING_CONSTANT.text)
                    .lparams {
                        gravity = left
                        width = wrapContent
                    }
            val fieldSizeAdapter = ArrayAdapter<String>(applicationContext, R.layout.simple_spinner_item, arrayOf("4x4", "5x5", "6x6", "7x7"))
            val fieldSizeSpinner = spinner {
                adapter = fieldSizeAdapter
            }
//            textView(StringConstantEnum.TIME_LIMIT_STRING_CONSTANT.text)
//                    .lparams {
//                        gravity = left
//                        width = wrapContent
//                    }
//            val timeAdapter = ArrayAdapter<String>(applicationContext, R.layout.simple_spinner_item, arrayOf("нет", "1 минута", "2 минуты"))
//            val timeSpinner = spinner {
//                adapter = timeAdapter
//            }
            textView(StringConstantEnum.PICK_START_WORD_STRING_CONSTANT.text)
                    .lparams {
                        gravity = left
                        width = wrapContent
                    }
            editText(StringConstantEnum.START_WORD_STRING_CONSTANT.text){
                id = ViewIDEnum.START_WORD_EDIT_TEXT_ID.id
                setSelectAllOnFocus(true)
            }.lparams {
                        gravity = left
                        width = wrapContent
                    }
            val player1EditText = find<EditText>(ViewIDEnum.PLAYER_1_EDIT_TEXT_ID.id)
            val player2EditText = find<EditText>(ViewIDEnum.PLAYER_2_EDIT_TEXT_ID.id)
            val startWordEditText = find<EditText>(ViewIDEnum.START_WORD_EDIT_TEXT_ID.id)
            button(StringConstantEnum.START_GAME_STRING_CONSTANT.text){
                onClick {
                    if(isCorrectSettings(fieldSizeSpinner, startWordEditText)) {
                        var startWord = startWordEditText.text.toString()
                        if (startWord == StringConstantEnum.START_WORD_STRING_CONSTANT.text) {
                            database.use {
                                startWord = database.selectRandomWord(database.readableDatabase, fieldSizeSpinner.selectedItemPosition + 4)
                            }
                        }
                        database.use {
                            update("games", "isFinished" to 1)
                        }
                        addNewGameIntoDB(fieldSizeSpinner, player1EditText, player2EditText, startWord)
                        startActivity<GameActivity>("player1" to player1EditText.text.toString(),
                                "player2" to player2EditText.text.toString(),
                                "fieldSize" to (fieldSizeSpinner.selectedItemPosition + 4),
//                                "time" to (timeSpinner.selectedItem.toString()),
                                "word" to startWord)
                    }
                    else
                        toast(StringConstantEnum.UNCORRECT_WORD_LENGTH_STRING_CONSTANT.text)
                }
            }.lparams { width = matchParent }
        }
    }

    private fun addNewGameIntoDB(fieldSizeSpinner: Spinner, player1EditText: EditText, player2EditText: EditText, startWord: String) {
        database.use {
            val gameId = database.getMaxIdFromGameTable(database.readableDatabase) + 1
            database.insertIntoGameTable(database.readableDatabase, gameId,
                    player1EditText.text.toString(), player2EditText.text.toString(),
                    (fieldSizeSpinner.selectedItemPosition + 4), startWord, 0)
        }
    }
}
fun isCorrectSettings(fieldSizeSpinner: Spinner, startWordEditText: EditText) =
        (fieldSizeSpinner.selectedItemPosition + 4) == startWordEditText.text.toString().length ||
                startWordEditText.text.toString() == StringConstantEnum.START_WORD_STRING_CONSTANT.text

