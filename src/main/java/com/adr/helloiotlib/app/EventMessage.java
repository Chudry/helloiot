//    HelloIoT is a dashboard creator for MQTT
//    Copyright (C) 2017-2019 Adrián Romero Corchado.
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
package com.adr.helloiotlib.app;

import com.adr.helloiotlib.format.MiniVar;
import com.adr.helloiotlib.format.MiniVarInt;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author adrian
 */
public class EventMessage {

    private final String topic;
    private final byte[] message;
    private final Map<String, MiniVar> properties;

    public EventMessage(String topic, byte[] message) {
        this.topic = topic;
        this.message = message;
        this.properties = new HashMap<>();
    }

    public EventMessage(String topic, byte[] message, Map<String, MiniVar> properties) {
        this.topic = topic;
        this.message = message;
        this.properties = properties;
    }

    public String getTopic() {
        return topic;
    }

    public byte[] getMessage() {
        return message;
    }
    
    public MiniVar getProperty(String key) {
        MiniVar value;
        return ((value = properties.get(key)) != null) ? value : MiniVarInt.NULL;
    }
    
    public EventMessage clone(String newtopic) {
        return new EventMessage(newtopic, message, properties);
    }
}
