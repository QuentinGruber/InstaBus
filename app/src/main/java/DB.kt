import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.jar.Attributes

class DB(context: Context,
         factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME,
        factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_PRODUCTS_TABLE = ("CREATE TABLE " +
                TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                "title TEXT, " +
                "stationName TEXT," +
                "imagePath TEXT)"
                )

        Log.d("ici",CREATE_PRODUCTS_TABLE)
        db.execSQL(CREATE_PRODUCTS_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }
    fun addStationPicture(stationPicture: StationPhoto) {
        val values = ContentValues()
        values.put("title", stationPicture.title)
        values.put("stationName", stationPicture.stationName)
        values.put("imagePath", stationPicture.imagePath)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    fun getStationPictures(stationName:String): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME WHERE stationName = '$stationName'", null)
    }
    fun getAllStationPictures(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "instabus.db"
        val TABLE_NAME = "stationPicture"
        val COLUMN_ID = "_id"
    }
}