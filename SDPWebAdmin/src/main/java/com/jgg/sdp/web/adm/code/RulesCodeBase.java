package com.jgg.sdp.web.adm.code;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jgg.sdp.domain.rules.IRule;
import com.jgg.sdp.domain.rules.RULCond;
import com.jgg.sdp.domain.rules.RULSample;
import com.jgg.sdp.domain.services.rules.*;
import com.jgg.sdp.web.adm.json.*;
import com.jgg.sdp.web.core.MDText;

import static com.jgg.sdp.rules.ctes.CDGRules.*;

@Component
public class RulesCodeBase {
	
    @Autowired
    private RULCondsService condService;    
    @Autowired
    private RULMessagesService msgService;    
    @Autowired
    private RULDescService descService;    
    @Autowired
    private RULSamplesService sampService;    

	public List<JSonRuleCond> getRawConditions(Long idCond, String[] lang) {
		List<JSonRuleCond> list = new ArrayList<JSonRuleCond>();
		
		Long id  = (idCond < 0L) ? idCond * OP_NEGATED : idCond;
		if (id == OP_POSITIVE) return list;
		
	    for (RULCond cond: condService.getConditions(id)) {
	    	JSonRuleCond c = new JSonRuleCond();
	    	if (idCond < 0L ) c.setNegated(true);
	    	c.setIdCond(cond.getIdCond());
	    	c.setIdSeq(cond.getIdSeq());
	    	c.setLvalue(cond.getLvalue());
	    	c.setLvalueType(cond.getLvalueType());
	    	c.setOperator(cond.getOperator());
	    	c.setRvalue(cond.getRvalue());
	    	c.setRvalueType(cond.getRvalueType());
	    	c.setMessage(msgService.getMessage(cond.getIdMsg(), lang));
	    	c.setTms(cond.getTms());
	    	c.setUid(cond.getUid());
	    	list.add(c);
	    }
	    
	    return list;
	}

	public String mountMessage(IRule obj, String[] lang) {
		if (obj.getIdMsg() == null) return null;
	    String msg = msgService.getMessage(obj.getIdMsg(), lang);
	    return msg;
	}
	
    public String mountDescription(Long id, String[] lang) {
    	if (id == null) return null;
    	List<String> raw = descService.getDescription(id, lang); 
    	if (raw == null || raw.size() == 0) return null;
    	MDText md = new MDText();
    	return md.renderBlock(raw);
    }

    public JSonRuleSample mountSample(Long idSample, String[] lang) {
    	if (idSample == null) return null;
    	JSonRuleSample sample = new JSonRuleSample();
    	StringBuilder sb = new StringBuilder();
    	for (RULSample s : sampService.getDescription(idSample)) {
    		sb.append(s.getData());
    		sb.append('\n');
    	}
    	sample.setDesc(sb.toString());
    	
    	sb = new StringBuilder();
    	for (RULSample s : sampService.getSampleOK(idSample)) {
    		sb.append(s.getData());
    		sb.append('\n');
    	}
    	sample.setOk(sb.toString());
    	
    	sb = new StringBuilder();
    	for (RULSample s : sampService.getSampleKO(idSample)) {
    		sb.append(s.getData());
    		sb.append('\n');
    	}
    	sample.setKo(sb.toString());
    	
    	return sample;
    }
    
	
}
