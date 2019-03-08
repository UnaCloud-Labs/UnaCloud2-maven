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
@Table(name = "image")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Image.findAll", query = "SELECT i FROM Image i")
    , @NamedQuery(name = "Image.findById", query = "SELECT i FROM Image i WHERE i.id = :id")
    , @NamedQuery(name = "Image.findByVersion", query = "SELECT i FROM Image i WHERE i.version = :version")
    , @NamedQuery(name = "Image.findByAccessProtocol", query = "SELECT i FROM Image i WHERE i.accessProtocol = :accessProtocol")
    , @NamedQuery(name = "Image.findByFixedDiskSize", query = "SELECT i FROM Image i WHERE i.fixedDiskSize = :fixedDiskSize")
    , @NamedQuery(name = "Image.findByImageVersion", query = "SELECT i FROM Image i WHERE i.imageVersion = :imageVersion")
    , @NamedQuery(name = "Image.findByIsPublic", query = "SELECT i FROM Image i WHERE i.isPublic = :isPublic")
    , @NamedQuery(name = "Image.findByLastUpdate", query = "SELECT i FROM Image i WHERE i.lastUpdate = :lastUpdate")
    , @NamedQuery(name = "Image.findByMainFile", query = "SELECT i FROM Image i WHERE i.mainFile = :mainFile")
    , @NamedQuery(name = "Image.findByName", query = "SELECT i FROM Image i WHERE i.name = :name")
    , @NamedQuery(name = "Image.findByPassword", query = "SELECT i FROM Image i WHERE i.password = :password")
    , @NamedQuery(name = "Image.findByState", query = "SELECT i FROM Image i WHERE i.state = :state")
    , @NamedQuery(name = "Image.findByToken", query = "SELECT i FROM Image i WHERE i.token = :token")
    , @NamedQuery(name = "Image.findByUser", query = "SELECT i FROM Image i WHERE i.user = :user")})
public class Image implements Serializable {

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
    @Column(name = "access_protocol")
    private String accessProtocol;
    @Basic(optional = false)
    @Column(name = "fixed_disk_size")
    private long fixedDiskSize;
    @Basic(optional = false)
    @Column(name = "image_version")
    private int imageVersion;
    @Basic(optional = false)
    @Column(name = "is_public")
    private boolean isPublic;
    @Column(name = "last_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @Column(name = "main_file")
    private String mainFile;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "state")
    private String state;
    @Column(name = "token")
    private String token;
    @Basic(optional = false)
    @Column(name = "user")
    private String user;
    @ManyToMany(mappedBy = "imageList", fetch = FetchType.LAZY)
    private List<Cluster> clusterList;
    @JoinColumn(name = "platform_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Platform platform;
    @JoinColumn(name = "repository_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Repository repository;
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User owner;
    @JoinColumn(name = "operating_system_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private OperatingSystem operatingSystem;
    @OneToMany(mappedBy = "imageId", fetch = FetchType.LAZY)
    private List<DeployedImage> deployedImageList;

    public Image() {
    }

    public Image(Long id) {
        this.id = id;
    }

    public Image(Long id, long version, String accessProtocol, long fixedDiskSize, int imageVersion, boolean isPublic, String name, String password, String state, String user) {
        this.id = id;
        this.version = version;
        this.accessProtocol = accessProtocol;
        this.fixedDiskSize = fixedDiskSize;
        this.imageVersion = imageVersion;
        this.isPublic = isPublic;
        this.name = name;
        this.password = password;
        this.state = state;
        this.user = user;
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

    public String getAccessProtocol() {
        return accessProtocol;
    }

    public void setAccessProtocol(String accessProtocol) {
        this.accessProtocol = accessProtocol;
    }

    public long getFixedDiskSize() {
        return fixedDiskSize;
    }

    public void setFixedDiskSize(long fixedDiskSize) {
        this.fixedDiskSize = fixedDiskSize;
    }

    public int getImageVersion() {
        return imageVersion;
    }

    public void setImageVersion(int imageVersion) {
        this.imageVersion = imageVersion;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getMainFile() {
        return mainFile;
    }

    public void setMainFile(String mainFile) {
        this.mainFile = mainFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @XmlTransient
    public List<Cluster> getClusterList() {
        return clusterList;
    }

    public void setClusterList(List<Cluster> clusterList) {
        this.clusterList = clusterList;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User ownerId) {
        this.owner = ownerId;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystemId(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    @XmlTransient
    public List<DeployedImage> getDeployedImageList() {
        return deployedImageList;
    }

    public void setDeployedImageList(List<DeployedImage> deployedImageList) {
        this.deployedImageList = deployedImageList;
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
        if (!(object instanceof Image)) {
            return false;
        }
        Image other = (Image) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uniandes.unacloud.database.Image[ id=" + id + " ]";
    }
    
}
