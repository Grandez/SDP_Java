package com.jgg.sdp.ivp.base;

import com.jgg.sdp.ivp.jaxb.IVPBlock;

public class IVPEnvironment {

	private IVPConfig cfg;
/*	
	public void setEnvironment(int block, IVPConfig cfg, IVPEnvType envs) {
		this.cfg = cfg;
		
		if (envs == null) return;
		
		for (Block b : envs.getBlock()) {
			if (b.getId() == block) {
				setEnv(b);
				return;
			}
		}
	}
	*/
	private void setEnv(IVPBlock block) {
		
	}
}
