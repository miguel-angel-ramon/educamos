package es.jccm.edu.shared.application.domain.revisionAudit;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

@MappedSuperclass
public class CustomDefaultRevisionEntity  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1247207905186510450L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELPHOS_MODACC.SEQ_AUD_REVINFO")
	@SequenceGenerator(name = "DELPHOS_MODACC.SEQ_AUD_REVINFO", sequenceName = "DELPHOS_MODACC.SEQ_AUD_REVINFO", allocationSize = 1)
	@RevisionNumber
	@Column(name="REVISION_ID")
	private int id;

	@RevisionTimestamp
	@Column(name="REVTSTMP")
	private long timestamp;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Transient
	public Date getRevisionDate() {
		return new Date( timestamp );
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof CustomDefaultRevisionEntity) ) {
			return false;
		}

		final CustomDefaultRevisionEntity that = (CustomDefaultRevisionEntity) o;
		return id == that.id
				&& timestamp == that.timestamp;
	}

	@Override
	public int hashCode() {
		int result;
		result = id;
		result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
		return result;
	}

	@Override
	public String toString() {
		return "CustomDefaultRevisionEntity(id = " + id
				+ ", revisionDate = " + DateFormat.getDateTimeInstance().format( getRevisionDate() ) + ")";
	}
	
}
