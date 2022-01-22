package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import sql.ConnectSql;

public class HighScoreController {

	@FXML 
	private Label player1;
	@FXML 
	private Label player2;
	@FXML 
	private Label player3;	
	@FXML 
	private Label player4;
	@FXML 
	private Label player5;
	@FXML 
	private Label player6;
	@FXML 
	private Label player7;
	@FXML 
	private Label player8;
	@FXML 
	private Label player9;
	@FXML
	private Label player10;
	
	
	@FXML private Label victory1;
	@FXML private Label victory2;
	@FXML private Label victory3;
	@FXML private Label victory4;
	@FXML private Label victory5;
	@FXML private Label victory6;
	@FXML private Label victory7;
	@FXML private Label victory8;
	@FXML private Label victory9;
	@FXML private Label victory10;
	
	
    public HighScoreController() 
    {
    }
     
    @FXML
    private void initialize() 
    {
    	ConnectSql dBCon = new ConnectSql("test","toto","toto");
		ArrayList<String> test = dBCon.getTab();

		int i=1;
		for (String obj : test) {
			if (i==1)player1.setText(obj);
			if (i==2)victory1.setText(obj);
			if (i==3)player2.setText(obj);
			if (i==4)victory2.setText(obj);
			if (i==5)player3.setText(obj);
			if (i==6)victory3.setText(obj);
			if (i==7)player4.setText(obj);
			if (i==8)victory4.setText(obj);
			if (i==9)player5.setText(obj);
			if (i==10)victory5.setText(obj);
			i++;				
			}
    }
    
    
	
	



     
    // location and resources will be automatically injected by the FXML loader 
    @FXML
    private URL location;
     
    @FXML
    private ResourceBundle resources;
}
     
