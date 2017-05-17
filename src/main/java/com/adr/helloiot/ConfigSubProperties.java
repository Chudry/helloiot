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
package com.adr.helloiot;

/**
 *
 * @author adrian
 */
public class ConfigSubProperties implements SubProperties {
    
    private final ConfigProperties properties;
    private final String prefix;
    
    public ConfigSubProperties(ConfigProperties properties, String prefix) {
        this.properties = properties;
        this.prefix = prefix;
    }
    
    @Override
    public String getProperty(String key) {
        return properties.getProperty(prefix + key);
    }
    
    @Override
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(prefix + key, defaultValue);
    }
    
    @Override
    public void setProperty(String key, String value) {
        properties.setProperty(prefix + key, value);
    }
}
