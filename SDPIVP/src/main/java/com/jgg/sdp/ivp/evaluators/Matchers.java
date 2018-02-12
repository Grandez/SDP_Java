package com.jgg.sdp.ivp.evaluators;

import com.jgg.sdp.module.ivp.IVPCase;

public class Matchers {

	public static int matchInteger(Integer res, IVPCase c) {
		int tgt = c.getValueInteger();
		
		if (tgt != res) c.setMsgErr(String.format("Expected: %d. Found: %d",  tgt, res));
		return (tgt == res) ? 0 : 1;
	}

}
