package cursojava.jpahibernate.orm.modelobanco.entidades;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Embeddable
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CuentaPk implements Serializable {
	
	@Pattern(regexp = "^\\d{4}$")
	private String banco;
	
	@Pattern(regexp = "^\\d{4}$")
	private String sucursal;
	
	@Pattern(regexp = "^\\d{10}$")
	private String numero;
	
	public CuentaPk() {
	}

	public CuentaPk(String banco, String sucursal, String numero) {
		this.banco = banco;
		this.sucursal = sucursal;
		this.numero = numero;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Override
	public int hashCode() {
		return Objects.hash(banco, numero, sucursal);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CuentaPk other = (CuentaPk) obj;
		return Objects.equals(banco, other.banco) && Objects.equals(numero, other.numero)
				&& Objects.equals(sucursal, other.sucursal);
	}

	
	///////////////////////////////////////////////////////////////////////////////
	// Opcional
	
	@Override
	public String toString() {
		return String.format("CuentaPk [banco=%s, sucursal=%s, numero=%s]", banco, sucursal, numero);
	}
	
	
	

}


