/*
 * 
 */
package com.jgg.sdp.web.persister.rest;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.core.ctes.CDG;
import com.jgg.sdp.domain.core.SDPFile;
import com.jgg.sdp.domain.core.SDPSource;
import com.jgg.sdp.domain.services.core.SDPFileService;
import com.jgg.sdp.domain.services.core.SDPSourceService;
import com.jgg.sdp.tools.Fechas;
import com.jgg.sdp.tools.Firma;
import com.jgg.sdp.tools.json.*;
import com.jgg.sdp.web.persister.json.MemberInfo;

@Controller
public class PersisterController {
	
    @Autowired
    SDPFileService      fileService;	
    @Autowired
    SDPSourceService    sourceService;

    @RequestMapping(value="/sources", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody ResponseEntity<String> processFile ( @RequestBody final String body) {
		try {
	     MemberInfo member = deserialize(body);
	     store(member);
	//byte[] raw =  Base64.getDecoder().decode(payload);
		}
		catch (Exception e) {
			return new ResponseEntity<>("KO", HttpStatus.UNPROCESSABLE_ENTITY);
		}
      return new ResponseEntity<>("OK", HttpStatus.OK);
    }	
	
	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	private MemberInfo deserialize(String data) throws Exception {
		JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(data);
        
        MemberInfo member = new MemberInfo();
        member.setName((String) json.get("fileName"));
        member.setType(((Long) json.get("fileType")).intValue());
        member.setModules(((Long) json.get("modules")).intValue());
        member.setUid((String) json.get("user"));
        member.setTms(new Timestamp(System.currentTimeMillis()));
        member.setSource((String) json.get("fileData"));
        member.setFirma(Firma.calculate(member.getSource()));
        return member;
	}
	
	private void store(MemberInfo member) {
		
		fileService.beginTrans();
		
		SDPFile file = fileService.findByNameAndType(member.getName(), member.getType());
		if (file != null && member.getFirma().compareTo(file.getFirma()) == 0) return;
		
		file = new SDPFile();
		file.setIdFile(Fechas.serial());
		file.setArchivo(member.getName());
		file.setFirma(member.getFirma());
		file.setNumModulos(member.getModules());
		file.setTipo(member.getType());
		file.setTms(member.getTms());
		file.setUid(member.getUid());
		file.setEstado(CDG.STATUS_PENDING);
		file.setIdVersion(Fechas.serial());
		fileService.update(file);
		
		SDPSource source = new SDPSource();
		source.setIdFile(file.getIdFile());
		source.setIdVersion(file.getIdVersion());
		source.setSource(member.getSource().getBytes());
		sourceService.update(source);
		
		fileService.commitTrans();
	}
}
