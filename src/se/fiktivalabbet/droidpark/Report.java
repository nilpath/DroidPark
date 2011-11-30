package se.fiktivalabbet.droidpark;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

import se.fiktivalabbet.droidpark.carpicker.CarPickerView;
import se.fiktivalabbet.droidpark.database.Constants;
import se.fiktivalabbet.droidpark.database.ReportsDataBase;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class Report extends Activity implements OnClickListener
{
	private Spinner errorCode;
	private Button dateBtn;
	private int dateYear;
	private int dateMonth;
	private int dateDay;
	private int timeHour;
	private int timeMin;
	private Button timeStartBtn;
	private Button timeEndBtn;
	private final String BASE_URL = "http://www.programvaruteknik.nu/dt031g/droid-park/get.php?regnr=";
	private EditText regNum;
	private EditText dutyNo;
	private CarPickerView carBrand;
	private EditText place;
	private Button mapBtn;
	private ReportsDataBase reportsDB;

	
	static final int DATE_DIALOG_ID = 0;
	static final int TIME_START_DIALOG_ID = 1;
	static final int TIME_END_DIALOG_ID = 2;
	
	static final int CARBRAND_REQUEST_CODE = 0;
	static final int GET_LONG_LAT = 11;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.report);
	        
	        reportsDB = new ReportsDataBase(this);
	        
	        
	        final Calendar c = Calendar.getInstance();
	        dateYear = c.get(Calendar.YEAR);
	        dateMonth = c.get(Calendar.MONTH);
	        dateDay = c.get(Calendar.DAY_OF_MONTH);
	        timeHour = c.get(Calendar.HOUR_OF_DAY);
	        timeMin = c.get(Calendar.MINUTE);
	        
	        errorCode = (Spinner) findViewById(R.id.errorCodeSpinner);
	        ArrayAdapter<CharSequence> a = ArrayAdapter.createFromResource(this, R.array.errorCode_array, android.R.layout.simple_spinner_item);
	        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			errorCode.setAdapter(a);
			
			place = (EditText) findViewById(R.id.adressInput);
			mapBtn = (Button) findViewById(R.id.mapBtn);
			mapBtn.setOnClickListener(this);
			
			dateBtn = (Button) findViewById(R.id.dateBtn);
			dateBtn.setOnClickListener(this);
			
			timeStartBtn = (Button) findViewById(R.id.timeStartBtn);
			timeStartBtn.setOnClickListener(this);
			
			timeEndBtn = (Button) findViewById(R.id.timeEndBtn);
			timeEndBtn.setOnClickListener(this);
			
			regNum = (EditText) findViewById(R.id.regNumInput);
			regNum.setOnFocusChangeListener(new OnFocusChangeListener()
			{
				public void onFocusChange(View v, boolean hasFocus)
				{
					Log.v("onFocus", "enterFocusEvent");
					switch(v.getId())
					{
					case R.id.regNumInput:
						if(!hasFocus)
						{
							Log.v("onFocus", "lostFocus");
							URL u;
							URLConnection uc;
							String line;
							String[] splitLine;
							try
							{
								u = new URL(BASE_URL+regNum.getText().toString().toLowerCase());
								uc = u.openConnection();
								BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
								line = in.readLine();
								if(line != null)
								{
									splitLine = line.split("<br>");
									((CarPickerView)findViewById(R.id.carTypeInput)).setText(splitLine[1]);
									((EditText)findViewById(R.id.carModelInput)).setText(splitLine[2]);
									((EditText)findViewById(R.id.yearMakeInput)).setText(splitLine[3]);
								}
							} catch (MalformedURLException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					}
				}
			});
			
			carBrand = (CarPickerView) findViewById(R.id.carTypeInput);
			carBrand.setChooseOnClickListener(this);
			
			dutyNo = (EditText) findViewById(R.id.dutyNumInput);
			if(!Prefs.getDutyNo(this).equals(""))
			{
				dutyNo.setText(Prefs.getDutyNo(this));
			}
			
			View sendForm = findViewById(R.id.sendFormBtn);
			sendForm.setOnClickListener(this);   
	    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.settings_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.clearForm:
			clearForm();
			break;
		case R.id.settings:
			startActivity(new Intent(this, Prefs.class));
		}
		return false;
	}
	 
	private void clearForm()
	{
		((EditText)findViewById(R.id.adressInput)).setText("");
		((Button)findViewById(R.id.dateBtn)).setText("Datum då dådet inträffade");
		((Button)findViewById(R.id.timeStartBtn)).setText("Starttid för övervakning");
		((Button)findViewById(R.id.timeEndBtn)).setText("Sluttid för övervakning");
		((EditText)findViewById(R.id.regNumInput)).setText("");
		((CarPickerView)findViewById(R.id.carTypeInput)).setText("");
		((EditText)findViewById(R.id.yearMakeInput)).setText("");
		((EditText)findViewById(R.id.carModelInput)).setText("");
		((EditText)findViewById(R.id.feeInput)).setText("");
		((EditText)findViewById(R.id.dutyNumInput)).setText("");
		((Spinner)findViewById(R.id.errorCodeSpinner)).setSelection(0);
	}

	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.dateBtn:
			showDialog(DATE_DIALOG_ID);
			break;
		case R.id.timeStartBtn:
			showDialog(TIME_START_DIALOG_ID);
			break;
		case R.id.timeEndBtn:
			showDialog(TIME_END_DIALOG_ID);
			break;
		case R.id.sendFormBtn:
			handleForm();
			break;
		case CarPickerView.CAR_PICKER_ID:
			Log.v("onClick", "CarPicker");
			Intent i = new Intent(this, CarPicker.class);
			startActivityForResult(i, CARBRAND_REQUEST_CODE);
			break;
		case R.id.mapBtn:
			Intent intent = new Intent(this, MapPicker.class);
			startActivityForResult(intent, GET_LONG_LAT);
			break;
		}	
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		Log.v("requestCode", String.valueOf(requestCode));
		//Log.v("requestCode", String.valueOf(requestCode));
		if(requestCode == CARBRAND_REQUEST_CODE)
		{
			if(resultCode == RESULT_OK)
			{
				String brand = data.getStringExtra("carBrand");
				if(brand != null)
					carBrand.setText(brand);
			}
		}
		else if(requestCode == GET_LONG_LAT)
		{
			Log.v("resultCode", String.valueOf(resultCode));
			Log.v("Result_ok", String.valueOf(RESULT_OK));
			if(resultCode == RESULT_OK)
			{
				String pos = data.getStringExtra("longLat");
				if(pos != null)
					Log.v("ActivityResult", pos);
					place.setText("["+pos+"]");
			}
		}
	}
	
	private void handleForm()
	{
		View v;
		boolean isFilledInCorrectly = true;
		SQLiteDatabase db = reportsDB.getWritableDatabase();
		ContentValues cv = new ContentValues();
		Log.v("handleForm", "Enter");
		
		v = findViewById(R.id.carTypeInput);
		if(((CarPickerView) v).getText().toString().equals(""))
			isFilledInCorrectly = false;
		cv.put(Constants.CAR_BRAND, ((CarPickerView) v).getText().toString());
		
		v = findViewById(R.id.adressInput);
		if(((EditText) v).getText().toString().equals(""))
			isFilledInCorrectly = false;
		cv.put(Constants.AREA, ((EditText) v).getText().toString());
		
		v = findViewById(R.id.dateBtn);
		if(((Button) v).getText().toString().contains("Datum"))
			isFilledInCorrectly = false;
		cv.put(Constants.DATE, ((Button) v).getText().toString());
		
		v = findViewById(R.id.timeStartBtn);
		if(((Button) v).getText().toString().contains("Starttid"))
			isFilledInCorrectly = false;
		cv.put(Constants.START_TIME, ((Button) v).getText().toString());
		
		v = findViewById(R.id.timeEndBtn);
		if(((Button) v).getText().toString().contains("Sluttid"))
			isFilledInCorrectly = false;
		cv.put(Constants.END_TIME, ((Button) v).getText().toString());
		
		v = findViewById(R.id.regNumInput);
		if(((EditText) v).getText().toString() == "")
			isFilledInCorrectly = false;
		cv.put(Constants.REG_NUM, ((EditText) v).getText().toString());
		
		v = findViewById(R.id.carModelInput);
		cv.put(Constants.CAR_MODEL, ((EditText) v).getText().toString());
		
		v = findViewById(R.id.yearMakeInput);
		cv.put(Constants.YEAR_MODEL, ((EditText) v).getText().toString());
		
		v = findViewById(R.id.errorCodeSpinner);
		cv.put(Constants.ERROR_CODE, ((Spinner) v).getSelectedItem().toString());
		
		v = findViewById(R.id.feeInput);
		if(((EditText) v).getText().toString().equals(""))
			isFilledInCorrectly = false;
		cv.put(Constants.FEE, ((EditText) v).getText().toString());
		
		v = findViewById(R.id.dutyNumInput);
		if(((EditText) v).getText().toString().equals(""))
			isFilledInCorrectly = false;
		cv.put(Constants.DUTY_NUM, ((EditText) v).getText().toString());
		
		if(isFilledInCorrectly)
		{
			try
			{
				db.insertOrThrow(Constants.TABLE_NAME, null, cv);
				Toast toast = Toast.makeText(this, "Registrerat i Databasen", Toast.LENGTH_SHORT);
				toast.show();
			}
			finally
			{
				reportsDB.close();
			}
		}
		else
		{
			Toast toast = Toast.makeText(this, "Du har inte fyllt i alla nödvänliga fällt", Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener()
	{
		
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth)
		{
			dateYear = year;
			dateMonth = monthOfYear;
			dateDay = dayOfMonth;
			displayDate();
			
		}

		private void displayDate()
		{
			dateBtn.setText(new StringBuilder().append(dateYear).append("-").append(dateMonth+1).append("-").append(dateDay));
		}
	};
	
	private TimePickerDialog.OnTimeSetListener timeStartListener = new TimePickerDialog.OnTimeSetListener()
	{
		
		public void onTimeSet(TimePicker view, int hourOfDay, int minute)
		{
			timeHour = hourOfDay;
			timeMin = minute;
			displayTime();
			
		}

		private void displayTime()
		{
			timeStartBtn.setText(new StringBuilder().append(timeHour).append(":").append(timeMin));
		}
	};
	
	private TimePickerDialog.OnTimeSetListener timeEndListener = new TimePickerDialog.OnTimeSetListener()
	{
		
		public void onTimeSet(TimePicker view, int hourOfDay, int minute)
		{
			timeHour = hourOfDay;
			timeMin = minute;
			displayTime();
			
		}

		private void displayTime()
		{
			timeEndBtn.setText(new StringBuilder().append(timeHour).append(":").append(timeMin));
		}
	};
	
	
	
	@Override
	protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_DIALOG_ID:
            return new DatePickerDialog(this,
                        datePickerListener,
                        dateYear, dateMonth, dateDay);
        case TIME_START_DIALOG_ID:
        	return new TimePickerDialog(this, timeStartListener, timeHour, timeMin, true);
        case TIME_END_DIALOG_ID:
        	return new TimePickerDialog(this, timeEndListener, timeHour+1, timeMin, true);
        }
        return null;
    }
	
	@Override
	public void onResume()
	{
		dutyNo.setText(Prefs.getDutyNo(this));
		super.onResume();
	}
	
}
