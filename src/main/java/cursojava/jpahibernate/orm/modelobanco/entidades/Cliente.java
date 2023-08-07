package cursojava.jpahibernate.orm.modelobanco.entidades;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

// Podemos renombrar entidades
// @Entity(name = "ClienteBanco")
@Entity
@Table(schema = "MODELOBANCO", name = "CLIENTES")
@NamedQueries(
	{
		@NamedQuery(
			name = "Cliente.buscarClientesPorNombreYApellidos",
			query = "select cte from Cliente cte where cte.nombre like ?1 or cte.apellidos like ?2"
		),
		@NamedQuery(
			name = "Cliente.buscarClientesPorAltaEntreFechas",
			query = "select cte from Cliente cte where cte.fechaAlta between :fechaInicio and :fechaFin"
		),		
		@NamedQuery(
			name = "Cliente.buscarPorSaldoEnRango",
			query = "select distinct cte from Cliente cte join fetch cte.cuentas cta where cta.saldo between :min and :max"
		),
		@NamedQuery(
			name = "Cliente.leerDatosClientes",
			query = "select cte.nif, cte.nombre, cte.apellidos from Cliente cte"
		),
		@NamedQuery(
			name = "Cliente.leerTodosLosClientes",
			query = "select cte from Cliente cte"
		),
		@NamedQuery(
			name = "Cliente.leerTodosLosClientesConCuentas",
			query = "select cte from Cliente cte left outer join fetch cte.cuentas cta"
		),
		@NamedQuery(
			name = "Cliente.borrar",
			query = "delete from Cliente cte where cte.nif = :nif"
		)		
	}
)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Cliente {

	@Id
	private String nif;

	private String nombre;

	private String apellidos;

	private String domicilio;

	@Column(name = "CP")
	private Integer codigoPostal;

	private String ciudad;

	private String provincia;

	@Column(name = "FECHA_NACIMIENTO")
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;
//	private java.sql.Date fechaNacimiento;
//	private LocalDate fechaNacimiento;

	@Column(name = "FECHA_ALTA", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlta;
//	private Timestamp fechaAlta;
//	private LocalDateTime fechaAlta;

	// Ocasiona la precarga de la colección de cuentas
	// @ManyToMany(fetch = FetchType.EAGER)
	@ManyToMany
	@JoinTable(schema = "MODELOBANCO", name = "TITULARES",
		joinColumns = {
			@JoinColumn(name = "NIF", referencedColumnName = "NIF")
		}, 
		inverseJoinColumns = {
			@JoinColumn(name = "BANCO", referencedColumnName = "BANCO"),
			@JoinColumn(name = "SUCURSAL", referencedColumnName = "SUCURSAL"),
			@JoinColumn(name = "NUMERO", referencedColumnName = "NUMERO")
		}
	)
	@XmlTransient
	@JsonIgnore
	private Set<Cuenta> cuentas = new HashSet<Cuenta>();

	public Cliente() {
	}

	public Cliente(String nif, String nombre, String apellidos) {
		super();
		this.nif = nif;
		this.nombre = nombre;
		this.apellidos = apellidos;
	}

	public Cliente(String nif, String nombre, String apellidos, String domicilio, Integer codigoPostal, String ciudad,
			String provincia, Date fechaNacimiento) {
		super();
		this.nif = nif;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.domicilio = domicilio;
		this.codigoPostal = codigoPostal;
		this.ciudad = ciudad;
		this.provincia = provincia;
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public Integer getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(Integer codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Set<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(Set<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}

	@Override
	public String toString() {
		return String.format(
				"Cliente [nif=%s, nombre=%s, apellidos=%s, domicilio=%s, codigoPostal=%s, ciudad=%s, provincia=%s, fechaNacimiento=%s, fechaAlta=%s]",
				nif, nombre, apellidos, domicilio, codigoPostal, ciudad, provincia, fechaNacimiento, fechaAlta);
	}

}
