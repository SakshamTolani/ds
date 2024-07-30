// Question-9
// -------------------------------------------------------------------------------------------------------------------------------------------------
// package org.cloudbus.cloudsim.examples;

// import java.text.DecimalFormat;
// import java.util.ArrayList;
// import java.util.Calendar;
// import java.util.LinkedList;
// import java.util.List;

// import org.cloudbus.cloudsim.Cloudlet;
// import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
// import org.cloudbus.cloudsim.Datacenter;
// import org.cloudbus.cloudsim.DatacenterBroker;
// import org.cloudbus.cloudsim.DatacenterCharacteristics;
// import org.cloudbus.cloudsim.Host;
// import org.cloudbus.cloudsim.Log;
// import org.cloudbus.cloudsim.Pe;
// import org.cloudbus.cloudsim.Storage;
// import org.cloudbus.cloudsim.UtilizationModel;
// import org.cloudbus.cloudsim.UtilizationModelFull;
// import org.cloudbus.cloudsim.Vm;
// import org.cloudbus.cloudsim.VmAllocationPolicySimple;
// import org.cloudbus.cloudsim.VmSchedulerTimeShared;
// import org.cloudbus.cloudsim.core.CloudSim;
// import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
// import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
// import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

// public class CloudSimExample9 {
// 	/** The cloudlet list. */
// 	private static List<Cloudlet> cloudletList;

// 	/** The vmlist. */
// 	private static List<Vm> vmlist;
	
// 	private static List<Vm> createVM(int userId, int vms) {

// 		//Creates a container to store VMs. This list is passed to the broker later
// 		LinkedList<Vm> list = new LinkedList<Vm>();

// 		//VM Parameters
// 		long size = 20000; //image size (MB)
// 		int ram = 2048; //vm memory (MB)
// 		int mips = 1000;
// 		long bw = 1000;
// 		int pesNumber = 1; //number of cpus
// 		String vmm = "Xen"; //VMM name

// 		//create VMs
// 		Vm[] vm = new Vm[vms];

// 		for(int i=0;i<vms;i++){
// 			vm[i] = new Vm(i, userId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
// 			//for creating a VM with a space shared scheduling policy for cloudlets:
// 			//vm[i] = Vm(i, userId, mips, pesNumber, ram, bw, size, priority, vmm, new CloudletSchedulerSpaceShared());

// 			list.add(vm[i]);
// 		}

// 		return list;
// 	}
	
// 	private static List<Cloudlet> createCloudlet(int userId, int cloudlets){
// 		// Creates a container to store Cloudlets
// 		LinkedList<Cloudlet> list = new LinkedList<Cloudlet>();

// 		//cloudlet parameters
// 		long length = 40000;
// 		long fileSize = 300;
// 		long outputSize = 400;
// 		int pesNumber = 1;
// 		UtilizationModel utilizationModel = new UtilizationModelFull();

// 		Cloudlet[] cloudlet = new Cloudlet[cloudlets];

// 		for(int i=0;i<cloudlets;i++){
// 			cloudlet[i] = new Cloudlet(i, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
// 			// setting the owner of these Cloudlets
// 			cloudlet[i].setUserId(userId);
// 			list.add(cloudlet[i]);
// 		}

// 		return list;
// 	}
	
// 	public static void main(String[] args) {
// 		Log.printLine("Starting CloudSimExample9...");

// 		try {
// 			// First step: Initialize the CloudSim package. It should be called
// 			// before creating any entities.
// 			int num_user = 1;   // number of grid users
// 			Calendar calendar = Calendar.getInstance();
// 			boolean trace_flag = false;  // mean trace events

// 			// Initialize the CloudSim library
// 			CloudSim.init(num_user, calendar, trace_flag);

// 			// Second step: Create Datacenters
// 			//Datacenters are the resource providers in CloudSim. We need at list one of them to run a CloudSim simulation
// 			@SuppressWarnings("unused")
// 			Datacenter datacenter0 = createDatacenter("Datacenter_0");

// 			//Third step: Create Broker
// 			DatacenterBroker broker = createBroker();
// 			int brokerId = broker.getId();

// 			//Fourth step: Create VMs and Cloudlets and send them to broker
// 			vmlist = createVM(brokerId,10); //creating 10 vms
// 			cloudletList = createCloudlet(brokerId,40); // creating 40 cloudlets

// 			broker.submitVmList(vmlist);
// 			broker.submitCloudletList(cloudletList);

// 			// Fifth step: Starts the simulation
// 			CloudSim.startSimulation();

// 			// Final step: Print results when simulation is over
// 			List<Cloudlet> newList = broker.getCloudletReceivedList();

// 			CloudSim.stopSimulation();

// 			printCloudletList(newList);

// 			Log.printLine("CloudSimExample9 finished!");
// 		}
// 		catch (Exception e)
// 		{
// 			e.printStackTrace();
// 			Log.printLine("The simulation has been terminated due to an unexpected error");
// 		}
// 	}
	
// 	private static Datacenter createDatacenter(String name){

// 		// Here are the steps needed to create a PowerDatacenter:
// 		// 1. We need to create a list to store one or more
// 		//    Machines
// 		List<Host> hostList = new ArrayList<Host>();

// 		// 2. A Machine contains one or more PEs or CPUs/Cores. Therefore, should
// 		//    create a list to store these PEs before creating
// 		//    a Machine.
// 		List<Pe> peList1 = new ArrayList<Pe>();

// 		int mips = 1000;

// 		// 3. Create PEs and add these into the list.
// 		//for a quad-core machine, a list of 4 PEs is required:
// 		peList1.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
// 		peList1.add(new Pe(1, new PeProvisionerSimple(mips)));
// 		peList1.add(new Pe(2, new PeProvisionerSimple(mips)));
// 		peList1.add(new Pe(3, new PeProvisionerSimple(mips)));

// 		//Another list, for a quad-core machine
// 		List<Pe> peList2 = new ArrayList<Pe>();

// 		peList2.add(new Pe(0, new PeProvisionerSimple(mips)));
// 		peList2.add(new Pe(1, new PeProvisionerSimple(mips)));
		

// 		//4. Create Hosts with its id and list of PEs and add them to the list of machines
// 		int hostId=0;
// 		int ram = 8192; //host memory (MB)
// 		long storage = 1000000; //host storage
// 		int bw = 10000;

