/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uniandes.unacloud.web.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jaime Chavarriaga
 */
@Entity
@Table(name = "laboratory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Laboratory.findAll", query = "SELECT l FROM Laboratory l")
    , @NamedQuery(name = "Laboratory.findById", query = "SELECT l FROM Laboratory l WHERE l.id = :id")
    , @NamedQuery(name = "Laboratory.findByVersion", query = "SELECT l FROM Laboratory l WHERE l.version = :version")
    , @NamedQuery(name = "Laboratory.findByEnable", query = "SELECT l FROM Laboratory l WHERE l.enable = :enable")
    , @NamedQuery(name = "Laboratory.findByHighAvailability", query = "SELECT l FROM Laboratory l WHERE l.highAvailability = :highAvailability")
    , @NamedQuery(name = "Laboratory.findByName", query = "SELECT l FROM Laboratory l WHERE l.name = :name")
    , @NamedQuery(name = "Laboratory.findByNetworkQuality", query = "SELECT l FROM Laboratory l WHERE l.networkQuality = :networkQuality")})
public class Laboratory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "version")
    private long version;
    @Basic(optional = false)
    @Column(name = "enable")
    private boolean enable;
    @Basic(optional = false)
    @Column(name = "high_availability")
    private boolean highAvailability;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "network_quality")
    private String networkQuality;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "laboratoryId", fetch = FetchType.LAZY)
    private List<IPPool> ippoolList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "laboratoryId", fetch = FetchType.LAZY)
    private List<PhysicalMachine> physicalMachineList;

    public Laboratory() {
    }

    public Laboratory(Long id) {
        this.id = id;
    }

    public Laboratory(Long id, long version, boolean enable, boolean highAvailability, String name, String networkQuality) {
        this.id = id;
        this.version = version;
        this.enable = enable;
        this.highAvailability = highAvailability;
        this.name = name;
        this.networkQuality = networkQuality;
    }

    public Long getId() {
        return id;
    }
    public Long getDatabaseId() {
    	return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean getHighAvailability() {
        return highAvailability;
    }

    public void setHighAvailability(boolean highAvailability) {
        this.highAvailability = highAvailability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNetworkQuality() {
        return networkQuality;
    }

    public void setNetworkQuality(String networkQuality) {
        this.networkQuality = networkQuality;
    }

    @XmlTransient
    public List<IPPool> getIppoolList() {
        return ippoolList;
    }

    public void setIppoolList(List<IPPool> ippoolList) {
        this.ippoolList = ippoolList;
    }

    @XmlTransient
    public List<PhysicalMachine> getPhysicalMachineList() {
        return physicalMachineList;
    }

    public void setPhysicalMachineList(List<PhysicalMachine> physicalMachineList) {
        this.physicalMachineList = physicalMachineList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Laboratory)) {
            return false;
        }
        Laboratory other = (Laboratory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uniandes.unacloud.database.Laboratory[ id=" + id + " ]";
    }
 
    // Methods
    
	/**
	 * Returns the list of available execution IP in laboratory to be assigned
	 * @return list of Execution IP
	 */
	public List<IP> getAvailableIps() {
		List <IP> ips = new ArrayList<>();
		for (IPPool pool : getIppoolList()) {
			for (IP ip : pool.getIpList()) {
				if (ip.getState() == "AVAILABLE" /* IPEnum.AVAILABLE */ ) {
					ips.add(ip);
				}
			}
		}
		return ips;
	}    
    
    
}
