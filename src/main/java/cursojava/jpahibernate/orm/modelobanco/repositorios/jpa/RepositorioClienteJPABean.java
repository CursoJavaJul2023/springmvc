package cursojava.jpahibernate.orm.modelobanco.repositorios.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import cursojava.jpahibernate.orm.modelobanco.entidades.Cliente;
import cursojava.jpahibernate.orm.modelobanco.repositorios.RepositorioCliente;
import cursojava.jpahibernate.orm.modelobanco.repositorios.jdbc.NegocioException;

@Repository
//@Primary
//@Transactional(rollbackFor = NegocioException.class)
public class RepositorioClienteJPABean implements RepositorioCliente {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	// @Transactional(rollbackFor = NegocioException.class)
	public void alta(Cliente cte) throws NegocioException {
		
		try {
			
			em.persist(cte);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			throw new NegocioException("No se pudo dar alta de cliente " + cte, e);
		}
		
	}

	@Override
	//@Transactional(readOnly = true)
	public Cliente buscarPorNif(String nif) throws NegocioException {

		try {
			
			return em.find(Cliente.class, nif);
			
		} catch (Exception e) {

			e.printStackTrace();
			
			throw new NegocioException("No se pudo consultar cliente con nif:" + nif, e);
		}

	}

	@Override
	// @Transactional(rollbackFor = NegocioException.class)
	public void modificar(Cliente cte) throws NegocioException {
		
		try {
			
			em.merge(cte);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			throw new NegocioException("No se pudo modificar el cliente " + cte, e);
		}
		
	}

	@Override
	//@Transactional(readOnly = true)
	public List<Cliente> buscarTodos() throws NegocioException {
		
		try {
			
			return em.createNamedQuery("Cliente.leerTodosLosClientes", Cliente.class).getResultList();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			throw new NegocioException("No se pudo consultar clientes", e);
		}
	}

	@Override
	public List<Cliente> cargarTodosConCuentasAsignadas() throws NegocioException {
		
		try {
			
			return em.createNamedQuery("Cliente.leerTodosLosClientesConCuentas", Cliente.class).getResultList();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			throw new NegocioException("No se pudo consultar clientes", e);
		}
	}

}





