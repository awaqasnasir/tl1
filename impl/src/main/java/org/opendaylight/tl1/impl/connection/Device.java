/*
 * Copyright © 2015 Yoyodyne, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.tl1.impl.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.opendaylight.tl1.impl.Tl1Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Device extends Thread{
	private String ipAddress;
	private String port;
	private String response="";
	Socket device;
	PrintStream write;
	BufferedReader read;
	private static final Logger LOG = LoggerFactory.getLogger(Tl1Provider.class);
	public Device(String ip,String port){
		ipAddress=ip;
	    this.port=port;
	}
	public boolean connect(){
		try {
			device=new Socket("192.168.56.102",2222);
			write=new PrintStream(device.getOutputStream());
			read=new BufferedReader(new InputStreamReader(device.getInputStream()));
			
			// sending hand shake message
			write.println("HandShake");
			LOG.info("handhsake message sent");
			System.out.println("handhsake message sent");
			//getting response
			 response=read.readLine();
			if(response.equalsIgnoreCase("Handshake response")){
				System.out.println("hand shake response receive");
				LOG.info("hand shake response receive: connection esatablished");
				//asign ip to device
				write.println(ipAddress);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	public void run(){
		try {
			
				// wait for device to send message
				while(true){
					if(device.getInputStream().available()!=0){
						response=read.readLine();
						
						LOG.info("in While: message receive"+response);
					}
				}
				
				
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public String sendCommand(String command){
		System.out.println("about to send command");
		// sending command to device
		write.println(command);
		System.out.println("command sent");
		
		try {
			System.out.println("about to receive command reply");
			response = read.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("command reply is "+response);
		return response;
		
	}
	public String getIp(){
		return ipAddress;
	}
}
