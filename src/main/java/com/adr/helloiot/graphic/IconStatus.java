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

package com.adr.helloiot.graphic;

import com.adr.fonticon.FontAwesome;
import com.adr.fonticon.IconFontList;
import javafx.scene.Node;

/**
 *
 * @author adrian
 */
public interface IconStatus {
    public Node buildIcon(String status); 

    public static IconStatus valueOf(String value) {
        if ("NULL".equals(value)) {
            return new IconNull();
        } else if ("TEXT".equals(value)) {
            return new IconText();
        } else if ("BULB".equals(value)) {
            return new Bulb();
        } else if (value.startsWith("BULB/")) {
            return new Bulb(FontAwesome.valueOf(value.substring(5)));            
        } else if (value.startsWith("BULBTEXT/")) {
            return new Bulb(new IconFontList(value.substring(9), "ROBOTO BOLD"));            
        } else if ("PADLOCK".equals(value)) {
            return new Padlock();
        } else if ("TOGGLE".equals(value)) {
            return new IconToggle();
        } else if ("POWER".equals(value)) {
            return new Power();
        } else if (value.startsWith("POWER/")) {
            return new Power(FontAwesome.valueOf(value.substring(6)));
        } else if (value.startsWith("POWERTEXT/")) {
            return new Power(new IconFontList(value.substring(10), "ROBOTO BOLD"));
        } else if ("TEXT".equals(value)) {
            return new IconTextSwitch();
        } else if (value.startsWith("TEXT/")) {
            String param = value.substring(5);
            if ("OPEN/CLOSED".equals(param)) {
                return new IconTextSwitch("OPEN", "CLOSED");
            } else if ("ON/OFF".equals(param)) {
                return new IconTextSwitch("ON", "OFF");
            } else if ("UP/DOWN".equals(param)) {
                return new IconTextSwitch("UP", "DOWN");
            } else {
                return new IconTextSwitch();   
            }
        } else {
            throw new RuntimeException("Cannot create IconStatus: " + value);
        }
    }      

}
