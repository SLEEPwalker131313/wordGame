package tryanko.test.com.wordgame

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.button
import org.jetbrains.anko.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.verticalLayout

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            button {
                text = "Продолжить игру"
            }
            button {
                text = "Новая игра"
                onClick{
                    startActivity<GameSettingsActivity>()
                }
            }
            button {
                text = "Правила"
            }
        }
    }
}
