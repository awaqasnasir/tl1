/*
 * Copyright © 2015 Yoyodyne, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.tl1.impl;

import java.util.List;
import java.util.concurrent.Future;

//import org.opendaylight.controller.md.sal.binding.api.NotificationPublishService;
//import org.opendaylight.controller.sal.binding.api.NotificationProviderService;

import org.opendaylight.tl1.impl.connection.ConnectionEstablisher;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.AddDeviceInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.AddDeviceOutput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.AddDeviceOutputBuilder;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.md.sal.binding.impl.rev131028.modules.module.configuration.binding.broker.impl.binding.broker.impl.NotificationPublishServiceBuilder;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.impl.rev141210.modules.module.configuration.tl1.NotificationService;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.Device;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.Device.DeviceStatu;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.DeviceBuilder;
//import org.opendaylight.tl1.impl.connection.*;

import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.SendTl1commandToADeviceInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.SendTl1commandToADeviceOutput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.SendTl1commandToADeviceOutputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.SendTl1commandToAllDeviceInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.SendTl1commandToAllDeviceOutput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.SendTl1commandToAllDeviceOutputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.SendTl1commandToMultipleDeviceInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.SendTl1commandToMultipleDeviceOutput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.SendTl1commandToMultipleDeviceOutputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.Tl1Service;
//import org.opendaylight.yangtools.yang.binding.Notification;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tl1ProviderImpl implements Tl1Service{
	private static final Logger LOG = LoggerFactory.getLogger(Tl1ProviderImpl.class);

	// adding a device implementation
	@Override
	public Future<RpcResult<AddDeviceOutput>> addDevice(AddDeviceInput input) {
		// TODO Auto-generated method stub
		MDSal.writeToDeviceRegistry(input);
		if(ConnectionEstablisher.getInstance().connect(input.getIp())){
			AddDeviceOutput output=new AddDeviceOutputBuilder().setStatus(AddDeviceOutput.Status.Success).build();
			return RpcResultBuilder.success(output).buildFuture();
		}
		
		AddDeviceOutput output=new AddDeviceOutputBuilder().setStatus(AddDeviceOutput.Status.FailedToConnect).build();
		return RpcResultBuilder.success(output).buildFuture();
	}
	
	// implementation of sending commnad to a device
	@Override
	public Future<RpcResult<SendTl1commandToADeviceOutput>> sendTl1commandToADevice(
			SendTl1commandToADeviceInput input) {
		// TODO Auto-generated method stub
		String command=Utility.convert_Into_Tl1_Command(input);
		String commandResponse=ConnectionEstablisher.getInstance().getDevice(input.getTID()).sendCommand(command);
		SendTl1commandToADeviceOutput output=new SendTl1commandToADeviceOutputBuilder().setCommandResponse(commandResponse).build();
		
		return RpcResultBuilder.success(output).buildFuture();
	}
	
	
	

	
	// implementation of sendTl1commandToAllDevice function
	@Override
	public Future<RpcResult<SendTl1commandToAllDeviceOutput>> sendTl1commandToAllDevice(
			SendTl1commandToAllDeviceInput input) {
		
		
		List<String> reponces=ConnectionEstablisher.getInstance().sendCommandToAll(input);
		SendTl1commandToAllDeviceOutput output=new SendTl1commandToAllDeviceOutputBuilder().setCommandResponses(reponces).build();
		
		// TODO Auto-generated method stub
		return RpcResultBuilder.success(output).buildFuture();
	}
	
	// implementation of sendTl1commandToMultipleDevice function
	@Override
	public Future<RpcResult<SendTl1commandToMultipleDeviceOutput>> sendTl1commandToMultipleDevice(
			SendTl1commandToMultipleDeviceInput input) {
		// TODO Auto-generated method stub
		List<String> responces=ConnectionEstablisher.getInstance().sendCommandToMultiple(input);
		SendTl1commandToMultipleDeviceOutput output=new SendTl1commandToMultipleDeviceOutputBuilder().setCommandResponses(responces).build();
		
		return RpcResultBuilder.success(output).buildFuture();
	}
	
	
	
	
	
	
	
	
	
//	@SuppressWarnings("deprecation")
//	private static NotificationProviderService notificationrovider;
//	public static void setNotify(NotificationProviderService notify) {
//		notificationrovider = notify;
//	}
	/*@Override
	public Future<RpcResult<HelloTl1Output>> helloTl1(HelloTl1Input input) {
		HelloTl1Output output=new HelloTl1OutputBuilder().setGreeting(ConnectionEstablisher.getInstance().getDevice(input.getIp()).sendCommand(input.getMessage())).build();
		
		// publishing notification
		//Device notification=new DeviceBuilder().setDeviceStatu(DeviceStatu.Working).build();
		//NotificationProviderService notificationProvider = providerContextImplementation.getSALService(NotificationProviderService.class);
		
		//notificationrovider.publish(notification);
		return RpcResultBuilder.success(output).buildFuture();
	}
	*/
	

}
