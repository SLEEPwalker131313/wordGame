package tryanko.test.com.wordgame

import android.content.Context
import android.content.IntentSender
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import org.jetbrains.anko.MAXDPI
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "MyDatabase", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }
    override fun onCreate(db: SQLiteDatabase) {

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }
    fun createGameTable(db: SQLiteDatabase){
        db.createTable("games", true,
                "_id" to INTEGER + PRIMARY_KEY + UNIQUE,
                "player1" to TEXT,
                "player2" to TEXT,
                "fieldSize" to INTEGER,
                "startWord" to TEXT,
                "isFinished" to INTEGER)
    }
    fun insertIntoGameTable(db: SQLiteDatabase, player1: String, player2: String,
                            fieldSize: Int, startWord: String, isFinished: Int){
        db.insert("games",
                "player1" to player1,
                "player2" to player2,
                "fieldSize" to fieldSize,
                "startWord" to startWord,
                "isFinished" to isFinished)
    }
    fun selectFromGameTable(db: SQLiteDatabase){
        db.select("games").exec {  }
    }
    fun dropGameTable(db: SQLiteDatabase){db.dropTable("games")}
    fun createDetailTable(db: SQLiteDatabase){
        db.createTable("detail", true,
                "_step" to INTEGER + PRIMARY_KEY + UNIQUE,
                "game_id" to INTEGER,
                "playerTurn" to INTEGER,
                "newLiter" to TEXT,
                "newLiterXCoord" to INTEGER,
                "newLiterYCoord" to INTEGER
                )
        FOREIGN_KEY("game_id", "games", "_id")
    }
    fun dropDetailTable(db: SQLiteDatabase){db.dropTable("detail")}

    fun insertIntoTable(db: SQLiteDatabase) {
    }

}

// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(getApplicationContext())