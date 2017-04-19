package tryanko.test.com.wordgame

import android.R
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.tintedTextView
import org.jetbrains.anko.db.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val size = 3
        //TODO:Доработать, не удобно обращаться к массивам для добавления ar[i].add( editText {
        //Старый вид, вдруг забуду=)
        //val ar = Array<ArrayList<EditText>>(size+1, {ArrayList<EditText>()})
        val ar = Array(size+1, {ArrayList<EditText>()})
        database.use{
//            dropTable("nouns")
//            createTable("nouns", true,
//                    "_id" to INTEGER + PRIMARY_KEY + UNIQUE,
//                    "word" to TEXT)
//
//            Log.d("dbName", database.databaseName)
//            database.insertIntoTable(database.readableDatabase)
//            database.selectFromTable(database.readableDatabase, "nouns")
            select("nouns").exec { Log.d("sd", count.toString()) }
            val parser = rowParser { word: String? -> Log.d("11", word.toString()) }
            select("nouns", "word")
                    .where("(word = {tmp})",
                            "tmp" to "азбука")
                    .exec {
                        if(count == 1)
                                parseSingle(parser)
                    }
        }

        verticalLayout {
            //TODO: Прикрепить к полю, перерисовать текстурки
            val tmp = ArrayAdapter<String>(applicationContext, R.layout.simple_spinner_item, arrayOf("ds", "dsss", "bv", "54df", "zzz"))
            spinner {
                adapter = tmp
                prompt = "Размер поля"
                setSelection(2)
            }

            var textView = textView("азбук")
            gridLayout {
                //Обработка касаний
                setOnTouchListener { v, event ->
                    if(event.action == MotionEvent.ACTION_MOVE) {
                        //TODO: Дополнительные проверки на то какием именно вьюшки должны передаваться
                        event.viewIsTouched(ar[0][0])
                        event.viewIsTouched(ar[1][1])
                    }
                    true
                }
                rowCount = size
                columnCount = size
                var c = 0

                for (i in 0..size - 1) {
                    for (j in 0..size - 1) {
                        ar[i].add(editText {

                            //TODO: Переработать на тач
                            //TODO: Альтернатива конкатенации
                            onClick {
                                textView.text = textView.text.toString()+text
                            }

                            //Просто для красоты
                            hint = c++.toString()
                            //Выделение всего содержимого при клике на поле
                            setSelectAllOnFocus(true)
                            textChangedListener {
                                onTextChanged { charSequence, start, before, count ->
                                    kotlin.run {
                                        //Не больше одного символа
                                        selectAll()
                                    }
                                }
                            }
                        })
                    }
                }
            }
            button {
                text = "check word"
                onClick{
                    toast(textView.text)
                    database.use {
                        select("nouns", "word")
                                .where("(word = {tmp})",
                                        "tmp" to textView.text)
                                .exec {
                                    if(count == 1) {
                                        Log.d("check word", "find!")
                                        toast(textView.text)
                                    } else{
                                        toast("wrong!")
                                    }

                                }
                    }
                }
            }
        }
    }
}


/**
 * Проверка касания соответствующего отображаемого элемента
 * @param view объект типа [View] содержащий в себе границы
 */
fun MotionEvent.viewIsTouched(view: View){
    if(x >= view.left && x <= view.right) {
        if(y >= view.top && x <= view.bottom) {
            Log.d("Target", "yeah!")
            view.setBackgroundColor(Color.GREEN)
        }
    }
}

