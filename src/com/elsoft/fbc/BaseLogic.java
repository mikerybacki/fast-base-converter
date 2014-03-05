package com.elsoft.fbc;

import java.util.Locale;

import android.content.Context;

/**
 * Base logic, keeping track of the converted value
 * @author mikeytr@outlook.com
 */

public class BaseLogic {

	private long val;
	private Context mContext;
	
	public BaseLogic(Context context) { 
		val = 0;
		mContext = context;
	}
	
	public void setValue(long value) {
		val = value;
	}
	
	public void setValue(String value, int baseIndex) {
		val = Long.parseLong(value, getBaseValue(baseIndex));		
	}
	
	public String getValue(int baseIndex) {
		String value = "0";
		switch (getBaseValue(baseIndex)) {
			case 2: value = Long.toBinaryString(val);
			break;
			case 8: value = Long.toOctalString(val);
			break;
			case 10: value = Long.toString(val);
			break;
			case 16: value = Long.toHexString(val).toUpperCase(Locale.getDefault());
			break;
		}
		return value;
	}
	
	// Get base value from base index
	private int getBaseValue(int baseIndex) {
		return mContext.getResources().getIntArray(R.array.bases)[baseIndex];
	}
}
