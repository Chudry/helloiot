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

package com.adr.helloiot;

import com.adr.fonticon.FontAwesome;
import com.adr.fonticon.IconBuilder;
import com.adr.helloiot.client.TopicStatus;
import com.adr.helloiot.device.format.StringFormat;
import com.adr.helloiot.unit.StartFlow;
import com.adr.helloiot.unit.UnitPage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application.Parameters;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

/**
 *
 * @author adrian
 */
public class MainManagerClient implements MainManager {
    
    private static final Logger LOGGER = Logger.getLogger(MainManagerClient.class.getName());

    private HelloIoTApp helloiotapp = null;
    private ClientLoginNode clientlogin = null;
    
    private ConfigProperties configprops = null;
    private StackPane root = null;
    
    private void showLogin() {     

        clientlogin = new ClientLoginNode();
        clientlogin.setURL(configprops.getProperty("mqtt.url", "tcp://localhost:1883"));
        clientlogin.setUserName(configprops.getProperty("mqtt.username", ""));
        clientlogin.setConnectionTimeout(Integer.parseInt(configprops.getProperty("mqtt.connectiontimeout", Integer.toString(MqttConnectOptions.CONNECTION_TIMEOUT_DEFAULT))));
        clientlogin.setKeepAliveInterval(Integer.parseInt(configprops.getProperty("mqtt.keepaliveinterval", Integer.toString(MqttConnectOptions.KEEP_ALIVE_INTERVAL_DEFAULT))));
        clientlogin.setDefaultQoS(Integer.parseInt(configprops.getProperty("mqtt.defaultqos", "1")));
        clientlogin.setVersion(Integer.parseInt(configprops.getProperty("mqtt.version", Integer.toString(MqttConnectOptions.MQTT_VERSION_DEFAULT))));
        clientlogin.setCleanSession(Boolean.parseBoolean(configprops.getProperty("mqtt.cleansession", Boolean.toString(MqttConnectOptions.CLEAN_SESSION_DEFAULT))));
        clientlogin.setTopicPrefix(configprops.getProperty("mqtt.topicprefix", ""));
        clientlogin.setTopicApp(configprops.getProperty("mqtt.topicapp", "_LOCAL_/_sys_helloIoT/mainapp"));

        clientlogin.setBrokerPane(Integer.parseInt(configprops.getProperty("client.broker", "0"))); //none

        clientlogin.setOnNextAction(e -> {                
            showApplication();
            hideLogin(); 
        });
        root.getChildren().add(clientlogin);        
    }
    
    private void hideLogin() {
        if (clientlogin != null) {
            root.getChildren().remove(clientlogin);
            clientlogin = null;
        }        
    }
    
    private void showApplication() {
        
        configprops.setProperty("mqtt.url", clientlogin.getURL());
        configprops.setProperty("mqtt.username", clientlogin.getUserName());
        configprops.setProperty("mqtt.connectiontimeout", Integer.toString(clientlogin.getConnectionTimeout()));
        configprops.setProperty("mqtt.keepaliveinterval", Integer.toString(clientlogin.getKeepAliveInterval()));
        configprops.setProperty("mqtt.defaultqos", Integer.toString(clientlogin.getDefaultQoS()));
        configprops.setProperty("mqtt.version", Integer.toString(clientlogin.getVersion()));
        configprops.setProperty("mqtt.cleansession", Boolean.toString(clientlogin.isCleanSession()));
        configprops.setProperty("mqtt.topicprefix", clientlogin.getTopicPrefix());
        configprops.setProperty("mqtt.topicapp", clientlogin.getTopicApp());
        
        configprops.setProperty("client.broker", Integer.toString(clientlogin.getBrokerPane()));
        
        try {
            configprops.save();
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, "Cannot save configuration properties.", ex);
        }

        ApplicationConfig config = new ApplicationConfig();
        config.mqtt_url = clientlogin.getURL();
        config.mqtt_username = clientlogin.getUserName();
        config.mqtt_password = clientlogin.getPassword();
        config.mqtt_connectiontimeout = clientlogin.getConnectionTimeout();
        config.mqtt_keepaliveinterval = clientlogin.getKeepAliveInterval();
        config.mqtt_defaultqos = clientlogin.getDefaultQoS();
        config.mqtt_version = clientlogin.getVersion();
        config.mqtt_cleansession = clientlogin.isCleanSession();
        config.mqtt_topicprefix = clientlogin.getTopicPrefix();
        config.mqtt_topicapp = clientlogin.getTopicApp();
        
