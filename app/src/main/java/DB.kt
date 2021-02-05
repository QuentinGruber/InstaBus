import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

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

        db.execSQL(CREATE_PRODUCTS_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
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
        val stationNameWithoutQuote = stationName.replace("'", "''")
        return db.rawQuery("SELECT * FROM $TABLE_NAME WHERE stationName = '$stationNameWithoutQuote'", null)
    }

    fun deleteStationPicture(imagePath:String): Boolean {
        val db = this.writableDatabase
        return try {
            db.delete(TABLE_NAME,"imagePath = '$imagePath'",null)
            true
        }
        catch (e: Throwable){
            false
        }
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "instabus.db"
        const val TABLE_NAME = "stationPicture"
        const val COLUMN_ID = "_id"
    }
}