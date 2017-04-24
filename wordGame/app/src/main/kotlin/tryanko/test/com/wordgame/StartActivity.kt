package tryanko.test.com.wordgame

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import org.jetbrains.anko.*
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select
import tryanko.test.com.wordgame.GameActivity

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            button {
                text = StringConstantEnum.START_GAME_STRING_CONSTANT.text
                onClick{
                    startActivity<GameSettingsActivity>()
                }
            }
            button {
                text = StringConstantEnum.RULES_WORD_CONSTANT.text
            }
        }
        database.use {
//            database.createUsedWordsTable(database.readableDatabase)
//            database.createGameTable(database.readableDatabase)

//            database.dropGameTable(database.readableDatabase)
//            var _id = database.getMaxIdFromGameTable(database.readableDatabase)
//            database.insertIntoTable(database.readableDatabase)
//            database.insertIntoGameTable(database.readableDatabase, _id, "Вадим", "Саша", 6, "арарат", 0)
//            database.insertIntoGameTable(database.readableDatabase, "Вадим", "Саша", 6, "сугроб", 0)
//            select("games", "MAX(_id)").exec { parseList(rowParser { _id: Int-> Log.d("Max id", _id.toString()) }) }
//            database.selectFromGameTable(database.readableDatabase)
//            select("games").exec{Log.d("games", count.toString())}
//            database.createDetailTable(database.readableDatabase)
//            select("games").exec { toast(count.toString()) }
//            select("detail").exec { toast(count.toString()) }


//            database.dropGameTable(database.readableDatabase)
//            database.dropUsedWordsTable(database.readableDatabase)
        }
    }
}


fun MotionEvent.viewIsTouched(view: View){
    if(x >= view.left && x <= view.right) {
        if(y >= view.top && y <= view.bottom) {
            Log.d("Target", "yeah!")
            view.setBackgroundColor(Color.GREEN)
        }
    }
}