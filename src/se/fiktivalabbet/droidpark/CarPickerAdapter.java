package se.fiktivalabbet.droidpark;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class CarPickerAdapter extends BaseAdapter
{
	private Context ctx;
	private Integer[] carIds = {
			  R.drawable.alfa_romeo, R.drawable.aston_martin, R.drawable.audi,
			  R.drawable.bmw, R.drawable.cadillac, R.drawable.chevrolet, 
			  R.drawable.chrysler, R.drawable.citroen, R.drawable.ferrari,
			  R.drawable.fiat, R.drawable.ford, R.drawable.honda,
			  R.drawable.hyundai, R.drawable.jaguar, R.drawable.kia,
			  R.drawable.lamborghini, R.drawable.lancia, R.drawable.lexus,
			  R.drawable.mazda, R.drawable.mitsubishi, R.drawable.nissan,
			  R.drawable.opel, R.drawable.peugeot, R.drawable.porsche,
			  R.drawable.saab, R.drawable.skoda, R.drawable.suzuki,
			  R.drawable.toyota, R.drawable.volkswagen, R.drawable.volvo
			};
	private String[] carNames = {
			  "Alfa Romeo", "Aston Martin", "Audi", "BMW", "Cadillac", 
			  "Chevrolet", "Chrysler", "Citroen", "Ferrari", "Fiat",
			  "Ford", "Honda", "Hyundai", "Jaguar", "Kia", 
			  "Lamborghini", "Lancia", "Lexus", "Mazda", "Mitsubishi", 
			  "Nissan", "Opel", "Peugeot", "Porche", "Saab", 
			  "Skoda", "Suzuki", "Toyota", "Volkswagen", "Volvo"
			};

	public CarPickerAdapter(Context context)
	{
		this.ctx = context;
	}
	
	public int getCount()
	{
		return carNames.length;
	}

	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		ImageView iv;
		if( convertView == null)
		{
			iv = new ImageView(this.ctx);
			iv.setLayoutParams(new GridView.LayoutParams(90,90));
			iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
			iv.setPadding(10, 10, 10, 10);
		}
		else
		{
			iv = (ImageView) convertView;
		}
		
		iv.setImageResource(carIds[position]);
		iv.setTag(carNames[position]);
		return iv;
	}

}
