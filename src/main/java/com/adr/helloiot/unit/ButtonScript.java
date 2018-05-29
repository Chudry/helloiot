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
package com.adr.helloiot.unit;

import com.adr.hellocommon.dialog.MessageUtils;
import com.adr.helloiot.HelloIoTAppPublic;
import com.adr.helloiot.util.CompletableAsync;
import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;

/**
 *
 * @author adrian
 */
public class ButtonScript extends ButtonBase {

    private final Map<String, Object> params = new HashMap<>();
    private ScriptCode code = null;

    @Override
    public void construct(HelloIoTAppPublic app) {
        super.construct(app);
        code.construct(app);
    }

    public Map<String, Object> getParameters() {
        return params;
    }

    public void setScriptCode(ScriptCode code) {
        this.code = code;
    }

    public ScriptCode getScriptCode() {
        return code;
    }

    @Override
    protected void doRun(ActionEvent event) {
        if (code == null) {
            MessageUtils.showError(MessageUtils.getRoot(this), getLabel(), resources.getString("message.nocode"));
        } else {
            CompletableAsync.handleError(code.run(params), 
                ex -> {
                    MessageUtils.showException(MessageUtils.getRoot(ButtonScript.this), getLabel(), resources.getString("message.erroraction"), ex);
                });       
        }
    }
}
