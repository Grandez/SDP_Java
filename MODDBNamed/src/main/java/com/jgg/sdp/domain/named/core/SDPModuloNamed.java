package com.jgg.sdp.domain.named.core;

import javax.persistence.*;

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

public class SDPModuloNamed {
}
