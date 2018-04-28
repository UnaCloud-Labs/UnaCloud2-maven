package uniandes.unacloud.utils.examples.zip;

import java.io.File;

import uniandes.unacloud.utils.file.Zipper;

public class Zipping {
	
	public static void main(String[] args) throws Exception {
		File file = new File("C:\\Users\\Cesar\\Desktop\\Spark.zip");
		File folder = new File("C:\\Users\\Cesar\\Desktop\\Spark_admin");
		Zipper.zipIt(file, folder);		
		Zipper.unzipIt(file, "C:\\Users\\Cesar\\Desktop\\Result");
	}

}
