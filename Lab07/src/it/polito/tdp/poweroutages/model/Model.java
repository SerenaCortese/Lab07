package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.db.PowerOutageDAO;

public class Model {

	private PowerOutageDAO podao;
	
	private List<Nerc> nerc;
	private List<PowerOutages> guasti;
	
	private NercIdMap nercMap;
	private PowerOutagesIdMap guastiMap;
	
	private List<PowerOutages> soluzione;
	
	public Model() {
		podao = new PowerOutageDAO();
		
		nercMap = new NercIdMap();
		guastiMap = new PowerOutagesIdMap();
		
		nerc = podao.getNercList(nercMap); //memorizzo tutti i NERC presenti nel DB
		for(Nerc n : nerc) {
			//aggiungo riferimenti incrociati: popolo lista poList di ogni NERC in memoria
			podao.getPowerOutagesList(guastiMap, n);
		}
	}
	
	public List<Nerc> getNercList() {
		return nerc;
	}

	public List<PowerOutages> calcolaWorstCase(int anni, int ore, Nerc nercScelto) {

		this.soluzione = new ArrayList<PowerOutages>();
		List<PowerOutages> parziale = new ArrayList<PowerOutages>();
		guasti = nercScelto.getPoList();
		
		this.recursive(parziale,anni,ore);
		
		return soluzione;
	}
	
	public void recursive(List<PowerOutages> parziale, int anni, int ore) {
		// condizione terminazione
		if (this.totalePersone(parziale) > this.totalePersone(soluzione)) {
			this.soluzione = new ArrayList<PowerOutages>(parziale);
		
		}
		
		for (PowerOutages prova : guasti) {
			if (!parziale.contains(prova)) {
				parziale.add(prova);

				if (this.aggiuntaValida(parziale, anni, ore)) {
					recursive(parziale, anni, ore);
				}

				parziale.remove(parziale.size() - 1);
			}
		}
	}

	private boolean aggiuntaValida(List<PowerOutages> parziale, int anni, int ore) {
		
		if(this.totaleOre(parziale) > ore) {
			return false;
		}
		
		for(PowerOutages p1: parziale) {
			for(PowerOutages p2: parziale) {
				if(controllaData(p1.getDataFine(),p2.getDataFine()) > anni) {
					return false;
				}
			}
		}
		return true;
	}

	private int controllaData(LocalDateTime dataFine, LocalDateTime dataFine2) {
		return Math.abs(dataFine.getYear() - dataFine2.getYear());
	}

	public int totalePersone(List<PowerOutages> parziale) {
		int tot=0;
		for(PowerOutages p: parziale) {
			tot+= p.getCustomersAffected();
		}
		return tot;
	}
	
	public long totaleOre(List<PowerOutages> parziale) {
		int tot=0;
		for(PowerOutages p: parziale) {
			tot+= p.getDurata();
		}
		return tot;
	}
	
	
	

	

}
