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
            linearLayout {
                onTouch { v, event ->
                    if(event.action == MotionEvent.ACTION_DOWN){
//                        toast("Hi!")
//                        backgroundColor = Color.RED

                    }
                    if(event.action == MotionEvent.ACTION_MOVE) {

                        forEachChild {
//                            Log.d("forEach", it.toString())
                            Log.d("myCoord", "x: ${event.x} y: ${event.y}")
                            Log.d("forEach", "left: ${it.left} right: ${it.right} top: ${it.top} bot: ${bottom}")
                            event.viewIsTouched(it)
                        }
//                        Log.d("focusedChild", focusedChild.)
//                        backgroundColor = Color.GREEN
//                        event.viewIsTouched(child)
//                        event.viewIsTouched(find<TextView>(102))
//                        event.viewIsTouched(find<TextView>(103))
//                        event.viewIsTouched(find<TextView>(104))
//                        focusedChild.backgroundColor = Color.GREEN
//                        v.childrenRecursiveSequence()
//                        v.backgroundColor = Color.GREEN
//                        backgroundColor = Color.GREEN
//                        focusedChild.backgroundColor = Color.GREEN
                    }
                    true
                }
                for(i in 0..2){
                    //Работает с textView
                    editText(i.toString()){
                        cursorVisible = false
                        showSoftInputOnFocus = false
                        touchscreenBlocksFocus = true
//                        onClick{
//                            backgroundColor = Color.RED
//                        }
                        onTouch { view, motionEvent ->
                            if(motionEvent.action == MotionEvent.ACTION_POINTER_DOWN){
                                toast("HI! ACTION_POINTER_DOWN")
                            }
                            if(motionEvent.action == MotionEvent.ACTION_BUTTON_PRESS){
                                toast("HI! ACTION_BUTTON_PRESS")
                            }
                            if(motionEvent.action == MotionEvent.ACTION_DOWN){
                                toast("HI!ACTION_DOWN")
                                Log.d("parent", parent.toString())
                            }
                            if(motionEvent.action == MotionEvent.ACTION_MOVE){
                                toast("HI! ACTION_MOVE")
                                Log.d("parent", parent.toString())
                                backgroundColor = Color.RED
//                                onFocusChange { view, b -> motionEvent.viewIsTouched(view)
//                                    Log.d("check","view") }
                            }
                            if(motionEvent.action == MotionEvent.ACTION_MASK){
                                toast("HI! ACTION_MASK")
                            }
                            if(motionEvent.action == MotionEvent.ACTION_HOVER_MOVE)
                                backgroundColor = Color.RED
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
        if(y >= view.top && x <= view.bottom) {
            Log.d("Target", "yeah!")
            view.setBackgroundColor(Color.GREEN)
        }
    }
}