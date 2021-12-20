package com.colliu.colliu.controllers;

import com.colliu.colliu.MasterController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.w3c.dom.Text;

public class ProfileController {

    public void setMaster(MasterController master) {
        this.master = master;
    }
    @FXML
    private Button btnReturn;
    private Label Announcements;

    //Profile tab admin controlls
    @FXML
    private Button btnBanUser;
    private Button btnUnbanUser;
    private Button btnPromoteUserToAdmin;
    private Label lblAdministratorControls;
    private TextField tfAdministratorConstrols;

    //Change name tab
    @FXML
    private Button onButtonChangeFirstName;
    private Button btnChangeLastName;
    private Label lblFirstNameChangedSuccessfully;
    private Label lblFirstNameMatch;
    private Label lblLastNameMatch;
    private Label lblLastNameChangedSuccessfully;
    private Text txtChangeName;
    private TextField tfEnterNewFirstName;
    private TextField tfNewLastName;
    private TextField tfRepeatNewLastName;
    private TextField tfRepeatFirstName;

    //Change password tab
    @FXML
    private Button btnChangePasword;
    private Label btnChangePasswordError;
    private Label lblPasswordChangedSuccessfully;
    private Label lblPasswordComplexityError;
    private PasswordField tfCurrentPassword;
    private PasswordField tfNewPassword;
    private PasswordField tfRepeatNewPassword;
    private Text txtChangePassword;

    //Change program tab
    @FXML
    private Button btnDataVetenskap;
    private Button btnSoftwareEngineering;
    private Button btnSystemvetenskap;

    //Testing stuff
    @FXML
    private Button btnCreateErikHarring;
    private Button btnCreateKrisAdmin;
    private Button btbTestingUserInfo;
    private Label lblTest;



}