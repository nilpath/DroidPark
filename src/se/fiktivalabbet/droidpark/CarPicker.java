package se.fiktivalabbet.droidpark;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import se.fiktivalabbet.droidpark.CarPickerAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class CarPicker extends Activity
{
	private GridView grid;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.carpicker);
		
		grid = (GridView) findViewById(R.id.carPicker);
		grid.setAdapter(new CarPickerAdapter(this));
		
		grid.setOnItemClickListener(new OnItemClickListener()
		{

			public void onItemClick(AdapterView<?> adapter, View v, int position,
					long id)
			{
				setResult(RESULT_OK, new Intent().putExtra("carBrand", v.getTag().toString()));
				finish();
			}
		});
		
	}

}
