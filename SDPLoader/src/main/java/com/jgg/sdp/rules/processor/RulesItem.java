package com.jgg.sdp.rules.processor;

import java.sql.Timestamp;

import com.jgg.sdp.adt.SDPBag;
import com.jgg.sdp.domain.rules.RULGroup;
import com.jgg.sdp.domain.rules.RULItem;
import com.jgg.sdp.domain.services.rules.RULItemsService;
import com.jgg.sdp.loader.jaxb.rules.*;

public class RulesItem {

	private RULItemsService itemsService = new RULItemsService();
	
	private SDPBag<RULItem> items = new SDPBag<RULItem>();

	private RulesDescription descs = RulesDescription.getInstance();
	private RulesCondition   conds = RulesCondition.getInstance();
	private RulesSample      samps = RulesSample.getInstance();

	private static RulesItem item = null;
	
	public RulesItem() {
		RULItemsService.setLastId(0L);
	}
	
	public static RulesItem getInstance() {
		if (item == null) item = new RulesItem();
		RULItemsService.setLastId(0L);
		return item;
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
		
		ConditionType cond = xmlItem.getOnCondition();
		
		item.setActive(conds.createCondition( key * 10,      cond, xmlItem.isActive()));
		item.setIdDesc(descs.createDescription(key, xmlItem.getDescription()));
		item.setIdSample(samps.createSample(key, xmlItem.getSample()));
		item.setUid(System.getProperty("user.name"));
		item.setTms(new Timestamp(System.currentTimeMillis()));
		
		items.add(item);
		return item;
	}

	public RULItem createItem(Item xmlItem) {
		return new RULItem();
	}

	public SDPBag<RULItem> getItems() {
		if (item == null) return new SDPBag<RULItem>();
		return items;
	}

    public static void clear() {
    	item = null;
    }

}
