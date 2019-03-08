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
@Table(name = "ippool")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ippool.findAll", query = "SELECT i FROM Ippool i")
    , @NamedQuery(name = "Ippool.findById", query = "SELECT i FROM Ippool i WHERE i.id = :id")
    , @NamedQuery(name = "Ippool.findByVersion", query = "SELECT i FROM Ippool i WHERE i.version = :version")
    , @NamedQuery(name = "Ippool.findByGateway", query = "SELECT i FROM Ippool i WHERE i.gateway = :gateway")
    , @NamedQuery(name = "Ippool.findByMask", query = "SELECT i FROM Ippool i WHERE i.mask = :mask")
    , @NamedQuery(name = "Ippool.findByPrivateNet", query = "SELECT i FROM Ippool i WHERE i.privateNet = :privateNet")})
public class IPPool implements Serializable {

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
    @Column(name = "gateway")
    private String gateway;
    @Basic(optional = false)
    @Column(name = "mask")
    private String mask;
    @Basic(optional = false)
    @Column(name = "private_net")
    private boolean privateNet;
    @OneToMany(mappedBy = "ipPoolId", fetch = FetchType.LAZY)
    private List<IP> ipList;
    @JoinColumn(name = "laboratory_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Laboratory laboratory;

    public IPPool() {
    }

    public IPPool(Long id) {
        this.id = id;
    }

    public IPPool(Long id, long version, String gateway, String mask, boolean privateNet) {
        this.id = id;
        this.version = version;
        this.gateway = gateway;
        this.mask = mask;
        this.privateNet = privateNet;
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

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public boolean getPrivateNet() {
        return privateNet;
    }

    public void setPrivateNet(boolean privateNet) {
        this.privateNet = privateNet;
    }

    @XmlTransient
    public List<IP> getIpList() {
        return ipList;
    }

    public void setIpList(List<IP> ipList) {
        this.ipList = ipList;
    }

    public Laboratory getLaboratoryId() {
        return laboratory;
    }

    public void setLaboratoryId(Laboratory laboratory) {
        this.laboratory = laboratory;
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
        if (!(object instanceof IPPool)) {
            return false;
        }
        IPPool other = (IPPool) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uniandes.unacloud.database.Ippool[ id=" + id + " ]";
    }
    
}
