package it.polito.tdp.poweroutages.model;

import java.util.HashMap;
import java.util.Map;

public class PowerOutagesIdMap {
	
	private Map<Integer, PowerOutages> map;
	
	public PowerOutagesIdMap() {
		map = new HashMap<>();
	}
	
	public PowerOutages get(int id) {
		return map.get(id);
	}
	public PowerOutages get(PowerOutages po) {
		PowerOutages old = map.get(po.getId());
		if (old == null) {
			map.put(po.getId(), po);
			return po;
		}
		return old;
	}
	
	public void put(int id, PowerOutages po) {
		map.put(id, po);
	}

}
