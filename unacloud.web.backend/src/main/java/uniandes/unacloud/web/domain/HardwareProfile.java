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
@Table(name = "hardware_profile")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HardwareProfile.findAll", query = "SELECT h FROM HardwareProfile h")
    , @NamedQuery(name = "HardwareProfile.findById", query = "SELECT h FROM HardwareProfile h WHERE h.id = :id")
    , @NamedQuery(name = "HardwareProfile.findByVersion", query = "SELECT h FROM HardwareProfile h WHERE h.version = :version")
    , @NamedQuery(name = "HardwareProfile.findByCores", query = "SELECT h FROM HardwareProfile h WHERE h.cores = :cores")
    , @NamedQuery(name = "HardwareProfile.findByName", query = "SELECT h FROM HardwareProfile h WHERE h.name = :name")
    , @NamedQuery(name = "HardwareProfile.findByRam", query = "SELECT h FROM HardwareProfile h WHERE h.ram = :ram")})
public class HardwareProfile implements Serializable {

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
    @Column(name = "cores")
    private int cores;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "ram")
    private int ram;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hardwareProfile", fetch = FetchType.LAZY)
    private List<Execution> executionList;

    public HardwareProfile() {
    }

    public HardwareProfile(Long id) {
        this.id = id;
    }

    public HardwareProfile(Long id, long version, int cores, String name, int ram) {
        this.id = id;
        this.version = version;
        this.cores = cores;
        this.name = name;
        this.ram = ram;
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

    public int getCores() {
        return cores;
    }

    public void setCores(int cores) {
        this.cores = cores;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    @XmlTransient
    public List<Execution> getExecutionList() {
        return executionList;
    }

    public void setExecutionList(List<Execution> executionList) {
        this.executionList = executionList;
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
        if (!(object instanceof HardwareProfile)) {
            return false;
        }
        HardwareProfile other = (HardwareProfile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uniandes.unacloud.database.HardwareProfile[ id=" + id + " ]";
    }
    
}
