/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;


import it.polito.tdp.food.model.Model;
import it.polito.tdp.food.model.StringAndNumber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	txtResult.clear();
    	
    	String sorgente = this.boxPorzioni.getSelectionModel().getSelectedItem();
    	if (sorgente == null) {
    		txtResult.setText("Seleziona una porzione");
    	}
        
    	Integer passi;
    	
    	try {
    		passi = Integer.parseInt(this.txtPassi.getText());
    		
    	}catch (NumberFormatException e) {
    		txtResult.appendText("Inserisci un numero valido di calorie!");
    	    return;
    	}
    	List<String> camminoMax = this.model.trovaCammino(sorgente, passi);
    	Integer peso  =  this.model.peso(camminoMax);
    	for(String s: camminoMax) {
    		txtResult.appendText(s +"\n");
    	}
    	txtResult.appendText(String.format("Somma dei pesi del cammino : %d", peso));
    	
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
        
    	String sorgente = this.boxPorzioni.getSelectionModel().getSelectedItem();
    	if (sorgente == null) {
    		txtResult.setText("Seleziona una porzione");
    	}

    	try {
    		List<StringAndNumber> connessi = this.model.getNodiConnessi(sorgente);
    		
    		for(StringAndNumber s : connessi) {
    			txtResult.appendText(s.toString() +"\n");
    		}
    		
    	} catch (RuntimeException e) {
    	
    		txtResult.setText("La porzione selezionata non è nel grafo ");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	Integer calorie;
    	
    	try {
    		calorie = Integer.parseInt(this.txtCalorie.getText());
    		
    	}catch (NumberFormatException e) {
    		txtResult.appendText("Inserisci un numero valido di calorie!");
    	    return;
    	}
    	this.model.creaGrafo(calorie);
    	txtResult.appendText(String.format("Grafo creato con %d vertici e %d archi", this.model.nVertici(), this.model.nArchi()));
    	
    	
    	this.boxPorzioni.getItems().addAll(this.model.getPorzioni(calorie));
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    
    }
}
