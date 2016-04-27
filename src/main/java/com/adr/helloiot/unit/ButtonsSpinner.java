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

package com.adr.helloiot.unit;

import com.adr.fonticon.FontAwesome;
import com.adr.fonticon.IconBuilder;
import com.adr.hellocommon.utils.AbstractController;
import com.adr.helloiot.device.DeviceSimple;
import com.adr.helloiot.EventMessage;
import com.adr.helloiot.HelloIoTAppPublic;
import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import jidefx.utils.AutoRepeatButtonUtils;

/**
 *
 * @author adrian
 */
public class ButtonsSpinner extends StackPane implements Unit, AbstractController {
    
    @FXML private Button goup;
    @FXML private Button godown;
    @FXML private Label level;
    @FXML private Label label;
    
    private DeviceSimple device = null;

    public ButtonsSpinner() {   

        this.load("/com/adr/helloiot/fxml/buttonsspinner.fxml");   
    }
    
    @FXML public void initialize() {
        goup.setGraphic(IconBuilder.create(FontAwesome.FA_CARET_UP, 24).build());
        AutoRepeatButtonUtils.install(goup);
        godown.setGraphic(IconBuilder.create(FontAwesome.FA_CARET_DOWN, 24).build());
        AutoRepeatButtonUtils.install(godown);
        label.setText(null);
        level.setText(null);
        setDisable(true);
    }
    
    @Subscribe
    public void receivedStatus(EventMessage message) {
        Platform.runLater(() -> updateStatus(message.getMessage()));  
    }

    private void updateStatus(String status) {
        level.setText(device.getFormat().format(status));
        goup.setDisable(!device.hasNextStatus());
        godown.setDisable(!device.hasPrevStatus());
    }  
    
    @Override
    public void construct(HelloIoTAppPublic app) {
        Unit.super.construct(app);
        device.subscribeStatus(this);
        updateStatus(null);        
    }

    @Override
    public void destroy() {
        Unit.super.destroy();
        device.unsubscribeStatus(this);    
    }
    
    @Override
    public void start() {
        setDisable(false);
    }

    @Override
    public void stop() {
        setDisable(true);
    }

    @Override
    public Node getNode() {
        return this;
    }
    
    public void setDevice(DeviceSimple device) {
        this.device = device;
        if (getLabel() == null) {
            setLabel(device.getProperties().getProperty("label"));
        }     
    }
    
    public DeviceSimple getDevice() {
        return device;
    }
    
    public void setLabel(String value) {
        label.setText(value);
    }
    
    public String getLabel() {
        return label.getText();
    }
    
    @FXML
    void onGoDown(ActionEvent event) {
        device.sendStatus(device.prevStatus());
    }

    @FXML
    void onGoUp(ActionEvent event) {
        device.sendStatus(device.nextStatus());
    }
}
