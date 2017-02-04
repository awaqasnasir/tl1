/*
 * Copyright © 2015 Yoyodyne, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.tl1.impl;

import java.util.List;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.ProviderContext;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.RpcRegistration;
import org.opendaylight.controller.sal.binding.api.BindingAwareProvider;
import org.opendaylight.tl1.impl.connection.ConnectionEstablisher;
//import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
//import org.opendaylight.controller.sal.binding.api.NotificationService;
//import org.opendaylight.tl1.impl.connection.connectionListener;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.DeviceInfo;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.DeviceInfoBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.Tl1Service;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.Futures;

public class Tl1Provider implements BindingAwareProvider, AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(Tl1Provider.class);
    private RpcRegistration<Tl1Service> tl1Service;
    @Override
    public void onSessionInitiated(ProviderContext session) {
    	
    
        LOG.info("Tl1Provider Session Initiated");
        tl1Service=session.addRpcImplementation(Tl1Service.class, new Tl1ProviderImpl());
        DataBroker db = session.getSALService(DataBroker.class);
        MDSal.initializeDataTree(db);
        //ConnectionEstablisher.getInstance().makeConnection();
        
    }

    @Override
    public void close() throws Exception {
        LOG.info("Tl1Provider Closed");
    }

}