        config.app_clock = true;
        config.app_exitbutton = false;
        config.app_retryconnection = false;
        
        helloiotapp = new HelloIoTApp(config);
                
        // add sample panes
        ResourceBundle resources = ResourceBundle.getBundle("com/adr/helloiot/fxml/main");   
        
        if (clientlogin.getBrokerPane() == 1) {
            UnitPage info = new UnitPage("info", IconBuilder.create(FontAwesome.FA_INFO, 24.0).build(), resources.getString("page.info"));
            helloiotapp.addUnitPages(Arrays.asList(info));            
            helloiotapp.addFXMLFileDevicesUnits("local:com/adr/helloiot/panes/mosquitto");
        }
  
        if (clientlogin.isLightsPane()) {
            helloiotapp.addUnitPages(Arrays.asList(
                new UnitPage("light", IconBuilder.create(FontAwesome.FA_LIGHTBULB_O, 24.0).build(), resources.getString("page.light")))
            );
            helloiotapp.addFXMLFileDevicesUnits("local:com/adr/helloiot/panes/samplelights");
        }
        
        if (clientlogin.isGaugesPane()) {
            helloiotapp.addUnitPages(Arrays.asList(
                new UnitPage("temperature", IconBuilder.create(FontAwesome.FA_DASHBOARD, 24.0).build(), resources.getString("page.temperature")))
            );
            helloiotapp.addFXMLFileDevicesUnits("local:com/adr/helloiot/panes/sampletemperature");
        }
        
        helloiotapp.addDevicesUnits(Collections.emptyList(), Collections.singletonList(new StartFlow()));

        TopicStatus ts;
        
        ts = TopicStatus.buildTopicPublishRetained("hello/test1", 0, StringFormat.valueOf("INTEGER"), true);
        helloiotapp.addDevicesUnits(ts.getDevices(), ts.getUnits());
        
        ts = TopicStatus.buildTopicPublishRetained("hello/test1", 0, StringFormat.valueOf("HEXADECIMAL"), false);
        helloiotapp.addDevicesUnits(ts.getDevices(), ts.getUnits());
        
        ts = TopicStatus.buildTopicPublish("hello/test1", -1, StringFormat.valueOf("INTEGER"), true);
        helloiotapp.addDevicesUnits(ts.getDevices(), ts.getUnits());
        
        ts = TopicStatus.buildTopicPublish("hello/test1", -1, StringFormat.valueOf("BASE64"), false);
        helloiotapp.addDevicesUnits(ts.getDevices(), ts.getUnits());
        
        ts = TopicStatus.buildTopicSubscription("hello/test1", 1, StringFormat.valueOf("INTEGER"), true);
        helloiotapp.addDevicesUnits(ts.getDevices(), ts.getUnits());
        
        ts = TopicStatus.buildTopicSubscription("hello/test1", 1, StringFormat.valueOf("INTEGER"), false);
        helloiotapp.addDevicesUnits(ts.getDevices(), ts.getUnits());
        
//        ts = TopicStatus.buildTopicSubscription("$SYS/broker/uptime", -1, StringFormatIdentity.INSTANCE, false);
//        helloiotapp.addDevicesUnits(ts.getDevices(), ts.getUnits());

        
        EventHandler<ActionEvent> showloginevent = (event -> {
            showLogin();            
            hideApplication();            
        });
        helloiotapp.setOnDisconnectAction(showloginevent);
        helloiotapp.getMQTTNode().setToolbarButton(showloginevent, IconBuilder.create(FontAwesome.FA_SIGN_OUT, 18.0).build(), resources.getString("label.disconnect"));

        root.getChildren().add(helloiotapp.getMQTTNode());
        helloiotapp.startAndConstruct();        
    }
    
    private void hideApplication() {
        if (helloiotapp != null) {
            helloiotapp.stopAndDestroy();
            root.getChildren().remove(helloiotapp.getMQTTNode());
            helloiotapp = null;     
        }        
    }
    
    @Override
    public void construct(StackPane root, Parameters params) {        
        this.configprops = new ConfigProperties(params);
        this.root = root;
        try {
            this.configprops.load();
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, null, ex);
        }

        showLogin();
    }
    
    @Override
    public void destroy() {
        hideLogin();
        hideApplication();
    }
}