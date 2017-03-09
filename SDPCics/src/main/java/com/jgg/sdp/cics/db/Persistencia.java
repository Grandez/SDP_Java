package com.jgg.sdp.cics.db;

import java.sql.Timestamp;

import com.jgg.sdp.core.ctes.CDG;
import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.module.*;

import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.module.*;

import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.items.CICSVerb;

public class Persistencia {

	static int count = 0;
	
	private SDPAplicacionService  appService         = new SDPAplicacionService();
	private SDPModuloService      moduloService      = new SDPModuloService();
	
	private MODVersionService     versionService     = new MODVersionService();
    private MODCicsService        cicsService        = new MODCicsService();
   
	private Module     module;
    private Long       tms;
    private Long       idModule;
    private Long       idVersion;
    private MODVersion version;
    private boolean    hasCICS;
    
    private int tsType = 0;
    
    /**
     * Procesa los verbos CICS
     * Solo actualiza la tabla MOD_CICS y el estado
     * 
     * @param module El objeto modulo
     * @return true  si tenia sentencias cics
     *         false si no las tenia
     */
	public boolean process(Module module) {
		this.module = module;
		
		idModule = 0L;
		
		tms = System.currentTimeMillis();
		
		hasCICS = (module.getCICSVerbs().size() > 0);
		
		beginTrans();
		
		// Si el modulo no existe y no tiene CICS no hacer nada
		idModule = processModule();
		if (idModule == 0L) {
			commitTrans();
			return false;
		}
		
		idVersion = processVersion();
		processCICS();
		updateVersion();
		commitTrans();
		return hasCICS;
	}
	
	private long processModule() {
    	SDPModulo mod = moduloService.findByName(module.getName());
        if (mod == null && hasCICS == false) return 0L;
        return (mod == null) ? createModule() : mod.getIdModulo();
	}
	
	private long processVersion() {
		/*
    	version = versionService.findByFirma(idModule, module.getSource().getFirma());
        return (version == null) ? createVersion() : version.getIdVersion();
        */
		return 1L;
    }

	private void processCICS() {
		cicsService.delete(idVersion);
		for (CICSVerb verbo : module.getCICSVerbs()) {
            MODCics verb = new MODCics();
            verb.setIdVersion(idVersion);
            verb.setVerbo(verbo.getVerbo());
            verb.setVeces(verbo.getCount());
            verb.setTipo(verbo.getTipo());
            cicsService.update(verb);
            
            tsType |= verbo.getTipo();
		}
	}
	
	private long createModule() {
        SDPModulo modulo = new SDPModulo((tms * 10) + (count++ % 10));
       	modulo.setIdAppl(0L);
       	modulo.setNombre(module.getName());
//       	modulo.setArchivo(module.getFileName());
       	modulo.setUid(System.getProperty("user.name"));
       	modulo.setTms(new Timestamp(tms + 1));
       	modulo.setTipo(module.getType());
       	modulo.setIdVersion(0L);
       	
   		actualizaAplicacion(modulo.getIdAppl());
    	moduloService.update(modulo);
    	return modulo.getIdModulo();
	}

	private Long createVersion() {
		int tsmode = 0;
		
		Summary summary = module.getSummary();
    	
    	version = new MODVersion(idModule, (tms * 10) + (count++ % 10));

        version.setNombre(module.getName());
    	version.setTipo(module.getType());

    	version.setDesc(module.getDescription());
    	version.setUid(System.getProperty("user.name"));
    	
    	version.setTms(new Timestamp(System.currentTimeMillis()));

    	version.setEstado  (CDG.STATUS_CICS);
    	tsmode = summary.getTSMode();

    	versionService.update(version);
    	
    	moduloService.updateVersion(idModule, version.getIdVersion());

        return version.getIdVersion();
	}	

	private void updateVersion() {
    	versionService.update(version);
	}	
	
    /*
     * JGG 
     * A partir de cierto momento (C3P0 o asi) en lugar de hacer un UPDATE
     * hace un INSERT con lo que genera duplicados
     * Se cambia por una sentencia UPDATE
     */
    private void actualizaAplicacion(Long idAppl) {
        SDPAplicacion app = appService.findById(idAppl);
        if (app != null) {
            appService.updateVolumen(app.getAplicacion());
        }
    }

    private void beginTrans() {
    	moduloService.beginTrans();
    }

    private void commitTrans() {
    	try {
    	   moduloService.commitTrans();
    	}
    	catch (Exception e) {
    		moduloService.rollbackTrans();
    	}
    }

}
