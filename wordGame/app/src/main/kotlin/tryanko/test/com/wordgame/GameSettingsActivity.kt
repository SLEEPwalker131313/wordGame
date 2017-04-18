package tryanko.test.com.wordgame

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import org.jetbrains.anko.editText
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.onClick
import org.jetbrains.anko.verticalLayout

class GameSettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            linearLayout {
                editText("Player 1") {
                    setSelectAllOnFocus(true)
                }
                editText("Player 2") {
                    setSelectAllOnFocus(true)
                }
            }
        }
    }
}
