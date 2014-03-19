package com.elsoft.fbc.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.elsoft.fbc.BaseLogic;
import com.elsoft.fbc.R;

import android.test.AndroidTestCase;

public class BaseLogicTest extends AndroidTestCase {

	protected BaseLogic logic;
	protected int indexDec;
	protected long rndLong;
	
	protected void setUp() throws Exception {
		logic = new BaseLogic(this.getContext());
		
		// get index of hex base
		int[] basesArray = this.getContext().getResources().getIntArray(R.array.bases);
		List<Integer> bases = new ArrayList<Integer>();
		for(int i : basesArray) {
			bases.add(i);
		}
		indexDec = bases.indexOf(10);
		super.setUp();
		
		// setup random long for tests
		Random random = new Random();
		rndLong = random.nextLong();
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * Test that value at output is the same value that is put in
	 */
	public void testInputEqualsOutput() {
		logic.setValue(rndLong);
		assertEquals(Long.toString(rndLong), logic.getValue(indexDec));
	}
}
