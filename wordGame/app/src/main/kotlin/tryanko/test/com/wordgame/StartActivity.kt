package tryanko.test.com.wordgame

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.*
import org.jetbrains.anko.db.select

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
        database.use {
//            database.dropGameTable(database.readableDatabase)
//            database.createGameTable(database.readableDatabase)
//            database.insertIntoGameTable(database.readableDatabase, "Вадим", "Саша", 6, "азбука", 0)
//            database.insertIntoGameTable(database.readableDatabase, "Вадим", "Саша", 6, "аркатм", 0)
            database.selectFromGameTable(database.readableDatabase)
//            database.createDetailTable(database.readableDatabase)
//            select("games").exec { toast(count.toString()) }
//            select("detail").exec { toast(count.toString()) }
        }
    }
}
