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

import com.adr.helloiot.device.format.MiniVarBoolean;
import com.adr.helloiot.device.format.StringFormatSwitch;

/**
 *
 * @author adrian
 */
public class DeviceSwitch extends DeviceSimple {

    public DeviceSwitch() {
        setFormat(new StringFormatSwitch());
    }

    @Override
    public String getDeviceName() {
        return resources.getString("devicename.deviceswitch");
    }


    @Override
    public byte[] nextStatus() {
        return getFormat().devalue(MiniVarBoolean.TRUE);
    }
    
    @Override
    public byte[] rollNextStatus() {
        return getFormat().devalue(new MiniVarBoolean(!varStatus().asBoolean()));
    }
    
    @Override
    public byte[] prevStatus() {
        return getFormat().devalue(MiniVarBoolean.FALSE);
    }
    
    @Override
    public byte[] rollPrevStatus() {
        return getFormat().devalue(new MiniVarBoolean(!varStatus().asBoolean()));
    }
    
    @Override
    public boolean hasPrevStatus() {
        return varStatus().asBoolean();
    }

    @Override
    public boolean hasNextStatus() {
        return !varStatus().asBoolean();
    }

    public void sendON() {
        sendStatus(getFormat().devalue(MiniVarBoolean.TRUE));
    }

    public void sendOFF() {
        sendStatus(getFormat().devalue(MiniVarBoolean.FALSE));
    }

    public void sendSWITCH() {
        sendStatus(rollNextStatus());
    }

    public void sendON(long duration) {

        if (varStatus().asBoolean() && !hasTimer()) {
            // If  already on and not with a timer then do nothing
            return;
        }

        sendStatus(getFormat().devalue(MiniVarBoolean.TRUE));
        sendStatus(getFormat().devalue(MiniVarBoolean.FALSE), duration);
    }
}
