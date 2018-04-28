package uniandes.unacloud.utils.security.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import uniandes.unacloud.utils.security.HashGenerator;
import uniandes.unacloud.utils.securty.exceptions.NetException;
import static uniandes.unacloud.utils.security.net.SSLProtocolKeys.*;

/**
 * Implementation of SSL socket using a key store
 * @author CesarF
 *
 */
public class SSLUnaClientSocket extends SSLUnaSocket {
	
	/**
	 * SSL socket for communication with server
	 */
	private SSLSocket socket;
	
	/**
	 * Stream to write messages in socket
	 */
	private DataOutputStream output;
	
	/**
	 * Stream to read messages from socket
	 */
	private DataInputStream input;	
		
	/**
	 * Creates a client socket setting all attributes requires by SSLUnaSocket
	 * @param port
	 * @param ipAddress
	 * @param storeType
	 * @param keyStorePath
	 * @param password
	 * @param protocol
	 * @param algorithm
	 * @param trustedStoreType
	 * @param trustedKeyStorePath
	 * @param trustedPassword
	 * @param trustedAlgorithm
	 * @throws Exception
	 */
	public SSLUnaClientSocket(int port, String ipAddress, String storeType, String keyStorePath, String password, String protocol, String algorithm,
			String trustedStoreType, String trustedKeyStorePath, String trustedPassword, String trustedAlgorithm) throws Exception {
		super(port, ipAddress, storeType, keyStorePath, password, algorithm, protocol, trustedStoreType, trustedKeyStorePath, trustedPassword, trustedAlgorithm);	
	}
	
	/**
	 * Creates a new client socket with only trusted certificates store
	 * @param port
	 * @param ipAddress
	 * @param storeType
	 * @param keyStorePath
	 * @param password
	 * @param protocol
	 * @param algorithm
	 * @throws Exception
	 */
	public SSLUnaClientSocket(int port, String ipAddress, String storeType, String keyStorePath, String password, String protocol, String algorithm) throws Exception {
		super(port, ipAddress, null, null, null, null, protocol, storeType, keyStorePath, password, algorithm);		
	}
	
	/**
	 * Creates a new client socket with only trusted certificates store using the default protocol and default algorithm
	 * @param port
	 * @param ipAddress
	 * @param storeType
	 * @param keyStorePath
	 * @param password
	 * @throws Exception
	 */
	public SSLUnaClientSocket(int port, String ipAddress, String storeType, String keyStorePath, String password) throws Exception {
		this(port, ipAddress, storeType, keyStorePath, password, DEFAULT_PROTOCOL, KeyManagerFactory.getDefaultAlgorithm());
	}

	@Override
	protected void initializeSocket(SSLContext context) throws Exception {
		 SSLSocketFactory ssf = context.getSocketFactory();
	     socket = (SSLSocket) ssf.createSocket(ipAddress, port);
		 output = new DataOutputStream(socket.getOutputStream());
		 input = new DataInputStream(socket.getInputStream());
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
	 * Read file from socket
	 * This method creates a not SSL socket to send file. It validates if sender and file are valid 
	 * @return File received
	 * @throws Exception in case receiving process failed
	 */
	public File readFile(int port, String path) throws Exception {
		File file = null;
		Socket s = null;
		try (ServerSocket ss = new ServerSocket(port)) {
			writeInt(READY_FOR_RECEIVE);
			if (readInt() == READY_FOR_SEND) {
				String key = read();
				s = ss.accept();
				DataInputStream dsS = new DataInputStream(s.getInputStream());
				if (key.equals(dsS.readUTF())) {
					System.out.println("Key is valid");
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
				}
				else {
					dsS.close();					
					throw new NetException("Key is not valid");
				}
				s.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (s != null)
				s.close();
			throw e;
		}
		return file;
	}
	
	/**
	 * Writes file in socket
	 * This method creates a no SSL socket to send file. It validates if sender and file are valid 
	 * @param file to be send
	 * @param port where will be open the new socket to send file
	 * @throws Exception in case sending process failed
	 */
	public void writeFile(File file, int port) throws Exception {
		Socket fileSocket = null;
		try (ServerSocket ss = new ServerSocket(port)) {
			writeInt(READY_FOR_SEND);
			if (readInt() == READY_FOR_RECEIVE) {
				String key = read();
				fileSocket = ss.accept();
				DataInputStream dsS = new DataInputStream(fileSocket.getInputStream());
				if (key.equals(dsS.readUTF())) {
					System.out.println("Key is valid");
					writeDouble(file.length());
					if (readInt() != ENOUGH_SPACE) 
						throw new NetException("Server responses there is not enough space for file");	
					else 
						System.out.println("There is enough space for file in client");
					String checksum = HashGenerator.generateChecksumMD5(file);
					write(checksum);		
					DataOutputStream dOs = new DataOutputStream(fileSocket.getOutputStream());			
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
						throw new NetException("Server responses file is not valid");						
				}
				else {
					dsS.close();					
					throw new NetException("Key is not valid");
				}
				fileSocket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (fileSocket != null)
				fileSocket.close();
			throw e;
		}
	}
	
	/**
	 * Close streams and ssl socket
	 * @throws Exception in case socket close process failed
	 */
	public void close() throws Exception {
		input.close();
		output.close();
		socket.close();
	}

}