// 		hostList.add(
//     			new Host(
//     				hostId,
//     				new RamProvisionerSimple(ram),
//     				new BwProvisionerSimple(bw),
//     				storage,
//     				peList1,
//     				new VmSchedulerTimeShared(peList1)
//     			)
//     		); // This is our first machine

// 		hostId++;

// 		hostList.add(
//     			new Host(
//     				hostId,
//     				new RamProvisionerSimple(ram),
//     				new BwProvisionerSimple(bw),
//     				storage,
//     				peList2,
//     				new VmSchedulerTimeShared(peList1)
//     			)
//     		); // Second machine
		
		
// 		hostId++;

// 		hostList.add(
//     			new Host(
//     				hostId,
//     				new RamProvisionerSimple(ram),
//     				new BwProvisionerSimple(bw),
//     				storage,
//     				peList2,
//     				new VmSchedulerTimeShared(peList2)
//     			)
//     		); // Third machine
		
		
// 		hostId++;

// 		hostList.add(
//     			new Host(
//     				hostId,
//     				new RamProvisionerSimple(ram),
//     				new BwProvisionerSimple(bw),
//     				storage,
//     				peList2,
//     				new VmSchedulerTimeShared(peList2)
//     			)
//     		); // Fourth machine


// 		//To create a host with a space-shared allocation policy for PEs to VMs:
// 		//hostList.add(
//     	//		new Host(
//     	//			hostId,
//     	//			new CpuProvisionerSimple(peList1),
//     	//			new RamProvisionerSimple(ram),
//     	//			new BwProvisionerSimple(bw),
//     	//			storage,
//     	//			new VmSchedulerSpaceShared(peList1)
//     	//		)
//     	//	);

// 		//To create a host with a oportunistic space-shared allocation policy for PEs to VMs:
// 		//hostList.add(
//     	//		new Host(
//     	//			hostId,
//     	//			new CpuProvisionerSimple(peList1),
//     	//			new RamProvisionerSimple(ram),
//     	//			new BwProvisionerSimple(bw),
//     	//			storage,
//     	//			new VmSchedulerOportunisticSpaceShared(peList1)
//     	//		)
//     	//	);


// 		// 5. Create a DatacenterCharacteristics object that stores the
// 		//    properties of a data center: architecture, OS, list of
// 		//    Machines, allocation policy: time- or space-shared, time zone
// 		//    and its price (G$/Pe time unit).
// 		String arch = "x86";      // system architecture
// 		String os = "Linux";          // operating system
// 		String vmm = "Xen";
// 		double time_zone = 10.0;         // time zone this resource located
// 		double cost = 3.0;              // the cost of using processing in this resource
// 		double costPerMem = 0.05;		// the cost of using memory in this resource
// 		double costPerStorage = 0.1;	// the cost of using storage in this resource
// 		double costPerBw = 0.1;			// the cost of using bw in this resource
// 		LinkedList<Storage> storageList = new LinkedList<Storage>();	//we are not adding SAN devices by now

// 		DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
//                 arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw);


// 		// 6. Finally, we need to create a PowerDatacenter object.
// 		Datacenter datacenter = null;
// 		try {
// 			datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
// 		} catch (Exception e) {
// 			e.printStackTrace();
// 		}

// 		return datacenter;
// 	}
	
// 	private static DatacenterBroker createBroker(){

// 		DatacenterBroker broker = null;
// 		try {
// 			broker = new DatacenterBroker("Broker");
// 		} catch (Exception e) {
// 			e.printStackTrace();
// 			return null;
// 		}
// 		return broker;
// 	}

// 	/**
// 	 * Prints the Cloudlet objects
// 	 * @param list  list of Cloudlets
// 	 */
// 	private static void printCloudletList(List<Cloudlet> list) {
// 		int size = list.size();
// 		Cloudlet cloudlet;

// 		String indent = "    ";
// 		Log.printLine();
// 		Log.printLine("========== OUTPUT ==========");
// 		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
// 				"Data center ID" + indent + "VM ID" + indent + indent + "Time" + indent + "Start Time" + indent + "Finish Time");

// 		DecimalFormat dft = new DecimalFormat("###.##");
// 		for (int i = 0; i < size; i++) {
// 			cloudlet = list.get(i);
// 			Log.print(indent + cloudlet.getCloudletId() + indent + indent);

// 			if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
// 				Log.print("SUCCESS");

// 				Log.printLine( indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId() +
// 						indent + indent + indent + dft.format(cloudlet.getActualCPUTime()) +
// 						indent + indent + dft.format(cloudlet.getExecStartTime())+ indent + indent + indent + dft.format(cloudlet.getFinishTime()));
// 			}
// 		}

// 	}
	
// }

// ------------------------------------------------------------------------------------------------------------------------------------------------------


// QUESTION-10



// --------------------------------------------------------------------------------------------------------------------------------------------------------

// package org.cloudbus.cloudsim.examples;

// import java.text.DecimalFormat;
// import java.util.ArrayList;
// import java.util.Calendar;
// import java.util.LinkedList;
// import java.util.List;

// import org.cloudbus.cloudsim.Cloudlet;
// import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
// import org.cloudbus.cloudsim.Datacenter;
// import org.cloudbus.cloudsim.DatacenterBroker;
// import org.cloudbus.cloudsim.DatacenterCharacteristics;
// import org.cloudbus.cloudsim.Host;
// import org.cloudbus.cloudsim.Log;
// import org.cloudbus.cloudsim.Pe;
// import org.cloudbus.cloudsim.Storage;
// import org.cloudbus.cloudsim.UtilizationModel;
// import org.cloudbus.cloudsim.UtilizationModelFull;
// import org.cloudbus.cloudsim.Vm;
// import org.cloudbus.cloudsim.VmAllocationPolicySimple;
// import org.cloudbus.cloudsim.VmSchedulerTimeShared;
// import org.cloudbus.cloudsim.core.CloudSim;
// import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
// import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
// import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

// public class CloudSimExample10 {
// 	/** The cloudlet list. */
// 	private static List<Cloudlet> cloudletList;

// 	/** The vmlist. */
// 	private static List<Vm> vmlist;
	
