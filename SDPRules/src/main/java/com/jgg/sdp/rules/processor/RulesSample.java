package com.jgg.sdp.rules.processor;

import java.util.ArrayList;

import com.jgg.sdp.domain.rules.RULSample;
import com.jgg.sdp.rules.xml.jaxb.SampleType;
import com.jgg.sdp.tools.Cadena;

public class RulesSample {
	private ArrayList<RULSample> samples = new ArrayList<RULSample>();
	
    private static RulesSample samp = null;
    
    private RulesSample() {
    	
    }
    
    public static RulesSample getInstance() {
    	if (samp == null) samp = new RulesSample();
    	return samp;
    }

    public ArrayList<RULSample> getSamples() {
    	return samples;
    }
    
    public Long createSample(long key, SampleType s) {
    	if (s == null) return 0L;
    	processSample(key, 0, s.getCorrect());
    	processSample(key, 1, s.getBad());
		
        return key;	
    }
    
    private void processSample(long key, int type, String data) {
    	String[] lines = data.split("\\\n");
    	lines = adjustLines(lines);
    	for (int idx = 0; idx < lines.length; idx++) {
    		RULSample s = new RULSample();
    		s.setIdSample(key);
    		s.setType(type);
    		s.setLine(idx + 1);
    		s.setData(lines[idx]);
    		samples.add(s);
    	}
    }
    
    public void clear() {
    	samples = new ArrayList<RULSample>();
    }


    private String[] adjustLines(String[] l) {
    	String[] lAft = new String[l.length];
    	int bef = 0;
    	int aft = 0;
    	int indent = 128;
    	
    	for (int idx = 0; idx < l.length; idx++) {
    		bef = l[idx].length();
    		lAft[idx] = Cadena.ltrim(l[idx]); 
    		aft = lAft[idx].length();
    		int diff = bef - aft; 
    		if (diff > 0) {
    	       if (diff < indent) indent = diff;		
    		}
    	}
    	String pad = Cadena.spaces(indent);
    	for (int idx = 0; idx < lAft.length; idx++) {
    		lAft[idx] = pad + lAft[idx]; 
    	}
        return lAft;    	
    }
}
