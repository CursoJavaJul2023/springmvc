package cursojava.jpahibernate.orm.modelobanco.servicios;

import java.math.BigDecimal;

import org.springframework.transaction.annotation.Transactional;

import cursojava.jpahibernate.orm.modelobanco.entidades.CuentaPk;
import cursojava.jpahibernate.orm.modelobanco.repositorios.jdbc.NegocioException;

public interface ServicioOperacionesModeloBanco {

	void crearClientes(int desde, int numero) throws NegocioException;

	void crearCuentas(int numeroBancos, int numeroSucursales, int numeroCuentas) throws NegocioException;

	void asignarCuentasAClientes(int numeroMaximoDeCuentas) throws NegocioException;

	void cargarConDatosModeloBanco(int desde, int numero, int numeroBancos, int numeroSucursales, int numeroCuentas,
			int numeroMaximoDeCuentas) throws NegocioException;

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
	void hacerTransferencia(CuentaPk ctaOrigen, CuentaPk ctaDestino, BigDecimal cantidad) throws NegocioException;

}
