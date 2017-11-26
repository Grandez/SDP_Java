package com.jgg.sdp.rules;

import com.jgg.sdp.domain.rules.*;
import com.jgg.sdp.domain.services.rules.*;

public class RulesManager {

	private RULGroupsService groupsService = new RULGroupsService();
	private RULItemsService  itemsService  = new RULItemsService();
	
	public boolean activateGroupByName(String name) {
		RULGroup g = groupsService.getByName(name);
		if (g == null) return true;
		if (g.getActive() < 0) {
			g.setActive(g.getActive() * -1);
			groupsService.update(g);
		}
		return false;
	}
	
	public boolean deactivateGroupByName(String name) {
		RULGroup g = groupsService.getByName(name);
		if (g == null) return true;
		if (g.getActive() > 0) {
			g.setActive(g.getActive() * -1);
			groupsService.update(g);
		}
		return false;
	}

	public boolean activateItemByObject(String name) {
		RULItem i = itemsService.getByObject(name);
		if (i == null) return true;
		if (i.getActive() < 0) {
			i.setActive(i.getActive() * -1);
			itemsService.update(i);
		}
		return false;
	}
	
	public boolean deactivateItemByObject(String name) {
		RULItem i = itemsService.getByObject(name);
		if (i == null) return true;
		if (i.getActive() > 0) {
			i.setActive(i.getActive() * -1);
			itemsService.update(i);
		}
		return false;
	}

}
