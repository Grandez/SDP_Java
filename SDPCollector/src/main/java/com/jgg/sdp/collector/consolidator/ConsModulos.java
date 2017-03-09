/*
 * 
 */
package com.jgg.sdp.collector.consolidator;

import java.math.*;

import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.domain.services.module.*;
import com.jgg.sdp.domain.services.session.*;
import com.jgg.sdp.domain.services.traps.*;
import com.jgg.sdp.domain.session.*;

public class ConsModulos {


    private MODVersionService versionService = new MODVersionService();
    private TRPModuloService  trpModService  = new TRPModuloService();
    private SESModuloService  sesionService  = new SESModuloService();
    private ConsSummarizer    summarizer     = new ConsSummarizer();
    
    private ConsClaves claves = null;
    
    /**
     * Sumariza la informacion de las llamadas a modulos realizadas durante la
     * sesion Controla los modulos no monitorizadosa traves de las sentencias
     * CALL que no tienen registro de sesion asociado.
     *
     * @param claves
     *            Las claves del proceso
     */
	public void consolide(ConsClaves claves) {
	    this.claves = claves;
	    for (Object[] tupla : trpModService.getModulos( claves.getIdSesion())) {
	        SESModulo mod = generaSesionModulo(tupla, claves.getIdSesion());
	        summarizer.summarizeModulo(mod, claves);
	    }
	}
	
	private SESModulo generaSesionModulo(Object[] tupla, String idSesion) {
	    SESModulo mod = new SESModulo();
        MODVersion ver = versionService.getByFirma((String) tupla[0]);
        
        if (ver == null) {
           mod.setIdVersion(0L);
	    }   
        else {
            mod.setIdVersion(ver.getIdVersion());        
        }
        
        mod.setIdSesion(idSesion);

        mod.setOrden(0L);
        mod.setNombre((String) tupla[1]);
        mod.setVeces(((BigInteger)tupla[2]).longValue());

        mod.setTotElapsed(((BigDecimal)tupla[3]).longValue());
        mod.setTotCpu    (((BigDecimal)tupla[4]).longValue());
        mod.setTotSuspend(((BigDecimal)tupla[5]).longValue());
        mod.setIntElapsed(((BigDecimal)tupla[6]).longValue());
        mod.setIntCpu    (((BigDecimal)tupla[7]).longValue());
        mod.setIntSuspend(((BigDecimal)tupla[8]).longValue());

        mod.setMinTotElapsed(((BigInteger)tupla[9]).longValue());
        mod.setMinTotCpu    (((BigInteger)tupla[10]).longValue());
        mod.setMinTotSuspend(((BigInteger)tupla[11]).longValue());
        mod.setMinIntElapsed(((BigInteger)tupla[12]).longValue());
        mod.setMinIntCpu    (((BigInteger)tupla[13]).longValue());
        mod.setMinIntSuspend(((BigInteger)tupla[14]).longValue());

        mod.setMaxTotElapsed(((BigInteger)tupla[15]).longValue());
        mod.setMaxTotCpu    (((BigInteger)tupla[16]).longValue());
        mod.setMaxTotSuspend(((BigInteger)tupla[17]).longValue());
        mod.setMaxIntElapsed(((BigInteger)tupla[18]).longValue());
        mod.setMaxIntCpu    (((BigInteger)tupla[19]).longValue());
        mod.setMaxIntSuspend(((BigInteger)tupla[20]).longValue());

        mod.setAvgTotElapsed(((BigDecimal)tupla[21]).longValue());
        mod.setAvgTotCpu    (((BigDecimal)tupla[22]).longValue());
        mod.setAvgTotSuspend(((BigDecimal)tupla[23]).longValue());
        mod.setAvgIntElapsed(((BigDecimal)tupla[24]).longValue());
        mod.setAvgIntCpu    (((BigDecimal)tupla[25]).longValue());
        mod.setAvgIntSuspend(((BigDecimal)tupla[26]).longValue());

        mod.setTms(claves.getTms());
        
        sesionService.update(mod);
        return mod;
    }

}
