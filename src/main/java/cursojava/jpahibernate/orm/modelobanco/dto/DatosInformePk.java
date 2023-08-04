package cursojava.jpahibernate.orm.modelobanco.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class DatosInformePk implements Serializable {
	
	private String banco;
	private String sucursal;

}
