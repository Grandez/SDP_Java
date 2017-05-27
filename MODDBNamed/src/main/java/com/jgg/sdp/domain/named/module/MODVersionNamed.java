package com.jgg.sdp.domain.named.module;

import javax.persistence.*;

import com.jgg.sdp.domain.module.MODVersion;
import com.jgg.sdp.domain.services.AbstractService;

@NamedQueries({
    @NamedQuery( name="MODVersion.findByVersion" 
                ,query="Select v FROM MODVersion v " +
                       "         WHERE v.idVersion = ?1 ")

   ,@NamedQuery( name="MODVersion.VersionesPorModulo" 
                ,query="Select v FROM MODVersion v " +
                               " WHERE v.idModulo = ?1 " +
         		               " ORDER BY idVersion DESC")
})
public class MODVersionNamed extends AbstractService<MODVersion>{

	public MODVersion find(long idVersion) {
		return super.find("findByVersion", idVersion);
	}
}
