package se.fiktivalabbet.droidpark;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Prefs extends PreferenceActivity
{
	private static final String OPT_DUTY_NO = "Tjänst";
	private static final String OPT_DUTY_DEFAULT = "";
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}
	
	public static String getDutyNo(Context ctx)
	{
		return PreferenceManager.getDefaultSharedPreferences(ctx).getString(OPT_DUTY_NO, OPT_DUTY_DEFAULT);
	}
}
