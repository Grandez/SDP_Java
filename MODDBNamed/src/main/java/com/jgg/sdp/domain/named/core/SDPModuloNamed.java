package com.jgg.sdp.domain.named.core;

import java.util.*;

import javax.persistence.*;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.core.SDPModulo;
import com.jgg.sdp.domain.services.AbstractService;

@NamedQueries({
	@NamedQuery( name="SDPModulo.ModulosPorAplicacion" 
                ,query="Select m FROM SDPModulo m WHERE m.idAppl = ?1 AND m.activo = 1 ORDER BY m.nombre")
   ,@NamedQuery( name="SDPModulo.CuentaPorAplicacion" 
                ,query="Select COUNT(m.idAppl) FROM SDPModulo m WHERE m.idAppl = ?1 AND m.activo = 1")
   ,@NamedQuery( name="SDPModulo.ModulosModificados" 
                ,query="Select m FROM SDPModulo m WHERE m.tms > ?1")
   ,@NamedQuery( name="SDPModulo.ModulosActivos" 
                ,query="Select m FROM SDPModulo m WHERE m.activo = 1")
   ,@NamedQuery( name="SDPModulo.find"
                ,query="SELECT m FROM SDPModulo m WHERE m.idModulo = ?1")   
   ,@NamedQuery( name="SDPModulo.findByName"
                ,query="SELECT m FROM SDPModulo m WHERE m.nombre = ?1")   
   ,@NamedQuery( name="SDPModulo.updateVersion"
                ,query="UPDATE SDPModulo m SET m.idVersion = ?2 WHERE idModulo = ?1")   
   ,@NamedQuery( name="SDPModulo.ModulosPorPseudoMascara" 
                ,query="Select m FROM SDPModulo m WHERE m.nombre LIKE ?1")
	
})

@Repository
public class SDPModuloNamed extends AbstractService<SDPModulo> {
	public List<SDPModulo> getModulesByAppl(Long idAppl) {
        return listRecords("Select m FROM SDPModulo m WHERE m.idAppl = ?1 AND m.activo = 1 ORDER BY m.nombre", idAppl);
	      //return list("ModulosPorAplicacion", idAppl); 	
	}


    public Set<Long> getConjuntoActivo(Long id) {
        Set<Long> datos = new HashSet<Long>();
        for (SDPModulo m : getModulesByAppl(id)) {
            datos.add(m.getIdModulo());    
        }
        return datos;
    }

}
