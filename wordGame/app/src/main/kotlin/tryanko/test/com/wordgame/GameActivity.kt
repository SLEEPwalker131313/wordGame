package tryanko.test.com.wordgame

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            linearLayout {
                textView(intent.getStringExtra("player1"))
                textView(intent.getStringExtra("player2"))
            }
            textView(intent.getIntExtra("fieldSize", 4).toString())
            textView(intent.getStringExtra("word"))
        }
    }
}
