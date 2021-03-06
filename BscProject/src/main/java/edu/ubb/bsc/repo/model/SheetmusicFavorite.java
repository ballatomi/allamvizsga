package edu.ubb.bsc.repo.model;
// Generated Apr 25, 2016 8:46:05 PM by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * SheetmusicFavorite generated by hbm2java
 */
@XmlRootElement
@Entity
@Table(name = "sheetmusic_favorite", catalog = "bscproject")
public class SheetmusicFavorite implements java.io.Serializable {

	private Integer id;
	private SheetMusic sheetMusic;
	private User user;

	public SheetmusicFavorite() {
	}

	public SheetmusicFavorite(SheetMusic sheetMusic, User user) {
		this.sheetMusic = sheetMusic;
		this.user = user;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne//(fetch = FetchType.LAZY)
	@JoinColumn(name = "sheetMusicID")
	public SheetMusic getSheetMusic() {
		return this.sheetMusic;
	}

	public void setSheetMusic(SheetMusic sheetMusic) {
		this.sheetMusic = sheetMusic;
	}

	@ManyToOne//(fetch = FetchType.LAZY)
	@JoinColumn(name = "userID")
	public User getUser() {
		return this.user;
	}


	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "SheetmusicFavorite [id=" + id + ", sheetMusic=" + sheetMusic + ", user=" + user + "]";
	}
}
