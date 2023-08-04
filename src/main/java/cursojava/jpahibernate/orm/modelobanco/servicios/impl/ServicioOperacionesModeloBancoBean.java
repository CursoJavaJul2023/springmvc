package cursojava.jpahibernate.orm.modelobanco.servicios.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cursojava.jpahibernate.orm.modelobanco.entidades.Cliente;
import cursojava.jpahibernate.orm.modelobanco.entidades.Cuenta;
import cursojava.jpahibernate.orm.modelobanco.entidades.CuentaPk;
import cursojava.jpahibernate.orm.modelobanco.entidades.Movimiento;
import cursojava.jpahibernate.orm.modelobanco.repositorios.RepositorioCliente;
import cursojava.jpahibernate.orm.modelobanco.repositorios.RepositorioCuenta;
import cursojava.jpahibernate.orm.modelobanco.repositorios.RepositorioMovimiento;
import cursojava.jpahibernate.orm.modelobanco.repositorios.jdbc.NegocioException;
import cursojava.jpahibernate.orm.modelobanco.servicios.CodigoError;
import cursojava.jpahibernate.orm.modelobanco.servicios.ServicioOperacionesModeloBanco;

@Service
public class ServicioOperacionesModeloBancoBean implements ServicioOperacionesModeloBanco {
	
	@Autowired
	private RepositorioCliente repoCliente;
	
	@Autowired
	private RepositorioCuenta repoCuenta;
	
	@Autowired
	private RepositorioMovimiento repoMovimiento;
	
	@Override
	@Transactional(rollbackFor = NegocioException.class, propagation = Propagation.REQUIRED)
	public void crearClientes(int desde, int numero) throws NegocioException {
		
		try {
			
			for(int i = desde; i <= desde + numero; i++) {
				
				Cliente cte = new Cliente(
					String.format("%08dA", i),
					"Nombre " + i,
					"Apellidos " + i
				);
				
				repoCliente.alta(cte);			
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new NegocioException("No pudo crear clientes", e);
		}
		
	}
	
	@Override
	@Transactional(rollbackFor = NegocioException.class)
	public void crearCuentas(int numeroBancos, int numeroSucursales, int numeroCuentas) throws NegocioException {

		try {
			Random rnd = new Random();

			for(int i = 1; i <= numeroBancos; i++) {
				for(int j = 1; j <= numeroSucursales; j++) {
					for(int k = 1; k <= numeroCuentas; k++) {
						
						Cuenta cta = new Cuenta(
							new CuentaPk(
								String.format("%04d", i),
								String.format("%04d", j),
								String.format("%010d", k)
							),
							BigDecimal.valueOf( rnd.nextDouble() * 1000.0)
						);
						
						repoCuenta.alta(cta);
						
						Movimiento mov = new Movimiento(cta, "Alta de la cuenta");
						
						repoMovimiento.alta(mov);
					}
				}
			}		

		} catch (Exception e) {
			e.printStackTrace();
			
			throw new NegocioException("No se pudeo crear cuentas", e);

		}

		
	}
	
	@Override
	@Transactional(rollbackFor = NegocioException.class)
	public void asignarCuentasAClientes(int numeroMaximoDeCuentas) throws NegocioException {

		try {
			Random rnd = new Random();
			
			List<Cliente> losClientes = repoCliente.cargarTodosConCuentasAsignadas();
			
			List<Cuenta> lasCuentas = repoCuenta.buscarTodas();
			
			for(Cliente cliente : losClientes) {
				
				int numeroCuentas = rnd.nextInt(numeroMaximoDeCuentas);
				
				for(int i = 0; i <= numeroCuentas; i++) {
					
					Cuenta cuenta = lasCuentas.get(rnd.nextInt(lasCuentas.size()));
					cliente.getCuentas().add(cuenta);					
				}
				
				// Para probar que funciona la transacción como REQUIRED
				// throw new IllegalStateException("Cortamos la asignación de cuentas a clientes");
			}

		} catch (Exception e) {
			e.printStackTrace();
			
			throw new NegocioException("No se pudo asignar cuentas a clientes", e);

		}

	}
	
	@Override
	@Transactional(rollbackFor = NegocioException.class)
	public void cargarConDatosModeloBanco(int desde, int numero,
			int numeroBancos, int numeroSucursales, int numeroCuentas, int numeroMaximoDeCuentas) throws NegocioException {
		
		try {
			
			crearClientes(desde, numero);
			crearCuentas(numeroBancos, numeroSucursales, numeroCuentas);
			asignarCuentasAClientes(numeroMaximoDeCuentas);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			throw new NegocioException("No se pudo realizar la carga de datos", e);
		}
		
	}
	
	
	/**
	 * 
	 * Realiza la transferencia de la cantidad entre los saldos de cuentas
	 * Se modifican los saldos y crean movimientos para cuenta
	 * 
	 * Se valida que existan las cuentas origen y destino
	 * Se valida que la cuenta origen no se queda con saldo negativo
	 * 
	 * @param ctaOrigen
	 * @param ctaDestino
	 * @param cantidad
	 * @throws NegocioException
	 */
	@Override
	@Transactional(rollbackFor = NegocioException.class)
	public void hacerTransferencia(CuentaPk ctaOrigen, CuentaPk ctaDestino, BigDecimal cantidad) throws NegocioException {
		
		try {
			
			Cuenta origen = repoCuenta.buscarPorCodigo(ctaOrigen);
			
			if(origen == null) {
				throw new NegocioException(CodigoError.CTA_ORIGEN_NO_EXISTE, "Cuenta origen no existe");
			}
			
			Cuenta destino = repoCuenta.buscarPorCodigo(ctaDestino);
			
			if(destino == null) {
				throw new NegocioException(CodigoError.CTA_DESTINO_NO_EXISTE, "Cuenta destino no existe");
			}
			
			if(origen.getSaldo().subtract(cantidad).compareTo(BigDecimal.ZERO) < 0) {
				throw new NegocioException(CodigoError.SALDO_INSUFICIENTE_EN_CUENTA_ORIGEN, "Saldo insuficiente en cuenta origen");
			}
			
			origen.setSaldo(origen.getSaldo().subtract(cantidad));
			destino.setSaldo(destino.getSaldo().add(cantidad));
			
			Movimiento movOrigen = new Movimiento(
					origen,
					String.format("Transferencia realizada a la cuenta %s por importe %f", ctaDestino, cantidad)
			);
			
			repoMovimiento.alta(movOrigen);
			
			Movimiento movDestino = new Movimiento(
					destino,
					String.format("Transferencia recibida de la cuenta %s por importe %f", ctaOrigen, cantidad)
			);
			
			repoMovimiento.alta(movDestino);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			throw new NegocioException(CodigoError.ERROR_EN_TRANSFERENCIA, "No pudo realizar transferencia", e);
		}
		
	}

}









