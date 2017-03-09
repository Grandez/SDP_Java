/*
 * 
 */
package com.jgg.sdp.collector.consolidator;

import java.util.*;
import java.math.*;

import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.domain.services.module.*;
import com.jgg.sdp.domain.services.session.*;
import com.jgg.sdp.domain.services.traps.*;
import com.jgg.sdp.domain.session.*;
import com.jgg.sdp.domain.traps.*;


public class ConsParrafos {

	private ConsClaves claves;

	private MODParrafoService     modParrafoService = new MODParrafoService();
	private TRPParrafoService     trpParrafoService = new TRPParrafoService();
	private TRPParrWorkingService trpWorkingService = new TRPParrWorkingService();  
    private SESParrafoService     sesParrafoService = new SESParrafoService();

    private ConsSummarizer     summarizer = new ConsSummarizer();
    
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

		calculaParrafos();
	}

	/**
	 * Los parrafos estan divididos en dos bloques
	 * Por un lado esta el tiempo consumido en el parrafo (TRPParrafo)
	 * Por otro los contadores de uso de esos parrafos (TRPParrWorking)
	 */
	private void calculaParrafos() {
	    HashMap<String, Long>tbUsoParrafos = obtieneUsoParrafos();
	    List<Object[]> tuplas = trpParrafoService.getSumaParrafos( claves.getIdSesion()
	                                                              ,claves.getModFirma());
	    for (Object[] tupla : tuplas) {
	        SESParrafo parr = new SESParrafo();
	        
	        String nombre = (String) tupla[0];
	        Long veces    = tbUsoParrafos.get(nombre);
	        if (veces == 0) veces = 1L;
	        
            parr.setIdSesion(claves.getIdSesion());
            parr.setIdVersion(claves.getModVersion());
            parr.setOrden(0L);
            parr.setNombre((String) tupla[0]);
            parr.setVeces(veces);

            parr.setTotElapsed(((BigDecimal) tupla[3]).longValue());
            parr.setTotCpu    (((BigDecimal) tupla[4]).longValue());
            parr.setTotSuspend(((BigDecimal) tupla[5]).longValue());
            
            parr.setIntElapsed(((BigDecimal) tupla[6]).longValue());
            parr.setIntCpu    (((BigDecimal) tupla[7]).longValue());
            parr.setIntSuspend(((BigDecimal) tupla[8]).longValue());
           
            parr.setAvgTotElapsed(((BigDecimal) tupla[3]).longValue() / veces);
            parr.setAvgTotCpu    (((BigDecimal) tupla[4]).longValue() / veces);
            parr.setAvgTotSuspend(((BigDecimal) tupla[5]).longValue() / veces);
            
            parr.setAvgIntElapsed(((BigDecimal) tupla[6]).longValue() / veces);
            parr.setAvgIntCpu    (((BigDecimal) tupla[7]).longValue() / veces);
            parr.setAvgIntSuspend(((BigDecimal) tupla[8]).longValue() / veces);
            
            parr.setMinTotElapsed(((BigInteger) tupla[9]).longValue());
	        parr.setMinTotCpu    (((BigInteger) tupla[10]).longValue());
            parr.setMinTotSuspend(((BigInteger) tupla[11]).longValue());
	        
            parr.setMinIntElapsed(((BigInteger) tupla[12]).longValue());
            parr.setMinIntCpu    (((BigInteger) tupla[13]).longValue());
            parr.setMinIntSuspend(((BigInteger) tupla[14]).longValue());

            parr.setMaxTotElapsed(((BigInteger) tupla[15]).longValue());
            parr.setMaxTotCpu    (((BigInteger) tupla[16]).longValue());
            parr.setMaxTotSuspend(((BigInteger) tupla[17]).longValue());
            
            parr.setMaxIntElapsed(((BigInteger) tupla[18]).longValue());
            parr.setMaxIntCpu    (((BigInteger) tupla[19]).longValue());
            parr.setMaxIntSuspend(((BigInteger) tupla[20]).longValue());
            
            sesParrafoService.update(parr);
	    }
	}
	
	private HashMap<String, Long> obtieneUsoParrafos() {
	    HashMap<String, Long> hashParrafos = new HashMap<String, Long>();
	    ArrayList<String> pName  = new ArrayList<String>();
       
	    List<MODParrafo>  parrs = modParrafoService.getParrafosByIndex(claves.getModVersion());
        for (MODParrafo parr : parrs)  pName.add(parr.getNombre());

        long[] pVeces = new long[pName.size()];
        List<TRPParrWorking> lista = trpWorkingService.getParrafosBySesion(claves.getIdSesion(), 
                                                                           claves.getModFirma());       
        for (TRPParrWorking p : lista) {
            String[] toks = p.getDatos().split("X");
            for (int idx = 0; idx < toks.length; idx++ ) pVeces[idx] += Long.parseLong(toks[idx]);
        }
        
        for (int iParr = 0; iParr < pName.size(); iParr++) {
            hashParrafos.put(pName.get(iParr), pVeces[iParr]);
        }
        return hashParrafos;
	}
	
    /**
     * Calcula las medias globales de los parrafos por modulo.
     *
     * @param claves
     *            claves de proceso
     * @param lstModulos
     *            Lista de modulos ejecutados en la sesion
     */
    public void consolide(ConsClaves claves, ArrayList<Long> lstModulos) {
        for (Long idVersion : lstModulos) {
            summarizer.summarizeParrafos(idVersion, claves);
        }
    }

}
