/*
 * Copyright © 2015 Yoyodyne, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.tl1.impl;

import java.util.concurrent.Future;

//import org.opendaylight.controller.md.sal.binding.api.NotificationPublishService;
//import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.tl1.impl.connection.CommThread;
import org.opendaylight.tl1.impl.connection.ConnectionEstablisher;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.AddIpInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.AddIpOutput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.AddIpOutputBuilder;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.md.sal.binding.impl.rev131028.modules.module.configuration.binding.broker.impl.binding.broker.impl.NotificationPublishServiceBuilder;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.impl.rev141210.modules.module.configuration.tl1.NotificationService;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.Device;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.Device.DeviceStatu;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.DeviceBuilder;
//import org.opendaylight.tl1.impl.connection.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.HelloTl1Input;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.HelloTl1Output;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.HelloTl1OutputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.Tl1Service;
//import org.opendaylight.yangtools.yang.binding.Notification;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tl1ProviderImpl implements Tl1Service{
	private static final Logger LOG = LoggerFactory.getLogger(Tl1ProviderImpl.class);
//	@SuppressWarnings("deprecation")
//	private static NotificationProviderService notificationrovider;
//	public static void setNotify(NotificationProviderService notify) {
//		notificationrovider = notify;
//	}
	@Override
	public Future<RpcResult<HelloTl1Output>> helloTl1(HelloTl1Input input) {
		HelloTl1Output output=new HelloTl1OutputBuilder().setGreeting(ConnectionEstablisher.getInstance().getDevice(input.getIp()).sendCommand(input.getMessage())).build();
		
		// publishing notification
		//Device notification=new DeviceBuilder().setDeviceStatu(DeviceStatu.Working).build();
		//NotificationProviderService notificationProvider = providerContextImplementation.getSALService(NotificationProviderService.class);
		
		//notificationrovider.publish(notification);
		return RpcResultBuilder.success(output).buildFuture();
	}
	@Override
	public Future<RpcResult<AddIpOutput>> addIp(AddIpInput input) {
		// TODO Auto-generated method stub
		MDSal.writeToDeviceRegistry(input);
		ConnectionEstablisher.getInstance().connect(input.getIp());
		AddIpOutput output=new AddIpOutputBuilder().setStatus(AddIpOutput.Status.Success).build();
		return RpcResultBuilder.success(output).buildFuture();
	}

}
