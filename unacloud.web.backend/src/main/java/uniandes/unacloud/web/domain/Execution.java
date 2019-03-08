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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import uniandes.unacloud.share.enums.ExecutionStateEnum;

/**
 *
 * @author Jaime Chavarriaga
 */
@Entity
@Table(name = "execution")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Execution.findAll", query = "SELECT e FROM Execution e")
    , @NamedQuery(name = "Execution.findById", query = "SELECT e FROM Execution e WHERE e.id = :id")
    , @NamedQuery(name = "Execution.findByVersion", query = "SELECT e FROM Execution e WHERE e.version = :version")
    , @NamedQuery(name = "Execution.findByCopyTo", query = "SELECT e FROM Execution e WHERE e.copyTo = :copyTo")
    , @NamedQuery(name = "Execution.findByDuration", query = "SELECT e FROM Execution e WHERE e.duration = :duration")
    , @NamedQuery(name = "Execution.findByLastReport", query = "SELECT e FROM Execution e WHERE e.lastReport = :lastReport")
    , @NamedQuery(name = "Execution.findByMessage", query = "SELECT e FROM Execution e WHERE e.message = :message")
    , @NamedQuery(name = "Execution.findByName", query = "SELECT e FROM Execution e WHERE e.name = :name")
    , @NamedQuery(name = "Execution.findByStartTime", query = "SELECT e FROM Execution e WHERE e.startTime = :startTime")
    , @NamedQuery(name = "Execution.findByStopTime", query = "SELECT e FROM Execution e WHERE e.stopTime = :stopTime")})
public class Execution implements Serializable {

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
    @Column(name = "copy_to")
    private long copyTo;
    @Basic(optional = false)
    @Column(name = "duration")
    private long duration;
    @Basic(optional = false)
    @Column(name = "last_report")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastReport;
    @Basic(optional = false)
    @Column(name = "message")
    private String message;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "stop_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date stopTime;
    @JoinColumn(name = "execution_node_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PhysicalMachine executionNode;
    @JoinColumn(name = "deploy_image_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DeployedImage deployedImage;
    @JoinColumn(name = "state_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ExecutionState state;
    @JoinColumn(name = "hardware_profile_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private HardwareProfile hardwareProfile;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "execution", fetch = FetchType.LAZY)
    private List<NetInterface> netInterfaceList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "execution", fetch = FetchType.LAZY)
    private List<ExecutionHistory> executionHistoryList;

    public Execution() {
    }

    public Execution(Long id) {
        this.id = id;
    }

    public Execution(Long id, long version, long copyTo, long duration, Date lastReport, String message, String name) {
        this.id = id;
        this.version = version;
        this.copyTo = copyTo;
        this.duration = duration;
        this.lastReport = lastReport;
        this.message = message;
        this.name = name;
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

    public long getCopyTo() {
        return copyTo;
    }

    public void setCopyTo(long copyTo) {
        this.copyTo = copyTo;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Date getLastReport() {
        return lastReport;
    }

    public void setLastReport(Date lastReport) {
        this.lastReport = lastReport;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public PhysicalMachine getExecutionNode() {
        return executionNode;
    }

    public void setExecutionNode(PhysicalMachine executionNode) {
        this.executionNode = executionNode;
    }

    public DeployedImage getDeployedImage() {
        return deployedImage;
    }

    public void setDeployedImage(DeployedImage deployedImage) {
        this.deployedImage = deployedImage;
    }

    public ExecutionState getState() {
        return state;
    }

    public void setStateId(ExecutionState state) {
        this.state = state;
    }

    public HardwareProfile getHardwareProfile() {
        return hardwareProfile;
    }

    public void setHardwareProfile(HardwareProfile hardwareProfile) {
        this.hardwareProfile = hardwareProfile;
    }

    @XmlTransient
    public List<NetInterface> getNetInterfaceList() {
        return netInterfaceList;
    }

    public void setNetInterfaceList(List<NetInterface> netInterfaceList) {
        this.netInterfaceList = netInterfaceList;
    }

    @XmlTransient
    public List<ExecutionHistory> getExecutionHistoryList() {
        return executionHistoryList;
    }

    public void setExecutionHistoryList(List<ExecutionHistory> executionHistoryList) {
        this.executionHistoryList = executionHistoryList;
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
        if (!(object instanceof Execution)) {
            return false;
        }
        Execution other = (Execution) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uniandes.unacloud.database.Execution[ id=" + id + " ]";
    }
    
    
	//-----------------------------------------------------------------
	// Methods
	//-----------------------------------------------------------------

    public String remainingTime() {
		if (stopTime == null || this.getState().getState() != "DEPLOYED" /*ExecutionStateEnum.DEPLOYED*/) 
			return "--";
		long millisTime = (stopTime.getTime() - System.currentTimeMillis()) / 1000L;
		String s = "" + millisTime % 60;
        String m = "" + ((long)(millisTime / 60)) % 60;
        String h = "" + ((long)(millisTime / 60 / 60));
        if (s.length() == 1) s = "0" + s;
        if (m.length() == 1) m = "0" + m;
        return h + "h:" + m + "m:" + s + "s";    	
    }

    public  IP mainIp() {
    	return this.getNetInterfaceList().get(0).getIp();
    }
    
}
