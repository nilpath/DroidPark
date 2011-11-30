package se.fiktivalabbet.droidpark.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import se.fiktivalabbet.droidpark.database.Constants;

public class ReportsDataBase extends SQLiteOpenHelper
{
	
	public ReportsDataBase(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public static final String DATABASE_NAME = "reports.db";
	public static final int DATABASE_VERSION = 1;

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL("CREATE TABLE " + Constants.TABLE_NAME + " (" +
				android.provider.BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				Constants.AREA + " TEXT, " +
				Constants.DATE + " TEXT, " +
				Constants.START_TIME + " TEXT, " +
				Constants.END_TIME + " TEXT, " +
				Constants.REG_NUM + " TEXT, " +
				Constants.CAR_BRAND + " TEXT, " +
				Constants.CAR_MODEL + " TEXT, " +
				Constants.YEAR_MODEL + " INTEGER, " +
				Constants.ERROR_CODE + " INTEGER, " +
				Constants.FEE + " INTEGER, " +
				Constants.DUTY_NUM + " INTEGER);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
		onCreate(db);	
	}
}
