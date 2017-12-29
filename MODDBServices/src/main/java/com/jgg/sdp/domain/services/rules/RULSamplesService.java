package com.jgg.sdp.domain.services.rules;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.rules.RULSample;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class RULSamplesService extends AbstractService<RULSample> {

	public List<RULSample> getDescription(Long idSample) {
		return listQuery(RULSample.listSampleByType, idSample, 0);
	}
	public List<RULSample> getSampleOK(Long idSample) {
		return listQuery(RULSample.listSampleByType, idSample, 1);
	}
	public List<RULSample> getSampleKO(Long idSample) {
		return listQuery(RULSample.listSampleByType, idSample, 2);
	}
	
	public void deleteSample(Long key) {
		deleteQuery(RULSample.delSample, key);
	}

}
