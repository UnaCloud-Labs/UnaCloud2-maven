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
@Table(name = "ip")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ip.findAll", query = "SELECT i FROM IP i")
    , @NamedQuery(name = "Ip.findById", query = "SELECT i FROM IP i WHERE i.id = :id")
    , @NamedQuery(name = "Ip.findByVersion", query = "SELECT i FROM IP i WHERE i.version = :version")
    , @NamedQuery(name = "Ip.findByIp", query = "SELECT i FROM IP i WHERE i.ip = :ip")
    , @NamedQuery(name = "Ip.findByClass1", query = "SELECT i FROM IP i WHERE i.class1 = :class1")
    , @NamedQuery(name = "Ip.findByState", query = "SELECT i FROM IP i WHERE i.state = :state")})
public class IP implements Serializable {

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
    @Column(name = "ip")
    private String ip;
    @Basic(optional = false)
    @Column(name = "class")
    private String class1;
    @Column(name = "state")
    private String state;
    @JoinColumn(name = "ip_pool_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private IPPool ipPool;
    @OneToMany(mappedBy = "ip", fetch = FetchType.LAZY)
    private List<NetInterface> netInterfaceList;
    @OneToMany(mappedBy = "ip", fetch = FetchType.LAZY)
    private List<PhysicalMachine> physicalMachineList;

    public IP() {
    }

    public IP(Long id) {
        this.id = id;
    }

    public IP(Long id, long version, String ip, String class1) {
        this.id = id;
        this.version = version;
        this.ip = ip;
        this.class1 = class1;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClass1() {
        return class1;
    }

    public void setClass1(String class1) {
        this.class1 = class1;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public IPPool getIpPoolId() {
        return ipPool;
    }

    public void setIpPoolId(IPPool ipPool) {
        this.ipPool = ipPool;
    }

    @XmlTransient
    public List<NetInterface> getNetInterfaceList() {
        return netInterfaceList;
    }

    public void setNetInterfaceList(List<NetInterface> netInterfaceList) {
        this.netInterfaceList = netInterfaceList;
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
        if (!(object instanceof IP)) {
            return false;
        }
        IP other = (IP) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uniandes.unacloud.database.Ip[ id=" + id + " ]";
    }
    
}
