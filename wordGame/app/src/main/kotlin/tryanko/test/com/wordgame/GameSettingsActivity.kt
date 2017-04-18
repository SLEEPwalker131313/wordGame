package tryanko.test.com.wordgame

import android.R
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.ArrayAdapter
import org.jetbrains.anko.*

class GameSettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            linearLayout {
                editText("Player 1") {
                    setSelectAllOnFocus(true)
                }.lparams{
                    weight = 1F
                }
                editText("Player 22") {
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
            val tmp = ArrayAdapter<String>(applicationContext, R.layout.simple_spinner_item, arrayOf("4x4", "5x5", "6x6", "7x7"))
            spinner {
                adapter = tmp
//                setSelection(2)
            }
            textView("Слово: ")
                    .lparams {
                        gravity = left
                        width = wrapContent
                    }
            editText("Случайное")
                    .lparams {
                        gravity = left
                        width = wrapContent
                    }
            button("Начать игру")
                    .lparams {
                        width = matchParent
                    }
        }
    }
}
