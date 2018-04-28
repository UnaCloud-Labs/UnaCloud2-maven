package uniandes.unacloud.utils.security.net;

import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * Abstract class which represents a socket context. 
 * This class is used to create SSL server sockets and SSL sockets
 * @author CesarF
 *
 */
public abstract class SSLUnaSocket {
	
	/**
	 * Default communication protocol
	 */
	protected final static String DEFAULT_PROTOCOL = "TLS";
	
	/**
	 * Port to create server socket o socket
	 */
	protected int port;
	
	/**
	 * IPaddress to create socket
	 */
	protected String ipAddress;
	
	/**
	 * Configures an SSLcontext create a SSL server socket o SSL socket
	 * @param port to create socket o server socket
	 * @param ip to create socket
	 * @param storeType type of store where is located local certificates, e.g JKS
	 * @param keyStorePath path where is located key store
	 * @param password key store password
	 * @param protocol of communication: e.g TLS
	 * @param algorithm to encrypt
	 * @param trustedStoreType type of store where is located trusted certificates from other nodes, e.g JKS
	 * @param trustedKeyStorePath path where is located trusted key store for external certificates
	 * @param trustedPassword trusted key store password
	 * @param trustedAlgorithm to encrypt
	 * @throws Exception in case paths are null
	 */
	public SSLUnaSocket(int port, String ip, String storeType, String keyStorePath, String password, String algorithm, String protocol,
			String trustedStoreType, String trustedKeyStorePath, String trustedPassword, String trustedAlgorithm) throws Exception {
		
		if (keyStorePath == null && trustedKeyStorePath == null) 
			throw new Exception("Key store or trusted key stored must not be null");
		
		this.port = port;
		this.ipAddress = ip;
				
		KeyManager[] keyManagers = null;
		if (keyStorePath != null) {
			 KeyStore keyStore = KeyStore.getInstance(storeType);
		     keyStore.load(new FileInputStream(keyStorePath),
		            password.toCharArray());
	
		     KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);
		     kmf.init(keyStore, password.toCharArray());
		     keyManagers = kmf.getKeyManagers();
		 }
	     
		 TrustManager[] trustManagers = null;
		 if (trustedKeyStorePath != null) {
			 KeyStore trustedStore = KeyStore.getInstance(trustedStoreType);
		     trustedStore.load(new FileInputStream(trustedKeyStorePath), 
		    		 trustedPassword.toCharArray());

		     TrustManagerFactory tmf = TrustManagerFactory.getInstance(trustedAlgorithm);
		     tmf.init(trustedStore);		     
		     trustManagers = tmf.getTrustManagers();
		 }	    
		 SSLContext sc = SSLContext.getInstance(protocol);
	     sc.init(keyManagers, trustManagers, null);

	     initializeSocket(sc);
	}
	
	/**
	 * Allows to create a socket base in a SSL configured context
	 * @param context to create SSL socket
	 * @throws Exception in case socket can not be created
	 */
	protected abstract void initializeSocket(SSLContext context) throws Exception;

}
