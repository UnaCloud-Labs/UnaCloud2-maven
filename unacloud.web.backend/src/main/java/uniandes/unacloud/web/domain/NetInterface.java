/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uniandes.unacloud.web.domain;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jaime Chavarriaga
 */
@Entity
@Table(name = "net_interface")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NetInterface.findAll", query = "SELECT n FROM NetInterface n")
    , @NamedQuery(name = "NetInterface.findById", query = "SELECT n FROM NetInterface n WHERE n.id = :id")
    , @NamedQuery(name = "NetInterface.findByVersion", query = "SELECT n FROM NetInterface n WHERE n.version = :version")
    , @NamedQuery(name = "NetInterface.findByName", query = "SELECT n FROM NetInterface n WHERE n.name = :name")})
public class NetInterface implements Serializable {

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
    @JoinColumn(name = "ip_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private IP ip;
    @JoinColumn(name = "execution_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Execution execution;

    public NetInterface() {
    }

    public NetInterface(Long id) {
        this.id = id;
    }

    public NetInterface(Long id, long version, String name) {
        this.id = id;
        this.version = version;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IP getIp() {
        return ip;
    }

    public void setIp(IP ip) {
        this.ip = ip;
    }

    public Execution getExecution() {
        return execution;
    }

    public void setExecution(Execution execution) {
        this.execution = execution;
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
        if (!(object instanceof NetInterface)) {
            return false;
        }
        NetInterface other = (NetInterface) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uniandes.unacloud.database.NetInterface[ id=" + id + " ]";
    }
    
}
