/*
 * Copyright © 2015 Yoyodyne, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.tl1.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.common.api.data.ReadFailedException;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.AddDeviceInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.DeviceRegistry;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.DeviceRegistryBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.device.registry.DeviceRegistryEntry;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.device.registry.DeviceRegistryEntryBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tl1.rev150105.device.registry.DeviceRegistryEntryKey;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

public class MDSal {
	static List<String> allDevices=null;
	private static DataBroker dbroker;
private static final Logger LOG = LoggerFactory.getLogger(Tl1Provider.class);
	
  public static void initializeDataTree(DataBroker db) {
	  dbroker=db;
  LOG.info("Preparing to initialize the greeting registry");
  WriteTransaction transaction = db.newWriteOnlyTransaction();
  InstanceIdentifier<DeviceRegistry> iid = InstanceIdentifier.create(DeviceRegistry.class);
  DeviceRegistry registry = new DeviceRegistryBuilder().
          build();
  transaction.put(LogicalDatastoreType.OPERATIONAL, iid, registry);
  CheckedFuture<Void, TransactionCommitFailedException> future = transaction.submit();
  Futures.addCallback(future, new LoggingFuturesCallBack<Void>("Failed to write greeting to greeting registry", LOG));
}
  // to write a entry in mdsal
  public static void writeToDeviceRegistry(String ip, String port){
	  WriteTransaction transaction=dbroker.newWriteOnlyTransaction();
	  InstanceIdentifier<DeviceRegistryEntry> iid=toInstaceIdentifier(ip);
	  DeviceRegistryEntry entry=new DeviceRegistryEntryBuilder()
			  .setIp(ip)
			  .setPort(port)
			  .build();
	  transaction.put(LogicalDatastoreType.OPERATIONAL, iid, entry);
	  CheckedFuture<Void,TransactionCommitFailedException> future=transaction.submit();
	  Futures.addCallback(future, new LoggingFuturesCallBack<Void>("Failed to write greeting to greeting registry", LOG));
	  
  }
  // to convert instance into identifier
  private static InstanceIdentifier<DeviceRegistryEntry> toInstaceIdentifier(String ip){
	  InstanceIdentifier<DeviceRegistryEntry> iid=InstanceIdentifier.create(DeviceRegistry.class)
			  .child(DeviceRegistryEntry.class, new DeviceRegistryEntryKey(ip));
			  
			  
			  return iid;
  }
  public static List<String> getAllDevices (){
	   
	  InstanceIdentifier<DeviceRegistry> identifier=InstanceIdentifier.create(DeviceRegistry.class);
	  ReadOnlyTransaction transaction = dbroker.newReadOnlyTransaction();
      
      ReadOnlyTransaction readTx = dbroker.newReadOnlyTransaction();
      ListenableFuture<Optional<DeviceRegistry>> dataFuture = readTx.read(LogicalDatastoreType.OPERATIONAL,identifier);
      Futures.addCallback(dataFuture, new FutureCallback<Optional<DeviceRegistry>>() {
          @Override
          public void onSuccess(final Optional<DeviceRegistry> result) {
              if(result.isPresent()) {
               // data are present in data store.
            	  allDevices=ExtractIps(result.get().getDeviceRegistryEntry());
                  //doSomething(result.get());
              } else {
                  // data are not present in data store.
            	  allDevices=null;
              }
          }
          @Override
          public void onFailure(final Throwable t) {
              // Error during read
          }
      
  });
  
  
  return allDevices;
  }
  private static List<String> ExtractIps(List<DeviceRegistryEntry> list){
	  List<String> ips=new ArrayList<String>();
	  
	  for(int i=0;i<list.size();i++){
		  ips.add(list.get(i).getIp());
	  }
	  return ips;
	  
  }
  public static int loadNodes(String path){
	  
	// The name of the file to open.
      String fileName = path;

      // This will reference one line at a time
      String line = null;

      try {
          // FileReader reads text files in the default encoding.
          FileReader fileReader = 
              new FileReader(fileName);

          // Always wrap FileReader in BufferedReader.
          BufferedReader bufferedReader = 
              new BufferedReader(fileReader);

          while((line = bufferedReader.readLine()) != null) {
        	  String[] deviceInfo=line.split(",");
        	  
              System.out.println(line);
              
          }   

          // Always close files.
          bufferedReader.close();         
      }
      catch(FileNotFoundException ex) {
          System.out.println(
              "Unable to open file '" + 
              fileName + "'");                
      }
      catch(IOException ex) {
          System.out.println(
              "Error reading file '" 
              + fileName + "'");                  
          // Or we could just do this: 
          // ex.printStackTrace();
      }
	  
	return 0;
	  
  }
 
}
