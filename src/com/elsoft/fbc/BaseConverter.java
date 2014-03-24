package com.elsoft.fbc;

import java.util.Arrays;

import com.elsoft.fbc.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
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
	private HorizontalScrollView fromTextScroll; 
	private HorizontalScrollView toTextScroll;
	private int fromBase;
	private int toBase;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.initUI();
		logic = new BaseLogic(this.getApplicationContext());
		GridView gridView = (GridView) this.findViewById(R.id.gridview1);
		gridView.setAdapter(new ButtonAdapter(gridView, this, null));
		gridView.setOnItemLongClickListener(new OnItemLongClickListener() {
			/**
			 * Click listener for long clicks
			 */
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
				// long click on "delete" button
				String[] buttons = getApplicationContext().getResources().getStringArray(R.array.buttons);
				if (buttons[position].equalsIgnoreCase(
						getApplicationContext().getResources().getString(R.string.button_delete)))
				{
					clearFromDisplay();
					updateLogic();
					updateToString();
				}
				return false;
			}
			
		});
		gridView.setOnItemClickListener(new OnItemClickListener() {
			/**
			 * Click listener for Grid View
			 */
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				String[] buttonCodes = getApplicationContext().getResources().getStringArray(R.array.buttons);
				// normal digit/letter button (are all a single character long)
				if (buttonCodes[position].length() == 1) {
					writeDigit(getResources().getStringArray(R.array.buttons)[position]);
					updateLogic();
					updateToString();
					scrollRight();
				} 
				// delete button
				else if (buttonCodes[position].equalsIgnoreCase(
						getApplicationContext().getResources().getString(R.string.button_delete))) 
				{
					removeLastDigit();
					updateLogic();
					updateToString();
					scrollRight();
				} 
				// clear button
				else if (buttonCodes[position].equalsIgnoreCase(
						getApplicationContext().getResources().getString(R.string.button_clear))) {
					clearFromDisplay();
					updateLogic();
					updateToString();
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
		fromTextScroll = (HorizontalScrollView) this.findViewById(R.id.scroll1);
		toTextScroll = (HorizontalScrollView) this.findViewById(R.id.scroll2);
		
		this.setDefaultValues();
		
		fromSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			/**
			 * Click listener for From-Spinner
			 */
			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int item, long longvalue) {
				fromBase = item;
				setFromString(logic.getValue(toBase));
				disableNonbaseButtons();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// do nothing
			}
		});
		
		toSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			/**
			 * Click listener for To-Spinner
			 */
			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int item, long longvalue) {
				toBase = item;
				updateToString();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// do nothing
			}
		});
	}
	
	private void writeDigit(String digit) {
		if (getFromString().equals("0")) {
			setFromString(digit);
		} else {
			String newValue = getFromString().concat(digit);
			if (newValue.length() > 255) {
				Toast.makeText(getApplicationContext(), this.getApplicationContext().
						getResources().getString(R.string.high_number), Toast.LENGTH_SHORT).show();
			} else {
				setFromString(newValue);
			}
		}
	}
	
	private void removeLastDigit() {
		int length = fromText.getText().length();
		if (length > 1) {
			setFromString(fromText.getText().subSequence(0, length-1).toString());
		} else clearFromDisplay();
	}
	
	private void updateLogic() {
		logic.setValue(getFromString(), fromBase);
	}
	
	private void clearFromDisplay() {
		setFromString("0");
	}
	
	private String getFromString() {
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
		// decimal
		fromBase = 2;
		toSpinner.setSelection(0);
		// binary
		toBase = 0;
		
		setFromString("0");
		toText.setText("0");
	}
	
	// Enable only buttons for a certain base 
	private void disableNonbaseButtons() {
		GridView gridView = (GridView) findViewById(R.id.gridview1);
		ButtonAdapter adapter = (ButtonAdapter) gridView.getAdapter();
		
		Resources resourceGetter = getApplicationContext().getResources();
		
		String[] buttons = resourceGetter.getStringArray(R.array.buttons);
		String[] basechars = resourceGetter.getStringArray(R.array.basechars);
		String[] specialbuttons = { resourceGetter.getString(R.string.button_clear),
				resourceGetter.getString(R.string.button_delete) };
				
		for(int index = 0; index < buttons.length ; index++) {
			if (basechars[fromBase].contains(buttons[index]) || 
				Arrays.asList(specialbuttons).contains(buttons[index])) {
					adapter.setButtonEnabled(index, true);
				} else
				{
					adapter.setButtonEnabled(index, false);
				}
			}
	}
	
	private void scrollRight() {
		fromTextScroll.postDelayed(new Runnable(){
			@Override
			public void run() {
				fromTextScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
			}	
		}, 50L);
		toTextScroll.postDelayed(new Runnable(){
			@Override
			public void run() {
				toTextScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
			}	
		}, 50L);
		
	}
	
	private void setFromString(String textToDisplay) {
		fromText.setText(textToDisplay);
	}
	
	private void updateToString() {
		toText.setText(logic.getValue(toBase));
	}	
}