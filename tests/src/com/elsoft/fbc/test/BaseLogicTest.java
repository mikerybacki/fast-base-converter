package com.elsoft.fbc.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.elsoft.fbc.BaseLogic;
import com.elsoft.fbc.R;

import android.test.AndroidTestCase;

public class BaseLogicTest extends AndroidTestCase {

	protected BaseLogic logic;
	protected int indexBin;
	protected int indexOct;
	protected int indexDec;
	protected int indexHex;
	protected long rndLong;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		logic = new BaseLogic(this.getContext());
		
		// get index of hex base
		int[] basesArray = this.getContext().getResources().getIntArray(R.array.bases);
		List<Integer> bases = new ArrayList<Integer>();
		for(int i : basesArray) {
			bases.add(i);
		}
		indexBin = bases.indexOf(2);
		indexOct = bases.indexOf(8);
		indexDec = bases.indexOf(10);
		indexHex = bases.indexOf(16);

		// setup random long for tests
		Random random = new Random();
		rndLong = random.nextLong();
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * Test that value at output is the same (dec) value that is put in
	 */
	public void testInputEqualsOutput() {
		logic.setValue(rndLong);
		assertEquals(Long.toString(rndLong), logic.getValue(indexDec));
	}
	
	/**
	 * Test that bin output is correct
	 */
	public void testInputEqualsOutputBin() {
		logic.setValue(rndLong);
		String hexValue = Long.toBinaryString(rndLong);
		assertEquals(hexValue, logic.getValue(indexBin));
	}
	
	/**
	 * Test that oct output is correct
	 */
	public void testInputEqualsOutputOct() {
		logic.setValue(rndLong);
		String hexValue = Long.toOctalString(rndLong);
		assertEquals(hexValue, logic.getValue(indexOct));
	}
	
	/**
	 * Test that hex output is correct
	 */
	public void testInputEqualsOutputHex() {
		logic.setValue(rndLong);
		String hexValue = Long.toHexString(rndLong);
		assertEquals(hexValue, logic.getValue(indexHex).toLowerCase());
	}
}
