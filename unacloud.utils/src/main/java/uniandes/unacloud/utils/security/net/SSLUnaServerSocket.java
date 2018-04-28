package uniandes.unacloud.utils.security.net;

import java.io.IOException;
import java.net.Socket;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

/**
 * Implementation of SSL server socket using a key store
 * @author CesarF
 *
 */
public class SSLUnaServerSocket extends SSLUnaSocket {
	
	/**
	 * SSL server socket
	 */
	private SSLServerSocket serverSocket;
		
	/**
	 * Creates a server socket setting all attributes requires by SSLUnaSocket
	 * @param port
	 * @param storeType
	 * @param keyStorePath
	 * @param password
	 * @param protocol
	 * @param algorithm
	 * @param trustedStoreType
	 * @param trustedKeyStorePath
	 * @param trustedPassword
	 * @param trustedProtocol
	 * @param trustedAlgorithm
	 * @throws Exception
	 */
	public SSLUnaServerSocket (int port, String storeType, String keyStorePath, String password, String protocol, String algorithm,
			String trustedStoreType, String trustedKeyStorePath, String trustedPassword, String trustedProtocol, String trustedAlgorithm) throws Exception {
		super(port, null, storeType, keyStorePath, password, algorithm, protocol, trustedStoreType, trustedKeyStorePath, trustedPassword, trustedAlgorithm);		
	}
	
	/**
	 * Creates a new server socket with only store for local keys
	 * @param port
	 * @param storeType
	 * @param keyStorePath
	 * @param password
	 * @param protocol
	 * @param algorithm
	 * @throws Exception
	 */
	public SSLUnaServerSocket (int port, String storeType, String keyStorePath, String password, String protocol, String algorithm) throws Exception {
		super(port, null, storeType, keyStorePath, password, algorithm, protocol, null, null, null, null);		
	}
	
	/**
	 * Creates a new server socket with only store for local keys using the default protocol and default algorithm
	 * @param port
	 * @param storeType
	 * @param keyStorePath
	 * @param password
	 * @throws Exception
	 */
	public SSLUnaServerSocket(int port, String storeType, String keyStorePath, String password) throws Exception {
		this(port, storeType, keyStorePath, password, DEFAULT_PROTOCOL, KeyManagerFactory.getDefaultAlgorithm());
	}

	@Override
	protected void initializeSocket(SSLContext context) throws Exception {
		SSLServerSocketFactory ssf = context.getServerSocketFactory();
	    serverSocket = (SSLServerSocket) ssf.createServerSocket(port);		
	}
	
	/**
	 * Creates a SSLUnaServerClientSocket when server socket accept a new client
	 * @return SSLUnaServerClientSocket object
	 * @throws Exception in case communication failed
	 */
	public SSLUnaServerClientSocket acceptClient() throws Exception {
		Socket client = serverSocket.accept();
		SSLUnaServerClientSocket clientServer = new SSLUnaServerClientSocket(client);
		return clientServer;
	}
	
	/**
	 * Closes server socket
	 * @throws IOException
	 */
	public void close() throws IOException {
		serverSocket.close();
	}

}
