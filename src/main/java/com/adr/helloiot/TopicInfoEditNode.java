//    HelloIoT is a dashboard creator for MQTT
//    Copyright (C) 2017-2018 Adrián Romero Corchado.
//
//    This file is part of HelloIot.
//
//    HelloIot is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    HelloIot is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with HelloIot.  If not, see <http://www.gnu.org/licenses/>.
//
package com.adr.helloiot;

import com.adr.fonticon.FontAwesome;
import com.adr.fonticon.IconBuilder;
import com.adr.helloiot.util.FXMLNames;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

/**
 *
 * @author adrian
 */
public class TopicInfoEditNode implements TopicInfoNode {
        
    Runnable updatecurrent = null;
    
    @FXML
    private ResourceBundle resources;  
    @FXML
    private GridPane container;
    @FXML
    public ComboBox<String> editpage;    
    @FXML
    public TextField edittopic;
    @FXML
    public TextField edittopicpub;
    @FXML
    public ChoiceBox<String> editformat;
    @FXML
    public ColorPicker editcolor;
    @FXML
    Button clearcolor;
    @FXML
    public ColorPicker editbackground;
    @FXML
    Button clearbackground;
    @FXML
    public ChoiceBox<Integer> editqos;
    @FXML
    public ChoiceBox<Integer> editretained;
    @FXML
    public TextField editjsonpath;
    @FXML
    public CheckBox editmultiline; 

    public TopicInfoEditNode() {
        FXMLNames.load(this, "com/adr/helloiot/fxml/topicinfoeditnode");            
    }
    
    @Override
    public void useUpdateCurrent(Runnable updatecurrent) {
        this.updatecurrent = updatecurrent;
    }
    
    @Override
    public Node getNode() {
        return container;
    }
    
    private void updateCurrentTopic() {
        if (updatecurrent != null) {
            updatecurrent.run();
        }
    }

    @FXML
    public void initialize() {
        
        editpage.getItems().addAll("Lights", "Numbers");
        editpage.getEditor().textProperty().addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            updateCurrentTopic();
        });  
        
        edittopicpub.promptTextProperty().bind(edittopic.textProperty());
        edittopic.textProperty().addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            updateCurrentTopic();
        });
        edittopicpub.textProperty().addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            updateCurrentTopic();
        });
        
        editformat.setItems(FXCollections.observableArrayList(
                "STRING",
                "INT",
                "DOUBLE",
                "DECIMAL",
                "DEGREES",
                "BASE64",
                "HEX"));
        editformat.getSelectionModel().clearSelection();
        editformat.valueProperty().addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            updateCurrentTopic();
        });

        editjsonpath.textProperty().addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            updateCurrentTopic();
        });

        editmultiline.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            updateCurrentTopic();
        });

        clearcolor.setGraphic(IconBuilder.create(FontAwesome.FA_TRASH, 14.0).styleClass("icon-fill").build());
        editcolor.setValue(null);
        editcolor.valueProperty().addListener((ObservableValue<? extends Color> observable, Color oldValue, Color newValue) -> {
            updateCurrentTopic();
        });

        clearbackground.setGraphic(IconBuilder.create(FontAwesome.FA_TRASH, 14.0).styleClass("icon-fill").build());
        editbackground.setValue(null);
        editbackground.valueProperty().addListener((ObservableValue<? extends Color> observable, Color oldValue, Color newValue) -> {
            updateCurrentTopic();
        });

        editqos.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                if (object < 0) {
                    return resources.getString("label.default");
                } else {
                    return object.toString();
                }
            }

            @Override
            public Integer fromString(String string) {
                return Integer.getInteger(string);
            }
        });
        editqos.setItems(FXCollections.observableArrayList(-1, 0, 1, 2));
        editqos.getSelectionModel().clearSelection();
        editqos.valueProperty().addListener((ObservableValue<? extends Integer> ov, Integer old_val, Integer new_val) -> {
            updateCurrentTopic();
        });

        editretained.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                if (object < 0) {
                    return resources.getString("label.default");
                } else if (object == 0) {
                    return resources.getString("label.no");
                } else {
                    return resources.getString("label.yes");
                }
            }

            @Override
            public Integer fromString(String value) {
                return Integer.getInteger(value);
            }
        });
        editretained.setItems(FXCollections.observableArrayList(-1, 0, 1));
        editretained.getSelectionModel().clearSelection();
        editretained.valueProperty().addListener((ObservableValue<? extends Integer> ov, Integer old_val, Integer new_val) -> {
            updateCurrentTopic();
        });
        
    }    

    @FXML
    void onClearColor(ActionEvent event) {
        editcolor.setValue(null);
    }

    @FXML
    void onClearBackground(ActionEvent event) {
        editbackground.setValue(null);
    }    
}