// 	private static List<Vm> createVM(int userId, int vms) {

// 		//Creates a container to store VMs. This list is passed to the broker later
// 		LinkedList<Vm> list = new LinkedList<Vm>();

// 		//VM Parameters
// 		long size = 10000; //image size (MB)
// 		int ram = 2048; //vm memory (MB)
// 		int mips = 1000;
// 		long bw = 1000;
// 		int pesNumber = 1; //number of cpus
// 		String vmm = "Xen"; //VMM name

// 		//create VMs
// 		Vm[] vm = new Vm[vms];

// 		for(int i=0;i<vms;i++){
// 			vm[i] = new Vm(i, userId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
// 			//for creating a VM with a space shared scheduling policy for cloudlets:
// 			//vm[i] = Vm(i, userId, mips, pesNumber, ram, bw, size, priority, vmm, new CloudletSchedulerSpaceShared());

// 			list.add(vm[i]);
// 		}

// 		return list;
// 	}
// 	private static List<Cloudlet> createCloudlet(int userId, int cloudlets){
// 		// Creates a container to store Cloudlets
// 		LinkedList<Cloudlet> list = new LinkedList<Cloudlet>();

// 		//cloudlet parameters
// 		long length = 400000;
// 		long fileSize = 300;
// 		long outputSize = 300;
// 		int pesNumber = 1;
// 		UtilizationModel utilizationModel = new UtilizationModelFull();

// 		Cloudlet[] cloudlet = new Cloudlet[cloudlets];
// 		int vmBindId=0;
// 		for(int i=0;i<cloudlets;i++){
// 			cloudlet[i] = new Cloudlet(i, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
// 			// setting the owner of these Cloudlets
// 			cloudlet[i].setUserId(userId);
// 			cloudlet[i].setVmId(vmBindId);
// 			list.add(cloudlet[i]);
// 			if(i%2==1) {
// 				vmBindId++;
// 			}
// 		}

// 		return list;
// 	}
	
// 	public static void main(String[] args) {
// 		Log.printLine("Starting CloudSimExample10...");

// 		try {
// 			// First step: Initialize the CloudSim package. It should be called
// 			// before creating any entities.
// 			int num_user = 1;   // number of grid users
// 			Calendar calendar = Calendar.getInstance();
// 			boolean trace_flag = false;  // mean trace events

// 			// Initialize the CloudSim library
// 			CloudSim.init(num_user, calendar, trace_flag);

// 			// Second step: Create Datacenters
// 			//Datacenters are the resource providers in CloudSim. We need at list one of them to run a CloudSim simulation
// 			@SuppressWarnings("unused")
// 			Datacenter datacenter0 = createDatacenter("Datacenter_0");
// 			@SuppressWarnings("unused")
// 			Datacenter datacenter1 = createDatacenter("Datacenter_1");
// 			@SuppressWarnings("unused")
// 			Datacenter datacenter2 = createDatacenter("Datacenter_2");
// 			@SuppressWarnings("unused")
// 			Datacenter datacenter3 = createDatacenter("Datacenter_3");
// 			@SuppressWarnings("unused")
// 			Datacenter datacenter4 = createDatacenter("Datacenter_4");

// 			//Third step: Create Broker
// 			DatacenterBroker broker = createBroker();
// 			int brokerId = broker.getId();

// 			//Fourth step: Create VMs and Cloudlets and send them to broker
// 			vmlist = createVM(brokerId,10); //creating 10 vms
// 			cloudletList = createCloudlet(brokerId,20); // creating 40 cloudlets

// 			broker.submitVmList(vmlist);
// 			broker.submitCloudletList(cloudletList);

// 			// Fifth step: Starts the simulation
// 			CloudSim.startSimulation();

// 			// Final step: Print results when simulation is over
// 			List<Cloudlet> newList = broker.getCloudletReceivedList();

// 			CloudSim.stopSimulation();

// 			printCloudletList(newList);

// 			Log.printLine("CloudSimExample10 finished!");
// 		}
// 		catch (Exception e)
// 		{
// 			e.printStackTrace();
// 			Log.printLine("The simulation has been terminated due to an unexpected error");
// 		}
// 	}
// 	private static Datacenter createDatacenter(String name){

// 		// Here are the steps needed to create a PowerDatacenter:
// 		// 1. We need to create a list to store one or more
// 		//    Machines
// 		List<Host> hostList = new ArrayList<Host>();

// 		// 2. A Machine contains one or more PEs or CPUs/Cores. Therefore, should
// 		//    create a list to store these PEs before creating
// 		//    a Machine.
// 		List<Pe> peList1 = new ArrayList<Pe>();

// 		int mips = 1000;

// 		// 3. Create PEs and add these into the list.
// 		//for a quad-core machine, a list of 4 PEs is required:
// 		peList1.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
// 		peList1.add(new Pe(1, new PeProvisionerSimple(mips)));
		

// 		//4. Create Hosts with its id and list of PEs and add them to the list of machines
// 		int hostId=0;
// 		int ram = 2048; //host memory (MB)
// 		long storage = 1000000; //host storage
// 		int bw = 10000;

// 		hostList.add(
//     			new Host(
//     				hostId,
//     				new RamProvisionerSimple(ram),
//     				new BwProvisionerSimple(bw),
//     				storage,
//     				peList1,
//     				new VmSchedulerTimeShared(peList1)
//     			)
//     		); // This is our first machine

// 		hostId++;

// 		hostList.add(
//     			new Host(
//     				hostId,
//     				new RamProvisionerSimple(ram),
//     				new BwProvisionerSimple(bw),
//     				storage,
//     				peList1,
//     				new VmSchedulerTimeShared(peList1)
//     			)
//     		); // Second machine


// 		//To create a host with a space-shared allocation policy for PEs to VMs:
// 		//hostList.add(
//     	//		new Host(
//     	//			hostId,
//     	//			new CpuProvisionerSimple(peList1),
//     	//			new RamProvisionerSimple(ram),
//     	//			new BwProvisionerSimple(bw),
//     	//			storage,
//     	//			new VmSchedulerSpaceShared(peList1)
//     	//		)
//     	//	);

