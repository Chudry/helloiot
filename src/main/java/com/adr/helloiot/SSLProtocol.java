//    HelloIoT is a dashboard creator for MQTT
//    Copyright (C) 2018 Adrián Romero Corchado.
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
public enum SSLProtocol {
    
  TSLv1("TSLv1"),
  TSLv11("TSLv1.1"),
  TSLv12("TSLv1.2"),
  TSLv13("TSLv1.3"),
  SSL_TSL("SSL_TSL"),
  SSLv3("SSLv3");
  
  private final String displayname;
  
  private SSLProtocol(String displayname) {
      this.displayname = displayname;
  }  
  
  public String getDisplayName() {
      return displayname;
  }
  
  @Override
  public String toString() {
      return displayname;
  }
}
