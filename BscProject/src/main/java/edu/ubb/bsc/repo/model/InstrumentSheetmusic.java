package edu.ubb.bsc.repo.model;
// Generated Mar 7, 2016 8:10:23 PM by Hibernate Tools 4.3.1

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
 * InstrumentSheetmusic generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@XmlRootElement
@Table(name = "instrument_sheetmusic", catalog = "bscproject")
public class InstrumentSheetmusic implements java.io.Serializable {

	private Integer id;
	private Instrument instrument;
	private SheetMusic sheetMusic;

	public InstrumentSheetmusic() {
	}

	public InstrumentSheetmusic(Instrument instrument, SheetMusic sheetMusic) {
		this.instrument = instrument;
		this.sheetMusic = sheetMusic;
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
	@JoinColumn(name = "instrumentID")
	public Instrument getInstrument() {
		return this.instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	@ManyToOne//(fetch = FetchType.LAZY)
	@JoinColumn(name = "sheetMusicID")
	public SheetMusic getSheetMusic() {
		return this.sheetMusic;
	}

	public void setSheetMusic(SheetMusic sheetMusic) {
		this.sheetMusic = sheetMusic;
	}

}