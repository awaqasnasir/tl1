/*
 * Copyright © 2015 Yoyodyne, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.tl1.impl.connection;

import java.util.ArrayList;
import java.util.List;

import org.opendaylight.tl1.impl.MDSal;

public class ConnectionEstablisher {
	private static ConnectionEstablisher obj;
	private List<String> ips;
	//={"192.168.56.101","192.168.56.102","192.168.56.103"};
	private static ArrayList<Device> devices;
	public static ConnectionEstablisher getInstance(){
		if(obj==null){
			obj=new ConnectionEstablisher();
			
		}
		return obj;
		
	}
	//constructer
	
	public ConnectionEstablisher(){
		devices=new ArrayList<Device>();
		
	}
	
	public void connectToAll(){
		ips=MDSal.getAllDevices();
		for(int i=0;i<ips.size();i++){
			Device dev=new Device(ips.get(i));
			dev.start();
			devices.add(dev);
			
		}
		
	}
	public void connect(String ip){
		Device dev=new Device(ip);
		dev.start();
		devices.add(dev);
		
	}
	public Device getDevice(String ip){
		for(int i=0;i<devices.size();i++)
		{
			if(devices.get(i).getIp().equals(ip)){
				return devices.get(i);
				
			}
		}
		return null;
		
	}
			
	

}
