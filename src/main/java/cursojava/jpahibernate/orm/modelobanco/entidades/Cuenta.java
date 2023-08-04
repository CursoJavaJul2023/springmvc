package cursojava.jpahibernate.orm.modelobanco.entidades;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cursojava.jpahibernate.orm.modelobanco.dto.DatosInforme;

@Entity
@Table(schema = "MODELOBANCO", name = "CUENTAS")
@NamedNativeQueries(
	{
		@NamedNativeQuery(
			name = "Cuentas.informeDeSucursalesPorSaldos",
			query = "select banco, sucursal, saldo_total, rank() over (partition by banco order by saldo_total desc)  " +
					"from ( " +
						"select banco, sucursal, avg(saldo) as saldo_medio, sum(saldo) as saldo_total " +
							"from modelobanco.cuentas " +
							"group by banco, sucursal " +
							"order by banco, sucursal " +
					") as resumen",
			resultClass = DatosInforme.class
		)		
	}
)
@NamedQueries(
	{
		@NamedQuery(
			name = "Cuenta.leerTodasLasCuentas",
			query = "select cta from Cuenta cta"
		)
	}
)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Cuenta {

	@EmbeddedId
	private CuentaPk codigo;

	private BigDecimal saldo;

	@Column(name = "LIMITE_DESCUBIERTO")
	private BigDecimal limiteDescubierto;

	@Column(name = "LIMITE_OPERACION")
	private BigDecimal limiteOperacion;

	@Column(name = "FECHA_ALTA", insertable = false, updatable = false)
	private LocalDateTime fechaAlta;

	@OneToMany(mappedBy = "cuenta")
	@JsonIgnore
	@XmlTransient
	private Set<Movimiento> movimientos = new HashSet<Movimiento>();

	@ManyToMany(mappedBy = "cuentas")
	@JsonIgnore
	@XmlTransient
	private Set<Cliente> clientes = new HashSet<Cliente>();

	public Cuenta() {
	}

	public Cuenta(CuentaPk codigo, BigDecimal saldo) {
		super();
		this.codigo = codigo;
		this.saldo = saldo;
	}

	public Cuenta(CuentaPk codigo, BigDecimal saldo, BigDecimal limiteDescubierto, BigDecimal limiteOperacion) {
		super();
		this.codigo = codigo;
		this.saldo = saldo;
		this.limiteDescubierto = limiteDescubierto;
		this.limiteOperacion = limiteOperacion;
	}

	public CuentaPk getCodigo() {
		return codigo;
	}

	public void setCodigo(CuentaPk codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public BigDecimal getLimiteDescubierto() {
		return limiteDescubierto;
	}

	public void setLimiteDescubierto(BigDecimal limiteDescubierto) {
		this.limiteDescubierto = limiteDescubierto;
	}

	public BigDecimal getLimiteOperacion() {
		return limiteOperacion;
	}

	public void setLimiteOperacion(BigDecimal limiteOperacion) {
		this.limiteOperacion = limiteOperacion;
	}

	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Set<Movimiento> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(Set<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}

	public Set<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(Set<Cliente> clientes) {
		this.clientes = clientes;
	}

	@Override
	public String toString() {
		return String.format("Cuenta [codigo=%s, saldo=%s, limiteDescubierto=%s, limiteOperacion=%s, fechaAlta=%s]",
				codigo, saldo, limiteDescubierto, limiteOperacion, fechaAlta);
	}

}
