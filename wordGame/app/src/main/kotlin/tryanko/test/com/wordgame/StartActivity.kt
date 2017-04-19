package tryanko.test.com.wordgame

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.jetbrains.anko.*
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.rowParser
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
            var _id = database.getMaxIdFromGameTable(database.readableDatabase)
//            database.insertIntoGameTable(database.readableDatabase, _id, "Вадим", "Саша", 6, "арарат", 0)
//            database.insertIntoGameTable(database.readableDatabase, "Вадим", "Саша", 6, "сугроб", 0)
//            select("games", "MAX(_id)").exec { parseList(rowParser { _id: Int-> Log.d("Max id", _id.toString()) }) }
//            database.selectFromGameTable(database.readableDatabase)
//            database.createDetailTable(database.readableDatabase)
//            select("games").exec { toast(count.toString()) }
//            select("detail").exec { toast(count.toString()) }
        }
    }
}
