/*
 * 
 */
package com.jgg.sdp.collector.consolidator;

import java.util.List;

import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.domain.services.log.*;
import com.jgg.sdp.domain.services.module.*;
import com.jgg.sdp.domain.services.session.*;
import com.jgg.sdp.domain.services.summary.*;
import com.jgg.sdp.domain.session.*;
import com.jgg.sdp.domain.summary.*;
import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.*;

public class ConsSummarizer {

    private MODVersionService versionService = new MODVersionService();
    
    private SESParrafoService sesParrService = new SESParrafoService();
    private SESIOService      ioService      = new SESIOService();
    
    private SUMModuloService  sumModService  = new SUMModuloService();
    private SUMParrafoService sumParrService = new SUMParrafoService();
    private LOGInputService   logService     = new LOGInputService();
    
    private Configuration cfg = Configuration.getInstance();

    private ConsClaves claves;
    
    /**
     * Summarize modulo.
     *
     * @param mod
     *            the mod
     * @param claves
     *            the claves
     */
    public void summarizeModulo(SESModulo mod, ConsClaves claves) {
        this.claves = claves;
        
        SUMModulo sum   = sumModService.find(mod.getIdVersion());
        SESIO     sesio = ioService.find(mod.getIdSesion(), mod.getIdVersion());
        
        if (sum == null) {
            sum = createSumModulo(mod,sesio);
        }
        else {
            validateTiempos(sum, mod, sesio);
            updateSumModulo(sum, mod, sesio);
        }
        sumModService.update(sum);
    }

    private SUMModulo createSumModulo(SESModulo mod, SESIO sesio) {
        SUMModulo sum = new SUMModulo();
        
        MODVersion ver = versionService.getByVersion(mod.getIdVersion());

        if (ver == null) {
            sum.setIdModulo(0L);
            sum.setNombre(mod.getNombre());
        }
        else {
            sum.setIdModulo (ver.getIdModulo());
            sum.setNombre   (ver.getNombre());
        }
        
        sum.setIdVersion(mod.getIdVersion());

        sum.setVeces    (mod.getVeces());

        sum.setAvgElapsed(mod.getAvgTotElapsed());
        sum.setAvgCpu    (mod.getAvgTotCpu());
        sum.setAvgSuspend(mod.getAvgTotSuspend());

        sum.setMinElapsed(mod.getMinTotElapsed());
        sum.setMinCpu    (mod.getMinTotCpu());
        sum.setMinSuspend(mod.getMinTotSuspend());

        sum.setMaxElapsed(mod.getMaxTotElapsed());
        sum.setMaxCpu    (mod.getMaxTotCpu());
        sum.setMaxSuspend(mod.getMaxTotSuspend());


        if (sesio == null) {
            sum.setAvgLeido(0L);
            sum.setAvgEscrito(0L);
            sum.setMinLeido(0L);
            sum.setMinEscrito(0L);
            sum.setMaxLeido(0L);
            sum.setMaxEscrito(0L);
        }
        else {
            sum.setAvgLeido(sesio.getAcRead());
            sum.setAvgEscrito(sesio.getAcInsert());
            sum.setMinLeido(sesio.getAcRead());
            sum.setMinEscrito(sesio.getAcInsert());
            sum.setMaxLeido(sesio.getAcRead());
            sum.setMaxEscrito(sesio.getAcInsert());
        }
        return sum;
    }
    
    
    private SUMModulo updateSumModulo(SUMModulo sum, SESModulo mod, SESIO sesio) {

        Long veces = sum.getVeces() + mod.getVeces();
        
        sum.setAvgElapsed(((sum.getAvgElapsed() * sum.getVeces()) + mod.getTotElapsed()) / veces);
        sum.setAvgCpu    (((sum.getAvgCpu()     * sum.getVeces()) + mod.getTotCpu())     / veces);
        sum.setAvgSuspend(((sum.getAvgSuspend() * sum.getVeces()) + mod.getTotSuspend()) / veces);
        
        if (sum.getMinElapsed() > mod.getMinTotElapsed()) sum.setMinElapsed(mod.getMinTotElapsed());
        if (sum.getMaxElapsed() < mod.getMaxTotElapsed()) sum.setMaxElapsed(mod.getMaxTotElapsed());

        if (sum.getMinCpu() > mod.getMinTotCpu()) sum.setMinCpu(mod.getMinTotCpu());
        if (sum.getMaxCpu() < mod.getMaxTotCpu()) sum.setMaxCpu(mod.getMaxTotCpu());

        if (sum.getMinSuspend() > mod.getMinTotSuspend()) mod.setMinTotSuspend(mod.getMinTotSuspend());
        if (sum.getMaxSuspend() < mod.getMaxTotSuspend()) mod.setMaxTotSuspend(mod.getMaxTotSuspend());

        if (sesio != null) {
           if (sum.getMinLeido()   > sesio.getAcRead())   sum.setMinLeido  (sesio.getAcRead());
           if (sum.getMaxLeido()   < sesio.getAcRead())   sum.setMaxLeido  (sesio.getAcRead());
           if (sum.getMinEscrito() > sesio.getAcInsert()) sum.setMinEscrito(sesio.getAcInsert());
           if (sum.getMaxEscrito() < sesio.getAcInsert()) sum.setMaxEscrito(sesio.getAcInsert());
           sum.setAvgLeido  (((sum.getAvgLeido()   * sum.getVeces()) + sesio.getAcRead())   / (sum.getVeces() + 1)) ;
           sum.setAvgEscrito(((sum.getAvgEscrito() * sum.getVeces()) + sesio.getAcInsert()) / (sum.getVeces() + 1)) ;
        }

        sum.setVeces(veces);

        return sum;
    }
    
