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
import org.opendaylight.tl1.impl.Utility;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.SendTl1commandToAllDeviceInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.SendTl1commandToMultipleDeviceInput;

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
			Device dev=new Device(ips.get(i),"2222");
			dev.start();
			devices.add(dev);
			
		}
		
	}
	public boolean connect(String ip,String port){
		Device dev=new Device(ip,port);
		if(dev.connect()){
			dev.start();
			devices.add(dev);
			return true;
		}
		return false;
		
		
	}
	public Device getDevice(String ip){
		for(int i=0;i<devices.size();i++)
		{
			if(devices.get(i).getIp().equals(ip)){
				System.out.println("device found with ip"+devices.get(i).getIp());
				return devices.get(i);
				
			}
		}
		return null;
		
	}
	public List<String> sendCommandToAll(SendTl1commandToAllDeviceInput input){
		List<String> responses=new ArrayList<String>();
		
		for(int i=0;i<devices.size();i++)
		{
			String command=Utility.convert_Into_Tl1_Command(input,devices.get(i).getIp());
			String response=devices.get(i).sendCommand(command);
			responses.add(response);
		}
		return responses;
	}

	public List<String> sendCommandToMultiple(SendTl1commandToMultipleDeviceInput input) {
		// TODO Auto-generated method stub
		List<String> Tids=input.getTIDs();
		List<String> responses=new ArrayList<String>();
		
		for(int i=0; i<Tids.size();i++){
			String command=Utility.convert_Into_Tl1_Command(input,Tids.get(i));
			String response=getDevice(Tids.get(i)).sendCommand(command);
			responses.add(response);
			
		}
		return responses;
		
		
	}
			
	

}
