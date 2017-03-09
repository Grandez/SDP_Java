/*
 * 
 */
package com.jgg.sdp.collector.process;
/**
 * Se deberia consolidar por modulos y luego por la sesion total
 * Pero todavia no esta claro que informacion detallada puede aportar
 * ese proceso.
 * Por ahora consolidamos unicamente a nivel sesion
 */


import java.util.ArrayList;

import com.jgg.sdp.collector.consolidator.*;
import com.jgg.sdp.domain.core.SDPModulo;
import com.jgg.sdp.domain.module.MODVersion;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.module.*;
import com.jgg.sdp.domain.services.session.*;
import com.jgg.sdp.domain.services.traps.*;
import com.jgg.sdp.domain.session.*;
import com.jgg.sdp.domain.traps.*;
import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.MSG;
import com.jgg.sdp.core.exceptions.SDPException;
// import com.jgg.sdp.core.jms.*;
import com.jgg.sdp.core.msg.Messages;

public class Consolidator implements IProcess {

//	private JMSQueue qInput;
	private Configuration cfg = Configuration.getInstance();

	private SDPModuloService     moduloService   = new SDPModuloService();

	private MODVersionService versionService  = new MODVersionService();

	private TRPSesionService  trpSesionService = new TRPSesionService();
	private SESSesionService  sesSesionService = new SESSesionService();
	
    private ConsClaves claves = new ConsClaves();

    private ConsCobertura    cobertura    = new ConsCobertura();
    private ConsParrafos     parrafos     = new ConsParrafos();
    private ConsPersistencia persistencia = new ConsPersistencia();
    private ConsModulos      modulos      = new ConsModulos();   
    private ConsArbol        arbol        = new ConsArbol();
    
    private ArrayList<Long>  lstModulos   = new ArrayList<Long>();
    
    /**
     * Recibe un mensaje con el identificador de la sesion
     * @return 0 Si el proceso ha sido correcto
     *         distinto de cero si se ha producido un error
     */
	public int process() {
		initSystems();
//		String txt = qInput.get();
		String txt = null;
		while (txt != null) {
		    processMessage(txt);
//			txt = qInput.get();
		}	
		endSystems();
		return 0;
	}

	private void processMessage(String sesion) {
        claves.setIdSesion(sesion);
        claves.setMaxOrden(0L);
        
	    SESSesion ses = copySesion();

	    claves.setTms(ses.getTms());
        claves.setUid(ses.getUid());
        
		processModulos();
		
		processSesion();
	    sesSesionService.commitTrans();
	    sesSesionService.beginTrans();

//JGG		removeTraps();
	}

	private SESSesion copySesion() {
		TRPSesion  trp = trpSesionService.findBySession(claves.getIdSesion());
        MODVersion ver = versionService.getByFirma(trp.getIdModulo());
        SDPModulo  mod = moduloService.findById(ver.getIdModulo());
        
		SESSesion ses = new SESSesion();
        ses.setIdSesion(trp.getIdSesion());
        ses.setIdVersion(ver.getIdVersion());
        
        ses.setCpu(trp.getCpu());
        ses.setElapsed(trp.getElapsed());
        ses.setFinished(trp.getFinished());
        ses.setLeido(trp.getLeido());
        ses.setEscrito(trp.getEscrito());
        ses.setSuspend(trp.getSuspend());
        ses.setTms(trp.getTms());
        ses.setUid(trp.getUid());

        sesSesionService.update(ses);
        
        claves.setMainId(ver.getIdModulo());
        claves.setMainFirma(trp.getIdModulo());
        claves.setMainVersion(ver.getIdVersion());
        claves.setMainNombre(ver.getNombre());
        claves.setMainAppl(mod.getIdAppl());
        return ses;
	}
	
	/**
	 * Procesa los modulos ejecutados en la sesion
	 */
	private void processModulos() {
	    TRPModuloService modService = new TRPModuloService();
	    for (TRPModulo modulo :  modService.getModulosBySesion(claves.getIdSesion())) {
	    	if (setClaves(modulo)) {
	    	    lstModulos.add(claves.getModVersion());
	    	    processModulo(modulo);
	    	}
	    }
	}
	
	private void processModulo(TRPModulo modulo) {
		cobertura.update(claves);
		parrafos.update(claves);
        persistencia.update(claves);
	}
	
	private void processSesion() {
		persistencia.consolide(claves, lstModulos);
		parrafos.consolide(claves, lstModulos);
		modulos.consolide(claves);
		arbol.consolide(claves);
	}

	/**
	 * Busca el modulo y establece las claves
	 * Puede que el modulo no exista si no esta monitorizado
	 * @param modulo Datos del modulo a buscar
	 * @return true si el modulo esta monitorizado
	 *         false si no lo esta
	 */
    private boolean setClaves(TRPModulo modulo) {
        MODVersion v = versionService.getByFirma(modulo.getIdModulo());

        // El modulo no esta monitorizado
        if (v == null)  return false;

        SDPModulo  mod = moduloService.findById(v.getIdModulo());
        
        claves.setModFirma(modulo.getIdModulo());
        claves.setModVersion(v.getIdVersion());     
        claves.setModId(v.getIdModulo()); 
        claves.setModAppl(mod.getIdAppl());
        claves.setModNombre(mod.getNombre());
        claves.setMinOrden(claves.getMaxOrden());
        claves.setMaxOrden(modulo.getOrden());
        return true;
    }
    
/*JGG Cuando finalize el proceso    	
	private void removeTraps() {
		trpSesionService.removeTraps(claves.getIdSesion());
	}
*/	
	private void initSystems() {
	    Messages.setLogger("COLLECTOR.CONSOLIDATOR");
	    /*
		qInput = JMSFactory.getJMSQueue();
		String qname = cfg.getQueue();
		if (qname == null) {
			qname = cfg.getQueueCollector();
			if (qname == null) throw new SDPException(MSG.ERR_NO_QUEUE);
		}
		qInput.openInput(qname);
		*/
		moduloService.beginTrans();
	}

	private void endSystems() {
//		qInput.close();
		moduloService.commitTrans();
	}

}
