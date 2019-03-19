package unacloud.allocation.runner;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import unacloud.allocation.runner.utils.UnaCloudConnection;
import uniandes.unacloud.web.domain.DeployedImage;
import uniandes.unacloud.web.domain.Execution;
import uniandes.unacloud.web.domain.ExecutionState;
import uniandes.unacloud.web.domain.HardwareProfile;
import uniandes.unacloud.web.domain.PhysicalMachine;
import uniandes.unacloud.web.pmallocators.AllocatorEnum;
import uniandes.unacloud.web.pmallocators.AllocatorException;
import uniandes.unacloud.web.pmallocators.ExecutionAllocator;
import uniandes.unacloud.web.pmallocators.PhysicalMachineAllocationDescription;

public class Allocate {

	private static class AllocateOptions {
		
		@Parameter(names = {"-in", "--labs"}, 
				description = "Comma-separated list of laboratory names",				
				required = true)
		private List<String> labs = new ArrayList<>();
		
		@Parameter(names = {"-a", "--algo"}, 
				description = "Name of the algorithm to use (Random, Round Robin, First Fit,           First Fit Decreasing, Best Fit, Sorting or Singleton)")
		private String algorithmName = "Singleton"; 
		
		@Parameter(names = {"-s", "--small"},
				description="Number of small instances")
		int numberOfSmall = 0;

		@Parameter(names = {"-m", "--medium"},
				description="Number of medium instances")
		int numberOfMedium = 0;

		@Parameter(names = {"-l", "--large"},
				description="Number of large instances")
		int numberOfLarge = 0;

		@Parameter(names = {"-x", "--xlarge"},
				description="Number of x-large instances")
		int numberOfXlarge = 0;
		
		@Parameter(names = {"-o", "--output"},
				description="Name of the file where the results will be stored")
		String outputfilename = "";
	
		@Parameter(names = {"-c", "--case"},
				description="comma separated values for the number of instances, e.g., \"1,1,1,3\"" )
		List<Integer> numbers;
		
		@Parameter(names = {"-i", "--useinput"},
				description="use test case description in the standard input")
		boolean useInput = false;
	}
	
	private static class TestCaseValues {
		@Parameter(description="comma separated values for the number of instances, e.g., \"1,1,1,3\"" )
		List<Integer> numbers;
	}
	
