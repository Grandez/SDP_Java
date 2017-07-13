package com.jgg.sdp.domain.named.module;

import javax.persistence.*;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.module.MODSummary;
import com.jgg.sdp.domain.services.AbstractService;

@NamedQueries({
    @NamedQuery( name="MODSummary.find"
                ,query="SELECT m FROM MODSummary m WHERE m.idVersion = ?1")   
})

@Repository
public class MODSummaryNamed extends AbstractService<MODSummary> {

	public MODSummary find(long idVersion) {
		return getRecord("SELECT m FROM MODSummary m WHERE m.idVersion = ?1", idVersion);
//		return super.find(idVersion);
	}
}
