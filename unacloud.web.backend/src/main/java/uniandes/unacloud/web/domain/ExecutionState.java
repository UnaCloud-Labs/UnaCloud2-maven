/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uniandes.unacloud.web.domain;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jaime Chavarriaga
 */
@Entity
@Table(name = "execution_state")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExecutionState.findAll", query = "SELECT e FROM ExecutionState e")
    , @NamedQuery(name = "ExecutionState.findById", query = "SELECT e FROM ExecutionState e WHERE e.id = :id")
    , @NamedQuery(name = "ExecutionState.findByVersion", query = "SELECT e FROM ExecutionState e WHERE e.version = :version")
    , @NamedQuery(name = "ExecutionState.findByControlMessage", query = "SELECT e FROM ExecutionState e WHERE e.controlMessage = :controlMessage")
    , @NamedQuery(name = "ExecutionState.findByControlTime", query = "SELECT e FROM ExecutionState e WHERE e.controlTime = :controlTime")
    , @NamedQuery(name = "ExecutionState.findByState", query = "SELECT e FROM ExecutionState e WHERE e.state = :state")})
public class ExecutionState implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "version")
    private long version;
    @Column(name = "control_message")
    private String controlMessage;
    @Column(name = "control_time")
    private BigInteger controlTime;
    @Basic(optional = false)
    @Column(name = "state")
    private String state;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stateId", fetch = FetchType.LAZY)
    private List<Execution> executionList;
    
    @OneToMany(mappedBy = "nextControlId", fetch = FetchType.LAZY)
    private List<ExecutionState> executionStateList;
    @JoinColumn(name = "next_control_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ExecutionState nextControlId;
    @OneToMany(mappedBy = "nextId", fetch = FetchType.LAZY)
    private List<ExecutionState> executionStateList1;
    @JoinColumn(name = "next_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ExecutionState nextId;
    @OneToMany(mappedBy = "nextRequestedId", fetch = FetchType.LAZY)
    private List<ExecutionState> executionStateList2;
    @JoinColumn(name = "next_requested_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ExecutionState nextRequestedId;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stateId", fetch = FetchType.LAZY)
    private List<ExecutionHistory> executionHistoryList;

    public ExecutionState() {
    }

    public ExecutionState(Long id) {
        this.id = id;
    }

    public ExecutionState(Long id, long version, String state) {
        this.id = id;
        this.version = version;
        this.state = state;
    }

    public Long getId() {
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

    public String getControlMessage() {
        return controlMessage;
    }

    public void setControlMessage(String controlMessage) {
        this.controlMessage = controlMessage;
    }

    public BigInteger getControlTime() {
        return controlTime;
    }

    public void setControlTime(BigInteger controlTime) {
        this.controlTime = controlTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @XmlTransient
    public List<Execution> getExecutionList() {
        return executionList;
    }

    public void setExecutionList(List<Execution> executionList) {
        this.executionList = executionList;
    }

    @XmlTransient
    public List<ExecutionState> getExecutionStateList() {
        return executionStateList;
    }

    public void setExecutionStateList(List<ExecutionState> executionStateList) {
        this.executionStateList = executionStateList;
    }

    public ExecutionState getNextControlId() {
        return nextControlId;
    }

    public void setNextControlId(ExecutionState nextControlId) {
        this.nextControlId = nextControlId;
    }

    @XmlTransient
    public List<ExecutionState> getExecutionStateList1() {
        return executionStateList1;
    }

    public void setExecutionStateList1(List<ExecutionState> executionStateList1) {
        this.executionStateList1 = executionStateList1;
    }

    public ExecutionState getNextId() {
        return nextId;
    }

    public void setNextId(ExecutionState nextId) {
        this.nextId = nextId;
    }

    @XmlTransient
    public List<ExecutionState> getExecutionStateList2() {
        return executionStateList2;
    }

    public void setExecutionStateList2(List<ExecutionState> executionStateList2) {
        this.executionStateList2 = executionStateList2;
    }

    public ExecutionState getNextRequestedId() {
        return nextRequestedId;
    }

    public void setNextRequestedId(ExecutionState nextRequestedId) {
        this.nextRequestedId = nextRequestedId;
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
        if (!(object instanceof ExecutionState)) {
            return false;
        }
        ExecutionState other = (ExecutionState) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uniandes.unacloud.database.ExecutionState[ id=" + id + " ]";
    }
    
}
