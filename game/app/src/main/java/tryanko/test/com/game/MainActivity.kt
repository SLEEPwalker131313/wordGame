package tryanko.test.com.game

import android.graphics.Color
import android.opengl.ETC1
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.TextView
import android.text.Editable
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import org.jetbrains.anko.*
import kotlin.coroutines.experimental.EmptyCoroutineContext.plus


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myTextBox = editText38
//        myTextBox.setOnTouchListener(object: View.OnTouchListener{
//            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//            }
//        })

//        myTextBox.addTextChangedListener(object : TextWatcher {
//
//            override fun afterTextChanged(s: Editable) {}
//
//            override fun beforeTextChanged(s: CharSequence, start: Int,
//                                           count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence, start: Int,
//                                       before: Int, count: Int) {
//                myTextBox.hint = "!"
//                myTextBox.setSelectAllOnFocus(true)
//
//            }
//        })



        val size = 3
        //TODO:Доработать, не удобно обращаться к массивам для добавления ar[i].add( editText {
        //Старый вид, вдруг забуду=)
        //val ar = Array<ArrayList<EditText>>(size+1, {ArrayList<EditText>()})
        val ar = Array(size+1, {ArrayList<EditText>()})
        verticalLayout {
            //TODO: Прикрепить к полю, перерисовать текстурки
            val tmp = ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_item, arrayOf("ds", "dsss", "bv", "54df", "zzz"))
            spinner {
                adapter = tmp
                prompt = "Размер поля"
                setSelection(2)
            }
            var textView = textView("text")
            gridLayout {

//                Старая запись, вдруг пригодится=)
//                setOnTouchListener(object: View.OnTouchListener{
//                    override fun onTouch(v: View?, event: MotionEvent): Boolean {
//                        if(event.action == MotionEvent.ACTION_MOVE) {
//                            event.viewIsTouched(ar[0][0])
//                            event.viewIsTouched(ar[1][1])
//                        }
//                        return true
//                    }
//                })

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
                            //onTouch { view, motionEvent -> true }
                            hint = c++.toString()
                            //Выделение всего содержимого при клике на поле
                            setSelectAllOnFocus(true)
                            textChangedListener {
                                onTextChanged { charSequence, start, before, count ->
                                    kotlin.run {
                                        //Не больше одного символа
                                        selectAll()
//                                        toast(charSequence.toString())
//                                        toast(isInTouchMode.toString())
                                    }
                                }
                            }
                        })
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