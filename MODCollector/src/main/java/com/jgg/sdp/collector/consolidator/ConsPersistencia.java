/*
 * 
 */
package com.jgg.sdp.collector.consolidator;

import java.util.*;

import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.domain.services.module.*;
import com.jgg.sdp.domain.services.session.*;
import com.jgg.sdp.domain.services.traps.*;
import com.jgg.sdp.domain.session.SESIO;
import com.jgg.sdp.domain.session.SESPersistencia;
import com.jgg.sdp.domain.session.SESSesion;
import com.jgg.sdp.domain.traps.*;
import com.jgg.sdp.core.ctes.*;

public class ConsPersistencia {

    private ConsClaves claves;

    private MODFicheroService      ficheroService = new MODFicheroService();
    private TRPPersistenciaService trpPersService = new TRPPersistenciaService();

    private SESSesionService       sesionService  = new SESSesionService();
    private SESPersistenciaService sesPersService = new SESPersistenciaService();
    private SESIOService           ioService      = new SESIOService();
    
	public void setClaves(ConsClaves claves) {
		this.claves = claves;
	}

	/**
     * Update.
     *
     * @param claves
     *            the claves
     */
	public void update(ConsClaves claves) {
		this.claves = claves;
        generarRegistrosPersistencia();
	}

	/**
     * Consolide.
     *
     * @param claves
     *            the claves
     * @param lstModulos
     *            the lst modulos
     */
	public void consolide(ConsClaves claves, ArrayList<Long> lstModulos) {
		this.claves = claves;
		List<MODFichero> lstMaestros = obtenerMaestros(lstModulos);
		if (lstMaestros.size() > 0) {
		    procesaMaestros(lstMaestros);
		}
		else {
		    calculaMaestros();
		}
		generarSesionIO();		
	}

	/**
     * Obtener maestros.
     *
     * @param lstModulos
     *            the lst modulos
     * @return the list
     */
	List<MODFichero> obtenerMaestros(ArrayList<Long> lstModulos) {
	    List<MODFichero> lista = new ArrayList<MODFichero>();
	    for (Long idVersion : lstModulos) {
	        for (MODFichero maestro : ficheroService.getMaestros(idVersion)) {
	            lista.add(maestro);
	        }
	    }
	    return lista;
	}
	
	private void procesaMaestros(List<MODFichero> lstFicheros) {
	    SESPersistencia f = null;
	    Long leido = 0L;
	    Long escrito = 0L;
	    for (MODFichero fichero : lstFicheros) {
	         f = sesPersService.getTotalFileById( claves.getIdSesion()
	                                             ,fichero.getIdVersion()
	                                             ,fichero.getIdFile());
	        leido += f.getAcSelect();
	        escrito += f.getAcInsert();
	    }
	    if (leido == 0L) {
	        leido = getMaxLeido();
	    }
	    if (escrito == 0L) {
	        escrito = getMaxEscrito();
	    }
	    updateSesion(leido, escrito);
	}
	
	private void calculaMaestros() {
        Long leido  = getMaxLeido();
        Long escrito = getMaxEscrito();
        updateSesion(leido, escrito);
	}
	
	private void updateSesion(Long leido, Long escrito) {
	    SESSesion sesion = sesionService.findBySession(claves.getIdSesion());
	    sesion.setLeido(leido);
	    sesion.setEscrito(escrito);
	    sesionService.update(sesion);
	}

	private Long getMaxLeido() {
	    return sesPersService.getMaxLeido(claves.getIdSesion());
	}
    private Long getMaxEscrito() {
        return sesPersService.getMaxEscrito(claves.getIdSesion());
    }

    
    
    
    
    private void generarSesionIO() {
        List<Object[]> tuplas = sesPersService.getTotalesBySesion(claves.getIdSesion());
        for (Object[] tupla : tuplas) {

            SESIO sesio = new SESIO();
            sesio.setIdSesion(claves.getIdSesion());
            sesio.setIdVersion(claves.getModVersion());

            sesio.setAcOpen   (tupla[0] == null ? 0L : (Long) tupla[0]);
            sesio.setAcClose  (tupla[1] == null ? 0L : (Long) tupla[1]);
            sesio.setAcInsert (tupla[2] == null ? 0L : (Long) tupla[2]);
            sesio.setAcRead   (tupla[3] == null ? 0L : (Long) tupla[3]);
            sesio.setAcUpdate (tupla[4] == null ? 0L : (Long) tupla[4]);
            sesio.setAcDelete (tupla[5] == null ? 0L : (Long) tupla[5]);
            sesio.setAcSelect (tupla[6] == null ? 0L : (Long) tupla[6]);
            sesio.setAcTotal  (tupla[7] == null ? 0L : (Long) tupla[7]);
            sesio.setTms      (claves.getTms());
            ioService.update(sesio);
        }
    }
    
	private void generarRegistrosPersistencia() {
		long valor = 0L;
        List<TRPPersistencia> lstPers = trpPersService.getPersistencia(claves.getIdSesion(), claves.getModFirma());
        
        // NO hay ficheros
        if (lstPers.size() == 0) return; 

        SESPersistencia[] tbFiles = crearFicheros(lstPers.get(0));
        
        for (TRPPersistencia pers : lstPers) {
		     String[] cont = pers.getFlags().split("X");
             for (int idxFile = 0; idxFile < (cont.length / 8) ; idxFile++) {
         	      tbFiles[idxFile].setIdFile(idxFile + 1);
                  tbFiles[idxFile].setIdSesion(claves.getIdSesion());
                  tbFiles[idxFile].setIdVersion(claves.getModVersion());
                  tbFiles[idxFile].setOrden(0L);
                  tbFiles[idxFile].setMaestro(CDG.MASTER_NO);
                  
        	      for (int idxAcc = 0; idxAcc < 8 ; idxAcc++) {
        		      valor = Long.parseLong(cont[(idxFile * 8) + idxAcc]);
        		      switch(idxAcc) {
                          case 0: tbFiles[idxFile].addAcOpen(valor);   break;
                          case 1: tbFiles[idxFile].addAcClose(valor);  break;
                          case 2: tbFiles[idxFile].addAcInsert(valor); break;
                          case 3: tbFiles[idxFile].addAcRead(valor);   break;
                          case 4: tbFiles[idxFile].addAcUpdate(valor); break;
                          case 5: tbFiles[idxFile].addAcDelete(valor); break;
                          case 6: tbFiles[idxFile].addAcSelect(valor); break;
                          case 7: tbFiles[idxFile].addAcTotal(valor);  break;
        		     }
        	     }
             }      
        }
        
        for (int index = 0; index < tbFiles.length; index++) {
            sesPersService.update(tbFiles[index]);
        }
	}
	
    private SESPersistencia[] crearFicheros(TRPPersistencia trap) {
        String[] cont = trap.getFlags().split("X");
        ArrayList<SESPersistencia> lstPers = new ArrayList<SESPersistencia>();
        
        for (int index = 0; index < (cont.length / 8); index++) lstPers.add(new SESPersistencia());
        
        return lstPers.toArray(new SESPersistencia[lstPers.size()]);    
    }

}