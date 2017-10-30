package com.jgg.sdp.rules.processor;

import java.util.ArrayList;
import java.util.List;

import com.jgg.sdp.core.ctes.SYS;
import com.jgg.sdp.domain.rules.RULSample;
import com.jgg.sdp.domain.rules.RULSampleDesc;
import com.jgg.sdp.domain.services.rules.RULSampleDescService;
import com.jgg.sdp.domain.services.rules.RULSamplesService;
import com.jgg.sdp.rules.xml.jaxb.SampleType;
import com.jgg.sdp.rules.xml.jaxb.TextType;
import com.jgg.sdp.tools.Cadena;

public class RulesSample {
	
	private RULSamplesService     sampService = new RULSamplesService();
	private RULSampleDescService descService  = new RULSampleDescService();
	
	private ArrayList<RULSample>     samples  = new ArrayList<RULSample>();
	private ArrayList<RULSampleDesc> descs    = new ArrayList<RULSampleDesc>();
	
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

    public ArrayList<RULSampleDesc> getSamplesDesc() {
    	return descs;
    }
    
    public Long createSample(long key, SampleType s) {
    	if (s == null) return 0L;
    	deleteSample(key);
    	processDescription(key, s.getDescription());
    	processSample(key, 0, s.getCorrect());
    	processSample(key, 1, s.getBad());
		
        return key;	
    }
    
    private void processDescription(Long key, List<TextType> data) {
		for (TextType t : data) {
			String l = t.getLang() == null ? SYS.DEF_LANG : t.getLang();
			String d = t.getDialect() == null ? l.toUpperCase() : t.getDialect();
			String[] lines = adjustLines(t.getValue());
			for (int idx = 0; idx < lines.length; idx++) {
			    RULSampleDesc r = new RULSampleDesc();
			    r.setIdDesc(key);
			    r.setIdLang(l);
			    r.setIdDialect(d);
			    r.setIdSeq(idx + 1);
			    r.setTxt(lines[idx]);
			    descs.add(r);
		   }	    
		}
    	
    }
    
    private void processSample(long key, int type, String data) {
    	String[] lines = adjustLines(data);
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
    	descs   = new ArrayList<RULSampleDesc>();
    }


    private String[] adjustLines(String data) {
    	String[] l = data.split("\\\n");
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
    
    private void deleteSample(Long key) {
    	sampService.deleteSample(key);
    	descService.deleteSampleDesc(key);
    }
}