// 		//To create a host with a oportunistic space-shared allocation policy for PEs to VMs:
// 		//hostList.add(
//     	//		new Host(
//     	//			hostId,
//     	//			new CpuProvisionerSimple(peList1),
//     	//			new RamProvisionerSimple(ram),
//     	//			new BwProvisionerSimple(bw),
//     	//			storage,
//     	//			new VmSchedulerOportunisticSpaceShared(peList1)
//     	//		)
//     	//	);


// 		// 5. Create a DatacenterCharacteristics object that stores the
// 		//    properties of a data center: architecture, OS, list of
// 		//    Machines, allocation policy: time- or space-shared, time zone
// 		//    and its price (G$/Pe time unit).
// 		String arch = "x86";      // system architecture
// 		String os = "Linux";          // operating system
// 		String vmm = "Xen";
// 		double time_zone = 10.0;         // time zone this resource located
// 		double cost = 3.0;              // the cost of using processing in this resource
// 		double costPerMem = 0.05;		// the cost of using memory in this resource
// 		double costPerStorage = 0.1;	// the cost of using storage in this resource
// 		double costPerBw = 0.1;			// the cost of using bw in this resource
// 		LinkedList<Storage> storageList = new LinkedList<Storage>();	//we are not adding SAN devices by now

// 		DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
//                 arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw);


// 		// 6. Finally, we need to create a PowerDatacenter object.
// 		Datacenter datacenter = null;
// 		try {
// 			datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
// 		} catch (Exception e) {
// 			e.printStackTrace();
// 		}

// 		return datacenter;
// 	}
	
// 	private static DatacenterBroker createBroker(){

// 		DatacenterBroker broker = null;
// 		try {
// 			broker = new DatacenterBroker("Broker");
// 		} catch (Exception e) {
// 			e.printStackTrace();
// 			return null;
// 		}
// 		return broker;
// 	}

// 	/**
// 	 * Prints the Cloudlet objects
// 	 * @param list  list of Cloudlets
// 	 */
// 	private static void printCloudletList(List<Cloudlet> list) {
// 		int size = list.size();
// 	Cloudlet cloudlet;

// 	String indent = "    ";
// 	Log.printLine();
// 	Log.printLine("========== OUTPUT ==========");
// 	Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
// 			"Data center ID" + indent + "VM ID" + indent + indent + "Time" + indent + "Start Time" + indent + "Finish Time");

// 	DecimalFormat dft = new DecimalFormat("###.##");
// 	for (int i = 0; i < size; i++) {
// 		cloudlet = list.get(i);
// 		Log.print(indent + cloudlet.getCloudletId() + indent + indent);

// 		if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
// 			Log.print("SUCCESS");

// 			Log.printLine( indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId() +
// 					indent + indent + indent + dft.format(cloudlet.getActualCPUTime()) +
// 					indent + indent + dft.format(cloudlet.getExecStartTime())+ indent + indent + indent + dft.format(cloudlet.getFinishTime()));
// 		}
// 	}

// }
// }
// -----------------------------------------------------------------------------------------------------------------------------------------------


// QUESTION-11




// -------------------------------------------------------------------------------------------------------------------------------------------------
/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation
 *               of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009, The University of Melbourne, Australia
 */


//  package org.cloudbus.cloudsim.examples;

//  import java.text.DecimalFormat;
//  import java.util.ArrayList;
//  import java.util.Calendar;
//  import java.util.Collection;
//  import java.util.Collections;
//  import java.util.LinkedList;
//  import java.util.List;
//  import java.util.stream.Collectors;
//  import java.lang.*;
//  import org.cloudbus.cloudsim.Cloudlet;
//  import org.cloudbus.cloudsim.CloudletSchedulerSpaceShared;
//  import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
//  import org.cloudbus.cloudsim.Datacenter;
//  import org.cloudbus.cloudsim.DatacenterBroker;
//  import org.cloudbus.cloudsim.DatacenterCharacteristics;
//  import org.cloudbus.cloudsim.Host;
//  import org.cloudbus.cloudsim.Log;
//  import org.cloudbus.cloudsim.Pe;
//  import org.cloudbus.cloudsim.Storage;
//  import org.cloudbus.cloudsim.UtilizationModel;
//  import org.cloudbus.cloudsim.UtilizationModelFull;
//  import org.cloudbus.cloudsim.Vm;
//  import org.cloudbus.cloudsim.VmAllocationPolicySimple;
//  import org.cloudbus.cloudsim.VmSchedulerTimeShared;
//  import org.cloudbus.cloudsim.core.CloudSim;
//  import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
//  import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
//  import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;
 
//  /**
//   * An example showing how to create
//   * scalable simulations.
//   */
//  /**SPECS:
//   * 
//   * VM:
//   * 	Size: 10,000
//   * 	Ram: 512
//   * 	mips: 1000
//   * 	pesNumber: 1
//   * Cloudlet:
//   * 	length:1000 (40 cloudlets will get different length)
//   * 	filesize:300
//   * 	outputsize: 300
//   * 	pesNumber: 1
//   * No of users=1
//   * Datacenter = 2
//   * Host = 2 (1 Quad, 1 Dual)
//   * Host:
//   * 	mips:1000
//   * 	ram:2048
//   * 	storage: 10,000,00
//   * VM = 1
//   * CLoudlets=40
//   * 
//   * IMPLEMENT SHORTEST JOB FIRST
//   * 
//   */
 
//  public class CloudSimExample11 {
 
//      /** The cloudlet list. */
//      private static List<Cloudlet> cloudletList;
 
//      /** The vmlist. */
//      private static List<Vm> vmlist;
 
//      private static List<Vm> createVM(int userId, int vms) {
 
//          //Creates a container to store VMs. This list is passed to the broker later
//          LinkedList<Vm> list = new LinkedList<Vm>();
 
//          //VM Parameters
//          long size = 10000; //image size (MB)
//          int ram = 512; //vm memory (MB)
//          int mips = 1000;
//          long bw = 1000;
//          int pesNumber = 1; //number of cpus
//          String vmm = "Xen"; //VMM name
 
//          //create VMs
//          Vm[] vm = new Vm[vms];
 
//          for(int i=0;i<vms;i++){
//              vm[i] = new Vm(i, userId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerSpaceShared());
//              //for creating a VM with a space shared scheduling policy for cloudlets:
//              //vm[i] = Vm(i, userId, mips, pesNumber, ram, bw, size, priority, vmm, new CloudletSchedulerSpaceShared());
 
