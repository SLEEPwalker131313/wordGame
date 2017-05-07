package tryanko.test.com.wordgame

import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import org.jetbrains.anko.*
import org.jetbrains.anko.db.select
import tryanko.test.com.wordgame.Activities.GameSettingsActivity
import tryanko.test.com.wordgame.DataBase.database
import tryanko.test.com.wordgame.Enum.StringConstantEnum
import java.io.IOException
import java.io.InputStream

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            background = resources.getDrawable(R.mipmap.main_background)
            textView {
                text = StringConstantEnum.START_GAME_STRING_CONSTANT.text
                applyCustomFont()
                textSize = 40F
                textColor = Color.BLACK
                gravity = Gravity.CENTER_HORIZONTAL
                gravity = Gravity.BOTTOM
                onClick {
                    startActivity<GameSettingsActivity>()
                }
            }.lparams(weight = 1F) {
                gravity = Gravity.CENTER
            }
            textView {
                text = StringConstantEnum.RULES_WORD_CONSTANT.text
                applyCustomFont()
                textSize = 40F
                textColor = Color.BLACK
                gravity = Gravity.CENTER_HORIZONTAL
                gravity = Gravity.TOP
                onClick {
                    alert {
                        customTitle {
                            linearLayout {
                                imageView(resources.getDrawable(R.mipmap.rules_title_icon))
                                        .lparams {
                                            leftMargin = 30
                                            topMargin = 20
                                        }
                                textView {
                                    text = StringConstantEnum.RULES_WORD_CONSTANT.text
                                    applyCustomFont()
                                    textSize = 30F
                                    gravity = Gravity.CENTER
                                }.lparams {
                                    leftMargin = 20
                                    topMargin = 20
                                }
                            }
                        }
                        customView {
                            scrollView {
                                background = resources.getDrawable(R.mipmap.paper)
                                verticalLayout {
                                    textView {
                                        text = StringConstantEnum.MAIN_RULES_TEXT_STRING_CONSTANT.text
                                        gravity = Gravity.CENTER_HORIZONTAL
                                        textColor = Color.BLACK
                                    }.lparams { marginLeftRight() }
                                    textView {
                                        text = StringConstantEnum.RULES_TITLE_TEXT_STRING_CONSTANT.text
                                        textColor = Color.BLACK
                                        textSize = 17F
                                    }.lparams { marginLeftRight() }
                                    textView {
                                        text = StringConstantEnum.RULE_1_STRING_CONSTANT.text
                                    }.lparams { marginLeftRight() }
                                    textView {
                                        text = StringConstantEnum.RULE_2_STRING_CONSTANT.text
                                    }.lparams { marginLeftRight() }
                                    textView {
                                        text = StringConstantEnum.RULE_3_STRING_CONSTANT.text
                                    }.lparams { marginLeftRight() }
                                    textView {
                                        text = StringConstantEnum.RULE_4_STRING_CONSTANT.text
                                    }.lparams { marginLeftRight() }
                                    textView {
                                        text = StringConstantEnum.RULE_5_STRING_CONSTANT.text
                                    }.lparams { marginLeftRight() }
                                }
                            }
                        }
                        positiveButton(StringConstantEnum.CLOSE_STRING_CONSTANT.text) {}
                    }.show()
                }
            }.lparams(weight = 1.5F) {
                gravity = Gravity.CENTER
            }
        }

//        Log.d("assets", str_data)
        database.use {
//            database.createDetailTable(database.readableDatabase)
//            database.createUsedWordsTable(database.readableDatabase)
//            database.createGameTable(database.readableDatabase)
//            database.createNounsTable(database.readableDatabase)
            //setup db for debug
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

            select("nouns").exec{
                Log.d("count", count.toString())
                if(count == 0){
                val text = "database/sqlscript"
                var buffer: ByteArray? = null
//                val iStream: InputStream
                try {
                    var iStream = assets.open(text)
                    val size = iStream.available()
                    buffer = ByteArray(size)
                    iStream.read(buffer)
                    iStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                val str_data = String(buffer!!)
                execSQL(str_data)
            }
            }

//            database.dropGameTable(database.readableDatabase)
//            database.dropUsedWordsTable(database.readableDatabase)
        }
    }

    /**
     * Apply our font for view
     */
    private fun TextView.applyCustomFont() {
        typeface = Typeface.createFromAsset(assets, "fonts/BadScript-Regular.ttf")
    }

    /**
     * Apply margin
     */
    private fun LinearLayout.LayoutParams.marginLeftRight() {
        leftMargin = 30
        rightMargin = 30
    }
}