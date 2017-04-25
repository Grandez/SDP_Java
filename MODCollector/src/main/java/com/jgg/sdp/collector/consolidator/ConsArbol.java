/*
 * 
 */
package com.jgg.sdp.collector.consolidator;

import java.math.*;
import java.util.*;

import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.module.*;
import com.jgg.sdp.domain.services.session.*;
import com.jgg.sdp.domain.services.summary.*;
import com.jgg.sdp.domain.services.traps.*;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.tools.Fechas;
import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.domain.session.*;
import com.jgg.sdp.domain.summary.*;


public class ConsArbol {

    private SDPModuloService  moduloService  = new SDPModuloService();
    private MODVersionService versionService = new MODVersionService();
    private TRPCallService    trpCallService = new TRPCallService();
    private SESModuloService  sesionService  = new SESModuloService();

    private MODCopyService depService = new MODCopyService();    
    private SESArbolService   sesArbolService   = new SESArbolService();
    private SUMArbolService   sumArbolService   = new SUMArbolService();

    private ConsClaves claves = null;
    private MODVersion verCalling;
    private MODVersion verCalled;

    /* Controla los casos en los que el modulo no ha sido monitorizado
     * pero si la llamada
     * Actualiza las dependencias
     * Si es un modulo no monitorizado lo crea virtualmente
     * Inserta la llamada en la tabla de sesiones
     */
    
    /**
     * Consolide.
     *
     * @param claves
     *            the claves
     */
    public void consolide(ConsClaves claves) {
        this.claves = claves;
        
        List<Object[]> listaCalls = trpCallService.getSumCalls(claves.getIdSesion());

        for (Object[] tupla : listaCalls) {
            verCalling = versionService.getByFirma((String) tupla[0]);
            verCalled  = versionService.getByFirma((String) tupla[1]);
            
            // Es un modulo no monitorizado. Darlo de alta
            if (verCalled == null) {
               verCalled = crearModuloNoMonitorizado((String) tupla[1], (String) tupla[2]); 
            }
            
            updateDependencias(verCalling, verCalled, (String) tupla[2]);
          
            // Si existe, se ha monitorizado, si no se crea el registro en SESModulo
            if (sesionService.find(claves.getIdSesion(), verCalled.getIdVersion()) == null) {
                generaModulo(verCalled, tupla);
            }    
            
            actualizaArbolSesion(tupla);
            actualizaArbolModulo(tupla);
        }
        
     }
    
    
    private void updateDependencias(MODVersion verCalling, MODVersion verCalled, String nombre) {
        MODCopy dep = depService.find(verCalling.getIdVersion(), nombre);
        // Si no existe solo es un modulo
        if (dep == null) {
            dep = new MODCopy();
            dep.setIdVersion(verCalling.getIdVersion());
            dep.setTipo(CDG.CALL_DYNAMIC);
            dep.setModulo(nombre);
        }
        dep.setEstado(CDG.DEP_ST_CHECKED);
        depService.update(dep);     
    }
    
    private void generaModulo(MODVersion version, Object[] tupla) {
        SESModulo mod = new SESModulo();
        
        mod.setIdSesion(claves.getIdSesion());
        mod.setIdVersion(version.getIdVersion());
        mod.setNombre(version.getNombre());
        
        mod.setOrden(((BigInteger)tupla[3]).longValue());
        mod.setVeces(((BigInteger)tupla[4]).longValue());

        mod.setTotElapsed(((BigDecimal)tupla[5]).longValue());
        mod.setTotCpu    (((BigDecimal)tupla[6]).longValue());
        mod.setTotSuspend(((BigDecimal)tupla[7]).longValue());
        mod.setIntElapsed(((BigDecimal)tupla[8]).longValue());
        mod.setIntCpu    (((BigDecimal)tupla[9]).longValue());
        mod.setIntSuspend(((BigDecimal)tupla[10]).longValue());

        mod.setMinTotElapsed(((BigInteger)tupla[11]).longValue());
        mod.setMinTotCpu    (((BigInteger)tupla[12]).longValue());
        mod.setMinTotSuspend(((BigInteger)tupla[13]).longValue());
        mod.setMinIntElapsed(((BigInteger)tupla[14]).longValue());
        mod.setMinIntCpu    (((BigInteger)tupla[15]).longValue());
        mod.setMinIntSuspend(((BigInteger)tupla[16]).longValue());

        mod.setMaxTotElapsed(((BigInteger)tupla[17]).longValue());
        mod.setMaxTotCpu    (((BigInteger)tupla[18]).longValue());
        mod.setMaxTotSuspend(((BigInteger)tupla[19]).longValue());
        mod.setMaxIntElapsed(((BigInteger)tupla[20]).longValue());
        mod.setMaxIntCpu    (((BigInteger)tupla[21]).longValue());
        mod.setMaxIntSuspend(((BigInteger)tupla[22]).longValue());

        mod.setAvgTotElapsed(((BigDecimal)tupla[23]).longValue());
        mod.setAvgTotCpu    (((BigDecimal)tupla[24]).longValue());
        mod.setAvgTotSuspend(((BigDecimal)tupla[25]).longValue());
        mod.setAvgIntElapsed(((BigDecimal)tupla[26]).longValue());
        mod.setAvgIntCpu    (((BigDecimal)tupla[27]).longValue());
        mod.setAvgIntSuspend(((BigDecimal)tupla[28]).longValue());

        sesionService.update(mod);
    }
    
