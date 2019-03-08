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

/**
 *
 * @author Jaime Chavarriaga
 */
@Entity
@Table(name = "deployment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deployment.findAll", query = "SELECT d FROM Deployment d")
    , @NamedQuery(name = "Deployment.findById", query = "SELECT d FROM Deployment d WHERE d.id = :id")
    , @NamedQuery(name = "Deployment.findByVersion", query = "SELECT d FROM Deployment d WHERE d.version = :version")
    , @NamedQuery(name = "Deployment.findByDuration", query = "SELECT d FROM Deployment d WHERE d.duration = :duration")
    , @NamedQuery(name = "Deployment.findByStartTime", query = "SELECT d FROM Deployment d WHERE d.startTime = :startTime")
    , @NamedQuery(name = "Deployment.findByStatus", query = "SELECT d FROM Deployment d WHERE d.status = :status")})
public class Deployment implements Serializable {

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
    @Column(name = "duration")
    private long duration;
    @Basic(optional = false)
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deploymentId", fetch = FetchType.LAZY)
    private List<DeployedImage> deployedImageList;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;
    @JoinColumn(name = "cluster_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cluster cluster;

    public Deployment() {
    }

    public Deployment(Long id) {
        this.id = id;
    }

    public Deployment(Long id, long version, long duration, Date startTime, String status) {
        this.id = id;
        this.version = version;
        this.duration = duration;
        this.startTime = startTime;
        this.status = status;
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public List<DeployedImage> getDeployedImageList() {
        return deployedImageList;
    }

    public void setDeployedImageList(List<DeployedImage> deployedImageList) {
        this.deployedImageList = deployedImageList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setClusterId(Cluster cluster) {
        this.cluster = cluster;
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
        if (!(object instanceof Deployment)) {
            return false;
        }
        Deployment other = (Deployment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uniandes.unacloud.database.Deployment[ id=" + id + " ]";
    }
    
}
