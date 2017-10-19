/*
 * 
 */
package com.jgg.sdp.web.persister.rest;

import java.io.ObjectInputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.collector.persister.*;
import com.jgg.sdp.common.SDPUnit;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.tools.FileNames;
import com.jgg.sdp.tools.Firma;
import com.jgg.sdp.tools.json.*;

@Controller
public class PersisterController {
	
	@Autowired
	private HttpServletRequest context;
	
    @Autowired
    SDPFileService      fileService;	
    @Autowired
    SDPSourceService    sourceService;

    @RequestMapping(value="/sources", method = RequestMethod.POST)
    @ResponseBody ResponseEntity<String> processFile () {
    	SDPUnit unit = null;
    	try{
            ObjectInputStream obj =  new ObjectInputStream(context.getInputStream());
            unit = (SDPUnit) obj.readObject();
        }catch(Exception e){
            e.printStackTrace();
        }
    	
    	SourcePersister persister = new SourcePersister();
		try {
//	     SourceInfo member = deserialize(body);
         persister.persistUnit(unit);	     
		}
//		catch (ParseException e) {
//			return new ResponseEntity<>("KO", HttpStatus.UNPROCESSABLE_ENTITY);
//		}
		catch (Exception e) {
			return new ResponseEntity<>("KO", HttpStatus.INTERNAL_SERVER_ERROR);
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
	
	private SourceInfo deserialize(String data) throws ParseException {
		JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(data);
        
        SourceInfo member = new SourceInfo();
        member.setFullPath((String) json.get("fullName"));
        member.setName(FileNames.getBaseName((String) json.get("fullName")));
        member.setType(((Long) json.get("fileType")).intValue());
        member.setModules(((Long) json.get("modules")).intValue());
        member.setUid((String) json.get("user"));
        member.setTms(new Timestamp(System.currentTimeMillis()));
        member.setSource((String) json.get("fileData"));
        member.setFirma(Firma.calculate(member.getSource()));
        return member;
	}
	
}
