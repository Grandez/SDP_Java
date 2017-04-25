/*
 * 
 */
package com.jgg.sdp.collector.process;

import java.sql.Timestamp;

import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.traps.*;
import com.jgg.sdp.domain.traps.*;
import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.exceptions.SDPException;
// import com.jgg.sdp.core.jms.*;
import com.jgg.sdp.core.msg.Messages;

public class Trapper implements IProcess {

	private final int TIPO    = 0;
	private final int ORDEN   = 1;
	private final int SESION  = 2;
	private final int MODULO  = 3;

    private final int FLG_COVER = 4;
    private final int FLG_PERS  = 4;
    private final int FLG_PARR  = 4;
    private final int LABEL     = 4;
    
	private final int MOD_USER    = 4;
//  private final int MOD_TMS     = 5;
	private final int MOD_ELAPSED = 6;
    private final int MOD_CPU     = 7;

    private final int CALL_CALLING     =  3;
    private final int CALL_CALLED      =  4;
    private final int CALL_NAME        =  5;
    private final int CALL_VECES       =  6;
    private final int CALL_TOT_ELAPSED =  7;
    private final int CALL_TOT_CPU     =  8;    
    private final int CALL_INT_ELAPSED =  9;
    private final int CALL_INT_CPU     = 10;    

    private final int TRAP_VECES       =  5;
    private final int TRAP_TOT_ELAPSED =  6;
    private final int TRAP_TOT_CPU     =  7;    
    private final int TRAP_INT_ELAPSED =  8;
    private final int TRAP_INT_CPU     =  9;    
    
//	private JMSQueue qInput;
//	private JMSQueue qOutput;

	private Configuration cfg = Configuration.getInstance();

	private SDPModuloService          moduloService   = new SDPModuloService();
	
	private TRPSesionService       sesionService   = new TRPSesionService();
	private TRPCoberturaService    coverService    = new TRPCoberturaService();
	private TRPPersistenciaService persService     = new TRPPersistenciaService();	
	private TRPCallService         callService     = new TRPCallService();	
	private TRPParrafoService      parrafoService  = new TRPParrafoService();
    private TRPModuloService       modService      = new TRPModuloService();

    private TRPParrWorkingService  parrWorkingService  = new TRPParrWorkingService();
    
	public int process() {
		initSystems();
//		String txt = qInput.get();
		String txt = null;
		while (txt != null) {
			String[] msgs = txt.split(":"); 
			for (int idx = 0; idx < msgs.length; idx++) {
			    processMessage(msgs[idx].split(";"));
			}
//			txt = qInput.get();
		}	
		endSystems();
		return 0;
	}

	private void processMessage(String[] toks) {
		switch (Integer.parseInt(toks[TIPO])) {
	        case TRAP.BEG_SESSION: 
	        	 processBeginMensaje(toks);
	             break;		 
	        case TRAP.END_SESSION:  
	        	 processEndMensaje(toks);
	             break;
	        case TRAP.MODULE:
	        	 processModule(toks);
	        	 break;
	        case TRAP.PARAGRAPH:
	             processParrafo(toks);
	             break;
	        case TRAP.CALL:
	        	 processCall(toks);
	        	 break;
            case TRAP.COVER: 
     	         processCobertura(toks); 
	             break;
            case TRAP.PARRWRK:
            	 processParrWorking(toks);
            	 break;
            case TRAP.PERS: 
   	             processPersistencia(toks); 
	             break;
	    }
		
	}
	
	private void processBeginMensaje(String[] toks) {
 
		TRPSesion eje = new TRPSesion();
		eje.setIdSesion(toks[SESION]);
		eje.setIdModulo(toks[MODULO]);
		eje.setUid(toks[MOD_USER]);
		
		eje.setElapsed(Long.parseLong(toks[MOD_ELAPSED]));
		eje.setCpu(Long.parseLong(toks[MOD_CPU]));	

		if (eje.getCpu() > eje.getElapsed()) eje.setElapsed(eje.getCpu()); 
		
		eje.setSuspend(eje.getElapsed() - eje.getCpu());
		eje.setTms(new Timestamp(System.currentTimeMillis()));
        eje.setFinished(0);
        eje.setLeido(0L);
        eje.setEscrito(0L);
		sesionService.update(eje);
	}

	private void processEndMensaje(String[] toks) {
		TRPSesion eje = sesionService.findBySession(toks[SESION]);
		
		// Los tiempos tomados al inicio mas los consumidos hasta el momento
		eje.setElapsed(eje.getElapsed() + Long.parseLong(toks[MOD_ELAPSED]));
		eje.setCpu(eje.getCpu() + Long.parseLong(toks[MOD_CPU]));
		
		if (eje.getCpu() > eje.getElapsed()) eje.setElapsed(eje.getCpu());
	      
		eje.setSuspend(eje.getElapsed() - eje.getCpu());
		eje.setFinished(1);
		eje.setLeido(0L);
		eje.setEscrito(0L);
		sesionService.update(eje);
        
//		qOutput.put(toks[SESION]);

		sesionService.commitTrans();
		sesionService.beginTrans();
	}

	private void processCobertura(String[] toks) {
        TRPCobertura cover = new TRPCobertura();
		
        cover.setIdSesion(toks[SESION]);
        cover.setIdModulo(toks[MODULO]);
        cover.setOrden(Long.parseLong(toks[ORDEN]));
        cover.setFlags(toks[FLG_COVER]);
        coverService.update(cover);
	}

