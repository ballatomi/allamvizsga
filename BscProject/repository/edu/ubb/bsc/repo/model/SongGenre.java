package edu.ubb.bsc.repo.model;
// Generated Feb 28, 2016 7:22:44 PM by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * SongGenre generated by hbm2java
 */
@XmlRootElement
@Entity
@Table(name = "song_genre", catalog = "bscproject")
public class SongGenre implements java.io.Serializable {

	private int songGenreId;
	private String songGenreName;

	public SongGenre() {
	}

	public SongGenre(int songGenreId) {
		this.songGenreId = songGenreId;
	}

	public SongGenre(int songGenreId, String songGenreName) {
		this.songGenreId = songGenreId;
		this.songGenreName = songGenreName;
	}

	@Id
	@Column(name = "song_genreID", unique = true, nullable = false)
	public int getSongGenreId() {
		return this.songGenreId;
	}

	public void setSongGenreId(int songGenreId) {
		this.songGenreId = songGenreId;
	}

	@Column(name = "song_genreName", length = 45)
	public String getSongGenreName() {
		return this.songGenreName;
	}

	public void setSongGenreName(String songGenreName) {
		this.songGenreName = songGenreName;
	}
}