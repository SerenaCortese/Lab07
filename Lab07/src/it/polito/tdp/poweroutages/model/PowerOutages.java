package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class PowerOutages implements Comparable<PowerOutages>{
	
	private int id;
	private int customersAffected;
	private LocalDateTime dataInizio;
	private LocalDateTime dataFine;
	private long durata;

	public PowerOutages(int id, int customersAffected, LocalDateTime dataInizio, LocalDateTime dataFine) {
		this.id = id;
		this.customersAffected = customersAffected;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.durata = Duration.between(this.dataInizio, this.dataFine).toHours();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomersAffected() {
		return customersAffected;
	}

	public void setCustomersAffected(int customersAffected) {
		this.customersAffected = customersAffected;
	}

	public LocalDateTime getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(LocalDateTime dataInizio) {
		this.dataInizio = dataInizio;
	}

	public LocalDateTime getDataFine() {
		return dataFine;
	}

	public void setDataFine(LocalDateTime dataFine) {
		this.dataFine = dataFine;
	}
	
	public long getDurata() {
		return durata;
	}

	public void setDurata(long durata) {
		this.durata = durata;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerOutages other = (PowerOutages) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return ""+dataInizio.getYear()+" "+ dataInizio+ " "+ dataFine+" "+durata+" "+customersAffected;
	}

	@Override
	public int compareTo(PowerOutages arg0) {
		return this.dataInizio.compareTo(arg0.dataInizio);
	}
	
	
	
	

}