//              list.add(vm[i]);
//          }
 
//          return list;
//      }
 
 
//      private static List<Cloudlet> createCloudlet(int userId, int cloudlets){
//          // Creates a container to store Cloudlets
//          LinkedList<Cloudlet> list = new LinkedList<Cloudlet>();
 
//          //cloudlet parameters
//          long length = 1000;
//          long fileSize = 300;
//          long outputSize = 300;
//          int pesNumber = 1;
//          UtilizationModel utilizationModel = new UtilizationModelFull();
 
//          Cloudlet[] cloudlet = new Cloudlet[cloudlets];
//          for(int i=0;i<cloudlets;i++){
//              int r = (int) (Math.random()*1000);
//              cloudlet[i] = new Cloudlet(i, length+r, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
//              // setting the owner of these Cloudlets
//              cloudlet[i].setUserId(userId);
//              list.add(cloudlet[i]);
//          }
 
//          return list;
//      }
 
 
//      ////////////////////////// STATIC METHODS ///////////////////////
 
//      /**
//       * Creates main() to run this example
//       */
//      public static void main(String[] args) {
//          Log.printLine("Starting CloudSimExample11...");
 
//          try {
//              // First step: Initialize the CloudSim package. It should be called
//              // before creating any entities.
//              int num_user = 1;   // number of grid users
//              Calendar calendar = Calendar.getInstance();
//              boolean trace_flag = false;  // mean trace events
 
//              // Initialize the CloudSim library
//              CloudSim.init(num_user, calendar, trace_flag);
 
//              // Second step: Create Datacenters
//              //Datacenters are the resource providers in CloudSim. We need at list one of them to run a CloudSim simulation
//              @SuppressWarnings("unused")
//              Datacenter datacenter0 = createDatacenter("Datacenter_0");
//              @SuppressWarnings("unused")
//              Datacenter datacenter1 = createDatacenter("Datacenter_1");
 
//              //Third step: Create Broker
//              DatacenterBroker broker = createBroker();
//              int brokerId = broker.getId();
//              //Fourth step: Create VMs and Cloudlets and send them to broker
//              vmlist = createVM(brokerId,1); //creating 20 vms
//              cloudletList = createCloudlet(brokerId,40); // creating 40 cloudlets
//              cloudletList = cloudletList.stream().sorted((o1, o2)->(Long.valueOf(o1.getCloudletLength())).
//                      compareTo(Long.valueOf(o2.getCloudletLength()))).
//                      collect(Collectors.toList());
//              Collections.reverse(cloudletList);
//              for(Cloudlet var : cloudletList) {
//                  System.out.println(var.getCloudletId() + " " + var.getCloudletLength());
//              }
//              broker.submitVmList(vmlist);
//              broker.submitCloudletList(cloudletList);
 
//              // Fifth step: Starts the simulation
//              CloudSim.startSimulation();
 
//              // Final step: Print results when simulation is over
//              List<Cloudlet> newList = broker.getCloudletReceivedList();
 
//              CloudSim.stopSimulation();
 
//              printCloudletList(newList);
 
//              Log.printLine("CloudSimExample11 finished!");
//          }
//          catch (Exception e)
//          {
//              e.printStackTrace();
//              Log.printLine("The simulation has been terminated due to an unexpected error");
//          }
//      }
 
//      private static Datacenter createDatacenter(String name){
 
//          // Here are the steps needed to create a PowerDatacenter:
//          // 1. We need to create a list to store one or more
//          //    Machines
//          List<Host> hostList = new ArrayList<Host>();
 
//          // 2. A Machine contains one or more PEs or CPUs/Cores. Therefore, should
//          //    create a list to store these PEs before creating
//          //    a Machine.
//          List<Pe> peList1 = new ArrayList<Pe>();
 
//          int mips = 1000;
 
//          // 3. Create PEs and add these into the list.
//          //for a quad-core machine, a list of 4 PEs is required:
//          peList1.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
//          peList1.add(new Pe(1, new PeProvisionerSimple(mips)));
//          peList1.add(new Pe(2, new PeProvisionerSimple(mips)));
//          peList1.add(new Pe(3, new PeProvisionerSimple(mips)));
 
//          //Another list, for a dual-core machine
//          List<Pe> peList2 = new ArrayList<Pe>();
 
//          peList2.add(new Pe(0, new PeProvisionerSimple(mips)));
//          peList2.add(new Pe(1, new PeProvisionerSimple(mips)));
 
//          //4. Create Hosts with its id and list of PEs and add them to the list of machines
//          int hostId=0;
//          int ram = 2048; //host memory (MB)
//          long storage = 1000000; //host storage
//          int bw = 10000;
 
//          hostList.add(
//                  new Host(
//                      hostId,
//                      new RamProvisionerSimple(ram),
//                      new BwProvisionerSimple(bw),
//                      storage,
//                      peList1,
//                      new VmSchedulerTimeShared(peList1)
//                  )
//              ); // This is our first machine
 
//          hostId++;
 
//          hostList.add(
//                  new Host(
//                      hostId,
//                      new RamProvisionerSimple(ram),
//                      new BwProvisionerSimple(bw),
//                      storage,
//                      peList2,
//                      new VmSchedulerTimeShared(peList2)
//                  )
//              ); // Second machine
 
 
//          // 5. Create a DatacenterCharacteristics object that stores the
//          //    properties of a data center: architecture, OS, list of
//          //    Machines, allocation policy: time- or space-shared, time zone
//          //    and its price (G$/Pe time unit).
//          String arch = "x86";      // system architecture
//          String os = "Linux";          // operating system
//          String vmm = "Xen";
//          double time_zone = 10.0;         // time zone this resource located
//          double cost = 3.0;              // the cost of using processing in this resource
//          double costPerMem = 0.05;		// the cost of using memory in this resource
//          double costPerStorage = 0.1;	// the cost of using storage in this resource
//          double costPerBw = 0.1;			// the cost of using bw in this resource
//          LinkedList<Storage> storageList = new LinkedList<Storage>();	//we are not adding SAN devices by now
 
