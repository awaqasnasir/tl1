/*
 * Copyright © 2015 Yoyodyne, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.tl1.impl.connection;
import java.io.*;
import java.net.*;

import org.opendaylight.tl1.impl.Tl1Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.opendaylight.tl1.impl.Tl1Provider;

public class CommThread extends Thread {
	private static Socket cs;
	
	
	public CommThread(Socket s) 
	{cs = s;
	
	}
	private static final Logger LOG = LoggerFactory.getLogger(Tl1Provider.class);

	public void run()
	{
		

		
		
			
			try {
				PrintStream ps=new PrintStream(cs.getOutputStream());
				System.out.println("connection Received: sending handshake message");
				LOG.info("connection Received: sending handshake message");
				ps.println("HandShake");	
				InputStreamReader isr=new InputStreamReader(cs.getInputStream());
				BufferedReader br =new BufferedReader(isr);
				String str=br.readLine();
				LOG.info("response;"+str);
				if(str.equals("HandShake Response")){
					System.out.println("connection established with a device");
					LOG.info("connection established with a device");
					
				}
				while(true){
					if(cs.getInputStream().available()!=0){
						String input=br.readLine();
						System.out.println("in while"+input);
						LOG.info("in while from fucntion"+input);
						
						
						
					}
				}
				
				
				
				
				

				
			}
			catch (Exception e)
			{
				
			}
		
	}
	public static String getDeveiceResponse() {
		PrintStream ps;
		String prop="";
		try {
			ps = new PrintStream(cs.getOutputStream());
			InputStreamReader isr=new InputStreamReader(cs.getInputStream());
			BufferedReader br =new BufferedReader(isr);
			ps.println("properties");
			System.out.println("Getting device poperties");
			LOG.info("Getting device poperties");
			
			
				prop = br.readLine();
			
				// TODO Auto-generated catch block
				cs.close();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		return prop;
	}
}

