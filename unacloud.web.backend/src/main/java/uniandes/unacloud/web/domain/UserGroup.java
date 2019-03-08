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
@Table(name = "user_group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserGroup.findAll", query = "SELECT u FROM UserGroup u")
    , @NamedQuery(name = "UserGroup.findById", query = "SELECT u FROM UserGroup u WHERE u.id = :id")
    , @NamedQuery(name = "UserGroup.findByVersion", query = "SELECT u FROM UserGroup u WHERE u.version = :version")
    , @NamedQuery(name = "UserGroup.findByName", query = "SELECT u FROM UserGroup u WHERE u.name = :name")
    , @NamedQuery(name = "UserGroup.findByVisualName", query = "SELECT u FROM UserGroup u WHERE u.visualName = :visualName")})
public class UserGroup implements Serializable {

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
    @Column(name = "visual_name")
    private String visualName;
    @ManyToMany(mappedBy = "userGroupList", fetch = FetchType.LAZY)
    private List<UserRestriction> userRestrictionList;
    @ManyToMany(mappedBy = "userGroupList", fetch = FetchType.LAZY)
    private List<User> userList;

    public UserGroup() {
    }

    public UserGroup(Long id) {
        this.id = id;
    }

    public UserGroup(Long id, long version, String name, String visualName) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.visualName = visualName;
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

    public String getVisualName() {
        return visualName;
    }

    public void setVisualName(String visualName) {
        this.visualName = visualName;
    }

    @XmlTransient
    public List<UserRestriction> getUserRestrictionList() {
        return userRestrictionList;
    }

    public void setUserRestrictionList(List<UserRestriction> userRestrictionList) {
        this.userRestrictionList = userRestrictionList;
    }

    @XmlTransient
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
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
        if (!(object instanceof UserGroup)) {
            return false;
        }
        UserGroup other = (UserGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uniandes.unacloud.database.UserGroup[ id=" + id + " ]";
    }
    
}
