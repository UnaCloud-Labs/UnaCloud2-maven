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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jaime Chavarriaga
 */
@Entity
@Table(name = "server_variable")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ServerVariable.findAll", query = "SELECT s FROM ServerVariable s")
    , @NamedQuery(name = "ServerVariable.findById", query = "SELECT s FROM ServerVariable s WHERE s.id = :id")
    , @NamedQuery(name = "ServerVariable.findByVersion", query = "SELECT s FROM ServerVariable s WHERE s.version = :version")
    , @NamedQuery(name = "ServerVariable.findByIsList", query = "SELECT s FROM ServerVariable s WHERE s.isList = :isList")
    , @NamedQuery(name = "ServerVariable.findByName", query = "SELECT s FROM ServerVariable s WHERE s.name = :name")
    , @NamedQuery(name = "ServerVariable.findByProgram", query = "SELECT s FROM ServerVariable s WHERE s.program = :program")
    , @NamedQuery(name = "ServerVariable.findByServerOnly", query = "SELECT s FROM ServerVariable s WHERE s.serverOnly = :serverOnly")
    , @NamedQuery(name = "ServerVariable.findByServerVariableType", query = "SELECT s FROM ServerVariable s WHERE s.serverVariableType = :serverVariableType")
    , @NamedQuery(name = "ServerVariable.findByVariable", query = "SELECT s FROM ServerVariable s WHERE s.variable = :variable")})
public class ServerVariable implements Serializable {

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
    @Column(name = "is_list")
    private boolean isList;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "program")
    private String program;
    @Basic(optional = false)
    @Column(name = "server_only")
    private boolean serverOnly;
    @Basic(optional = false)
    @Column(name = "server_variable_type")
    private String serverVariableType;
    @Basic(optional = false)
    @Column(name = "variable")
    private String variable;

    public ServerVariable() {
    }

    public ServerVariable(Long id) {
        this.id = id;
    }

    public ServerVariable(Long id, long version, boolean isList, String name, String program, boolean serverOnly, String serverVariableType, String variable) {
        this.id = id;
        this.version = version;
        this.isList = isList;
        this.name = name;
        this.program = program;
        this.serverOnly = serverOnly;
        this.serverVariableType = serverVariableType;
        this.variable = variable;
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

    public boolean getIsList() {
        return isList;
    }

    public void setIsList(boolean isList) {
        this.isList = isList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public boolean getServerOnly() {
        return serverOnly;
    }

    public void setServerOnly(boolean serverOnly) {
        this.serverOnly = serverOnly;
    }

    public String getServerVariableType() {
        return serverVariableType;
    }

    public void setServerVariableType(String serverVariableType) {
        this.serverVariableType = serverVariableType;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
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
        if (!(object instanceof ServerVariable)) {
            return false;
        }
        ServerVariable other = (ServerVariable) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uniandes.unacloud.database.ServerVariable[ id=" + id + " ]";
    }
    
}
