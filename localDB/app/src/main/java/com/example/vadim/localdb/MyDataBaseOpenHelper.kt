package com.example.vadim.localdb
//
//import android.content.Context
//import android.database.sqlite.SQLiteDatabase
//import android.util.Log
//import org.jetbrains.anko.db.*
//import android.content.IntentSender
//import android.content.res.AssetManager
//import org.jetbrains.anko.MAXDPI
//import jdk.nashorn.internal.runtime.ECMAErrors.getMessage
//import android.support.test.espresso.core.deps.guava.io.Flushables.flush
//import java.io.File
//import java.io.IOException
//import java.io.InputStream
//import java.io.OutputStream
//import java.nio.file.Files.exists
//
//
//
//class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "MyDatabase", null, 1) {
//    companion object {
//        private var instance: MyDatabaseOpenHelper? = null
//
//        @Synchronized
//        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
//            if (instance == null) {
//                instance = MyDatabaseOpenHelper(ctx.applicationContext)
//            }
//            return instance!!
//        }
//    }
//    override fun onCreate(db: SQLiteDatabase) {
//
//        var myInput: InputStream? = null
//        var myOutput: OutputStream? = null
//        try {
//            val file = File("wordGame.db")
//            if (!file.exists()) {
//                this.readableDatabase
//                //получаем локальную бд как поток
//                myInput = this.open(DB_NAME)
//                // Путь к новой бд
//                val outFileName = DB_PATH
//
//                // Открываем пустую бд
//                myOutput = FileOutputStream(outFileName)
//
//                // побайтово копируем данные
//                val buffer = ByteArray(1024)
//                var length: Int
//                while ((length = myInput!!.read(buffer)) > 0) {
//                    myOutput!!.write(buffer, 0, length)
//                }
//
//                myOutput!!.flush()
//                myOutput!!.close()
//                myInput!!.close()
//            }
//        } catch (ex: IOException) {
//            Log.d("DatabaseHelper", ex.getMessage())
//        }
//
////        createDetailTable(db)
////        createUsedWordsTable(db)
////        createGameTable(db)
////        createNounsTable(db)
//    }
//
//    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//    }
//    fun createNounsTable(db: SQLiteDatabase){
//        db.createTable("nouns", true,
//                "_id" to INTEGER + PRIMARY_KEY + UNIQUE,
//                "word" to TEXT)
//    }
//    fun createGameTable(db: SQLiteDatabase){
//        db.createTable("games", true,
//                "_id" to INTEGER + PRIMARY_KEY + UNIQUE,
//                "player1" to TEXT,
//                "player2" to TEXT,
//                "fieldSize" to INTEGER,
//                "startWord" to TEXT,
//                "isFinished" to INTEGER)
//    }
//    fun insertIntoGameTable(db: SQLiteDatabase, _id: Int, player1: String, player2: String,
//                            fieldSize: Int, startWord: String, isFinished: Int){
//        //Добавим запись в бд
//        db.insert("games",
//                "_id" to _id,
//                "player1" to player1,
//                "player2" to player2,
//                "fieldSize" to fieldSize,
//                "startWord" to startWord,
//                "isFinished" to isFinished)
//    }
//    fun selectFromGameTable(db: SQLiteDatabase){
//        val parser = rowParser { player1: String? -> Log.d("11", player1.toString()) }
//        db.select("games", "player1")
//                .exec { parseList(parser)  }
//    }
//    fun getMaxIdFromGameTable(db: SQLiteDatabase): Int{
//        var id = 0
//        //Проверка на пустоту запроса
//        db.select("games", "_id")
//                .exec {
//                    if(count!=0){
//                        //Нахождение максимального id среди добавленных ранее
//                        db.select("games", "MAX(_id)")
//                                .exec {
//                                    parseList(rowParser { _id: Int-> id = _id+1 })
//                                }
//                    }
//                }
//        return id
//    }
//    fun dropGameTable(db: SQLiteDatabase){db.dropTable("games")}
//    fun createDetailTable(db: SQLiteDatabase){
//        db.createTable("detail", true,
//                "_step" to INTEGER + PRIMARY_KEY + UNIQUE,
//                "game_id" to INTEGER,
//                "player1Score" to INTEGER,
//                "player2Score" to INTEGER,
//                "playerTurn" to INTEGER,
//                "newLiter" to TEXT,
//                "newLiterXCoord" to INTEGER,
//                "newLiterYCoord" to INTEGER)
//        FOREIGN_KEY("game_id", "games", "_id")
//    }
//    fun insertIntoDetailTable(db: SQLiteDatabase, _step: Int, game_id: Int,
//                              player1Score: Int, player2Score: Int,
//                              playerTurn: Int, newLiter: String,
//                              newLiterXCoord: Int, newLiterYCoord: Int){
//        db.insert("details",
//                "_step" to _step,
//                "game_id" to game_id,
//                "player1Score" to player1Score,
//                "player2Score" to player2Score,
//                "playerTurn" to playerTurn,
//                "newLiter" to newLiter,
//                "newLiterXCoord" to newLiterXCoord,
//                "newLiterYCoord" to newLiterYCoord)
//    }
//    fun dropDetailTable(db: SQLiteDatabase){db.dropTable("detail")}
//
//    fun createUsedWordsTable(db: SQLiteDatabase){
//        db.createTable("usedWords", true,
//                "game_id" to INTEGER,
//                "word" to TEXT,
//                "isPlayer1Turn" to INTEGER)
//        FOREIGN_KEY("game_id", "games", "_id")
//    }
//    fun insertIntoUsedWordsTable(db: SQLiteDatabase, game_id: Int, word: String, isPlayer1Turn: Boolean){
//        //Возможно перепутал
//        var turn = 0
//        if(isPlayer1Turn)
//            turn = 1
//        db.insert("usedWords",
//                "game_id" to game_id,
//                "word" to word,
//                "isPlayer1Turn" to turn)
//    }
//    fun dropUsedWordsTable(db: SQLiteDatabase){db.dropTable("usedWords")}
//
//    fun selectRandomWord(db: SQLiteDatabase, wordLength: Int): String{
//        var resultWord = ""
//        while(resultWord == "") {
//            //Рандомный ид в диапазоне
//            var r = 49+(Math.random() * 4159355).toInt()
////            Log.d("id", r.toString())
//            db.select("nouns", "word")
//                    .where("_id = {randomId}",
//                            "randomId" to r)
//                    .exec { if(count!=0)
//                        parseList(rowParser {
//                            word: String ->
//                            if(word.length == wordLength)
//                                resultWord = word
//                        })
//                    }
//        }
//        return resultWord
//    }
//
//
//}
//
//// Access property for Context
//val Context.database: MyDatabaseOpenHelper
//    get() = MyDatabaseOpenHelper.getInstance(getApplicationContext())


