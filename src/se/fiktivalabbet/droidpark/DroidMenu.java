package se.fiktivalabbet.droidpark;

import se.fiktivalabbet.droidpark.database.Constants;
import se.fiktivalabbet.droidpark.database.ReportsDataBase;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DroidMenu extends Activity implements OnClickListener {
	
	private Button report;
	private Button latestBtn;
	private ReportsDataBase reportsDB;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        reportsDB = new ReportsDataBase(this);
        
        report = (Button) findViewById(R.id.reportBtn);
        report.setOnClickListener(this);
        
        latestBtn = (Button) findViewById(R.id.latestReportBtn);
        latestBtn.setOnClickListener(this);
        
    }

	public void onClick(View v)
	{
		Log.v("onclickMenu", "clickRegistered");
		Intent i;
		switch(v.getId())
		{
		case R.id.reportBtn:
			Log.v("onclickMenu", "clickReport");
			i = new Intent(this, Report.class);
			startActivity(i);
			break;
		case R.id.latestReportBtn:
			displayLatestReport();
			break;
		}
		
	}

	private void displayLatestReport()
	{
		//android.provider.BaseColumns._ID
		String[] from = { Constants.AREA, Constants.DATE, Constants.START_TIME, Constants.END_TIME,
				Constants.REG_NUM, Constants.CAR_BRAND, Constants.CAR_MODEL, Constants.YEAR_MODEL,
				Constants.ERROR_CODE, Constants.FEE, Constants.DUTY_NUM};
		String orderBy = android.provider.BaseColumns._ID + " DESC";
		
		StringBuilder sb = new StringBuilder("Senaste inrapporterade överträdelse: \n");
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		SQLiteDatabase db = reportsDB.getReadableDatabase();
		Cursor c = db.query(Constants.TABLE_NAME, from, null, null, null, null, orderBy);
		c.moveToFirst();
		sb.append("Bilmärke: " + c.getString(5));
		sb.append("\nP-område: " + c.getString(0));
		sb.append("\nDatum: " + c.getString(1));
		sb.append("\nStart tid: " + c.getString(2));
		sb.append("\nSlut tid: " + c.getString(3));
		sb.append("\nRegistreringsnummer: " + c.getString(4));
		sb.append("\nBilmodell: " + c.getString(6));
		sb.append("\nÅrsmodell: " + c.getInt(7));
		sb.append("\nÖverträdelsepunkt: " + c.getInt(8));
		sb.append("\nBötes summa: " + c.getInt(9));
		sb.append("\nTjänstgöringsnummer: " + c.getInt(10));
		
		builder.setMessage(sb);
		AlertDialog alert = builder.create();
		alert.show();
	}
}