//          DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
//                  arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw);
 
 
//          // 6. Finally, we need to create a PowerDatacenter object.
//          Datacenter datacenter = null;
//          try {
//              datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
//          } catch (Exception e) {
//              e.printStackTrace();
//          }
 
//          return datacenter;
//      }
 
//      //We strongly encourage users to develop their own broker policies, to submit vms and cloudlets according
//      //to the specific rules of the simulated scenario
//      private static DatacenterBroker createBroker(){
 
//          DatacenterBroker broker = null;
//          try {
//              broker = new DatacenterBroker("Broker");
//          } catch (Exception e) {
//              e.printStackTrace();
//              return null;
//          }
//          return broker;
//      }
 
//      /**
//       * Prints the Cloudlet objects
//       * @param list  list of Cloudlets
//       */
//      private static void printCloudletList(List<Cloudlet> list) {
//          int size = list.size();
//          Cloudlet cloudlet;
 
//          String indent = "    ";
//          Log.printLine();
//          Log.printLine("========== OUTPUT ==========");
//          Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
//                  "Data center ID" + indent + "VM ID" + indent + indent + "Time" + indent + "Start Time" + indent + "Finish Time");
 
//          DecimalFormat dft = new DecimalFormat("###.##");
//          for (int i = 0; i < size; i++) {
//              cloudlet = list.get(i);
//              Log.print(indent + cloudlet.getCloudletId() + indent + indent);
 
//              if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
//                  Log.print("SUCCESS");
 
//                  Log.printLine( indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId() +
//                          indent + indent + indent + dft.format(cloudlet.getActualCPUTime()) +
//                          indent + indent + dft.format(cloudlet.getExecStartTime())+ indent + indent + indent + dft.format(cloudlet.getFinishTime()));
//              }
//          }
 
//      }
//  }


//---------------------------------------------------------------------------------------------------------------------------------------------------







// -------------------------------------------PYQ--------------------------------------------------------------

//---------------------------------------------------------------------------------------------------------------------------------------------------

// PYQ_QUESTION_1

//---------------------------------------------------------------------------------------------------------------------------------------------------

// import org.cloudbus.cloudsim.Cloudlet;
// import org.cloudbus.cloudsim.CloudletSchedulerSpaceShared;
// import org.cloudbus.cloudsim.Datacenter;
// import org.cloudbus.cloudsim.DatacenterBroker;
// import org.cloudbus.cloudsim.Host;
// import org.cloudbus.cloudsim.Pe;
// import org.cloudbus.cloudsim.Vm;
// import org.cloudbus.cloudsim.core.CloudSim;
// import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
// import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
// import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

// import java.util.ArrayList;
// import java.util.Calendar;
// import java.util.LinkedList;
// import java.util.List;

// public class SchedulerSpaceOrTime {
//     private static List<Cloudlet> cloudletList;
//     private static List<Vm> vmList;

//     public static void main(String[] args) {
//         Log.printLine("Starting CloudSim Example...");

//         try {
//             // Initialize CloudSim
//             int numUsers = 1;
//             Calendar calendar = Calendar.getInstance();
//             boolean traceFlag = false;

//             CloudSim.init(numUsers, calendar, traceFlag);

//             // Create Datacenter
//             Datacenter datacenter0 = createDatacenter("Datacenter_0");

//             // Create Broker
//             DatacenterBroker broker = createBroker();
//             int brokerId = broker.getId();

//             // Create VMs
//             vmList = new ArrayList<Vm>();
//             int vmId = 0;
//             int mips = 1000;
//             long size = 10000; // Image size (MB)
//             int ram = 512; // VM memory (MB)
//             long bw = 1000;
//             int pesNumber = 1; // Number of CPUs
//             String vmm = "Xen"; // VMM name

//             // Create four VMs
//             for (int i = 0; i < 4; i++) {
//                 Vm vm = new Vm(vmId++, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerSpaceShared());
//                 vmList.add(vm);
//             }

//             // Submit VM list to the broker
//             broker.submitVmList(vmList);

//             // Create Cloudlets
//             cloudletList = new ArrayList<Cloudlet>();
//             int idShift = 0;
//             int cloudletId = 0;
//             long length = 50000;
//             long fileSize = 300;
//             long outputSize = 300;
//             int pesNumberCloudlet = 1;
//             UtilizationModel utilizationModel = new UtilizationModelFull();

//             // Create eight Cloudlets
//             for (int i = 0; i < 8; i++) {
//                 Cloudlet cloudlet = new Cloudlet(cloudletId++, length, pesNumberCloudlet, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
//                 cloudlet.setUserId(brokerId);
//                 cloudletList.add(cloudlet);
//             }

//             // Submit Cloudlet list to the broker
//             broker.submitCloudletList(cloudletList);

//             // Start simulation
//             CloudSim.startSimulation();

//             // Print results when simulation is over
//             List<Cloudlet> newList = broker.getCloudletReceivedList();
//             CloudSim.stopSimulation();

//             printCloudletList(newList);

//             Log.printLine("CloudSim Example finished!");
//         } catch (Exception e) {
//             e.printStackTrace();
//             Log.printLine("Unwanted errors happen");
//         }
//     }

//     private static Datacenter createDatacenter(String name) {
//         // Here are the steps needed to create a Datacenter:
//         // 1. We need to create a list to store one or more Machines
//         List<Host> hostList = new ArrayList<Host>();

//         // 2. A Machine contains one or more PEs or CPUs/Cores. Therefore, should
//         // create a list to store these PEs before creating a Machine.
//         List<Pe> peList1 = new ArrayList<Pe>();

//         int mips = 1000;

//         // 3. Create PEs and add these into the list.
//         peList1.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
//         peList1.add(new Pe(1, new PeProvisionerSimple(mips)));
//         peList1.add(new Pe(2, new PeProvisionerSimple(mips)));
//         peList1.add(new Pe(3, new PeProvisionerSimple(mips)));

//         // 4. Create Hosts with its id and list of PEs and add them to the list of machines
//         int hostId = 0;
//         int ram = 2048; // host memory (MB)
//         long storage = 1000000; // host storage
//         int bw = 10000;

//         hostList.add(
//                 new Host(
//                         hostId,
//                         new RamProvisionerSimple(ram),
//                         new BwProvisionerSimple(bw),
//                         storage,
//                         peList1,
//                         new VmSchedulerTimeShared(peList1)
//                 )
//         ); // This is our first machine

