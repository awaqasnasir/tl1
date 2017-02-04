/*
 * Copyright © 2015 Yoyodyne, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.tl1.impl;

import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.SendTl1commandToADeviceInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.SendTl1commandToAllDeviceInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.SendTl1commandToMultipleDeviceInput;

public class Utility {
	public static String convert_Into_Tl1_Command(SendTl1commandToADeviceInput input){
		String command="";
		command=input.getVerb();
		//adding modifier
		if(input.getModeifier1()!=null){
			command=command+"-"+input.getModeifier1();
		}
		if(input.getModeifier2()!=null){
			command=command+"-"+input.getModeifier2();
		}
		//adding tiD
		
		command=command+":"+((input.getTID()==null)?"":input.getTID())+
				":"+((input.getAID()==null)?"":input.getAID())+   //adding AID
				":"+((input.getCTAG()==null)?"":input.getCTAG())+     //adding CTAG
				":"+((input.getGeneralBlock()==null)?"":input.getGeneralBlock())+       //adding General command
				":"+((input.getDataBlockE()==null)?"":input.getDataBlockE())+   // adding data block
				":"+((input.getDataBlockF()==null)?"":input.getDataBlockF())+
				":"+((input.getDataBlockG()==null)?"":input.getDataBlockG())+
				";";    // adding command terminator
		
		
		
		
		return command;
		
	}
	
	public static String convert_Into_Tl1_Command(SendTl1commandToAllDeviceInput input, String ip) {
		String command="";
		command=input.getVerb();
		//adding modifier
		if(input.getModeifier1()!=null){
			command=command+"-"+input.getModeifier1();
		}
		if(input.getModeifier2()!=null){
			command=command+"-"+input.getModeifier2();
		}
		//adding tiD
		
		command=command+":"+ip+
				":"+((input.getAID()==null)?"":input.getAID())+   //adding AID
				":"+((input.getCTAG()==null)?"":input.getCTAG())+     //adding CTAG
				":"+((input.getGeneralBlock()==null)?"":input.getGeneralBlock())+       //adding General command
				":"+((input.getDataBlockE()==null)?"":input.getDataBlockE())+   // adding data block
				":"+((input.getDataBlockF()==null)?"":input.getDataBlockF())+
				":"+((input.getDataBlockG()==null)?"":input.getDataBlockG())+
				";";    // adding command terminator
		
		
		
		
		return command;
	}

	public static String convert_Into_Tl1_Command(SendTl1commandToMultipleDeviceInput input, String ip) {
		String command="";
		command=input.getVerb();
		//adding modifier
		if(input.getModeifier1()!=null){
			command=command+"-"+input.getModeifier1();
		}
		if(input.getModeifier2()!=null){
			command=command+"-"+input.getModeifier2();
		}
		//adding tiD
		
		command=command+":"+ip+
				":"+((input.getAID()==null)?"":input.getAID())+   //adding AID
				":"+((input.getCTAG()==null)?"":input.getCTAG())+     //adding CTAG
				":"+((input.getGeneralBlock()==null)?"":input.getGeneralBlock())+       //adding General command
				":"+((input.getDataBlockE()==null)?"":input.getDataBlockE())+   // adding data block
				":"+((input.getDataBlockF()==null)?"":input.getDataBlockF())+
				":"+((input.getDataBlockG()==null)?"":input.getDataBlockG())+
				";";    // adding command terminator
		
		
		
		
		return command;
	}

}
