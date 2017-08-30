package com.jgg.sdp.domain.services.cfg;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.cfg.ATest;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class ATestService extends AbstractService<ATest> {

	public void createEntityManager() {
		super.createEntityManager();
	}
	public List<ATest> todo() {
		return listQuery(ATest.listAll);
	}
}
