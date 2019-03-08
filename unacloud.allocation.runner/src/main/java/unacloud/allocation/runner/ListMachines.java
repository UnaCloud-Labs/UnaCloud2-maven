package unacloud.allocation.runner;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import unacloud.allocation.runner.utils.UnaCloudConnection;
import uniandes.unacloud.web.domain.PhysicalMachine;

public class ListMachines {
	
	private static class ListMachinesOptions {
		
		@Parameter(description = "Comma-separated list of laboratory names")
		private List<String> labs = new ArrayList<>();
	}
	
	public static void main(String[] args) {
		
		ListMachinesOptions options = new ListMachinesOptions();
		JCommander.newBuilder().addObject(options).build().parse(args);
		
		
		UnaCloudConnection conn = UnaCloudConnection.getInstance();
	
		List<PhysicalMachine> machines = conn.findMachinesByLabs(options.labs);

        // Show all the machines
        System.out.println();
		for (PhysicalMachine machine : machines) {
			System.out.println("\t - " + machine.getName());
		}
		System.out.println("Total " + machines.size());

		conn.close();
		
	}	
	
}
