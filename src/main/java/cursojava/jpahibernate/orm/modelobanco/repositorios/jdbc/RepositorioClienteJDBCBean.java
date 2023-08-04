package cursojava.jpahibernate.orm.modelobanco.repositorios.jdbc;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import cursojava.jpahibernate.orm.modelobanco.entidades.Cliente;
import cursojava.jpahibernate.orm.modelobanco.repositorios.RepositorioCliente;

//@Repository
public class RepositorioClienteJDBCBean implements RepositorioCliente {
	
	@Autowired
	private JdbcTemplate plantillaJdbc;
	
	@Autowired
	private NamedParameterJdbcTemplate plantillaConParametrosJdbc;

	private RowMapper<Cliente> mapeadorCliente = (resultSet, rowNumber) -> {					
		return new Cliente(
				resultSet.getString("NIF"),
				resultSet.getString("NOMBRE"),
				resultSet.getString("APELLIDOS"),
				resultSet.getString("DOMICILIO"),
				resultSet.getInt("CP"),
				resultSet.getString("CIUDAD"),
				resultSet.getString("PROVINCIA"),
				resultSet.getDate("FECHA_NACIMIENTO")						
			);
		};
	
	@Override
	public void alta(Cliente cte) throws NegocioException {
		
		try {
			
			plantillaJdbc.update(
				"INSERT INTO MODELOBANCO.CLIENTES (NIF, NOMBRE, APELLIDOS, DOMICILIO, CP, CIUDAD, PROVINCIA, FECHA_NACIMIENTO) " +
						"VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)",
				cte.getNif(), cte.getNombre(), cte.getApellidos(), cte.getDomicilio(), cte.getCodigoPostal(), cte.getCiudad(),
					cte.getProvincia(), cte.getFechaNacimiento()
			);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			throw new NegocioException("No se pudo dar alta de cliente " + cte, e);
		}
		
	}
	
	@Override
	public Cliente buscarPorNif(String nif) throws NegocioException {
		
		try {
			return plantillaJdbc.queryForObject(
					"SELECT * FROM MODELOBANCO.CLIENTES WHERE NIF = ?",
					mapeadorCliente, 
					nif
			);
		} catch (Exception e) {

			e.printStackTrace();
			
			throw new NegocioException("No se pudo consultar cliente con nif:" + nif, e);
		}
		
	}	
	
	@Override
	public List<Cliente> buscarTodos() throws NegocioException {
		
		try {
			return plantillaJdbc.query(
				"SELECT * FROM MODELOBANCO.CLIENTES",
				mapeadorCliente
			);
		} catch (Exception e) {
			
			e.printStackTrace();
			
			throw new NegocioException("No se pudo consultar clientes", e);
		}
		
	}
	
	@Override
	public void modificar(Cliente cte) throws NegocioException {
		
		try {
			Map<String, Object> parametros = Map.of(
				"nif", cte.getNif(),
				"nombre", cte.getNombre(),
				"apellidos", cte.getApellidos(),
				"domicilio", cte.getDomicilio()
			);
			
			plantillaConParametrosJdbc.execute(
				"UPDATE MODELOBANCO.CLIENTES " +
					"SET NOMBRE = :nombre, APELLIDOS = :apellidos, DOMICILIO = :domicilio " +
						"WHERE NIF = :nif",
				parametros,
				(pstmt) -> pstmt.executeUpdate()
				
			);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			throw new NegocioException("No se pudo modificar el cliente " + cte, e);
		}
		
	}

	@Override
	public List<Cliente> cargarTodosConCuentasAsignadas() throws NegocioException {
		
		throw new NegocioException("Método sin implementar");
	}
	
	

}










