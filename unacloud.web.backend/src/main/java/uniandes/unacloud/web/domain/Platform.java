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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "platform")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Platform.findAll", query = "SELECT p FROM Platform p")
    , @NamedQuery(name = "Platform.findById", query = "SELECT p FROM Platform p WHERE p.id = :id")
    , @NamedQuery(name = "Platform.findByVersion", query = "SELECT p FROM Platform p WHERE p.version = :version")
    , @NamedQuery(name = "Platform.findByClassPlatform", query = "SELECT p FROM Platform p WHERE p.classPlatform = :classPlatform")
    , @NamedQuery(name = "Platform.findByFilesExtensions", query = "SELECT p FROM Platform p WHERE p.filesExtensions = :filesExtensions")
    , @NamedQuery(name = "Platform.findByMainExtension", query = "SELECT p FROM Platform p WHERE p.mainExtension = :mainExtension")
    , @NamedQuery(name = "Platform.findByName", query = "SELECT p FROM Platform p WHERE p.name = :name")
    , @NamedQuery(name = "Platform.findByPlatformVersion", query = "SELECT p FROM Platform p WHERE p.platformVersion = :platformVersion")})
public class Platform implements Serializable {

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
    @Column(name = "class_platform")
    private String classPlatform;
    @Column(name = "files_extensions")
    private String filesExtensions;
    @Basic(optional = false)
    @Column(name = "main_extension")
    private String mainExtension;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "platform_version")
    private String platformVersion;
    @JoinTable(name = "physical_machine_platform", joinColumns = {
        @JoinColumn(name = "platform_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "physical_machine_platforms_id", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<PhysicalMachine> physicalMachineList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "platformId", fetch = FetchType.LAZY)
    private List<Image> imageList;

    public Platform() {
    }

    public Platform(Long id) {
        this.id = id;
    }

    public Platform(Long id, long version, String classPlatform, String mainExtension, String name, String platformVersion) {
        this.id = id;
        this.version = version;
        this.classPlatform = classPlatform;
        this.mainExtension = mainExtension;
        this.name = name;
        this.platformVersion = platformVersion;
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

    public String getClassPlatform() {
        return classPlatform;
    }

    public void setClassPlatform(String classPlatform) {
        this.classPlatform = classPlatform;
    }

    public String getFilesExtensions() {
        return filesExtensions;
    }

    public void setFilesExtensions(String filesExtensions) {
        this.filesExtensions = filesExtensions;
    }

    public String getMainExtension() {
        return mainExtension;
    }

    public void setMainExtension(String mainExtension) {
        this.mainExtension = mainExtension;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatformVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }

    @XmlTransient
    public List<PhysicalMachine> getPhysicalMachineList() {
        return physicalMachineList;
    }

    public void setPhysicalMachineList(List<PhysicalMachine> physicalMachineList) {
        this.physicalMachineList = physicalMachineList;
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
        if (!(object instanceof Platform)) {
            return false;
        }
        Platform other = (Platform) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uniandes.unacloud.database.Platform[ id=" + id + " ]";
    }
    
}
