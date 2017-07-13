/**
 * Carga los issues a partir de ficheros CSV
 * 
 */
package com.jgg.sdp.rules;

import java.io.*;

import com.jgg.sdp.domain.rules.*;
import com.jgg.sdp.domain.services.rules.*;


public class Loader {

    RULGroupsService grpService = new RULGroupsService();
    RULDescService   msgService = new RULDescService();
    
    public static void main(String[] args) throws Exception {
        Loader loader = new Loader();
        loader.process(args);

    }
    
    private void process(String[] args) throws Exception {
        String base = "";
        if (args.length > 0) {
            base = args[0];
        }
        
       grpService.beginTrans();
        msgService.clean();
        grpService.clean();
        
        processGroups(base);
    }

    private void processGroups(String base) throws Exception{
        String line;
        
        BufferedReader br = new BufferedReader(new FileReader(base + "groups.csv"));
        
        line = br.readLine();
        
        while ((line = br.readLine()) != null) {
            String[] toks = line.split(";");
            RULGroup grp = new RULGroup();
            grp.setIdGroup(Integer.parseInt(toks[0]));
            grp.setIdParent(Integer.parseInt(toks[1]));
            grp.setActive(1);
            grp.setPrefix(toks[2]);
            grp.setIdDesc(Integer.parseInt(toks[0]));
            grpService.update(grp);
            
            RULDesc txt = new RULDesc();
            txt.setIdDesc(Integer.parseInt(toks[0]));
            txt.setIdLang("XX");
            txt.setIdDialect("XX");
            txt.setMsg(toks[3]);
            msgService.update(txt);
            
            txt = new RULDesc();
            txt.setIdDesc(Integer.parseInt(toks[0]));
            txt.setIdLang("ES");
            txt.setIdDialect("es");
            txt.setMsg(toks[3]);
            msgService.update(txt);
            
        }
        
        br.close();
    }
}