import android.database.SQLException
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.content.Context
import android.util.Log

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

internal class DatabaseHelper(private val myContext: Context) : SQLiteOpenHelper(myContext, DatabaseHelper.DB_NAME, null, DatabaseHelper.SCHEMA) {

    init {
        DB_PATH = myContext.filesDir.path + DB_NAME
    }

    override fun onCreate(db: SQLiteDatabase) {}
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun create_db() {
        var myInput: InputStream? = null
        var myOutput: OutputStream? = null
        try {
            val file = File(DB_PATH)
            if (!file.exists()) {
                this.readableDatabase
                //получаем локальную бд как поток
                myInput = myContext.assets.open(DB_NAME)
                // Путь к новой бд
                val outFileName = DB_PATH

                // Открываем пустую бд
                myOutput = FileOutputStream(outFileName)

                // побайтово копируем данные
                val buffer = ByteArray(1024)
                var length: Int
                while ((myInput!!.read( buffer)) > 0) {
//                    myInput.read(buffer)
                    myOutput!!.write(buffer)
                }

                myOutput!!.flush()
                myOutput.close()
                myInput!!.close()
            }
        } catch (ex: IOException) {
            Log.d("DatabaseHelper", ex.message)
        }

    }

    @Throws(SQLException::class)
    fun open(): SQLiteDatabase {

        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE)
    }

    companion object {
        private var DB_PATH: String = "" // полный путь к базе данных
        private val DB_NAME = "wordGame.db"
        private val SCHEMA = 1 // версия базы данных
        val TABLE = "users" // название таблицы в бд
        // названия столбцов
        val COLUMN_ID = "_id"
        val COLUMN_NAME = "name"
        val COLUMN_YEAR = "year"
    }
}

