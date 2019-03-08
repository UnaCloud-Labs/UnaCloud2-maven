/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uniandes.unacloud.web.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jaime Chavarriaga
 */
@Entity
@Table(name = "execution_history")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExecutionHistory.findAll", query = "SELECT e FROM ExecutionHistory e")
    , @NamedQuery(name = "ExecutionHistory.findById", query = "SELECT e FROM ExecutionHistory e WHERE e.id = :id")
    , @NamedQuery(name = "ExecutionHistory.findByVersion", query = "SELECT e FROM ExecutionHistory e WHERE e.version = :version")
    , @NamedQuery(name = "ExecutionHistory.findByChangeTime", query = "SELECT e FROM ExecutionHistory e WHERE e.changeTime = :changeTime")
    , @NamedQuery(name = "ExecutionHistory.findByMessage", query = "SELECT e FROM ExecutionHistory e WHERE e.message = :message")})
public class ExecutionHistory implements Serializable {

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
    @Column(name = "change_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date changeTime;
    @Column(name = "message")
    private String message;
    @JoinColumn(name = "execution_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Execution execution;
    @JoinColumn(name = "state_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ExecutionState state;

    public ExecutionHistory() {
    }

    public ExecutionHistory(Long id) {
        this.id = id;
    }

    public ExecutionHistory(Long id, long version, Date changeTime) {
        this.id = id;
        this.version = version;
        this.changeTime = changeTime;
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

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Execution getExecution() {
        return execution;
    }

    public void setExecution(Execution execution) {
        this.execution = execution;
    }

    public ExecutionState getState() {
        return state;
    }

    public void setState(ExecutionState state) {
        this.state = state;
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
        if (!(object instanceof ExecutionHistory)) {
            return false;
        }
        ExecutionHistory other = (ExecutionHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uniandes.unacloud.database.ExecutionHistory[ id=" + id + " ]";
    }
    
}
