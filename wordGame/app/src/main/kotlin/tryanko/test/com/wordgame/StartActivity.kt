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
//            button {
//                text = "Продолжить игру"
//                enabled = false
//            }
            button {
                text = "Новая игра"
                onClick{
                    startActivity<GameSettingsActivity>()
                }
            }
            button {
                text = "Правила"
            }
            //ВЫделение последовательных текствью
            verticalLayout {
//                requestChildFocus(it, this)
//                onTouch { v, event ->
//                    if(event.action == MotionEvent.ACTION_DOWN){
//                        toast("Hi!")
////                        backgroundColor = Color.RED
//
//                    }
//                    if(event.action == MotionEvent.ACTION_MOVE) {
//                        forEachChild {
////                            Log.d("forEach", it.toString())
//                            Log.d("myCoord", "x: ${event.x} y: ${event.y}")
//                            Log.d("forEach", "left: ${it.left} right: ${it.right} top: ${it.top} bot: ${bottom}")
//                            event.viewIsTouched(it)
//                        }
////                        Log.d("focusedChild", focusedChild.)
////                        backgroundColor = Color.GREEN
////                        event.viewIsTouched(child)
////                        event.viewIsTouched(find<TextView>(102))
////                        event.viewIsTouched(find<TextView>(103))
////                        event.viewIsTouched(find<TextView>(104))
////                        focusedChild.backgroundColor = Color.GREEN
////                        v.childrenRecursiveSequence()
////                        v.backgroundColor = Color.GREEN
////                        backgroundColor = Color.GREEN
////                        focusedChild.backgroundColor = Color.GREEN
//                    }
//                    true
//                }
                for(j in 0 ..5)
                    linearLayout {
                        for (i in 0..5) {
                            editText(i.toString()) {
                                onTouch { view, motionEvent ->
                                    if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                                        toast("HI!ACTION_DOWN")
                                        Log.d("parent", parent.toString())
                                    }
                                    if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                                        forEachChild {
                                            Log.d("myCoord", "x: ${motionEvent.x + i*(it.right - it.left)} y: ${motionEvent.y}")
                                            Log.d("forEach", "i: $i j: $j left: ${it.left} right: ${it.right} top: ${it.top} bot: ${bottom}")
                                            Log.d("parent", parent.toString())

                                            if(motionEvent.x + i*(it.right - it.left) >= it.left && motionEvent.x + i*(it.right - it.left) <= it.right) {
                                                if(motionEvent.y >= it.top && motionEvent.y <= it.bottom) {
                                                    Log.d("Target", "yeah!")
                                                    it.setBackgroundColor(Color.GREEN)
                                                }
                                            }

                                        }
                                    }
                                    false
                                }
                            }
                        }
                    }
            }.lparams(width = matchParent)

            gridLayout {
                rowCount = 6
                columnCount = 6
                for(j in 0 ..5)
                        for (i in 0..5) {
                            editText(i.toString()) {
                                width = 100
                                height = 100
                                onTouch { view, motionEvent ->
                                    if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                                        toast("HI!ACTION_DOWN")
                                        Log.d("parent", parent.toString())
                                    }
                                    if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                                        Log.d("childCount", childCount.toString())

                                        forEachChild {
                                            Log.d("myCoord", "x: ${motionEvent.x + i*(it.right - it.left)} y: ${motionEvent.y}")
                                            Log.d("forEach", "i: $i j: $j left: ${it.left} right: ${it.right} top: ${it.top} bot: ${bottom}")
                                            Log.d("parent", parent.toString())

                                            if(motionEvent.x + i*(it.right - it.left) >= it.left && motionEvent.x + i*(it.right - it.left) <= it.right) {
                                                if(motionEvent.y + j*(it.bottom - it.top) >= it.top && motionEvent.y+ j*(it.bottom - it.top) <= it.bottom) {
                                                    Log.d("Target", "yeah!")
                                                    it.setBackgroundColor(Color.GREEN)
                                                }
                                            }

                                        }
                                    }
                                    false
                                }
                            }
                        }

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