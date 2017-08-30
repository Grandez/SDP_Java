package com.jgg.sdp.calc.test;

public class SampleIssues {

	private int level0 = 9;
	private int level1 = 9;
	private int level2 = 4;
	private int level3 = 1;
	private int level4 = 1;
	private int level5 = 0;
	
	private int[] levels = new  int[7];
	private int[] counts = new  int[7];
	
	public SampleIssues() {
		for (int idx = 0; idx < 7; idx++) levels[idx] = idx;
		counts[0] = level0;
		counts[1] = level1;
		counts[2] = level2;
		counts[3] = level3;
		counts[4] = level4;
		counts[5] = level5;

	}
	
	public int[] getISSUES_NUM() {
		return counts;
	}
	
	public int[] getISSUES_LEVEL() {
		return levels;
	}
	
	public int getLevel0() {
		return level0;
	}
	public void setLevel0(int level0) {
		this.level0 = level0;
	}
	public int getLevel1() {
		return level1;
	}
	public void setLevel1(int level1) {
		this.level1 = level1;
	}
	public int getLevel2() {
		return level2;
	}
	public void setLevel2(int level2) {
		this.level2 = level2;
	}
	public int getLevel3() {
		return level3;
	}
	public void setLevel3(int level3) {
		this.level3 = level3;
	}
	public int getLevel4() {
		return level4;
	}
	public void setLevel4(int level4) {
		this.level4 = level4;
	}
	public int getLevel5() {
		return level5;
	}
	public void setLevel5(int level5) {
		this.level5 = level5;
	}
	
	
}
