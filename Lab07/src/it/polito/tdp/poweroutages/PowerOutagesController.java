package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PowerOutagesController {
	
	private Model model;
	private List<Nerc> nerc;

    @FXML 
    private ResourceBundle resources;

    @FXML 
    private URL location;

    @FXML 
    private ChoiceBox<Nerc> choiceBox;

    @FXML 
    private TextField txtYears;

    @FXML
    private TextField txtHours;

    @FXML
    private Button button;

    @FXML
    private TextArea txtResult;

    public void setModel(Model m) {
    	this.model = m;
    	this.setChoiceBox();
    }

	private void setChoiceBox() {

		// Ottieni tutti i corsi dal model
		nerc = model.getNercList();
		
		// Aggiungi tutti i corsi alla ComboBox
		choiceBox.getItems().addAll(nerc);
	}

    @FXML
    void doAction(ActionEvent event) {
    	txtResult.clear();
    	Nerc nercScelto = choiceBox.getValue();
    	if(nercScelto == null) {
    		txtResult.setText("Scegliere un Nerc.");
    		return;
    	}
    	try {
    		int anni = Integer.parseInt(txtYears.getText());
	    	int ore = Integer.parseInt(txtHours.getText());
	    	
	    	List<PowerOutages> soluzione = model.calcolaWorstCase(anni, ore, nercScelto);
	    	txtResult.appendText("Tot people affected: "+ model.totalePersone(soluzione)+"\n");
	    	txtResult.appendText("Tot hours of outage: "+ model.totaleOre(soluzione)+"\n");
	    	Collections.sort(soluzione);
	    	for(PowerOutages po: soluzione) {
	    		txtResult.appendText(po.toString()+"\n");
	    	}
	    }catch(NumberFormatException nfe) {
    		txtResult.setText("Inserire valori interi per anni e ore.");
    	}	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert choiceBox != null : "fx:id=\"choiceBox\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert button != null : "fx:id=\"button\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'PowerOutages.fxml'.";

    }
    
    }