//         // Create a DatacenterCharacteristics object that stores the
//         // properties of a data center: architecture, OS, list of
//         // Machines, allocation policy: time- or space-shared, time zone
//         // and its price (G$/Pe time unit).
//         String arch = "x86"; // system architecture
//         String os = "Linux"; // operating system
//         String vmm = "Xen";
//         double time_zone = 10.0; // time zone this resource located
//         double cost = 3.0; // the cost of using processing in this resource
//         double costPerMem = 0.05; // the cost of using memory in this resource
//         double costPerStorage = 0.001; // the cost of using storage in this
//         double costPerBw = 0.0; // the cost of using bw in this resource

//         LinkedList<Storage> storageList = new LinkedList<Storage>(); // we are not adding SAN devices by now

//         DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
//                 arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw);

//         // 6. Finally, we need to create a Datacenter object.
//         Datacenter datacenter = null;
//         try {
//             datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }

//         return datacenter;
//     }

//     // We strongly encourage users to develop their own broker policies, to
//     // submit vms and cloudlets according to the specific rules of the simulated scenario
//     private static DatacenterBroker createBroker() {
//         DatacenterBroker broker = null;
//         try {
//             broker = new DatacenterBroker("Broker");
//         } catch (Exception e) {
//             e.printStackTrace();
//             return null;
//         }
//         return broker;
//     }

//     private static void printCloudletList(List<Cloudlet> list) {
//         int size = list.size();
//         Cloudlet cloudlet;

//         String indent = "    ";
//         Log.printLine();
//         Log.printLine("========== OUTPUT ==========");
//         Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
//                 "Data center ID" + indent + "VM ID" + indent + "Time" + indent + "Start Time" + indent + "Finish Time");

//         for (int i = 0; i < size; i++) {
//             cloudlet = list.get(i);
//             Log.print(indent + cloudlet.getCloudletId() + indent + indent);

//             if (cloudlet.getStatus() == Cloudlet.SUCCESS) {
//                 Log.print("SUCCESS");

//                 Log.printLine(indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId() +
//                         indent + indent + cloudlet.getActualCPUTime() + indent + indent + cloudlet.getExecStartTime() +
//                         indent + indent + cloudlet.getFinishTime());
//             }
//         }
//     }
// }

// -----------------------------------------------------------------------------------------------------------------------------
// PYQ-QUESTION-2
// -----------------------------------------------------------------------------------------------------------------------------
// import org.cloudbus.cloudsim.*;
// import org.cloudbus.cloudsim.core.CloudSim;
// import org.cloudbus.cloudsim.provisioners.*;

// import java.util.ArrayList;
// import java.util.Calendar;
// import java.util.List;

// public class DestroyVMSimulation {

//     public static void main(String[] args) {
//         try {
//             // Initialize CloudSim
//             int numUser = 1; // number of cloud users
//             Calendar calendar = Calendar.getInstance();
//             boolean traceFlag = false; // mean trace events

//             CloudSim.init(numUser, calendar

// , traceFlag);

//             // Create Datacenters
//             Datacenter datacenter0 = createDatacenter("Datacenter_0");

//             // Create Broker
//             DatacenterBroker broker = createBroker();
//             int brokerId = broker.getId();

//             // Create VMs
//             List<Vm> vmList = new ArrayList<Vm>();
//             int vmid = 0;
//             int mips = 1000;
//             long size = 10000; // image size (MB)
//             int ram = 2048; // vm memory (MB)
//             long bw = 1000;
//             int pesNumber = 1; // number of cpus
//             String vmm = "Xen"; // VMM name

//             Vm vm1 = new Vm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerSpaceShared());
//             vmList.add(vm1);

//             // submit vm list to the broker
//             broker.submitVmList(vmList);

//             // Create Cloudlets
//             List<Cloudlet> cloudletList = new ArrayList<Cloudlet>();

//             int id = 0;
//             long length = 40000;
//             long fileSize = 300;
//             long outputSize = 300;
//             UtilizationModel utilizationModel = new UtilizationModelFull();

//             Cloudlet cloudlet1 = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
//             cloudlet1.setUserId(brokerId);
//             cloudletList.add(cloudlet1);

//             // submit cloudlet list to the broker
//             broker.submitCloudletList(cloudletList);

//             // Start simulation
//             CloudSim.startSimulation();

//             // Destroy VM
//             CloudSim.destroyVm(vm1.getId());

//             List<Cloudlet> newList = broker.getCloudletReceivedList();

//             CloudSim.stopSimulation();

//             // Print results
//             printCloudletList(newList);

//             Log.printLine("Simulation finished!");
//         } catch (Exception e) {
//             e.printStackTrace();
//             Log.printLine("Unwanted errors happened.");
//         }
//     }

//     private static Datacenter createDatacenter(String name) {
//         List<Host> hostList = new ArrayList<Host>();

//         List<Pe> peList = new ArrayList<Pe>();

//         int mips = 1000;

//         peList.add(new Pe(0, new PeProvisionerSimple(mips)));
//         peList.add(new Pe(1, new PeProvisionerSimple(mips)));
//         peList.add(new Pe(2, new PeProvisionerSimple(mips)));
//         peList.add(new Pe(3, new PeProvisionerSimple(mips)));

//         int hostId = 0;
//         int ram = 4096; // host memory (MB)
//         long storage = 1000000; // host storage
//         int bw = 10000;

//         hostList.add(new Host(
//                 hostId,
//                 new RamProvisionerSimple(ram),
//                 new BwProvisionerSimple(bw),
//                 storage,
//                 peList,
//                 new VmSchedulerTimeShared(peList)
//         ));

//         String arch = "x86";
//         String os = "Linux";
//         String vmm = "Xen";
//         double time_zone = 10.0;
//         double cost = 3.0;
//         double costPerMem = 0.05;
//         double costPerStorage = 0.001;
//         double costPerBw = 0.0;

//         LinkedList<Storage> storageList = new LinkedList<Storage>();

//         DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
//                 arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw);

//         Datacenter datacenter = null;
//         try {
//             datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }

//         return datacenter;
//     }

