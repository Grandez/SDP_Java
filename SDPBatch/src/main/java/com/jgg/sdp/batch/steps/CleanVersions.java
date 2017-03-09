package com.jgg.sdp.batch.steps;

import java.sql.Timestamp;
import java.util.*;

import com.jgg.sdp.domain.services.module.MODVersionService;

public class CleanVersions {

	private int maxVersions = 1;
	
	private MODVersionService versionService = new MODVersionService();
	
	public void setMaxVersions(int maxVersions) {
		this.maxVersions = maxVersions;
	}
	
	public void process() {
		List<Timestamp> lstTms = null;
		versionService.beginTrans();
		for (Long idModulo : versionService.getListOfModulesWithVersionsExceeded(maxVersions)) {
			lstTms = versionService.getTimestamps(idModulo);
			if (lstTms.size() > maxVersions) {
				versionService.deleteByTimestamp(lstTms.get(maxVersions));
			}
		}
		versionService.commitTrans();
	}
}
