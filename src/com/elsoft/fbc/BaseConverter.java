package com.elsoft.fbc;

import com.elsoft.fbc.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Base converter, main activity
 * @author mikeytr@outlook.com
 */

public class BaseConverter extends Activity {

	private BaseLogic logic;
	private Spinner toSpinner;
	private Spinner fromSpinner;
	private TextView fromText;
	private TextView toText;
	private int fromBase;
	private int toBase;

	private String hex = "ABCDEF0123456789";
	private	String dec = "0123456789";
	private	String oct = "01234567";
	private String bin = "01";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.initUI();
		logic = new BaseLogic();
		GridView gridView = (GridView) this.findViewById(R.id.gridview1);
		gridView.setAdapter(new ButtonAdapter(gridView, this, null));
		gridView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
				// long click on "delete" button
				String[] buttons = getApplicationContext().getResources().getStringArray(R.array.buttons);
				if (buttons[position].equalsIgnoreCase(
						getApplicationContext().getResources().getString(R.string.button_delete)))
				{
					clearFromDisplay();
					updateLogic();
					updateToDisplay();
				}
				return false;
			}
			
		});
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				String[] buttonCodes = getApplicationContext().getResources().getStringArray(R.array.buttons);
				// normal digit/letter button (are all a single character long)
				if (buttonCodes[position].length() == 1) {
					writeDigit(getResources().getStringArray(R.array.buttons)[position]);
					updateLogic();
					updateToDisplay();
				} 
				// delete button
				else if (buttonCodes[position].equalsIgnoreCase(
						getApplicationContext().getResources().getString(R.string.button_delete))) 
				{
					removeLastDigit();
					updateLogic();
					updateToDisplay();
				} 
				// clear button
				else if (buttonCodes[position].equalsIgnoreCase(
						getApplicationContext().getResources().getString(R.string.button_clear))) {
					clearFromDisplay();
					updateLogic();
					updateToDisplay();
				}
			}
		});
	}
	
	private void initUI() {
		setContentView(R.layout.main);
		fromSpinner = (Spinner) this.findViewById(R.id.spinnerbasefrom);
		toSpinner = (Spinner) this.findViewById(R.id.spinnerbaseto);
		fromText = (TextView) this.findViewById(R.id.textviewfrom);
		toText = (TextView) this.findViewById(R.id.textviewto);
		
		this.setDefaultValues();
		
		// Listener for setting from-base
		fromSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int item, long longvalue) {
				fromBase = getApplicationContext().getResources().getIntArray(
						R.array.bases)[item];
				updateFromDisplay();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// do nothing
			}
		});
		
		// Listener for setting to-base
		toSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int item, long longvalue) {
				toBase = getApplicationContext().getResources().getIntArray(
						R.array.bases)[item];
				updateToDisplay();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// do nothing
			}
		});
	}
	
	private void writeDigit(String digit) {
		if (fromBase == 2 && bin.contains(digit) ||
			fromBase == 8 && oct.contains(digit) ||
			fromBase == 10 && dec.contains(digit) ||
			fromBase == 16 && hex.contains(digit) 
			)
		{
			if (getDisplayString().equals("0")) {
				fromText.setText(digit);
			} else {
				String newValue = getDisplayString().concat(digit);
				try {
					Long.parseLong(newValue, fromBase);
					fromText.setText(newValue);
				}
				catch (java.lang.NumberFormatException exception) {
					Toast.makeText(getApplicationContext(), this.getApplicationContext().
							getResources().getString(R.string.high_number), Toast.LENGTH_SHORT).show();	
				}
			}
		}
	}	
	
	private void removeLastDigit() {
		int length = fromText.getText().length();
		if (length > 1) {
			fromText.setText(fromText.getText().subSequence(0, length-1));
		} else clearFromDisplay();
	}
	
	private void updateLogic() {
		logic.setValue(getDisplayString(), fromBase);
	}
	
	private void clearFromDisplay() {
		fromText.setText("0");
	}
	
	private String getDisplayString() {
		return fromText.getText().toString();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.base_converter, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        // About menu button
	    	case R.id.action_about:
	        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        	builder.setMessage(this.getString(R.string.contact_data));
	        	builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                    dialog.dismiss();
	                }
	                });
	        	builder.create().show();
	         	return true;
	    }
		return false;
	}

	private void setDefaultValues() {
		// Set spinner values and base values
		fromSpinner.setSelection(2);
		fromBase = getApplicationContext().getResources().getIntArray(
				R.array.bases)[2];
		toSpinner.setSelection(0);
		toBase = getApplicationContext().getResources().getIntArray(
				R.array.bases)[0];
		
		fromText.setText("0");
		toText.setText("0");
	}
	
	private void scrollRight() {
		HorizontalScrollView fromTextScroll = (HorizontalScrollView) this.findViewById(R.id.scroll1);
		HorizontalScrollView toTextScroll = (HorizontalScrollView) this.findViewById(R.id.scroll2);
		fromTextScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
		toTextScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
	}
	
	private void updateFromDisplay() {
		fromText.setText(logic.getValue(fromBase));
		scrollRight();
	}
	
	private void updateToDisplay() {
		toText.setText(logic.getValue(toBase));
		scrollRight();
	}	
}