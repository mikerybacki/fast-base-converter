package com.emergy.fbc;

import java.math.BigInteger;
import java.util.Locale;

import com.emergy.fbc.R;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * Base logic, keeping track of the converted value
 * @author mikeytr@outlook.com
 */

public class BaseLogic {

	private BigInteger value;
	private Context mContext;
	
	public BaseLogic(Context context) { 
		value = BigInteger.valueOf(0);
		mContext = context;
	}
	
	public void setValue(String value, int baseIndex) {
		try {
			this.value = new BigInteger(value, getBaseValue(baseIndex));
		}
		catch (NumberFormatException e) {
			// do nothing, result of user action?
		}
	}
	
	@SuppressLint("DefaultLocale")
	public String getValue(int baseIndex) {
		return value.toString(this.getBaseValue(baseIndex)).toUpperCase(Locale.US);
	}
	
	// Get base value from base index
	private int getBaseValue(int baseIndex) {
		return mContext.getResources().getIntArray(R.array.bases)[baseIndex];
	}
}