    private void validateTiempos (SUMModulo sum, SESModulo mod, SESIO sesio) {
        int logCode;
        long desv;
        long den;
        double aux;
        
        LOGData logData = montaLogData();
        
        Integer maxDesviacion = cfg.getInteger(CFG.DESV_ELAPSED);
        Long valor = sum.getAvgElapsed() * (1 + (maxDesviacion / 100));
        if (valor < mod.getAvgTotElapsed()) {
            den = (sum.getAvgElapsed() == 0) ? 1 : sum.getAvgElapsed();
            aux = (mod.getAvgTotElapsed() * 1.0) / den;
            desv = (long) (aux * 100);
            desv = desv % 100;
            
            logData.setParm(0,  desv);
            logService.registra(LOG.SES_ELAPSED, logData);
        }
        valor = sum.getAvgSuspend() * (1 + (maxDesviacion / 100));
        if (valor < mod.getAvgTotSuspend()) {
            den = (sum.getAvgSuspend() == 0) ? 1 : sum.getAvgSuspend();
            aux = (mod.getAvgTotSuspend() * 1.0) / den;
            desv = (long) (aux * 100);
            desv = desv % 100;

            logData.setParm(0,  desv);
            logService.registra(LOG.SES_SUSPEND, logData);
        }
        
        if (sesio == null) return;
        
        maxDesviacion = cfg.getInteger(CFG.DESV_INPUT);

        if (sesio.getAcRead() > 0 && sum.getAvgLeido() > sesio.getAcRead()) {
            aux = (sum.getAvgLeido() * 1.0 / sesio.getAcRead());
            valor = (long) (aux * 100);        
            valor = valor % 100;
            logCode = LOG.SES_LEIDO_MENOS;
        }
        else {
            valor =  (sum.getAvgLeido() == 0) ? 1 : sum.getAvgLeido();
            aux = (sesio.getAcRead() * 1.0) / valor;
            valor = (long) (aux * 100);
            valor = valor % 100;
            logCode = LOG.SES_LEIDO_MAS;
        }
        if (valor > maxDesviacion) {
            logData.setParm(0,  valor);
            logService.registra(logCode, logData);            
        }

        maxDesviacion = cfg.getInteger(CFG.DESV_OUTPUT);
 
        if (sesio.getAcInsert() > 0 && sum.getAvgEscrito() > sesio.getAcInsert()) {
            aux = (sum.getAvgEscrito() * 1.0) / sesio.getAcInsert();
            valor = (long) valor * 100;
            valor = valor % 100;
            logCode = LOG.SES_ESCRITO_MENOS;
        }
        else {
            valor = sum.getAvgEscrito() == 0 ? 1 : sum.getAvgEscrito();
            aux = (sesio.getAcInsert() * 1.0) / valor;
            valor = (long) (aux * 100);
            valor = valor % 100;
            logCode = LOG.SES_ESCRITO_MAS;
        }
        if (valor > maxDesviacion) {
            logData.setParm(0,  valor);
            logService.registra(logCode, logData);            
        }
        
    }

