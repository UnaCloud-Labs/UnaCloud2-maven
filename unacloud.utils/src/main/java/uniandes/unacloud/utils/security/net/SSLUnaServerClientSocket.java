package uniandes.unacloud.utils.security.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.Socket;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import uniandes.unacloud.utils.security.HashGenerator;
import uniandes.unacloud.utils.securty.exceptions.NetException;
import static uniandes.unacloud.utils.security.net.SSLProtocolKeys.*;

/**
 * Represents socket creates by ssl server socket to communicate with client
 * @author CesarF
 *
 */
public class SSLUnaServerClientSocket {

	/**
	 * Communication socket
	 */
	private Socket comSocket;
	
	/**
	 * Stream to write messages in socket
	 */
	private DataOutputStream output;
	
	/**
	 * Stream to read messages from socket
	 */
	private DataInputStream input;
		
	/**
	 * Creates socket to communicate with a client
	 * @param socket for communication
	 * @throws Exception
	 */
	public SSLUnaServerClientSocket(Socket socket) throws Exception {
		this.comSocket = socket;
		output = new DataOutputStream(comSocket.getOutputStream());
		input = new DataInputStream(comSocket.getInputStream());
	}
	
	/**
	 * Writes file in socket
	 * This method creates a no SSL socket to send file. It validates if sender and file are valid 
	 * @param file to be send
	 * @param port where will be open the new socket to send file
	 * @throws Exception in case sending process failed
	 */
	public void writeFile(File file, int port) throws Exception {
		
		if (readInt() == READY_FOR_RECEIVE) {
			writeInt(READY_FOR_SEND);
			String key = HashGenerator.randomString(30);
			write(key);			
			System.out.println("Start communication with " + comSocket.getInetAddress() + " - " + port);
			try (Socket fileSocket = new Socket(comSocket.getInetAddress(), port); 
					DataOutputStream dOs = new DataOutputStream(fileSocket.getOutputStream());) {							
				dOs.writeUTF(key);
				writeDouble(file.length());
				if (readInt() != ENOUGH_SPACE) 
					throw new NetException("Client responses there is not enough space for file");	
				else 
					System.out.println("There is enough space for file in client");
				String checksum = HashGenerator.generateChecksumMD5(file);
				write(checksum);										
				ZipOutputStream zos = new ZipOutputStream(dOs);
				{
					System.out.println("\tSending file: " + file);
					final byte[] buffer = new byte[1024 * 100];				
					zos.putNextEntry(new ZipEntry(file.getName()));							
					try (FileInputStream fis = new FileInputStream(file)) {
						for (int n; (n = fis.read(buffer)) != -1;)
							zos.write(buffer, 0, n);
					}							
					System.out.println("File sent");	
				}						
				zos.flush();	
				zos.closeEntry();
				if (readInt() == ERROR_FILE) 
					throw new NetException("Client responses file is not valid");				
				
			} catch (Exception e) {
				//it is necessary because we need to close filesocket 
				throw e;
			}
		}		
	}
	
	/**
	 * Read file from socket
	 * This method creates a not SSL socket to send file. It validates if sender and file are valid 
	 * @return File received
	 * @throws Exception in case receiving process failed
	 */
	public File readFile(int port, String path) throws Exception {
		File file = null;
		if (readInt() == READY_FOR_SEND) {
			writeInt(READY_FOR_RECEIVE);
			String key = HashGenerator.randomString(30);
			write(key);			
			System.out.println("Start communication with " + comSocket.getInetAddress() + " - " + port);
			try (Socket fileSocket = new Socket(comSocket.getInetAddress(), port); 
					DataOutputStream dOs = new DataOutputStream(fileSocket.getOutputStream());
					DataInputStream dsS = new DataInputStream(fileSocket.getInputStream());) {							
				dOs.writeUTF(key);
				long space = (long) readDouble();
				if (space >= new File(path).getFreeSpace()) {
					System.out.println("Space: " + space + " - " + new File(path).getFreeSpace() + " - " + (new File(path).getFreeSpace() - space) );
					writeInt(ERROR_FILE);
					throw new NetException("Not enough space for file");
				}		
				else {
					System.out.println("There is enough space for file");
					writeInt(ENOUGH_SPACE);
				}
				String checksum = read();					
				ZipInputStream zis = new ZipInputStream(dsS);
				System.out.println("\tStart downloading file");
				final byte[] buffer = new byte[1024 * 100];	
				ZipEntry entry = zis.getNextEntry();
				file = new File(path + entry.getName());
				System.out.println("\tReceiving file: " + file);
				try (FileOutputStream fos = new FileOutputStream(file)) {
					for (int n; (n = zis.read(buffer)) != -1;)
						fos.write(buffer, 0, n);																			
				}	
				System.out.println("Finish");
				zis.closeEntry();
				zis.close();
				String checksumFile = HashGenerator.generateChecksumMD5(file);
				if (!checksum.equals(checksumFile)) {
					file.delete();
					writeInt(ERROR_FILE);
					throw new NetException("Checksum is not valid");
				}
				else 
					writeInt(RECEIVED);	
			} catch (Exception e) {
				//it is necessary because we need to close filesocket 
				throw e;
			}
		}	
		return file;
	}
	
	/**
	 * Writes a string message in socket
	 * @param message to be written
	 * @throws Exception in case sending process failed
	 */
	public void write(String message) throws Exception {	
		 output.writeUTF(message);
         output.flush();
	}
	
	/**
	 * Writes an int message in socket
	 * @param message to be written
	 * @throws Exception in case sending process failed
	 */
	public void writeInt(int message) throws Exception {		
		output.writeInt(message);
        output.flush();
	}
	
	/**
	 * Writes a double message in socket
	 * @param message to be written
	 * @throws Exception in case sending process failed
	 */
	public void writeDouble(double message) throws Exception {
		output.writeDouble(message);
		output.flush();
	}
	
	/**
	 * Read String message from socket
	 * @return string message
	 * @throws Exception in case receiving process failed
	 */
	public String read() throws Exception {
		 return input.readUTF();
	}
	
	/**
	 * Read int message from socket
	 * @return int message
	 * @throws Exception in case receiving process failed
	 */
	public int readInt() throws Exception {
		 return input.readInt();
	}
	
	/**
	 * Read double message from socket
	 * @return double message
	 * @throws Exception in case receiving process failed
	 */
	public double readDouble() throws Exception {
		 return input.readDouble();
	}
	
	/**
	 * Close streams and ssl socket
	 * @throws Exception in case socket close process failed
	 */
	public void close() throws Exception {
		input.close();
		output.close();
		comSocket.close();
	}

}
