package tryanko.test.com.game

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
//
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
        //Доработать, не удобно обращаться к массивам ar[i].add( editText {
        val ar = Array<ArrayList<EditText>>(size+1, {ArrayList<EditText>()})
        verticalLayout {
            var textView = textView("text")
            gridLayout {
                setOnTouchListener(object: View.OnTouchListener{
                    override fun onTouch(v: View?, event: MotionEvent): Boolean {
                        //toast("touch!!!")
//                        Log.d("ar coord", "ar[0][1] x=${ar[0][1].x} y=${ar[0][1].y}")
//                        ar[0][1].
                        if(event.action == MotionEvent.ACTION_MOVE) {
                            if(event.x >= ar[0][1].left && event.x <= (ar[0][1].left + ar[0][1].width)) {
                                if(event.y >= ar[0][1].top && event.x <= (ar[0][1].top + ar[0][1].height)) {
                                    Log.d("Target", "yeah!")
                                }
                            }
                        }
                        return true
                    }
                })
                rowCount = size
                columnCount = size
                var c = 0

                for (i in 0..size - 1) {
                    for (j in 0..size - 1) {
                        ar[i].add(editText {
                            //TODO: Переработать
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
                ar[1][1].hint = "Ы"
                //            editText {
                //                //Выделение всего содержимого при клике на поле
                //                setSelectAllOnFocus(true)
                //                textChangedListener {
                //                    onTextChanged { charSequence, start, before, count -> kotlin.run {
                //                            //Не больше одного символа
                //                            selectAll()
                //                            toast(charSequence.toString())
                //                        }
                //                    }
                //                }
                //            }

            }
        }
    }
}
