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
package com.adr.helloiotlib.device;

import com.adr.helloiotlib.format.MiniVar;
import com.adr.helloiotlib.format.StringFormat;
import com.adr.helloiotlib.format.StringFormatIdentity;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import com.adr.helloiotlib.app.TopicManager;
import java.util.HashMap;

/**
 *
 * @author adrian
 */
public abstract class Device {

    protected ResourceBundle resources = ResourceBundle.getBundle("com/adr/helloiot/resources/devices");

    private String id = null; // can be null
    private String subscriptiontopic = null;
    private String publicationtopic = null;
    private StringFormat format;
    private final Properties properties = new Properties();
    private final Map<String, MiniVar> messageProperties = new HashMap<>();

    public Device() {
        setFormat(StringFormatIdentity.INSTANCE);
    }

    // The device generic device name
    public abstract String getDeviceName();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public final String getTopic() {
        return subscriptiontopic;
    }

    public final void setTopic(String topic) {
        this.subscriptiontopic = topic;
    }

    public final String getTopicPublish() {
        return publicationtopic == null ? subscriptiontopic : publicationtopic;
    }

    public final void setTopicPublish(String topic) {
        this.publicationtopic = topic;
    }

    public final StringFormat getFormat() {
        return format;
    }

    public final void setFormat(StringFormat format) {
        this.format = format;
    }

    public final Properties getProperties() {
        return properties;
    }

    public final Map<String, MiniVar> getMessageProperties() {
        return messageProperties;
    }

    // Runtime methods
    public abstract void construct(TopicManager manager);

    public abstract void destroy();
}
