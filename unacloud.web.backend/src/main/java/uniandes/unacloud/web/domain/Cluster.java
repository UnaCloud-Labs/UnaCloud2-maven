/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uniandes.unacloud.web.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "cluster")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cluster.findAll", query = "SELECT c FROM Cluster c")
    , @NamedQuery(name = "Cluster.findById", query = "SELECT c FROM Cluster c WHERE c.id = :id")
    , @NamedQuery(name = "Cluster.findByVersion", query = "SELECT c FROM Cluster c WHERE c.version = :version")
    , @NamedQuery(name = "Cluster.findByName", query = "SELECT c FROM Cluster c WHERE c.name = :name")
    , @NamedQuery(name = "Cluster.findByState", query = "SELECT c FROM Cluster c WHERE c.state = :state")})
public class Cluster implements Serializable {

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
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "state")
    private String state;
    @JoinTable(name = "cluster_image", joinColumns = {
        @JoinColumn(name = "cluster_images_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "image_id", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Image> imageList;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;
    @OneToMany(mappedBy = "clusterId", fetch = FetchType.LAZY)
    private List<Deployment> deploymentList;

    public Cluster() {
    }

    public Cluster(Long id) {
        this.id = id;
    }

    public Cluster(Long id, long version, String name, String state) {
        this.id = id;
        this.version = version;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @XmlTransient
    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public User getUser() {
        return user;
    }

    public void setUserId(User user) {
        this.user = user;
    }

    @XmlTransient
    public List<Deployment> getDeploymentList() {
        return deploymentList;
    }

    public void setDeploymentList(List<Deployment> deploymentList) {
        this.deploymentList = deploymentList;
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
        if (!(object instanceof Cluster)) {
            return false;
        }
        Cluster other = (Cluster) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uniandes.unacloud.database.Cluster[ id=" + id + " ]";
    }
    
}
