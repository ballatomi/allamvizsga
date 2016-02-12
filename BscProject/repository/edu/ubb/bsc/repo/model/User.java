package edu.ubb.bsc.repo.model;
// Generated Feb 9, 2016 1:41:43 PM by Hibernate Tools 4.3.1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * User generated by hbm2java
 */
@Entity
@Table(name = "user", catalog = "bscproject")
@XmlRootElement
public class User implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3123415447744076912L;
	private Integer userId;
	private String userName;
	private String userPassword;
	private String userMail;
	private String userTel;
	private Integer userRight;
	private Date userLastLogin;

	public User() {
	}

	public User(String userName, String userPassword, String userMail, String userTel, Integer userRight,
			Date userLastLogin, Set advertisements) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.userMail = userMail;
		this.userTel = userTel;
		this.userRight = userRight;
		this.userLastLogin = userLastLogin;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "userID", unique = true, nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "userName", length = 45)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "userPassword", length = 45)
	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@Column(name = "userMail", length = 45)
	public String getUserMail() {
		return this.userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	@Column(name = "userTel", length = 45)
	public String getUserTel() {
		return this.userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	@Column(name = "userRight")
	public Integer getUserRight() {
		return this.userRight;
	}

	public void setUserRight(Integer userRight) {
		this.userRight = userRight;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "userLastLogin", length = 0)
	public Date getUserLastLogin() {
		return this.userLastLogin;
	}

	public void setUserLastLogin(Date userLastLogin) {
		this.userLastLogin = userLastLogin;
	}

	public String toString(){
		return "\tuserId: " + userId+
		"\tuserName: " + userName +
		"\tuserMail: " + userMail +
		"\tuserRight: " + userRight;  
	}
}