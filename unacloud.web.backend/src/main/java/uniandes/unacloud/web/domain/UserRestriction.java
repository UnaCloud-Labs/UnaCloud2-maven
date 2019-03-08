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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jaime Chavarriaga
 */
@Entity
@Table(name = "user_restriction")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserRestriction.findAll", query = "SELECT u FROM UserRestriction u")
    , @NamedQuery(name = "UserRestriction.findById", query = "SELECT u FROM UserRestriction u WHERE u.id = :id")
    , @NamedQuery(name = "UserRestriction.findByVersion", query = "SELECT u FROM UserRestriction u WHERE u.version = :version")
    , @NamedQuery(name = "UserRestriction.findByName", query = "SELECT u FROM UserRestriction u WHERE u.name = :name")
    , @NamedQuery(name = "UserRestriction.findByValue", query = "SELECT u FROM UserRestriction u WHERE u.value = :value")})
public class UserRestriction implements Serializable {

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
    @Basic(optional = false)
    @Column(name = "value")
    private String value;
    @ManyToMany(mappedBy = "userRestrictionList", fetch = FetchType.LAZY)
    private List<User> userList;
    @JoinTable(name = "user_group_user_restriction", joinColumns = {
        @JoinColumn(name = "user_restriction_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "user_group_restrictions_id", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<UserGroup> userGroupList;

    public UserRestriction() {
    }

    public UserRestriction(Long id) {
        this.id = id;
    }

    public UserRestriction(Long id, long version, String name, String value) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.value = value;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @XmlTransient
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @XmlTransient
    public List<UserGroup> getUserGroupList() {
        return userGroupList;
    }

    public void setUserGroupList(List<UserGroup> userGroupList) {
        this.userGroupList = userGroupList;
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
        if (!(object instanceof UserRestriction)) {
            return false;
        }
        UserRestriction other = (UserRestriction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uniandes.unacloud.database.UserRestriction[ id=" + id + " ]";
    }
    
}
