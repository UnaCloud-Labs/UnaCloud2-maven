package uniandes.unacloud.utils.security.net;

/**
 * Contains all constants for SSL protocol
 * @author CesarF
 *
 */
public class SSLProtocolKeys {
	
	/**
	 * If socket is ready to receive file
	 */
	public static final int READY_FOR_RECEIVE = 12;
	
	/**
	 * If socket is ready to send file
	 */
	public static final int READY_FOR_SEND = 13;
	
	/**
	 * If file was received
	 */
	public static final int RECEIVED = 14;
	
	/**
	 * If there is an error in process
	 */
	public static final int ERROR_FILE = 15;
	
	/**
	 * If there is enough space to receive file
	 */
	public static final int ENOUGH_SPACE = 16;

}
