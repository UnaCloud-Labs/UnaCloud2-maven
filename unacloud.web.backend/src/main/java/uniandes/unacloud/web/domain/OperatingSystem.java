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
@Table(name = "operating_system")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OperatingSystem.findAll", query = "SELECT o FROM OperatingSystem o")
    , @NamedQuery(name = "OperatingSystem.findById", query = "SELECT o FROM OperatingSystem o WHERE o.id = :id")
    , @NamedQuery(name = "OperatingSystem.findByVersion", query = "SELECT o FROM OperatingSystem o WHERE o.version = :version")
    , @NamedQuery(name = "OperatingSystem.findByConfigurer", query = "SELECT o FROM OperatingSystem o WHERE o.configurer = :configurer")
    , @NamedQuery(name = "OperatingSystem.findByName", query = "SELECT o FROM OperatingSystem o WHERE o.name = :name")})
public class OperatingSystem implements Serializable {

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
    @Column(name = "configurer")
    private String configurer;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "operatingSystemId", fetch = FetchType.LAZY)
    private List<Image> imageList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "operatingSystemId", fetch = FetchType.LAZY)
    private List<PhysicalMachine> physicalMachineList;

    public OperatingSystem() {
    }

    public OperatingSystem(Long id) {
        this.id = id;
    }

    public OperatingSystem(Long id, long version, String configurer, String name) {
        this.id = id;
        this.version = version;
        this.configurer = configurer;
        this.name = name;
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

    public String getConfigurer() {
        return configurer;
    }

    public void setConfigurer(String configurer) {
        this.configurer = configurer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
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
        if (!(object instanceof OperatingSystem)) {
            return false;
        }
        OperatingSystem other = (OperatingSystem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uniandes.unacloud.database.OperatingSystem[ id=" + id + " ]";
    }
    
}
