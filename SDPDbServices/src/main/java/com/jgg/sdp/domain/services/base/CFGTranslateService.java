package com.jgg.sdp.domain.services.base;

import java.util.HashSet;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.CFG;
import com.jgg.sdp.core.ctes.SYS;
import com.jgg.sdp.domain.base.CFGTranslate;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class CFGTranslateService extends AbstractService<CFGTranslate> {

	// Al traducir codigos se debe mirar, no se sabe si es una traduccion o no
	// Por eso, puede ser que cada vez que venga mire si existe como mascara
	// en los dos idiomas y luego si existe el valor tambien en los dos idiomas
	//
	// Para evitarlo, guardamos en cache los campos que no son codigos
	
	private HashSet<String> noXlate = new HashSet<String>();

	private Configuration cfg = Configuration.getInstance();
	
	private CFGTranslate record = null;
	
	public String getTranslation(String field, Integer value, String lang) {
		String data = null;
		
		if (noXlate.contains(field) == true) return null;
		
		switch (getMask(field, lang)) {
	        case SYS.FLAG_REDIRECT: return getTranslation(record.getMsg(), value, lang);
		    case SYS.FLAG_MASK:     data = getTranslationMask(field,         value, lang); break;
		    case SYS.FLAG_FLAG:     data = getTranslationCode(SYS.DEF_XLATE, value, lang); break;
		    default:                data = getTranslationCode(field,         value, lang);
		};

		
		if (data == null) noXlate.add(field);
		
		return data;
	}
	
	private String getTranslationMask(String field, Integer value, String lang) {	
		int iMask = 1;
		int val = 0;
		String data = null;
		StringBuilder cadena = new StringBuilder();
		String concat = cfg.getString(CFG.EXCEL_MASK);
		
		// Caso especial 0
		if (value == 0) return getTranslationCode(field, val, lang);
		
		for (int i = 0 ; i < 8 ; i++) {
			val = value & (iMask << i);
			if (val != 0) {
				data = getTranslationCode(field, val, lang);
				if (data == null) return value.toString();
				if (cadena.length() > 0) cadena.append(concat);
				cadena.append(data);
			}
		}
		
		if (cadena.length() == 0) return value.toString();
		return cadena.toString();
	}

	private String getTranslationCode(String field, Integer value, String lang) {
		CFGTranslate record = findQuery (CFGTranslate.GetTranslationValue, field, value, lang);
		if (record == null) {
			record = findQuery (CFGTranslate.GetTranslationValue, field, value, SYS.DEF_LANG);
		}
		if (record == null) return null;
		return record.getMsg();
	}

	private Integer getMask(String field, String lang) {
		record = findQuery (CFGTranslate.GetMask, field, lang);
		if (record == null) {
			record = findQuery (CFGTranslate.GetMask, field, SYS.DEF_LANG);
		}
		if (record == null) return 0;
		return record.getIdValue();
	}

}
