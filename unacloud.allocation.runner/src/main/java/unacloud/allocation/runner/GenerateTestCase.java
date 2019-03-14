package unacloud.allocation.runner;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

public class GenerateTestCase {

	private static class GenerateTestCaseOptions {

		@Parameter(names= {"-c", "--cases"}, 
				description = "Number of cases to generate")
		private int cases = 1;
		
		@Parameter(names= {"-n", "--number"}, 
				description = "Number of machines to generate",
				required = true)
		private int numMachines;
		
		@Parameter(names= {"-p", "--profiles"}, 
				description = "Number of profiles to consider")
		private int numProfiles = 4;
	}
	
	public static void main(String[] args) {

		// process arguments
		GenerateTestCaseOptions options = new GenerateTestCaseOptions();
		try {
			JCommander.newBuilder().addObject(options).build().parse(args);			
		} catch (ParameterException e) {
			e.usage();
			System.err.println("Error: wrong parameters: " +e.getMessage());
			System.exit(-1);
		}
		
		// for each test case
		for (int caseNumber=0; caseNumber<options.cases; caseNumber++) {
			
			// create the array
			int numbers[] = new int[options.numProfiles];
			
			// generate the numbers
			for (int i=0; i<options.numMachines; i++) {
				
				// determine what profile use for machine number i
				int profile = (int) ((options.numProfiles) * Math.random());
				
				// increase the number
				numbers[profile]++;
				
			}
			
			// show the results
			int total = 0;
			for (int i=0; i<options.numProfiles; i++) {
				System.out.print(numbers[i]);
				System.out.print(",");
				
				total += numbers[i];
			}
			System.out.println(total);
		}
	}
}
