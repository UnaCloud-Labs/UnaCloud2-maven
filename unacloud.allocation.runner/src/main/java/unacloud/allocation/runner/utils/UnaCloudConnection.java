package unacloud.allocation.runner.utils;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import unacloud.allocation.runner.Allocate;
import uniandes.unacloud.web.domain.Laboratory;
import uniandes.unacloud.web.domain.PhysicalMachine;

public class UnaCloudConnection {

	private UnaCloudConnection() {
	}

	private static UnaCloudConnection instance;

	public static UnaCloudConnection getInstance() {
		if (instance == null)
			instance = new UnaCloudConnection();
		return instance;
	}

	private static final String PERSISTENCE_UNIT_NAME = "unacloud";
	
	private EntityManagerFactory factory;
	private EntityManager em = null;

	// Methods

	public void connectToUnaCloud() {

		Properties persistenceProperties = new Properties();

		try {
			// get properties from classloader
			InputStream is = Allocate.class.getResourceAsStream("unacloud.properties");
			if (is == null) {
				System.err.println("unacloud.properties not found on classpath");
			} else {
				persistenceProperties.load(is);
			}

		} catch (IOException e) {
			// e.printStackTrace();
		}

		try {
			// get properties from local folder
			FileReader reader = new FileReader("unacloud.properties");
			persistenceProperties.load(reader);
		} catch (IOException e) {
			System.err.println("unacloud.properties not found on local folder");
			// e.printStackTrace();
		}

		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, persistenceProperties);
		em = factory.createEntityManager();
	}

	public void close() {
		if (em != null && em.isOpen()) 
			em.close();
	}
	
	public List<PhysicalMachine> findMachinesByLabs(List<String> labNames) {

		if (em == null)
			connectToUnaCloud();
			
		// List of machines
		List<PhysicalMachine> machines = new ArrayList<PhysicalMachine>();

		for (String labName : labNames) {

			// Get the laboratories
			TypedQuery<Laboratory> labsQuery = em.createNamedQuery("Laboratory.findByName", Laboratory.class);
			labsQuery.setParameter("name", labName);
			List<Laboratory> labs = labsQuery.getResultList();

			for (Laboratory lab : labs) {
				System.err.println("Querying machines from " + lab.getName());
				machines.addAll(lab.getPhysicalMachineList());
			}

		}
		return machines;

	}
	
	
}
