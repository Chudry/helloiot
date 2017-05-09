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
package com.adr.helloiot.graphic;

import com.adr.fonticon.decorator.Shine;
import com.adr.helloiot.device.format.ValueFormatValue;
import com.adr.helloiot.util.ExternalFonts;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author adrian
 */
public class IconText extends IconStatus {

    @Override
    public Node buildIcon(ValueFormatValue value) {
        Text t = new Text(value.getFormatValue());
        t.setFont(Font.font(ExternalFonts.ROBOTOBOLD, FontWeight.BOLD, 18.0));
        t.setFill(Color.WHITE);
        new Shine(Color.DARKGRAY).decorate(t);        
        return t;
    }
}
