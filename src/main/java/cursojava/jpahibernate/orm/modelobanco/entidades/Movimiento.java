package cursojava.jpahibernate.orm.modelobanco.entidades;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(schema = "MODELOBANCO", name = "MOVIMIENTOS")
@NamedQueries(
	{
		@NamedQuery(
			name = "Movimiento.leerTodosLoMovimientos",
			query = "select mov from Movimiento mov"
		)
	}
)
public class Movimiento {
	
	@Id
	// Para columnas auto_increment
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// Para columnas que obtienen valores de una secuencia en la base de datos
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqgen")
//	@SequenceGenerator(schema = "MODELOBANCO", sequenceName = "SECUENCIA_ID_MOVIMIENTO", name = "seqgen")
	// Para emplear una tabla en la que cada fila simula ser una secuencia
//	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tabgen")
//	@TableGenerator(schema = "MODELOBANCO", table = "SECUENCIAS_SIMULADAS",
//		pkColumnName = "NOMBRE_SECUENCIA", pkColumnValue = "VALOR_SECUENCIA", name = "tabgen")
	private Integer id;
	
// No es necesario tener las columnas individuales que hacen de clave foranea
//	private String banco;
//	private String sucursal;
//	private String numero;
	
	@ManyToOne
	@JoinColumns(
		{
			@JoinColumn(name = "BANCO", referencedColumnName = "BANCO"),
			@JoinColumn(name = "SUCURSAL", referencedColumnName = "SUCURSAL"),
			@JoinColumn(name = "NUMERO", referencedColumnName = "NUMERO")
		}
	)
	private Cuenta cuenta;
	
	private String descripcion;
	
	@Column(name = "FECHA", insertable = false, updatable = false)
	private LocalDateTime fechaAlta;
		
	public Movimiento() {
	}

	public Movimiento(Cuenta cuenta, String descripcion) {
		this.cuenta = cuenta;
		this.descripcion = descripcion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	@Override
	public String toString() {
		return String.format("Movimiento [id=%s, cuenta=%s, descripcion=%s, fechaAlta=%s]", id, cuenta, descripcion,
				fechaAlta);
	}
	
	

}



