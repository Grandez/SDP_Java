package com.jgg.sdp.domain.services.rules;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.rules.RULSampleDesc;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class RULSampleDescService extends AbstractService<RULSampleDesc> {

	public void deleteSampleDesc(Long idDesc) {
		deleteQuery(RULSampleDesc.delSampleDescription, idDesc);
	}
}
