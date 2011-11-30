package se.fiktivalabbet.droidpark.carpicker;


import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class CarPickerView extends LinearLayout
{
	private Context ctx;
	private EditText carInput;
	private Button goBtn;
	
	public static final int CAR_PICKER_ID = 99;

	public CarPickerView(Context context)
	{
		super(context);
		this.ctx = context;
		initLayout();
	}
	
	public CarPickerView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.ctx = context;
		initLayout();
	}

	private void initLayout()
	{
		setOrientation(HORIZONTAL);
		
		carInput = new EditText(ctx);
		
		goBtn = new Button(ctx);
		goBtn.setText("VŠlj");
		
		this.addView(carInput, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 0.15f));
		this.addView(goBtn, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 0.85f));
	}
	
	public void setText(String s)
	{
		carInput.setText(s);
	}
	
	public Editable getText()
	{
		return carInput.getText();
	}

	public void setChooseOnClickListener(OnClickListener i)
	{
		goBtn.setOnClickListener(i);
		goBtn.setId(CAR_PICKER_ID);
	}

}
