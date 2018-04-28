package uniandes.unacloud.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.List;
import java.util.Observer;

/**
 * Class responsible to manages file processes: copy, delete and zip
 * @author CesarF
 *
 */
public class FileProcessor {
	
	/**
	 * Zip file concurrently using a thread
	 * @param fileName name of file to be zipped
	 * @param observer to return result when process finish
	 */
	public static void zipFileAsync(final String fileName, final Observer observer) {			
		new Thread() {
			@Override
			public void run() {
				try {
					File zip = zipFileSync(fileName);
					observer.update(null, zip);
				} catch (Exception e) {
					e.printStackTrace();
					observer.update(null, null);
				}				
			};
		}.start();		
	}
	
	/**
	 * Zip file synchronously, store file in same parent folder and return new zip file 
	 * @param fileName name of file
	 * @return zip file
	 * @throws Exception in case zipping error
	 */
	public static File zipFileSync(String fileName) throws Exception {
		File file = new File(fileName);
		File zipParent = null;
		if(file.isDirectory())
			zipParent = file;
		else 
			zipParent = file.getParentFile();
		File zip = new File(fileName + ".zip");
		Zipper.zipIt(zip, zipParent);
		return zip;
	}
	
	
	/**
	 * Zip files synchronously, return new zip file 
	 * @param fileName name of file
	 * @param list files
	 * @return zip file
	 * @throws Exception in case zipping error
	 */
	public static File zipFilesSync(String zipName, List<File> files) throws Exception {
		
		File zip = new File(zipName.endsWith(".zip")? zipName: zipName+ ".zip");
		Zipper.zipThem(zip, files);
		return zip;
	}
	
	/**
	 * Deletes file concurrently sing a thread
	 * @param fileName name of file o directory to be deleted
	 * @param observer to return result when process finish
	 */
	public static void deleteFileAsync(final String fileName, final Observer observer) {			
		new Thread() {
			@Override
			public void run() {
				try {
					Boolean aws = deleteFileSync(fileName);
					observer.update(null, aws);
				} catch (Exception e) {
					e.printStackTrace();
					observer.update(null, false);
				}				
			};
		}.start();		
	}
	
	/**
	 * Deletes file synchronously and return process result
	 * @param pathFile  name of file o directory to be deleted
	 * @return true in case file has been deleted, false in otherwise
	 * @throws Exception 
	 */
	public static boolean deleteFileSync(String pathFile) throws Exception {
		File file = new File(pathFile);
		if (file.exists()) {
			if (file.isDirectory()) {
				for (File f: file.listFiles())
					deleteFileSync(f.getAbsolutePath());
				System.out.println("Deletes folder: " + file.getAbsolutePath() + " " + file.delete());
			}
			else 
				System.out.println("Deletes file: " + file.getAbsolutePath() + " " + file.delete());
			return true;
		}
		return false;
	}
	
	/**
	 * Deletes all files in a folder, except those which matches with some exception
	 * @param pathFolder
	 * @param exception use regular expression
	 * @throws Exception
	 */
	public static void deleteFilesFolder(String pathFolder, final String exception ) throws Exception {
				
		FilenameFilter filter = null;
		
		if (exception != null && !exception.isEmpty()) 
			filter= new FilenameFilter() {			
				@Override
				public boolean accept(File dir, String name) {				
					return !name.matches(exception);
				}
			};	
			
		File file = new File(pathFolder);
		
		if (!file.exists()) 
			throw new IllegalArgumentException("Original File does not exist");
		
		if (!file.isDirectory()) 
			throw new IllegalArgumentException("Original File does not exist");
		
		for (File file2 : file.listFiles(filter)) 
			deleteFileSync(file2.getAbsolutePath());
	}
	
	/**
	 * Deletes all files in a folder, except those which matches with some exception
	 * @param pathFolder
	 * @param exception use regular expression
	 * @throws Exception
	 */
	public static void deleteFilesFolderAsync(final String pathFolder, final String exception, final Observer observer) throws Exception {
		new Thread() {
			@Override
			public void run() {
				try {
					deleteFilesFolder(pathFolder, exception);
					observer.update(null, true);
				} catch (Exception e) {
					e.printStackTrace();
					observer.update(null, false);
				}				
			};
		}.start();	
	}

	/**
	 * Creates a new file based in original one
	 * @param pathOriginal where is stored original file
	 * @param pathCopy where will be stored copy file
	 * @return new file
	 * @throws Exception
	 */
	public static File copyFileSync(String pathOriginal, String pathCopy) throws Exception {
		final byte[] buffer = new byte[1024 * 100];
		File original = new File(pathOriginal);
		File copy = new File(pathCopy);
		if (!original.exists()) 
			throw new IllegalArgumentException("Original File does not exist");
		try (FileInputStream streamTemp = new FileInputStream(original); FileOutputStream ouFile = new FileOutputStream(copy)) {
			for (int n; (n = streamTemp.read(buffer)) != -1;)
				ouFile.write(buffer, 0, n);																						
		}	
		return copy;
	}
}
