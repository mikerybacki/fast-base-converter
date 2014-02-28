package com.elsoft.fbc;

import java.util.Locale;

/**
 * Base logic, keeping track of the converted value
 * @author mikeytr@outlook.com
 */

public class BaseLogic {

	private long val;
	
	public BaseLogic() { 
		val = 0;
	}
	
	public void setValue(long value) {
		val = value;
	}
	
	public void setValue(String value, int base) {
		val = Long.parseLong(value, base);		
	}
	
	public String getValue(int base) {
		String value = "0";
		switch (base) {
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
}
