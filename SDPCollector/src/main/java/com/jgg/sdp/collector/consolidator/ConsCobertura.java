/*
 * 
 */
package com.jgg.sdp.collector.consolidator;

import java.util.List;

import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.domain.services.module.MODBloqueService;
import com.jgg.sdp.domain.services.traps.TRPCoberturaService;
import com.jgg.sdp.domain.traps.TRPCobertura;

public class ConsCobertura {

    private MODBloqueService     bloqueService = new MODBloqueService();
    private TRPCoberturaService  coverService  = new TRPCoberturaService();
    
	/**
     * Update.
     *
     * @param claves
     *            the claves
     */
	public void update(ConsClaves claves) {
		int usado = 0;
		
		MODBloque[]        bloques  = bloqueService.getBloques (claves.getModVersion());
        List<TRPCobertura> lstCover = coverService.getCobertura(claves.getIdSesion()
        		                                               ,claves.getModFirma());

        for (TRPCobertura cover : lstCover) {
            for (int idx = 0; idx < bloques.length; idx++) {
        	     String flags = cover.getFlags(); 
                 usado = flags.charAt(idx) == '0' ? 0 : 1;
                 if (usado == 1) bloques[idx].setUsado(-1);
            }
        }
        
        for (int idx = 0; idx < bloques.length; idx++) {
    	     if (bloques[idx].getUsado() == -1) {
    	         bloques[idx].setUsado(1);
    	         bloqueService.update(bloques[idx]);
            }
       }
	}

}
