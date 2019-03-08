/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uniandes.unacloud.web.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jaime Chavarriaga
 */
@Entity
@Table(name = "physical_machine")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PhysicalMachine.findAll", query = "SELECT p FROM PhysicalMachine p")
    , @NamedQuery(name = "PhysicalMachine.findById", query = "SELECT p FROM PhysicalMachine p WHERE p.id = :id")
    , @NamedQuery(name = "PhysicalMachine.findByVersion", query = "SELECT p FROM PhysicalMachine p WHERE p.version = :version")
    , @NamedQuery(name = "PhysicalMachine.findByAgentVersion", query = "SELECT p FROM PhysicalMachine p WHERE p.agentVersion = :agentVersion")
    , @NamedQuery(name = "PhysicalMachine.findByCores", query = "SELECT p FROM PhysicalMachine p WHERE p.cores = :cores")
    , @NamedQuery(name = "PhysicalMachine.findByDataSpace", query = "SELECT p FROM PhysicalMachine p WHERE p.dataSpace = :dataSpace")
    , @NamedQuery(name = "PhysicalMachine.findByFreeSpace", query = "SELECT p FROM PhysicalMachine p WHERE p.freeSpace = :freeSpace")
    , @NamedQuery(name = "PhysicalMachine.findByHighAvailability", query = "SELECT p FROM PhysicalMachine p WHERE p.highAvailability = :highAvailability")
    , @NamedQuery(name = "PhysicalMachine.findByLastReport", query = "SELECT p FROM PhysicalMachine p WHERE p.lastReport = :lastReport")
    , @NamedQuery(name = "PhysicalMachine.findByMac", query = "SELECT p FROM PhysicalMachine p WHERE p.mac = :mac")
    , @NamedQuery(name = "PhysicalMachine.findByName", query = "SELECT p FROM PhysicalMachine p WHERE p.name = :name")
    , @NamedQuery(name = "PhysicalMachine.findByPCores", query = "SELECT p FROM PhysicalMachine p WHERE p.pCores = :pCores")
    , @NamedQuery(name = "PhysicalMachine.findByRam", query = "SELECT p FROM PhysicalMachine p WHERE p.ram = :ram")
    , @NamedQuery(name = "PhysicalMachine.findByState", query = "SELECT p FROM PhysicalMachine p WHERE p.state = :state")
    , @NamedQuery(name = "PhysicalMachine.findByWithUser", query = "SELECT p FROM PhysicalMachine p WHERE p.withUser = :withUser")
    , @NamedQuery(name = "PhysicalMachine.findByLastLog", query = "SELECT p FROM PhysicalMachine p WHERE p.lastLog = :lastLog")
    , @NamedQuery(name = "PhysicalMachine.findByLastMonitoring", query = "SELECT p FROM PhysicalMachine p WHERE p.lastMonitoring = :lastMonitoring")})
public class PhysicalMachine implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "version")
    private long version;
    @Column(name = "agent_version")
    private String agentVersion;
    @Basic(optional = false)
    @Column(name = "cores")
    private int cores;
    @Basic(optional = false)
    @Column(name = "data_space")
    private long dataSpace;
    @Basic(optional = false)
    @Column(name = "free_space")
    private long freeSpace;
    @Basic(optional = false)
    @Column(name = "high_availability")
    private boolean highAvailability;
    @Column(name = "last_report")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastReport;
    @Basic(optional = false)
    @Column(name = "mac")
    private String mac;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "p_cores")
    private int pCores;
    @Basic(optional = false)
    @Column(name = "ram")
    private int ram;
    @Basic(optional = false)
    @Column(name = "state")
    private String state;
    @Basic(optional = false)
    @Column(name = "with_user")
    private boolean withUser;
    @Basic(optional = false)
    @Column(name = "last_log")
    private String lastLog;
    @Column(name = "last_monitoring")
    private String lastMonitoring;
    @ManyToMany(mappedBy = "physicalMachineList", fetch = FetchType.LAZY)
    private List<Platform> platformList;
    @OneToMany(mappedBy = "executionNodeId", fetch = FetchType.LAZY)
    private List<Execution> executionList;
    @JoinColumn(name = "ip_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private IP ip;
    @JoinColumn(name = "operating_system_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private OperatingSystem operatingSystem;
    @JoinColumn(name = "laboratory_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Laboratory laboratory;

    public PhysicalMachine() {
    }

    public PhysicalMachine(Long id) {
        this.id = id;
    }

    public PhysicalMachine(Long id, long version, int cores, long dataSpace, long freeSpace, boolean highAvailability, String mac, String name, int pCores, int ram, String state, boolean withUser, String lastLog) {
        this.id = id;
        this.version = version;
        this.cores = cores;
        this.dataSpace = dataSpace;
        this.freeSpace = freeSpace;
        this.highAvailability = highAvailability;
        this.mac = mac;
        this.name = name;
        this.pCores = pCores;
        this.ram = ram;
        this.state = state;
        this.withUser = withUser;
        this.lastLog = lastLog;
    }

    public Long getId() {
        return id;
    }
    public Long getDatabaseId() {		// database id
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

    public String getAgentVersion() {
        return agentVersion;
    }

    public void setAgentVersion(String agentVersion) {
        this.agentVersion = agentVersion;
    }

    public int getCores() {
        return cores;
    }

    public void setCores(int cores) {
        this.cores = cores;
    }

    public long getDataSpace() {
        return dataSpace;
    }

    public void setDataSpace(long dataSpace) {
        this.dataSpace = dataSpace;
    }

    public long getFreeSpace() {
        return freeSpace;
    }

    public void setFreeSpace(long freeSpace) {
        this.freeSpace = freeSpace;
    }

    public boolean getHighAvailability() {
        return highAvailability;
    }

    public void setHighAvailability(boolean highAvailability) {
        this.highAvailability = highAvailability;
    }

    public Date getLastReport() {
        return lastReport;
    }

    public void setLastReport(Date lastReport) {
        this.lastReport = lastReport;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPCores() {
        return pCores;
    }
    // TODO: name ?
    public int getpCores() {
        return pCores;
    }

    public void setPCores(int pCores) {
        this.pCores = pCores;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean getWithUser() {
        return withUser;
    }

    public void setWithUser(boolean withUser) {
        this.withUser = withUser;
    }

    public String getLastLog() {
        return lastLog;
    }

    public void setLastLog(String lastLog) {
        this.lastLog = lastLog;
    }

    public String getLastMonitoring() {
        return lastMonitoring;
    }

    public void setLastMonitoring(String lastMonitoring) {
        this.lastMonitoring = lastMonitoring;
    }

    @XmlTransient
    public List<Platform> getPlatformList() {
        return platformList;
    }
    // TODO: name ?
    public List<Platform> getAllPlatforms() {
        return platformList;
    }

    public void setPlatformList(List<Platform> platformList) {
        this.platformList = platformList;
    }

    @XmlTransient
    public List<Execution> getExecutionList() {
        return executionList;
    }

    public void setExecutionList(List<Execution> executionList) {
        this.executionList = executionList;
    }

    public IP getIp() {
        return ip;
    }

    public void setIp(IP ipId) {
        this.ip = ipId;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public Laboratory getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(Laboratory laboratory) {
        this.laboratory = laboratory;
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
        if (!(object instanceof PhysicalMachine)) {
            return false;
        }
        PhysicalMachine other = (PhysicalMachine) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uniandes.unacloud.database.PhysicalMachine[ id=" + id + " ]";
    }
    
}
