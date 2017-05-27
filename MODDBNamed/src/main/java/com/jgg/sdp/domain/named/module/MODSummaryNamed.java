package com.jgg.sdp.domain.named.module;

import javax.persistence.*;

import com.jgg.sdp.domain.module.MODSummary;
import com.jgg.sdp.domain.services.AbstractService;

@NamedQueries({
    @NamedQuery( name="MODSummary.find"
                ,query="SELECT m FROM MODSummary m WHERE m.idVersion = ?1")   
})

public class MODSummaryNamed extends AbstractService<MODSummary> {

	public MODSummary find(long idVersion) {
		return super.find(idVersion);
	}
}
