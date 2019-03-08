/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uniandes.unacloud.web.domain;

import java.io.Serializable;
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
@Table(name = "repository")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Repository.findAll", query = "SELECT r FROM Repository r")
    , @NamedQuery(name = "Repository.findById", query = "SELECT r FROM Repository r WHERE r.id = :id")
    , @NamedQuery(name = "Repository.findByVersion", query = "SELECT r FROM Repository r WHERE r.version = :version")
    , @NamedQuery(name = "Repository.findByCapacity", query = "SELECT r FROM Repository r WHERE r.capacity = :capacity")
    , @NamedQuery(name = "Repository.findByName", query = "SELECT r FROM Repository r WHERE r.name = :name")
    , @NamedQuery(name = "Repository.findByPath", query = "SELECT r FROM Repository r WHERE r.path = :path")})
public class Repository implements Serializable {

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
    @Column(name = "capacity")
    private int capacity;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "path")
    private String path;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "repository", fetch = FetchType.LAZY)
    private List<Image> imageList;

    public Repository() {
    }

    public Repository(Long id) {
        this.id = id;
    }

    public Repository(Long id, long version, int capacity, String name, String path) {
        this.id = id;
        this.version = version;
        this.capacity = capacity;
        this.name = name;
        this.path = path;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @XmlTransient
    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
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
        if (!(object instanceof Repository)) {
            return false;
        }
        Repository other = (Repository) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uniandes.unacloud.database.Repository[ id=" + id + " ]";
    }
    
}
