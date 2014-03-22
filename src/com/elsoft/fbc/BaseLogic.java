package com.elsoft.fbc;

import java.math.BigInteger;

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
		this.value = new BigInteger(value, getBaseValue(baseIndex));
	}
	
	public String getValue(int baseIndex) {
		return value.toString(this.getBaseValue(baseIndex));
	}
	
	// Get base value from base index
	private int getBaseValue(int baseIndex) {
		return mContext.getResources().getIntArray(R.array.bases)[baseIndex];
	}
}