//     private static DatacenterBroker createBroker() {
//         DatacenterBroker broker = null;
//         try {
//             broker = new DatacenterBroker("Broker");
//         } catch (Exception e) {
//             e.printStackTrace();
//             return null;
//         }
//         return broker;
//     }

//     private static void printCloudletList(List<Cloudlet> list) {
//         int size = list.size();
//         Cloudlet cloudlet;

//         String indent = "    ";
//         Log.printLine();
//         Log.printLine("========== OUTPUT ==========");
//         Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
//                 "Data center ID" + indent + "VM ID" + indent + "Time" + indent + "Start Time" + indent + "Finish Time");

//         for (int i = 0; i < size; i++) {
//             cloudlet = list.get(i);
//             Log.print(indent + cloudlet.getCloudletId() + indent + indent);

//             if (cloudlet.getStatus() == Cloudlet.SUCCESS) {
//                 Log.print("SUCCESS");

//                 Log.printLine(indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId() +
//                         indent + indent + cloudlet.getActualCPUTime() + indent + indent + cloudlet.getExecStartTime() +
//                         indent + indent + cloudlet.getFinishTime());
//             }
//         }
//     }
// }

// -----------------------------------------------------------------------------------------------------------------------------------

// PYQ-QUESTION-3

// -------------------------------------------------------------------------------------------------------------------------------------


// import org.cloudbus.cloudsim.*;
// import org.cloudbus.cloudsim.core.CloudSim;
// import org.cloudbus.cloudsim.provisioners.*;

// import java.util.ArrayList;
// import java.util.Calendar;
// import java.util.LinkedList;
// import java.util.List;

// public class ManualMigrationSimulation {

//     public static void main(String[] args) {
//         try {
//             // Initialize CloudSim
//             int numUser = 1; // number of cloud users
//             Calendar calendar = Calendar.getInstance();
//             boolean traceFlag = false; // mean trace events

//             CloudSim.init(numUser, calendar, traceFlag);

//             // Create Datacenters
//             Datacenter datacenter0 = createDatacenter("Datacenter_0");
//             Datacenter datacenter1 = createDatacenter("Datacenter_1");

//             // Create Broker
//             DatacenterBroker broker = createBroker();
//             int brokerId = broker.getId();

//             // Create VMs
//             List<Vm> vmList = new ArrayList<Vm>();
//             int vmid = 0;
//             int mips = 10000;
//             long size = 10000; // image size (MB)
//             int ram = 800000; // vm memory (MB)
//             long bw = 10000;
//             int pesNumber = 4; // number of cpus
//             String vmm = "Xen"; // VMM name

//             Vm vm1 = new Vm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerSpaceShared());
//             vmList.add(vm1);

//             // submit vm list to the broker
//             broker.submitVmList(vmList);

//             // Create Cloudlets
//             List<Cloudlet> cloudletList = new ArrayList<Cloudlet>();

//             int id = 0;
//             long length = 20000;
//             long fileSize = 300;
//             long outputSize = 300;
//             UtilizationModel utilizationModel = new UtilizationModelFull();

//             Cloudlet cloudlet1 = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
//             cloudlet1.setUserId(brokerId);
//             cloudletList.add(cloudlet1);

//             // submit cloudlet list to the broker
//             broker.submitCloudletList(cloudletList);

//             // Start simulation
//             CloudSim.startSimulation();

//             // Manually migrate VM
//             broker.migrateVm(vm1.getId(), datacenter1.getId());

//             List<Cloudlet> newList = broker.getCloudletReceivedList();

//             CloudSim.stopSimulation();

//             // Print results
//             printCloudletList(newList);

//             Log.printLine("Simulation finished!");
//         } catch (Exception e) {
//             e.printStackTrace();
//             Log.printLine("Unwanted errors happened.");
//         }
//     }

//     private static Datacenter createDatacenter(String name) {
//         List<Host> hostList = new ArrayList<Host>();

//         List<Pe> peList = new ArrayList<Pe>();

//         int mips = 1000;

//         peList.add(new Pe(0, new PeProvisionerSimple(mips)));
//         peList.add(new Pe(1, new PeProvisionerSimple(mips)));
//         peList.add(new Pe(2, new PeProvisionerSimple(mips)));
//         peList.add(new Pe(3, new PeProvisionerSimple(mips)));

//         int hostId = 0;
//         int ram = 2048; // host memory (MB)
//         long storage = 1000000; // host storage
//         int bw = 10000;

//         hostList.add(new Host(
//                 hostId,
//                 new RamProvisionerSimple(ram),
//                 new BwProvisionerSimple(bw),
//                 storage,
//                 peList,
//                 new VmSchedulerTimeShared(peList)
//         ));

//         String arch = "x86";
//         String os = "Linux";
       

//  String vmm = "Xen";
//         double time_zone = 10.0;
//         double cost = 3.0;
//         double costPerMem = 0.05;
//         double costPerStorage = 0.001;
//         double costPerBw = 0.0;

//         LinkedList<Storage> storageList = new LinkedList<Storage>();

//         DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
//                 arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw);

//         Datacenter datacenter = null;
//         try {
//             datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }

//         return datacenter;
//     }

//     private static DatacenterBroker createBroker() {
//         DatacenterBroker broker = null;
//         try {
//             broker = new DatacenterBroker("Broker");
//         } catch (Exception e) {
//             e.printStackTrace();
//             return null;
//         }
//         return broker;
//     }

//     private static void printCloudletList(List<Cloudlet> list) {
//         int size = list.size();
//         Cloudlet cloudlet;

//         String indent = "    ";
//         Log.printLine();
//         Log.printLine("========== OUTPUT ==========");
//         Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
//                 "Data center ID" + indent + "VM ID" + indent + "Time" + indent + "Start Time" + indent + "Finish Time");

//         for (int i = 0; i < size; i++) {
//             cloudlet = list.get(i);
//             Log.print(indent + cloudlet.getCloudletId() + indent + indent);

//             if (cloudlet.getStatus() == Cloudlet.SUCCESS) {
//                 Log.print("SUCCESS");

//                 Log.printLine(indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId() +
//                         indent + indent + cloudlet.getActualCPUTime() + indent + indent + cloudlet.getExecStartTime() +
//                         indent + indent + cloudlet.getFinishTime());
//             }
//         }
//     }
// }


public class Main {

}
