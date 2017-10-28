package com.jgg.sdp.rules.processor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.jgg.sdp.domain.rules.RULGroup;
import com.jgg.sdp.domain.rules.RULItem;
import com.jgg.sdp.domain.services.rules.RULItemsService;
import com.jgg.sdp.rules.xml.jaxb.*;

public class RulesItem {

	private RULItemsService itemsService = new RULItemsService();
	
	private ArrayList<RULItem> items = new ArrayList<RULItem>();

	private RulesDescription ruleDesc = RulesDescription.getInstance();
	private RulesCondition   ruleCond = RulesCondition.getInstance();
	private RulesSample      ruleSamp = RulesSample.getInstance();
	
	public RulesItem() {
		RULItemsService.setLastId(0L);
	}

	public RULItem createItem(RULGroup group, Item xmlItem) {
		long key = 0L;
		RULItem item =  null;
		Long id = xmlItem.getIdItem();
		if (id == null || id == 0) {
			item = itemsService.getByTextKey(xmlItem.getName());
		}
		else {
			item = itemsService.getById(group.getIdGroup(), id);
		}
		
		if (item == null) {
			if (id == null) id = itemsService.getNextId(group.getIdGroup());
			item = new RULItem();
			item.setIdGroup(group.getIdGroup());
			item.setIdItem(id);
			item.setObject(xmlItem.getName());
		}
		
		key = Long.parseLong(String.format("%02d%02d", item.getIdGroup(), item.getIdItem()));
		
		ConditionType cond = xmlItem.getActivateOnCondition();
		
		item.setActive(ruleCond.createCondition( key * 10,      cond, xmlItem.isActive()));
		item.setIdDesc(ruleDesc.createDescription(key, xmlItem.getDescription()));
		item.setIdSample(ruleSamp.createSample(key, xmlItem.getSample()));
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
	
}
