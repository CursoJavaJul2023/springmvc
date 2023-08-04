package cursojava.jpahibernate.orm.modelobanco.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;

//@Getter
//@Setter
//@ToString
//@EqualsAndHashCode
//@AllArgsConstructor
//@NoArgsConstructor
@Data
@Entity
@IdClass(DatosInformePk.class)
public class DatosInforme {
	
	@Id
	private String banco;
	
	@Id
	private String sucursal;
	
	@Column(name = "SALDO_TOTAL")
	private BigDecimal saldoTotal;
	private Long rank;

}
