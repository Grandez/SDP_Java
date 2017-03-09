/**
 * Monta el arbol de llamadas de un modulos
 * 
 * Gestiona dos estructuras:
 *    Resumen de modulos
 *    Arbol de llamadas
 *   
 * @author Javier Gonzalez Grandez
 * @version 4.1
 *   
 */

package com.jgg.sdp.excel.data;

import java.util.*;

import com.jgg.sdp.core.ctes.CDG;
import com.jgg.sdp.domain.core.SDPModulo;
import com.jgg.sdp.domain.module.MODCall;
import com.jgg.sdp.domain.module.MODVersion;
import com.jgg.sdp.domain.services.core.SDPModuloService;
import com.jgg.sdp.domain.services.module.MODCallService;
import com.jgg.sdp.domain.services.module.MODVersionService;

public class XLSDataTree implements XLSDataRecord {

 
	private MODCallService    callService = new MODCallService();
	private SDPModuloService  modService  = new SDPModuloService();
	private MODVersionService verService  = new MODVersionService();
	
	// Contiene el arbol de llamadas
	private XLSRoutine                  root = null;
		
	// Lista de rutinas
	private ArrayList<XLSRoutine>       ruts = new ArrayList<XLSRoutine>();
	
	// Mapeo de las rutinas a su posicion en la lista
	private HashMap<String, Integer>    map  = new HashMap<String, Integer>();
	
	// Mantiene el arbol actual para evitar recursividad
    private HashSet<String>             path = null;

    private int pos = -1;
    private XLSRoutine current = null;
    
    private int maxLevel = 0;
    private int tipo     = 0; // 0 - Resumen, 1 - detalle
    private int maxDepth = 0;
    
    public XLSDataTree(int tipo, int level) {
    	maxLevel = level;
    	this.tipo = tipo;
    }
    
	public boolean open(String table, Long key) {
        MODVersion ver = verService.getByVersion(key);
        if (ver == null) {
        	root.setModo(CDG.DEP_ST_NOT_PARSED);
        	return false;
        }

        boolean tree = (tipo == 0) ? false : true;
        
        generateTree(ver.getNombre(), tree);

        pos = -1;
		return false;
	}

	public boolean next() {
		if (++pos >= ruts.size()) {
			return false;
		}
		
		current = ruts.get(pos);
		return true;
	}

	public boolean close() {
        pos = -1;
		return false;
	}

	public Object getValue(String member) {
        if (current == null) return null;
        if (member.compareTo("MAX_LEVEL") == 0) return maxDepth;
		return current.getByName(member);
	}
    
	public int getLevel() {
		if (current == null) return 0;
		return current.getLevel();
	}

	private XLSRoutine generateTree(String modulo, boolean tree) {
		root = createRoot(modulo, tree);
		path = new HashSet<String>();
		
		addToTree(root, tree);
		addToPath(root);
		
		generateSubTree(0, root, tree);
		
        return root;
	}

	private void generateSubTree(int nivel, XLSRoutine root, boolean tree) {
		
		if (nivel > maxLevel) return;	
		if (nivel > maxDepth) maxDepth = nivel;
				
        SDPModulo mod = modService.findByNameAndType(root.getNombre(), CDG.SOURCE_CODE);
        
        if (mod == null) {
        	if (tree) root.setModo(CDG.DEP_ST_NOT_PARSED);
        	return;
        }
        
		for (MODCall dep : callService.getListCall(mod.getIdVersion())) {
        	XLSRoutine rut = createRoutine(dep, nivel + 1, true);
    		addToTree(rut, tree);
    		if (addToPath(rut)) return; // Recursivo

        	if ((nivel + 1) < maxLevel) {
        		if (rut.getStatus() != CDG.DEP_ST_VARIABLE) {
                	generateSubTree(nivel + 1, rut, tree);
                }	
        	}
        }
    	removeFromPath(root.getNombre());
	}
	
	private XLSRoutine createRoutine(MODCall call, int nivel, boolean tree) {
		XLSRoutine rut = new XLSRoutine(call.getModulo());
		
		rut.setReferences(call.getRefs());
		rut.setModo(call.getModo());
		rut.setMetodo(call.getMetodo());
		rut.setLevel(nivel);
		rut.setStatus(call.getEstado());
		rut.setEjecutado(call.getEjecutado());

		if (call.getEstado() == CDG.DEP_ST_VARIABLE) return rut;
		
		SDPModulo mod = modService.findByNameAndType(call.getModulo(), CDG.SOURCE_CODE);
		if (mod == null) {
			if (tree) rut.setStatus(CDG.DEP_ST_NOT_PARSED);
		}
		else {
			rut.setId(mod.getIdModulo());
		}		
		return rut;
	}
	
	private XLSRoutine createRoot(String name, boolean tree) {
		MODCall call = new MODCall();
		call.setModulo(name);
		call.setEstado(CDG.DEP_ST_UNDEF);
		call.setMetodo(CDG.CALL_UNDEF);
		call.setModo(CDG.CALL_NO_DEF);
		call.setRefs(0);
		call.setIdVersion(0L);
		call.setEjecutado(0);
		
		return createRoutine(call, 0, tree);
	}

	private void addToTree(XLSRoutine rut, boolean tree) {
		if (tree) {
			ruts.add(rut);
			return;
		}
		
		if (rut.getLevel() == 0) return;
		
		Integer pos = map.get(rut.getNombre());
	
		if (pos == null) {
			ruts.add(rut);
			map.put(rut.getNombre(), ruts.size() - 1);
			return;
		}
		
		XLSRoutine old = ruts.get(pos);
		old.addReferences(rut.getReferences());
		old.setLevel(rut.getLastLevel());
		old.combineModo(rut.getModo());
		old.combineMetodo(rut.getMetodo());
	}

	
	private boolean addToPath(XLSRoutine rut) {
		if (path.contains(rut.getNombre())) {
			rut.setRecursive();
			rut.addReferences(1);
			return true;
		}
		path.add(rut.getNombre());
		return false;
	}
	
	private void removeFromPath(String name) {
		path.remove(name);
	}

}