    private MODVersion crearModuloNoMonitorizado(String firma, String nombre) {
        long m = Fechas.serial();
        
        SDPModulo mod = new SDPModulo();
        mod.setIdModulo(m);
        mod.setIdAppl(SYS.APPL_NOT_MONITOR);
        mod.setNombre(nombre);
        mod.setTipo(CDG.MOD_ROUTINE);
        mod.setUid(SYS.DEF_USER);
        mod.setActivo(CDG.MODULE_INACTIVE);
        mod.setIdVersion(Fechas.serial());
        moduloService.update(mod);
        
        MODVersion ver = new MODVersion();
        ver.setIdModulo(mod.getIdModulo());
        ver.setIdVersion(mod.getIdVersion());
        ver.setNombre(nombre);
        ver.setTipo(CDG.MOD_ROUTINE);
        ver.setUid(SYS.DEF_USER);
        ver.setDesc(SYS.NULL);
        versionService.update(ver);
        
        return ver;
    }

    private void actualizaArbolSesion(Object[] tupla) {
        SESArbol arbol = new SESArbol();
        arbol.setIdSesion(claves.getIdSesion());
        arbol.setIdCalling(verCalling.getIdVersion());
        arbol.setIdCalled (verCalled.getIdVersion());
        arbol.setNombre((String) tupla[2]);

        arbol.setVeces(((BigInteger)tupla[4]).longValue());

        arbol.setTotElapsed(((BigDecimal)tupla[5]).longValue());
        arbol.setTotCpu    (((BigDecimal)tupla[6]).longValue());
        arbol.setTotSuspend(((BigDecimal)tupla[7]).longValue());
        
        arbol.setAvgElapsed(((BigDecimal)tupla[23]).longValue());
        arbol.setAvgCpu    (((BigDecimal)tupla[24]).longValue());
        arbol.setAvgSuspend(((BigDecimal)tupla[25]).longValue());

        arbol.setTms(claves.getTms());
        
        sesArbolService.update(arbol);        
    }

    private void actualizaArbolModulo(Object[] tupla) {
        
        SUMArbol arbol = sumArbolService.getCall(verCalling.getIdVersion(), 
                                                 verCalled.getIdVersion(),
                                                 (String) tupla[2]);        

        if (arbol == null) {
            arbol = nuevoArbol(tupla);
        }
        else {
           arbol.setSesiones  (arbol.getSesiones() + 1);
           arbol.setVeces     (arbol.getVeces() + ((BigInteger) tupla[4]).longValue());

           arbol.setTotElapsed(arbol.getTotElapsed() + ((BigDecimal)tupla[5]).longValue());
           arbol.setTotCpu    (arbol.getTotCpu()     + ((BigDecimal)tupla[6]).longValue());
           arbol.setTotSuspend(arbol.getTotSuspend() + ((BigDecimal)tupla[7]).longValue());
        
           arbol.setAvgElapsed(arbol.getTotElapsed() / arbol.getVeces());
           arbol.setAvgCpu    (arbol.getTotCpu()     / arbol.getVeces());
           arbol.setAvgSuspend(arbol.getTotSuspend() / arbol.getVeces());

           arbol.setTms(claves.getTms());
        } 
        
        sumArbolService.update(arbol);
    }

    private SUMArbol nuevoArbol(Object[] tupla) {
        SUMArbol arbol = new SUMArbol();

        arbol.setIdCalling(verCalling.getIdVersion());
        arbol.setIdCalled (verCalled.getIdVersion());
        arbol.setNombre((String) tupla[2]);

        arbol.setSesiones(1L);
        arbol.setVeces(((BigInteger)tupla[4]).longValue());

        arbol.setTotElapsed(((BigDecimal)tupla[5]).longValue());
        arbol.setTotCpu    (((BigDecimal)tupla[6]).longValue());
        arbol.setTotSuspend(((BigDecimal)tupla[7]).longValue());
        
        arbol.setAvgElapsed(((BigDecimal)tupla[23]).longValue());
        arbol.setAvgCpu    (((BigDecimal)tupla[24]).longValue());
        arbol.setAvgSuspend(((BigDecimal)tupla[25]).longValue());

        arbol.setTms(claves.getTms());
        return arbol;
    }
}
