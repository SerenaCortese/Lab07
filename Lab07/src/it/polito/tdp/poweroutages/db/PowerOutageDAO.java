package it.polito.tdp.poweroutages.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.NercIdMap;
import it.polito.tdp.poweroutages.model.PowerOutages;
import it.polito.tdp.poweroutages.model.PowerOutagesIdMap;

public class PowerOutageDAO {

	public List<Nerc> getNercList(NercIdMap nercMap) {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(nercMap.get(n));
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	/**
	 * Crea oggetto PowerOutages (se non lo trova nella powerOutagesIdMap) e lo aggiunge alla poList del NERC passato come parametro.
	 * @param powerOutagesMap Identity Map in cui cercare ed eventualmente aggiungere oggetto preso dal DB
	 * @param nerc Oggetto Nerc dentro cui salvo la lista di PowerOutages trovati
	 */
	public void getPowerOutagesList(PowerOutagesIdMap powerOutagesMap, Nerc nerc){
		String sql = "SELECT id, customers_affected as customers, date_event_began as inizio, date_event_finished as fine FROM poweroutages WHERE nerc_id = ?";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, nerc.getId());
			
			ResultSet res = st.executeQuery();

			while (res.next()) {
				PowerOutages po = new PowerOutages(res.getInt("id"),nerc.getId(), res.getInt("customers"),res.getTimestamp("inizio").toLocalDateTime(), res.getTimestamp("fine").toLocalDateTime());
				nerc.getPoList().add(powerOutagesMap.get(po));
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}


	}

}
