package com.emergy.fbc.test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.elsoft.fbc.R;
import com.emergy.fbc.BaseLogic;

import android.test.AndroidTestCase;
import android.util.Log;

public class BaseLogicTest extends AndroidTestCase {

	protected BaseLogic logic;
	protected int indexBin;
	protected int indexOct;
	protected int indexDec;
	protected int indexHex;
	protected BigInteger rndLong;
	
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

		// setup random biginteger for tests
		Random random = new Random();
		rndLong = BigInteger.valueOf(random.nextLong() & 0x0111111111111111L);
		rndLong = rndLong.add(BigInteger.valueOf(random.nextLong() & 0x0111111111111111L));
		rndLong = rndLong.add(BigInteger.valueOf(random.nextLong() & 0x0111111111111111L));
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test that value at output (dec) is the same value that is put in
	 */
	public void testInputEqualsOutputDec() {
		// binary value
		doTest(2, 10);
		// octal value
		doTest(8, 10);
		// dec value
		doTest(10, 10);
		// hex value
		doTest(16, 10);
	}
	
	/**
	 * Test that value at output (bin) is the same value that is put in
	 */
	public void testInputEqualsOutputBin() {
		// binary value
		doTest(2, 2);
		// octal value
		doTest(8, 2);
		// dec value
		doTest(10, 2);
		// hex value
		doTest(16, 2);
	}
	
	/**
	 * Test that value at output (oct) is the same value that is put in
	 */
	public void testInputEqualsOutputOct() {
		// binary value
		doTest(2, 8);
		// octal value
		doTest(8, 8);
		// dec value
		doTest(10, 8);
		// hex value
		doTest(16, 8);
	}
	
	/**
	 * Test that value at output (hex) is the same value that is put in
	 */
	public void testInputEqualsOutputHex() {
		// binary value
		doTest(2, 16);
		// octal value
		doTest(8, 16);
		// dec value
		doTest(10, 16);
		// hex value
		doTest(16, 16);
	}
	
	/**
	 * Private method for cross-testing different input/output bases
	 */
	private void doTest(int inputBase, int outputBase) {
		switch (inputBase)
		{
			case 2: logic.setValue(rndLong.toString(inputBase), indexBin);
			break;
			case 8: logic.setValue(rndLong.toString(inputBase), indexOct);
			break;
			case 10:logic.setValue(rndLong.toString(inputBase), indexDec);
			break;
			case 16:logic.setValue(rndLong.toString(inputBase), indexHex);
			break;
		}
		switch (outputBase)
		{
			case 2: assertEquals(rndLong.toString(outputBase), logic.getValue(indexBin));
			break;
			case 8: assertEquals(rndLong.toString(outputBase), logic.getValue(indexOct));
			break;	
			case 10:assertEquals(rndLong.toString(outputBase), logic.getValue(indexDec));
			break;
			case 16:assertEquals(rndLong.toString(outputBase), logic.getValue(indexHex));
			break;
		}
	}
	
}