    /**
     * Summarize parrafos.
     *
     * @param idVersion
     *            the id version
     * @param claves
     *            the claves
     */
    public void summarizeParrafos(Long idVersion, ConsClaves claves) {
        int idxSes = 0;
        this.claves = claves;
        List<SUMParrafo> lstSum = sumParrService.getParrafosByName(idVersion);
        List<SESParrafo> lstSes = sesParrService.getParrafosByName(claves.getIdSesion(), idVersion);
        
        for (int index = 0; index < lstSum.size(); index++) {
            SUMParrafo sum = lstSum.get(index); 
            for (idxSes = 0 ; idxSes < lstSes.size(); idxSes++) {
                if (sum.getNombre().compareTo(lstSes.get(idxSes).getNombre()) == 0) break;
            }
            if (idxSes < lstSes.size()) summarizeParrafo(sum, lstSes.get(idxSes)); 
        }
    }

    private void summarizeParrafo(SUMParrafo sum, SESParrafo ses) {
        long veces = sum.getVeces() + ses.getVeces();
        if (veces == 0) veces = 1;
                   
        if (ses.getVeces() > 0) {
            if (ses.getMaxTotElapsed() > sum.getMaxTotElapsed()) sum.setMaxTotElapsed(ses.getMaxTotElapsed());
            if (ses.getMaxTotCpu()     > sum.getMaxTotCpu())     sum.setMaxTotCpu    (ses.getMaxTotCpu());
            if (ses.getMaxTotSuspend() > sum.getMaxTotSuspend()) sum.setMaxTotSuspend(ses.getMaxTotSuspend());                
            if (ses.getMaxIntElapsed() > sum.getMaxIntElapsed()) sum.setMaxIntElapsed(ses.getMaxIntElapsed());
            if (ses.getMaxIntCpu()     > sum.getMaxIntCpu())     sum.setMaxIntCpu    (ses.getMaxIntCpu());
            if (ses.getMaxIntSuspend() > sum.getMaxIntSuspend()) sum.setMaxIntSuspend(ses.getMaxIntSuspend());                

            if (ses.getMinTotElapsed() < sum.getMinTotElapsed()) sum.setMinTotElapsed(ses.getMinTotElapsed());
            if (ses.getMinTotCpu()     < sum.getMinTotCpu())     sum.setMinTotCpu    (ses.getMinTotCpu());
            if (ses.getMinTotSuspend() < sum.getMinTotSuspend()) sum.setMinTotSuspend(ses.getMinTotSuspend());                
            if (ses.getMinIntElapsed() < sum.getMinIntElapsed()) sum.setMinIntElapsed(ses.getMinIntElapsed());
            if (ses.getMinIntCpu()     < sum.getMinIntCpu())     sum.setMinIntCpu    (ses.getMinIntCpu());
            if (ses.getMinIntSuspend() < sum.getMinIntSuspend()) sum.setMinIntSuspend(ses.getMinIntSuspend());                
            
        }

        sum.setTotElapsed(sum.getTotElapsed() + ses.getTotElapsed());
        sum.setTotCpu    (sum.getTotCpu()     + ses.getTotCpu());
        sum.setTotSuspend(sum.getTotSuspend() + ses.getTotSuspend());

        sum.setIntElapsed(sum.getIntElapsed() + ses.getIntElapsed());
        sum.setIntCpu    (sum.getIntCpu()     + ses.getIntCpu());
        sum.setIntSuspend(sum.getIntSuspend() + ses.getIntSuspend());

        sum.setTotElapsed(sum.getTotElapsed() + ses.getTotElapsed());
        sum.setTotCpu    (sum.getTotCpu()     + ses.getTotCpu());
        sum.setTotSuspend(sum.getTotSuspend() + ses.getTotSuspend());

        sum.setAvgTotElapsed(sum.getTotElapsed() / veces);
        sum.setAvgTotCpu    (sum.getTotCpu()     / veces);
        sum.setAvgTotSuspend(sum.getTotSuspend() / veces);
        
        sum.setAvgIntElapsed(sum.getIntElapsed() / veces);
        sum.setAvgIntCpu    (sum.getIntCpu()     / veces);
        sum.setAvgIntSuspend(sum.getIntSuspend() / veces);
        
        sum.setVeces(sum.getVeces() + ses.getVeces());
        sum.setSesiones(sum.getSesiones() + 1);
        sumParrService.update(sum);
    }
    
    private LOGData montaLogData() {
        LOGData data = new LOGData();
        
        data.setIdAppl(claves.getModAppl());
        data.setIdModulo(claves.getModId());
        data.setIdVersion(claves.getModVersion());
        data.setNombre(claves.getModNombre());
        data.setTms(claves.getTms());
        data.setUid(claves.getUid());
        return data;
    }
}
