//    HelloIoT is a dashboard creator for MQTT
//    Copyright (C) 2017 Adrián Romero Corchado.
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
package com.adr.helloiot.device;

import com.adr.helloiot.EventMessage;
import com.adr.helloiot.MQTTManager;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author adrian
 */
public class TreePublishSubscribe extends TreePublish {

    private MQTTManager.Subscription mqttstatus = null;
    private final Map<String, byte[]> status = new ConcurrentHashMap<>();

    public TreePublishSubscribe() {
        super();
        setRetained(true); // by default retained
    }

    @Override
    public String getDeviceName() {
        return resources.getString("devicename.treepublishsubscribe");
    }

    protected void consumeMessage(EventMessage message) {
        status.put(message.getTopic(), message.getMessage());
    }

    @Override
    public void construct(MQTTManager mqttManager) {
        super.construct(mqttManager);
        mqttstatus = mqttManager.subscribe(getTopic() + "/#", getQos());
        mqttstatus.setConsumer(this::consumeMessage);
    }

    @Override
    public void destroy() {
        super.destroy();
        mqttManager.unsubscribe(mqttstatus);
        mqttstatus = null;
    }

    public byte[] readMessage(String branch) {
        return status.get(getTopic() + "/" + branch);
    }

    public String loadMessage(String branch) {
        return getFormat().format(readMessage(branch));
    }
}