	public static void main(String[] args) {
		
		// process arguments
		AllocateOptions options = new AllocateOptions();
		try {
			JCommander.newBuilder().addObject(options).build().parse(args);			
		} catch (ParameterException e) {
			e.usage();
			System.err.println("Error: wrong parameters: " +e.getMessage());
			System.exit(-1);
		}

		// Look for the algorithm
		ExecutionAllocator allocator = null;
		try {
			allocator =  AllocatorEnum.getAllocatorByName(options.algorithmName).getAllocator();			
			if (allocator == null)	{
				throw new IllegalArgumentException();
			}
			
		} catch (IllegalArgumentException e) {
			System.err.println("Error: algorithm " + options.algorithmName + " not found");
			System.exit(-1);
		}
		
		
		// check if the values must be obtained from the the standard input
		if (options.useInput) {
			
			// read the line
			Scanner scanner = new Scanner(System.in); 
			String line = scanner.nextLine();
			scanner.close();

			// process the line using JCommander
			TestCaseValues testCase = new TestCaseValues();
			try {
				JCommander.newBuilder().addObject(testCase).build().parse(line.split(","));			
			} catch (ParameterException e) {
				e.usage();
				System.err.println("Error: wrong values in the test case : " +e.getMessage());
				System.exit(-1);
			}
			
			// translate numbers to the values
			if (testCase.numbers.size() > 0) {
				if (testCase.numbers.size() < 4) {
					System.err.println("Error: providing a case with less than 4 numbers.");
					System.exit(-1);				
				}
				options.numberOfSmall  = testCase.numbers.get(0);
				options.numberOfMedium = testCase.numbers.get(1);
				options.numberOfLarge  = testCase.numbers.get(2);
				options.numberOfXlarge = testCase.numbers.get(3);
			}

		} else {
			
			// check the number of requested machines
			if ((options.numbers == null  || options.numbers.size() == 0 )) {
				
				// if a test case was not provided, the sum of individual numbers must be greather than zero
				if (options.numberOfSmall + options.numberOfMedium + options.numberOfLarge + options.numberOfXlarge <= 0) {
					System.err.println("Error: number of requested machines is zero.");
					System.exit(-1);
				}
			
			} else {
			
				// if a test case is provided, the sum of individual numbers must be zero
				if (options.numberOfSmall + options.numberOfMedium + options.numberOfLarge + options.numberOfXlarge > 0) {
					System.err.println("Error: providing number of requested machines using both -case and individual numbers.");
					System.exit(-1);
				}
				
				// translate numbers to the values
				if (options.numbers.size() > 0) {
					if (options.numbers.size() < 4) {
						System.err.println("Error: providing a case with less than 4 numbers.");
						System.exit(-1);				
					}
					options.numberOfSmall  = options.numbers.get(0);
					options.numberOfMedium = options.numbers.get(1);
					options.numberOfLarge  = options.numbers.get(2);
					options.numberOfXlarge = options.numbers.get(3);
				}
		
			}				
				
		}
		
		// Connect to UnaCloud
		UnaCloudConnection conn = UnaCloudConnection.getInstance();

        // Look for all the machines to use in the allocation
		List<PhysicalMachine> physicalMachines = conn.findMachinesByLabs(options.labs);
		if (physicalMachines.size() == 0) {
			System.err.println("Error: no machines in the labs named " + options.labs);
		}		
		
		// Show request
		// ============
		
		System.out.println("Algorithm    : " + options.algorithmName);
		System.out.println("Requested Machines");
		System.out.println("       small : " + options.numberOfSmall);
		System.out.println("      medium : " + options.numberOfMedium);
		System.out.println("       large : " + options.numberOfLarge);
		System.out.println("      xlarge : " + options.numberOfXlarge);
		System.out.println("Laboratories");
		for(String lab : options.labs) {
			System.out.println("             : " + lab);
		}
		System.out.println("num machines : " + physicalMachines.size());
		System.out.println();
		
		// Process
		// =======
		
		// Create the Hardware Profiles
		HardwareProfile smallProfile = new HardwareProfile();
		smallProfile.setId(1L);
		smallProfile.setCores(1);
		smallProfile.setRam(1);
		
		HardwareProfile mediumProfile = new HardwareProfile();
		mediumProfile.setId(2L);
		mediumProfile.setCores(2);
		mediumProfile.setRam(2);

		HardwareProfile largeProfile = new HardwareProfile();
		largeProfile.setId(3L);
		largeProfile.setCores(3);
		largeProfile.setRam(4);

		HardwareProfile xlargeProfile = new HardwareProfile();
		xlargeProfile.setId(4L);
		xlargeProfile.setCores(1);
		xlargeProfile.setRam(8);

		// Create the ExecutionState to use
		ExecutionState execState = new ExecutionState();
		execState.setState("REQUESTED");
		
		// Create the DeployedImage to use
		DeployedImage depImage = new DeployedImage();
		depImage.setHighAvaliavility(false);
		depImage.setExecutionList(new ArrayList<Execution>());
		
		// create the list for executions
		List<Execution> executionList = new ArrayList<Execution>();
		
		// add the executions
		for (int i=0; i<options.numberOfSmall; i++) {
			Execution execution = new Execution();
			execution.setDeployedImage(depImage);
			execution.setName("test-small-");
			execution.setHardwareProfile(smallProfile);
			execution.setDuration(600);
			execution.setStateId(execState);
			
			depImage.getExecutionList().add(execution);
			executionList.add(execution);
		}
		
		for (int i=0; i<options.numberOfMedium; i++) {
			Execution execution = new Execution();
			execution.setDeployedImage(depImage);
			execution.setName("test-medium-");
			execution.setHardwareProfile(mediumProfile);
			execution.setDuration(600);
			execution.setStateId(execState);
			
			depImage.getExecutionList().add(execution);
			executionList.add(execution);
		}

		for (int i=0; i<options.numberOfLarge; i++) {
			Execution execution = new Execution();
			execution.setDeployedImage(depImage);
			execution.setName("test-large-");
			execution.setHardwareProfile(largeProfile);
			execution.setDuration(600);
			execution.setStateId(execState);
			
			depImage.getExecutionList().add(execution);
			executionList.add(execution);
		}

		for (int i=0; i<options.numberOfXlarge; i++) {
			Execution execution = new Execution();
			execution.setDeployedImage(depImage);
			execution.setName("test-xlarge-");
			execution.setHardwareProfile(xlargeProfile);
			execution.setDuration(600);
			execution.setStateId(execState);
			
			depImage.getExecutionList().add(execution);
			executionList.add(execution);
		}
		
		// create a list with machine allocation descriptions
		Map<Long, PhysicalMachineAllocationDescription> physicalMachineDescriptions = new HashMap<Long, PhysicalMachineAllocationDescription>();
		
		// Run allocation algorithm
		try {
			allocator.startAllocation(executionList, physicalMachines, physicalMachineDescriptions);
			
		} catch (AllocatorException e) {
			System.err.println("Error: error running the algorithm: " + e.getMessage());
			e.printStackTrace();
		}
		
		// Write the results to standard output
		System.out.println();
		System.out.println("Results:");
		for(Execution execution : executionList) {
			System.out.println("\t"
					+ execution.getExecutionNode().getIp().getIp() 
					+ ","
					+ execution.getHardwareProfile().getId());
		}
		
		// Write the results to file
		if (!options.outputfilename.isEmpty()) {
			try {
				PrintWriter writer = new PrintWriter(options.outputfilename);
				for(Execution execution : executionList) {
					writer.println(execution.getExecutionNode().getIp().getIp() 
							+ ","
							+ execution.getHardwareProfile().getId());
				}
				writer.close();
			} catch (Exception e) {
				System.err.println("Error: cannot create output file " + options.outputfilename +  " : " + e.getMessage());
				e.printStackTrace();
			}
		}
		
 		// Close connection to UnaCloud
		conn.close();
	}	
	
}
