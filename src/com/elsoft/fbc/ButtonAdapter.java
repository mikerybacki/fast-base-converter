package com.elsoft.fbc;

import java.util.ArrayList;

import com.elsoft.fbc.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;

/**
 * Custom button adapter for button grid
 * @author mikeytr@outlook.com
 */

public class ButtonAdapter extends BaseAdapter {

	public String[] buttonText;
		
	private Context mContext;
	private GridView gridView;
	
	public ButtonAdapter(GridView gridView, Context context, ArrayList<String> list) {
		this.gridView = gridView;
		mContext = context;
		buttonText = mContext.getResources().getStringArray(R.array.buttons);
	}
	
	@Override
	public int getCount() {
		return buttonText.length;
	}

	@Override
	public String getItem(int pos) {
		return buttonText[pos];
	}
	
	public void setButtonEnabled(int pos, boolean enabled) {
		View convertView = gridView.getChildAt(pos);
		convertView.setEnabled(enabled);
		Button textButton = (Button) convertView.findViewById(R.id.textInside);
		textButton.setEnabled(enabled);
		if (enabled) {
		textButton.setTextColor(mContext.getResources().getColor(
				R.color.button_text));
		} else {
		textButton.setTextColor(mContext.getResources().getColor(
				R.color.button_disabled_text));
		}
	}

	@Override
	public boolean areAllItemsEnabled()
	{
	    return false;
	}

	@Override
	public boolean isEnabled(int position)
	{
		return (gridView.getChildAt(position).isEnabled());
	}

	@Override
	public long getItemId(int pos) {
		return 0;
	}

	// Apply a selector/drawable as a background for the button
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	private void setButtonSelector(View button, Drawable selector) {
		if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			button.setBackgroundDrawable(selector);
		} else {
			button.setBackground(selector);
		}
	}
	
	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		Resources resourceGetter = mContext.getResources();
		String[] buttonCodes = resourceGetter.getStringArray(R.array.buttons);
		
		// inflate the view
		convertView = LayoutInflater.from(mContext).inflate(R.layout.button, null);

		// set height for each button to 1/5th of gridview's height and exclude horizontal spacing
		int buttonHeightPx = gridView.getHeight()/5 -  
				(resourceGetter.getDimensionPixelSize(R.dimen.gridview_verticalspacing));
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(android.view.ViewGroup.
				LayoutParams.MATCH_PARENT, buttonHeightPx);
		convertView.setLayoutParams(params);
		
		// set button text size
		Button textButton = (Button) convertView.findViewById(R.id.textInside);
		textButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, buttonHeightPx/2);
		ImageButton imageButton = (ImageButton) convertView.findViewById(R.id.imageButtonInside);
		
		// delete button, display image
		if (buttonCodes[pos].equalsIgnoreCase(
				resourceGetter.getString(R.string.button_delete))) {
			textButton.setVisibility(Button.GONE);
			imageButton.setVisibility(ImageButton.VISIBLE);
			// allow the GridView to capture ImageButton's clicks  
			imageButton.setFocusable(false);
			imageButton.setClickable(false);
			imageButton.setImageDrawable(resourceGetter.getDrawable(R.drawable.input_delete));
			setButtonSelector(imageButton, resourceGetter.getDrawable(R.layout.gridselector_red2));
		
		// dummy button, do not display at all
		} else if (buttonCodes[pos].equalsIgnoreCase(
				resourceGetter.getString(R.string.button_dummy))) {
			convertView.setEnabled(false);
			textButton.setEnabled(false);
			textButton.setVisibility(Button.GONE);

		// clear button, change name to clear-button name, set red background
		} else if (buttonCodes[pos].equalsIgnoreCase(
				resourceGetter.getString(R.string.button_clear))) {
			textButton.setText(resourceGetter.getString(R.string.button_clear_text));
			setButtonSelector(textButton, resourceGetter.getDrawable(R.layout.gridselector_red));
			
		// normal buttons
		} else {
			textButton.setText(buttonText[pos]);
			// set dark grey background for hexadecimal letter symbols
			if (resourceGetter.getString(R.string.buttons_letters).contains(buttonCodes[pos])) {
				setButtonSelector(textButton, resourceGetter.getDrawable(R.layout.gridselector_darkgrey));
			}
		}
		return convertView;
	}
}