package cursojava.jpahibernate.orm.modelobanco.dto;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EstadisticasCuenta {

	@Id
	private String banco;
	private String sucursal;
	private Long numero;
	private BigDecimal suma;
	private BigDecimal std;
	private BigDecimal var;
	
	public EstadisticasCuenta() {
	}

	public EstadisticasCuenta(String banco, String sucursal, Long numero, BigDecimal suma, BigDecimal std,
			BigDecimal var) {
		this.banco = banco;
		this.sucursal = sucursal;
		this.numero = numero;
		this.suma = suma;
		this.std = std;
		this.var = var;
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

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public BigDecimal getSuma() {
		return suma;
	}

	public void setSuma(BigDecimal suma) {
		this.suma = suma;
	}

	public BigDecimal getStd() {
		return std;
	}

	public void setStd(BigDecimal std) {
		this.std = std;
	}

	public BigDecimal getVar() {
		return var;
	}

	public void setVar(BigDecimal var) {
		this.var = var;
	}

	@Override
	public String toString() {
		return String.format("EstadisticasCuenta [banco=%s, sucursal=%s, numero=%s, suma=%s, std=%s, var=%s]", banco,
				sucursal, numero, suma, std, var);
	}
	
	

}
