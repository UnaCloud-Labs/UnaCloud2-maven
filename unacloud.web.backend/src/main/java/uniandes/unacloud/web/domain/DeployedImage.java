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
@Table(name = "deployed_image")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DeployedImage.findAll", query = "SELECT d FROM DeployedImage d")
    , @NamedQuery(name = "DeployedImage.findById", query = "SELECT d FROM DeployedImage d WHERE d.id = :id")
    , @NamedQuery(name = "DeployedImage.findByVersion", query = "SELECT d FROM DeployedImage d WHERE d.version = :version")
    , @NamedQuery(name = "DeployedImage.findByHighAvaliavility", query = "SELECT d FROM DeployedImage d WHERE d.highAvaliavility = :highAvaliavility")})
public class DeployedImage implements Serializable {

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
    @Column(name = "high_avaliavility")
    private boolean highAvaliavility;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deployImageId", fetch = FetchType.LAZY)
    private List<Execution> executionList;
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Image image;
    @JoinColumn(name = "deployment_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Deployment deployment;

    public DeployedImage() {
    }

    public DeployedImage(Long id) {
        this.id = id;
    }

    public DeployedImage(Long id, long version, boolean highAvaliavility) {
        this.id = id;
        this.version = version;
        this.highAvaliavility = highAvaliavility;
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

    public boolean getHighAvaliavility() {
        return highAvaliavility;
    }

    public void setHighAvaliavility(boolean highAvaliavility) {
        this.highAvaliavility = highAvaliavility;
    }

    @XmlTransient
    public List<Execution> getExecutionList() {
        return executionList;
    }

    public void setExecutionList(List<Execution> executionList) {
        this.executionList = executionList;
    }

    public Image getImage() {
        return image;
    }

    public void setImageId(Image image) {
        this.image = image;
    }

    public Deployment getDeployment() {
        return deployment;
    }

    public void setDeployment(Deployment deployment) {
        this.deployment = deployment;
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
        if (!(object instanceof DeployedImage)) {
            return false;
        }
        DeployedImage other = (DeployedImage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uniandes.unacloud.database.DeployedImage[ id=" + id + " ]";
    }
    
}
