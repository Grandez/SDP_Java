package com.jgg.sdp.rules.processor;

import java.sql.Timestamp;

import com.jgg.sdp.adt.ADTBag;
import com.jgg.sdp.domain.rules.RULGroup;
import com.jgg.sdp.domain.rules.RULItem;
import com.jgg.sdp.domain.services.rules.RULItemsService;
import com.jgg.sdp.loader.jaxb.rules.*;

import static com.jgg.sdp.rules.ctes.CDGRules.*;

public class RulesItem {

	private RULItemsService itemsService = new RULItemsService();
	
	private ADTBag<RULItem> items = new ADTBag<RULItem>();

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
			item = itemsService.getByName(xmlItem.getName());
		}
		else {
			item = itemsService.getById(group.getIdGroup(), id);
		}
		
		if (item == null) {
			if (id == null) id = itemsService.getNextId(group.getIdGroup());
			item = new RULItem();
			item.setIdGroup(group.getIdGroup());
			item.setIdItem(id);
			item.setName(xmlItem.getName());
			item.setObject(xmlItem.getObject().getValue());
		}
		
		item.setType(processType(xmlItem.getObject().getType()));
		
		key = Long.parseLong(String.format("%02d%02d", item.getIdGroup(), item.getIdItem()));
		
		item.setActive(processActivateConditions(key * 10, xmlItem));		
		item.setIdTitle(descs.createTitle(id, xmlItem.getTitle()));
		item.setIdDesc(descs.createDescription(key, xmlItem.getDescription()));
		item.setIdSample(samps.createSample(key, xmlItem.getSample()));
		item.setUid(System.getProperty("user.name"));
		item.setTms(new Timestamp(System.currentTimeMillis()));
		
		items.add(item);
		return item;
	}

	private Integer processType(SubObject type) {
		if (type == null) return null;
		switch (type) {
		   case VERB:   return TYPE_VERB;
		   case OPTION: return TYPE_OPTION;
		   case LVALUE: return TYPE_LVALUE;
		   case RVALUE: return TYPE_RVALUE;
		   case LIST:   return TYPE_LIST;
           default:     return TYPE_NONE;		   
		}   
	}
	
	private Long processActivateConditions(Long key, Item xmlItem) {
		Long active = processActive(xmlItem.isActive());
		if (xmlItem.getOnConditions() != null) {
			return conds.processConditions(key, xmlItem.getOnConditions().getCondition(), active);
		}
		if (xmlItem.getOnCondition() != null) {
			return conds.processCondition(key, xmlItem.getOnCondition(), active);
		}
		return active;
	}
	
	private Long processActive(Boolean bActive) {
		if (bActive == null) return ACTIVE;
		return (bActive) ? ACTIVE : INACTIVE;
	}
	
	public RULItem createItem(Item xmlItem) {
		return new RULItem();
	}

	public ADTBag<RULItem> getItems() {
		if (item == null) return new ADTBag<RULItem>();
		return items;
	}

    public static void clear() {
    	item = null;
    }

}
