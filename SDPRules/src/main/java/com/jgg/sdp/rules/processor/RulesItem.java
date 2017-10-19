package com.jgg.sdp.rules.processor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.jgg.sdp.domain.rules.RULItem;
import com.jgg.sdp.domain.services.rules.RULItemsService;
import com.jgg.sdp.rules.xml.jaxb.*;

import static com.jgg.sdp.rules.CDGRules.*;

public class RulesItem {

	private RULItemsService itemsService = new RULItemsService();
	
	private ArrayList<RULItem> items = new ArrayList<RULItem>();

	private RulesDescription ruleDesc = RulesDescription.getInstance();
	
	public RULItem createItem(Long idGroup, Item xmlItem) {
		RULItem item =  null;
		Long id = xmlItem.getIdItem();
		if (id == null || id == 0) {
			item = itemsService.getByTextKey(xmlItem.getName());
		}
		else {
			item = itemsService.getById(idGroup, id);
		}
		
		if (item == null) {
			if (id == null) id = itemsService.getNextId(idGroup);
			item = new RULItem();
			item.setIdGroup(idGroup);
			item.setIdItem(id);
			item.setObject(xmlItem.getName());
		}
		
		ConditionType cond = xmlItem.getActivateOnCondition();
		if (cond != null) {
			item.setActive(processCondition(cond));
		}
		else {
			item.setActive(xmlItem.isActive() ? ACTIVE : INACTIVE);
		}
		
		item.setIdDesc(ruleDesc.createDescription(mountKey(item), xmlItem.getDescription()));
		item.setUid(System.getProperty("user.name"));
		item.setTms(new Timestamp(System.currentTimeMillis()));
		items.add(item);
		return item;
	}

	public RULItem createItem(Item xmlItem) {
		return new RULItem();
	}

	public List<RULItem> getItems() {
		return items;
	}

	private Long processCondition(ConditionType cond) {
		RulesCondition c = RulesCondition.getInstance();
		return c.createCondition(cond);
	}
	
	private Long mountKey(RULItem item) {
		return Long.parseLong(String.format("%02d%02d", item.getIdGroup(), item.getIdItem()));
	}
	
}