	private void processParrWorking(String[] toks) {
        TRPParrWorking parr = new TRPParrWorking();
        parr.setIdSesion(toks[SESION]);
        parr.setIdModulo(toks[MODULO]);
        parr.setOrden(Long.parseLong(toks[ORDEN]));
        parr.setDatos(toks[FLG_PARR]);
        parrWorkingService.update(parr);
	}
	
	private void processPersistencia(String[] toks) {
        TRPPersistencia pers = new TRPPersistencia();
		
        pers.setIdSesion(toks[SESION]);
        pers.setIdModulo(toks[MODULO]);
        pers.setOrden(Long.parseLong(toks[ORDEN]));
        pers.setFlags(toks[FLG_PERS]);
        persService.update(pers);
	}

	private void processCall(String[] toks) {
        TRPCall call = new TRPCall();
		
        call.setIdSesion(toks[SESION]);
        call.setOrden(Long.parseLong(toks[ORDEN]));
        
        call.setIdCalling(toks[CALL_CALLING]);
        call.setIdCalled(toks[CALL_CALLED]);
        call.setModulo(toks[CALL_NAME]);
        
        call.setVeces(Long.parseLong(toks[CALL_VECES]));
        call.setTotElapsed(Long.parseLong(toks[CALL_TOT_ELAPSED]));
        call.setTotCpu(Long.parseLong(toks[CALL_TOT_CPU]));
        
        if (call.getTotCpu() > call.getTotElapsed()) {
            call.setTotElapsed(call.getTotCpu()); 
        }
        call.setTotSuspend(call.getTotElapsed() - call.getTotCpu());

        call.setIntElapsed(Long.parseLong(toks[CALL_INT_ELAPSED]));
        call.setIntCpu(Long.parseLong(toks[CALL_INT_CPU]));
        
        if (call.getIntCpu() > call.getIntElapsed()) {
            call.setIntElapsed(call.getIntCpu()); 
        }
        call.setIntSuspend(call.getIntElapsed() - call.getIntCpu());
        
        callService.update(call);
	}

	private void processModule(String[] toks) {

		TRPModulo mod = new TRPModulo();
		mod.setIdSesion(toks[SESION]);
		mod.setIdModulo(toks[MODULO]);
		mod.setOrden(Long.parseLong(toks[ORDEN]));
		mod.setModulo(toks[LABEL]);
		
		mod.setVeces(Long.parseLong(toks[TRAP_VECES]));
		mod.setTotElapsed(Long.parseLong(toks[TRAP_TOT_ELAPSED]));
		mod.setTotCpu(Long.parseLong(toks[TRAP_TOT_CPU]));	
		mod.setTotSuspend(mod.getTotElapsed() - mod.getTotCpu());

		mod.setIntElapsed(Long.parseLong(toks[TRAP_INT_ELAPSED]));
		mod.setIntCpu(Long.parseLong(toks[TRAP_INT_CPU]));	
		mod.setIntSuspend(mod.getIntElapsed() - mod.getIntCpu());

		// A veces, debido a la falta de precision y redondeos se pueden
		// producir valores negativos
		if (mod.getTotSuspend() < 0) mod.setTotSuspend(0L);
		if (mod.getIntSuspend() < 0) mod.setIntSuspend(0L);
		if (mod.getTotSuspend() < 0) mod.setTotSuspend(0L);
		
		modService.update(mod);
	}

    private void processParrafo(String[] toks) {

        TRPParrafo parr = new TRPParrafo();
        parr.setIdSesion(toks[SESION]);
        parr.setIdModulo(toks[MODULO]);
        parr.setOrden(Long.parseLong(toks[ORDEN]));
        parr.setEtiqueta(toks[LABEL]);
        
        parr.setVeces(Long.parseLong(toks[TRAP_VECES]));
        parr.setTotElapsed(Long.parseLong(toks[TRAP_TOT_ELAPSED]));
        parr.setTotCpu(Long.parseLong(toks[TRAP_TOT_CPU]));
        
        if (parr.getTotCpu() > parr.getTotElapsed()) {
            parr.setTotElapsed(parr.getTotCpu());
        }
        parr.setTotSuspend(parr.getTotElapsed() - parr.getTotCpu());

        parr.setIntElapsed(Long.parseLong(toks[TRAP_INT_ELAPSED]));
        parr.setIntCpu(Long.parseLong(toks[TRAP_INT_CPU]));  

        if (parr.getIntCpu() > parr.getIntElapsed()) {
            parr.setIntElapsed(parr.getIntCpu());
        }

        parr.setIntSuspend(parr.getIntElapsed() - parr.getIntCpu());
 
        parrafoService.update(parr);
    }

	private void initSystems() {
	    Messages.setLogger("COLLECTOR.TRAPPER");
/*	    
		qInput = JMSFactory.getJMSQueue();
		String qname = cfg.getQueue();
		if (qname == null) {
			qname = cfg.getQueueTrapper();
			if (qname == null) throw new SDPException(MSG.ERR_NO_QUEUE);
		}
		qInput.openInput(qname);
		
		qOutput = JMSFactory.getJMSQueue();
		qname = cfg.getOutputQueue();
		if (qname == null) {
			qname = cfg.getQueueCollector();
			if (qname == null) {
				throw new SDPException(MSG.ERR_NO_QUEUE_OUTPUT);
			}
		}
		qOutput.openOutput(qname);
*/
		moduloService.beginTrans();
	}

	private void endSystems() {
//		qInput.close();
//		qOutput.close();
		moduloService.commitTrans();
	}


}
