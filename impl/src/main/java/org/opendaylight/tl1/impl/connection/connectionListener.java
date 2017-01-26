/*
 * Copyright © 2015 Yoyodyne, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.tl1.impl.connection;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
public class connectionListener extends Thread{
	public  void run(){
		int requests=0; 
    	try
    	{
	    	ServerSocket ss=new ServerSocket(2222);
	    	
	    	System.out.println("Waiting for client request");
	    	
	    	while (true)
	    	{
	    	Socket client=ss.accept();
	    	requests++;
	    	System.out.println("Accepted Request# "+requests);
	    	CommThread ct = new CommThread(client);
	    	ct.start();
	    	}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

}