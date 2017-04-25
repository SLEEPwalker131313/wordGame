package tryanko.test.com.wordgame

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import org.jetbrains.anko.*
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select

class GameActivity : AppCompatActivity() {
    //TODO Еще раз проверить клавиатуру
    //TODO Зарефакторить селектПлейер
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val player1name = intent.getStringExtra("player1")
        val player2name = intent.getStringExtra("player2")
        val fieldSize = intent.getIntExtra("fieldSize", 4)
        val word = intent.getStringExtra("word")
        var startText = ""
        var isPlayer1Turn = true
        var gameIsOver = false
        val fieldMatrix = Array(fieldSize + 1, { ArrayList<EditText>() })
        var lastChange = PartOfFieldDetail(-1, -1, "")
        var availableToMakeAWordPartOfField = mutableListOf<PartOfFieldDetail>()
        var currentWordList = mutableListOf<PartOfFieldDetail>()
//        var player1
        verticalLayout {
            background = resources.getDrawable(tryanko.test.com.wordgame.R.mipmap.main_background)
            minimumWidth = matchParent
            linearLayout {
                topPadding = 50
                leftPadding = 80
                rightPadding = 40
                textView(player1name) {
                    textSize = 24F
                    typeface = Typeface.createFromAsset(assets, "fonts/BadScript-Regular.ttf")
                    background = resources.getDrawable(tryanko.test.com.wordgame.R.mipmap.name_background1)
                    id = ViewIDEnum.PLAYER_1_NAME_TEXT_VIEW_ID.id
                    gravity = Gravity.CENTER

                }.lparams {
                    leftMargin = 30
                    width = dip(170)
                }
                textView(player2name) {
                    textSize = 24F
                    typeface = Typeface.createFromAsset(assets, "fonts/BadScript-Regular.ttf")
                    background = resources.getDrawable(tryanko.test.com.wordgame.R.mipmap.name_background)
                    id = ViewIDEnum.PLAYER_2_NAME_TEXT_VIEW_ID.id
                    gravity = Gravity.CENTER
                }.lparams {
                    leftMargin = 30
                    width = dip(170)
                }
            }
            linearLayout {
                textView("0") {
                    setupScoreView()
                    id = ViewIDEnum.PLAYER_1_SCORE_TEXT_VIEW.id
                }.lparams {
                    leftMargin = 30
                    width = dip(170)
                }
                textView("0") {
                    setupScoreView()
                    id = ViewIDEnum.PLAYER_2_SCORE_TEXT_VIEW.id
                }.lparams {
                    leftMargin = 130
                    width = dip(170)
                }
            }
            linearLayout {
                leftPadding = 190
                imageView(resources.getDrawable(tryanko.test.com.wordgame.R.mipmap.cross)){
                    id = ViewIDEnum.BTN_ROLLBACK_BUTTON_ID.id
                }
                textView {
                    width = 450
                    textSize = 42F
                    textColor = Color.BLACK
                    typeface = Typeface.createFromAsset(assets, "fonts/BadScript-Regular.ttf")
                    gravity = Gravity.CENTER
                    id = ViewIDEnum.CURRENT_WORD_TEXT_VIEW_ID.id
                }
                imageView(resources.getDrawable(tryanko.test.com.wordgame.R.mipmap.ok)){
                    id = ViewIDEnum.BTN_OK_BUTTON_ID.id
                }
            }
            var currentWord = find<TextView>(ViewIDEnum.CURRENT_WORD_TEXT_VIEW_ID.id)
            gridLayout {
                gravity = Gravity.CENTER_HORIZONTAL
//                backgroundColor = Color.RED
//                backgroundDrawable = resources.getDrawable(R.mipmap.grid)

                rowCount = fieldSize
                columnCount = fieldSize
                for (i in 0..fieldSize - 1) {
                    for (j in 0..fieldSize - 1) {
                        if (isMiddleRow(fieldSize, i)) {
                            availableToMakeAWordPartOfField.add(PartOfFieldDetail(i, j, word[j].toString()))
                            startText = word[j].toString()
                        } else
                            startText = ""
                        fieldMatrix[i].add(editText(startText) {
                            gravity = Gravity.CENTER
                            backgroundDrawable = resources.getDrawable(R.mipmap.empty)
                            setEditTextWidth(fieldSize)
                            weightSum = 1F
                            //Тут можно выбрать тип клавиатуры
//                                inputType = InputType.TYPE_NULL
                            if (isMiddleRow(fieldSize, i)) {
                                permanentEditTextStyle()
                            } else if (isNearlyMiddleRow(fieldSize, i)) {
                                availableToChangeEditTextStyle()
                            } else {
                                unavailableEditTextStyle()
                            }
                            //Просто для красоты
//                                cursorVisible = false
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
                            //TODO: проблематично исправлять букву в том же поле, что и ранее
                            onTouch { view, motionEvent ->
                                if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                                    makeNewWord(availableToMakeAWordPartOfField, currentWord,
                                            currentWordList, fieldMatrix, fieldSize, i, j, lastChange, motionEvent)
                                }
                                false
                            }
                        })
                    }
//                    }.lparams {
//                        fieldLayoutStyle()
//                    }
                }
            }.lparams {
                leftMargin = 75
                rightMargin = 30
            }
            //Кнопки пропуска хода и просмотра списка использованных слов
            linearLayout {
                button(StringConstantEnum.PASS_STRING_CONSTANT.text) {
                    id = ViewIDEnum.BTN_PASS_ID.id
                    onClick {
                        passThisTurn(availableToMakeAWordPartOfField, currentWordList,
                                fieldMatrix, fieldSize, isPlayer1Turn, lastChange)
                        isPlayer1Turn = !isPlayer1Turn
                    }
                }
                button(StringConstantEnum.SHOW_WORD_LIST_STRING_CONSTANT.text) {
                    id = ViewIDEnum.BTN_SHOW_WORD_LIST_ID.id
                    onClick {
                        val player1wordList = mutableListOf<String>()
                        val player2wordList = mutableListOf<String>()
                        database.use {
                            select("usedWords", "word", "isPlayer1Turn")
                                    .where("game_id = {gameId}",
                                            "gameId" to database.getMaxIdFromGameTable(database.readableDatabase))
                                    .exec {
                                        Log.d("count", count.toString())
                                        parseList(rowParser {
                                            word: String, isPlayer1Turn: Int ->
                                            Log.d("usedWords", "word: $word isPlayer1Turn: $isPlayer1Turn")
                                            if (isPlayer1Turn == 1)
                                                player1wordList.add(word)
                                            else
                                                player2wordList.add(word)
                                        })
                                    }
                        }
                        alert("Использованные слова", "") {
                            customView {
                                linearLayout {
                                    verticalLayout {
                                        listView {
                                            adapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, player1wordList)
                                        }
                                    }.lparams { weight = 1F }
                                    verticalLayout {
                                        listView {
                                            adapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, player2wordList)
                                        }
                                    }.lparams { weight = 1F }
                                }
                            }
                            positiveButton("Закрыть") {}
                        }.show()
                    }
                }
            }
        }
        //Кнопка отмены
        find<ImageView>(ViewIDEnum.BTN_ROLLBACK_BUTTON_ID.id)
                .onClick {
                    //Оставляем вставленный символ
                    deselectNewWord(availableToMakeAWordPartOfField, currentWordList,
                            fieldMatrix, lastChange)
                    hideKeyBoard(fieldMatrix, 0, 0)
                }
        //Кнопка подтверждения хода
        find<ImageView>(ViewIDEnum.BTN_OK_BUTTON_ID.id)
                .onClick {
                    val currentWord = find<TextView>(ViewIDEnum.CURRENT_WORD_TEXT_VIEW_ID.id)
                    when {
                        !newSymbolWasUsed(currentWordList, lastChange) -> {
                            toast(StringConstantEnum.USE_NEW_SYMBOL_STRING_CONSTANT.text)
                        }
                        !isInTheDictionary(currentWord.text.toString()) -> {
                            toast(StringConstantEnum.WORD_NOT_FOUND_STRING_CONSTANT.text)
                        }
                        isUsedEarlier(currentWord.text.toString(), intent.getStringExtra("word")) -> {
                            toast(StringConstantEnum.WORD_WAS_USED_EARLIER_STRING_CONSTANT.text)
                        }
                        else -> {
                            updateScore(currentWordList, isPlayer1Turn)
                            finishThisTurn(availableToMakeAWordPartOfField, currentWordList,
                                    fieldMatrix, fieldSize, isPlayer1Turn, lastChange)
                            isPlayer1Turn = !isPlayer1Turn
                        }
                    }
                }

        //Добавление слушателей изменения текста поддержания уденственного нового символу на одном ходу
        for (i in 0..fieldSize - 1) {
            for (j in 0..fieldSize - 1) {
                fieldMatrix[i][j].textChangedListener {
                    onTextChanged { charSequence, start, before, count ->
                        //TODO Нормальное ли условие? МБ стоит додумать
                        if (charSequence.toString() != "") {
                            kotlin.run {
                                if (addedNewSymbol(lastChange)) {
                                    fieldMatrix[lastChange.x][lastChange.y].setText("")
                                    fieldMatrix[lastChange.x][lastChange.y].availableToChangeEditTextStyle()
                                }
                                rollbackStyle(availableToMakeAWordPartOfField, currentWordList, fieldMatrix)
                                fieldMatrix[i][j].thisTurnChangedEditTextStyle()
                                setLastChange(charSequence, i, j, lastChange)

                                hideKeyBoard(fieldMatrix, i, j)
                            }
                        }
                    }
                }
            }
        }
        //Первичная подсветка игрока
        selectCurrentPlayer(isPlayer1Turn)
    }

    private fun EditText.setEditTextWidth(fieldSize: Int) {
        width = when (fieldSize) {
            4 -> dip(90)
            5 -> dip(70)
            6 -> dip(60)
            else -> dip(50)
        }
    }

    private fun TextView.setupScoreView() {
        textSize = 30F
        textColor = Color.BLACK
        typeface = Typeface.createFromAsset(assets, "fonts/BadScript-Regular.ttf")
        gravity = Gravity.CENTER
    }

    private fun hideKeyBoard(fieldMatrix: Array<ArrayList<EditText>>, i: Int, j: Int): Boolean {
        val mgr: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return mgr.hideSoftInputFromWindow(fieldMatrix[i][j].windowToken, 0)
    }

    private fun rollbackStyle(availableToMakeAWordPartOfField: MutableList<PartOfFieldDetail>, currentWordList: MutableList<PartOfFieldDetail>, fieldMatrix: Array<ArrayList<EditText>>) {
        for (z in availableToMakeAWordPartOfField) {
            fieldMatrix[z.x][z.y].permanentEditTextStyle()
        }
        clearCurrentWordData(currentWordList)
    }

    private fun _GridLayout.makeNewWord(availableToMakeAWordPartOfField: MutableList<PartOfFieldDetail>,
                                        currentWord: TextView, currentWordList: MutableList<PartOfFieldDetail>,
                                        fieldMatrix: Array<ArrayList<EditText>>, fieldSize: Int,
                                        i: Int, j: Int, lastChange: PartOfFieldDetail,
                                        motionEvent: MotionEvent) {
        if (addedNewSymbol(lastChange)) {
            forEachChild {
                if (motionEvent.x + j * (it.right - it.left) >= it.left && motionEvent.x + j * (it.right - it.left) <= it.right) {
                    if (motionEvent.y + i * (it.bottom - it.top) >= it.top && motionEvent.y + i * (it.bottom - it.top) <= it.bottom) {
                        val tmp = it as EditText
                        val (newI, newJ) = newCoord(fieldMatrix, fieldSize, tmp)
                        availableToMakeAWordPartOfField
                                .filter { wordInAvailableListOrLastChange(fieldMatrix, newI, newJ, it, lastChange) }
                                .forEach {
                                    if (isAvailablePositionForUseInWord(currentWordList, newI, newJ)) {
                                        if (currentWordList.isEmpty() || (currentWordList.none { it.x == newI && it.y == newJ })) {
                                            tmp.selectTouchedView()
                                            updateCurrentWordVariables(currentWord, currentWordList, fieldMatrix, newI, newJ)
                                        }
                                    }
                                }
                    }
                }

            }
        }
    }

    private fun newCoord(fieldMatrix: Array<ArrayList<EditText>>, fieldSize: Int, tmp: EditText): Pair<Int, Int> {
        var newI = -1
        var newJ = -1
        for (m in 0..fieldSize - 1)
            for (n in 0..fieldSize - 1) {
                if ((fieldMatrix[m][n].left == tmp.left) && (fieldMatrix[m][n].top == tmp.top)) {
                    newI = m
                    newJ = n
                }
            }
        return Pair(newI, newJ)
    }

    private fun insertNewWordIntoUsedWordsTable(currentWord: String, isPlayer1Turn: Boolean) {
        database.insertIntoUsedWordsTable(database.readableDatabase, database.getMaxIdFromGameTable(database.readableDatabase), currentWord, isPlayer1Turn)
    }

    private fun updateScore(currentWordList: MutableList<PartOfFieldDetail>, isPlayer1Turn: Boolean) {
        if (isPlayer1Turn) {
            val player1score = find<TextView>(ViewIDEnum.PLAYER_1_SCORE_TEXT_VIEW.id)
            player1score.text = (player1score.text.toString().toInt() +
                    currentWordList.size).toString()
        } else {
            val player2score = find<TextView>(ViewIDEnum.PLAYER_2_SCORE_TEXT_VIEW.id)
            player2score.text = (player2score.text.toString().toInt() +
                    currentWordList.size).toString()
        }
    }

    private fun setLastChange(charSequence: CharSequence?, i: Int, j: Int, lastChange: PartOfFieldDetail) {
        lastChange.x = i
        lastChange.y = j
        lastChange.symbol = charSequence.toString()
    }

    private fun updateCurrentWordVariables(currentWord: TextView,
                                           currentWordList: MutableList<PartOfFieldDetail>,
                                           fieldMatrix: Array<ArrayList<EditText>>, i: Int, j: Int) {
        currentWordList.add(PartOfFieldDetail(i, j, fieldMatrix[i][j].text.toString()))
        currentWord.text = currentWord.text.toString() + fieldMatrix[i][j].text.toString()
    }

    private fun EditText.selectTouchedView() {
        backgroundDrawable = resources.getDrawable(R.mipmap.new_word)
    }

    private fun wordInAvailableListOrLastChange(fieldMatrix: Array<ArrayList<EditText>>,
                                                i: Int, j: Int, k: PartOfFieldDetail,
                                                lastChange: PartOfFieldDetail) =
            (k.x == i && k.y == j && k.symbol == fieldMatrix[i][j].text.toString()) ||
                    (lastChange.x == i && lastChange.y == j && lastChange.symbol == fieldMatrix[i][j].text.toString())

    private fun isAvailablePositionForUseInWord(currentWordList: MutableList<PartOfFieldDetail>, i: Int, j: Int) =
            currentWordList.isEmpty() ||
                    (Math.abs(currentWordList[currentWordList.lastIndex].x - i) == 1 &&
                            currentWordList[currentWordList.lastIndex].y == j) ||
                    (Math.abs(currentWordList[currentWordList.lastIndex].y - j) == 1 &&
                            currentWordList[currentWordList.lastIndex].x == i)


    private fun deselectNewWord(availableToMakeAWordPartOfField: MutableList<PartOfFieldDetail>,
                                currentWordList: MutableList<PartOfFieldDetail>,
                                fieldMatrix: Array<ArrayList<EditText>>,
                                lastChange: PartOfFieldDetail) {
        if (addedNewSymbol(lastChange)) {
            fieldMatrix[lastChange.x][lastChange.y].thisTurnChangedEditTextStyle()
        }
        rollbackStyle(availableToMakeAWordPartOfField, currentWordList, fieldMatrix)
    }

    private fun clearCurrentWordData(currentWordList: MutableList<PartOfFieldDetail>) {
        currentWordList.clear()
        find<TextView>(ViewIDEnum.CURRENT_WORD_TEXT_VIEW_ID.id).text = ""
    }

    private fun finishThisTurn(availableToMakeAWordPartOfField: MutableList<PartOfFieldDetail>,
                               currentWordList: MutableList<PartOfFieldDetail>,
                               fieldMatrix: Array<ArrayList<EditText>>,
                               fieldSize: Int, isPlayer1Turn: Boolean,
                               lastChange: PartOfFieldDetail) {
        var isPlayer1Turn1 = isPlayer1Turn
        var newWord = ""
        currentWordList
                .forEach { newWord += it.symbol }
        database.insertIntoUsedWordsTable(database.readableDatabase,
                database.getMaxIdFromGameTable(database.readableDatabase), newWord, isPlayer1Turn)
        availableToMakeAWordPartOfField.add(PartOfFieldDetail(lastChange.x, lastChange.y,
                lastChange.symbol))
        applyNewStyleForView(availableToMakeAWordPartOfField, fieldMatrix, fieldSize, lastChange)
        prepareDataToNextTurn(currentWordList, fieldMatrix, lastChange)
        isPlayer1Turn1 = !isPlayer1Turn1
        selectCurrentPlayer(isPlayer1Turn1)
    }

    private fun passThisTurn(availableToMakeAWordPartOfField: MutableList<PartOfFieldDetail>,
                             currentWordList: MutableList<PartOfFieldDetail>,
                             fieldMatrix: Array<ArrayList<EditText>>,
                             fieldSize: Int, isPlayer1Turn: Boolean,
                             lastChange: PartOfFieldDetail) {
        var isPlayer1Turn1 = isPlayer1Turn
        for (z in availableToMakeAWordPartOfField) {
            fieldMatrix[z.x][z.y].permanentEditTextStyle()
        }
        if (addedNewSymbol(lastChange)) {
            fieldMatrix[lastChange.x][lastChange.y].setText("")
            fieldMatrix[lastChange.x][lastChange.y].availableToChangeEditTextStyle()
        }
        prepareDataToNextTurn(currentWordList, fieldMatrix, lastChange)
        isPlayer1Turn1 = !isPlayer1Turn1
        selectCurrentPlayer(isPlayer1Turn1)
    }

    private fun addedNewSymbol(lastChange: PartOfFieldDetail) = lastChange.x != -1

    private fun prepareDataToNextTurn(currentWordList: MutableList<PartOfFieldDetail>,
                                      fieldMatrix: Array<ArrayList<EditText>>,
                                      lastChange: PartOfFieldDetail) {
        //TODO проверить действительно ли нам нужен фокусейбл
        //TODO если нет - Убрать соответствующий параметр из всех функций
//        fieldMatrix[lastChange.x][lastChange.y].isFocusable = false
        lastChange.x = -1
        lastChange.y = -1
        lastChange.symbol = ""
        clearCurrentWordData(currentWordList)
    }

    private fun applyNewStyleForView(availableToMakeAWordPartOfField: MutableList<PartOfFieldDetail>,
                                     fieldMatrix: Array<ArrayList<EditText>>, fieldSize: Int,
                                     lastChange: PartOfFieldDetail) {
        if (lastChange.x > 0) {
            if (fieldMatrix[lastChange.x - 1][lastChange.y].text.toString().equals(""))
                fieldMatrix[lastChange.x - 1][lastChange.y]
                        .availableToChangeEditTextStyle()
        }
        if (lastChange.x < fieldSize - 1) {
            if (fieldMatrix[lastChange.x + 1][lastChange.y].text.toString().equals(""))
                fieldMatrix[lastChange.x + 1][lastChange.y]
                        .availableToChangeEditTextStyle()
        }

        if (lastChange.y > 0) {
            if (fieldMatrix[lastChange.x][lastChange.y - 1].text.toString().equals(""))
                fieldMatrix[lastChange.x][lastChange.y - 1]
                        .availableToChangeEditTextStyle()
        }
        if (lastChange.y < fieldSize - 1) {
            if (fieldMatrix[lastChange.x][lastChange.y + 1].text.toString().equals(""))
                fieldMatrix[lastChange.x][lastChange.y + 1]
                        .availableToChangeEditTextStyle()
        }
        for (z in availableToMakeAWordPartOfField) {
            fieldMatrix[z.x][z.y].permanentEditTextStyle()
        }
    }

    private fun newSymbolWasUsed(currentWordList: MutableList<PartOfFieldDetail>,
                                 lastChange: PartOfFieldDetail) =
            currentWordList.any { it.x == lastChange.x && it.y == lastChange.y }

    //Применить стили в зависимости от того чей ход
    fun selectCurrentPlayer(isPlayer1Turn: Boolean) {
        val player1nameTextView = find<TextView>(ViewIDEnum.PLAYER_1_NAME_TEXT_VIEW_ID.id)
        val player2nameTextView = find<TextView>(ViewIDEnum.PLAYER_2_NAME_TEXT_VIEW_ID.id)
        if (isPlayer1Turn) {
            activePlayerTextViewStyle(player1nameTextView)
            nonActivePlayerTextViewStyle(player2nameTextView)
        } else {
            activePlayerTextViewStyle(player2nameTextView)
            nonActivePlayerTextViewStyle(player1nameTextView)
        }
    }

    private fun nonActivePlayerTextViewStyle(playerNameTextView: TextView) {
        playerNameTextView.alpha = 0.5F
    }

    private fun activePlayerTextViewStyle(playerNameTextView: TextView) {
        playerNameTextView.alpha = 1F
    }

    //Стиль для основного игрвоого поля
    private fun LinearLayout.LayoutParams.fieldLayoutStyle() {
        width = matchParent
        leftMargin = 30
        rightMargin = 30
    }

    //Стиль для пустых ячеек, недоступных для изменения
    private fun EditText.unavailableEditTextStyle() {
        isEnabled = false
        backgroundDrawable = resources.getDrawable(R.mipmap.empty)
    }

    //Стиль для пустых ячеек, значение которых можно изменить на этом ходу
    private fun EditText.availableToChangeEditTextStyle() {
        isEnabled = true
        backgroundDrawable = resources.getDrawable(R.mipmap.available)
    }

    private fun EditText.thisTurnChangedEditTextStyle() {
        isEnabled = true
        backgroundDrawable = resources.getDrawable(R.mipmap.this_turn_change)
    }

    private fun isUsedEarlier(currentWord: String, startWord: String): Boolean {
        var result = false
        val gameId = database.getMaxIdFromGameTable(database.readableDatabase)
        if (currentWord == startWord)
            return true
        database.use {
            select("usedWords")
                    .where("(game_id = {gameId}) and (word = {currentWord})",
                            "gameId" to gameId,
                            "currentWord" to currentWord)
                    .exec { if (count != 0) result = true }
        }
        return result
    }

    private fun isInTheDictionary(word: String): Boolean {
        var result = true
        database.use {
            select("nouns")
                    .where("(word = {word})",
                            "word" to word)
                    .exec {
                        if (count == 0)
                            result = false
                        Log.d("find", "find")
                    }
        }
        return result
    }

    //Стиль для завершенных ячеек, недоступных для изменения
    //Можно использовать для составления новых слов
    private fun EditText.permanentEditTextStyle() {
//        isEnabled = false
        backgroundDrawable = resources.getDrawable(R.mipmap.permanent)
        showSoftInputOnFocus = false
    }

    private fun isNearlyMiddleRow(fieldSize: Int, i: Int) =
            i == fieldSize / 2 - 1 || i == fieldSize / 2 + 1

    private fun isMiddleRow(fieldSize: Int, i: Int) = i == fieldSize / 2

    /**
     * Проверка касания соответствующего отображаемого элемента
     * @param view объект типа [View] содержащий в себе границы
     */
    fun MotionEvent.viewIsTouched(view: View) {
        if (x >= view.left && x <= view.right) {
            if (y >= view.top && x <= view.bottom) {
                Log.d("Target", "yeah!")
                view.setBackgroundColor(Color.GREEN)
            }
        }
    }
}
