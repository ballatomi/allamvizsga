package edu.ubb.bsc.repo.model;
// Generated Feb 9, 2016 1:41:43 PM by Hibernate Tools 4.3.1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Sheetmusic generated by hbm2java
 */
@Entity
@Table(name = "sheetmusic", catalog = "bscproject")
public class Sheetmusic implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7230849118801341864L;
	private Integer sheetMusicId;
	private String name;
	private String compositor;
	private Date uploadDate;
	private String length;
	private String license;
	private Integer viewsNum;
	private byte[] fileSound;
	private byte[] filePdf;

	public Sheetmusic() {
	}

	public Sheetmusic(String name, String compositor, Date uploadDate, String length, String license, Integer viewsNum,
			byte[] fileSound, byte[] filePdf) {
		this.name = name;
		this.compositor = compositor;
		this.uploadDate = uploadDate;
		this.length = length;
		this.license = license;
		this.viewsNum = viewsNum;
		this.fileSound = fileSound;
		this.filePdf = filePdf;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "sheetMusicID", unique = true, nullable = false)
	public Integer getSheetMusicId() {
		return this.sheetMusicId;
	}

	public void setSheetMusicId(Integer sheetMusicId) {
		this.sheetMusicId = sheetMusicId;
	}

	@Column(name = "name", length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "compositor", length = 45)
	public String getCompositor() {
		return this.compositor;
	}

	public void setCompositor(String compositor) {
		this.compositor = compositor;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "uploadDate", length = 0)
	public Date getUploadDate() {
		return this.uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	@Column(name = "length", length = 45)
	public String getLength() {
		return this.length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	@Column(name = "license", length = 45)
	public String getLicense() {
		return this.license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	@Column(name = "viewsNum")
	public Integer getViewsNum() {
		return this.viewsNum;
	}

	public void setViewsNum(Integer viewsNum) {
		this.viewsNum = viewsNum;
	}

	@Column(name = "fileSound")
	public byte[] getFileSound() {
		return this.fileSound;
	}

	public void setFileSound(byte[] fileSound) {
		this.fileSound = fileSound;
	}

	@Column(name = "filePDF")
	public byte[] getFilePdf() {
		return this.filePdf;
	}

	public void setFilePdf(byte[] filePdf) {
		this.filePdf = filePdf;
	}

}
