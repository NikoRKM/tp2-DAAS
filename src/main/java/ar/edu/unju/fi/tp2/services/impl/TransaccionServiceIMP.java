package ar.edu.unju.fi.tp2.services.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unju.fi.tp2.dto.TransaccionRequestDto;
import ar.edu.unju.fi.tp2.dto.TransaccionResponseDto;
import ar.edu.unju.fi.tp2.dto.TransferenciaRequestDto;
import ar.edu.unju.fi.tp2.dto.TransferenciaResponseDto;
import ar.edu.unju.fi.tp2.enums.EstadoTransaccion;
import ar.edu.unju.fi.tp2.enums.TipoTransaccion;
import ar.edu.unju.fi.tp2.exceptions.RecursoNoEncontradoException;
import ar.edu.unju.fi.tp2.exceptions.SaldoInsuficienteException;
import ar.edu.unju.fi.tp2.models.Adherente;
import ar.edu.unju.fi.tp2.models.CuentaFinanciera;
import ar.edu.unju.fi.tp2.models.Transaccion;
import ar.edu.unju.fi.tp2.repositories.AdherenteRepository;
import ar.edu.unju.fi.tp2.repositories.CuentaFinancieraRepository;
import ar.edu.unju.fi.tp2.repositories.TransaccionRepository;
import ar.edu.unju.fi.tp2.services.ICuentaFinancieraService;
import ar.edu.unju.fi.tp2.services.ITransaccionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransaccionServiceIMP implements ITransaccionService {

	private final TransaccionRepository transaccionRepository;

	private final CuentaFinancieraRepository cuentaFinancieraRepository;

	private final ICuentaFinancieraService cuentaFinancieraService;

	private final AdherenteRepository adherenteRepository;

	private static final BigDecimal LIMITE_TITULAR = new BigDecimal("100000");
	private static final BigDecimal LIMITE_ADHERENTE = new BigDecimal("70000");

	@Override
	@Transactional
	public TransaccionResponseDto saveTransaccion(TransaccionRequestDto transaccionDto) {
		CuentaFinanciera cuentaFinanciera = findCuentaFinanciera(transaccionDto.getCuentaFinanciera());

		if (transaccionDto.getTipoTransaccion().equals(TipoTransaccion.EXTRACCION)) {
			if (cuentaFinanciera.getSaldo().compareTo(transaccionDto.getMonto()) < 0) {
				log.warn("No hay suficiente saldo para completar la transaccion");
				throw new SaldoInsuficienteException();
			}
		}

		Transaccion transaccion = Transaccion.builder()
				.fechaHora(LocalDateTime.now())
				.monto(transaccionDto.getMonto())
				.tipoTransaccion(transaccionDto.getTipoTransaccion())
				.estadoTransaccion(transaccionDto.getEstadoTransaccion())
				.cuentaFinanciera(cuentaFinanciera)
				.build();

		Transaccion savedTransaccion = transaccionRepository.save(transaccion);
		log.info("Se ha creado la Transaccion: " + savedTransaccion.getId());
		return mapToResponseDto(savedTransaccion);
	}

	@Override
	@Transactional(readOnly = true)
	public TransaccionResponseDto findById(UUID id) {
		Transaccion transaccion = transaccionRepository.findById(id)
				.orElseThrow(() -> {
					log.info("NO se ha encontrado la Transaccion: " + id);
					return new RecursoNoEncontradoException(id, "Transaccion");
				});
		log.info("Se ha encontrado la Transaccion: " + transaccion.getId());
		return mapToResponseDto(transaccion);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TransaccionResponseDto> findAll() {
		return transaccionRepository.findAll()
				.stream()
				.map(this::mapToResponseDto)
				.toList();
	}

	@Override
	@Transactional
	public TransaccionResponseDto updateTransaccion(UUID id, TransaccionRequestDto transaccionDto) {
		Transaccion transaccion = transaccionRepository.findById(id)
				.orElseThrow(() -> {
					log.info("NO se ha encontrado la Transaccion: " + id);
					return new RecursoNoEncontradoException(id, "Transaccion");
				});
		log.info("Se ha encontrado la Transaccion: " + transaccion.getId());

		transaccion.setFechaHora(LocalDateTime.now());
		transaccion.setMonto(transaccionDto.getMonto());
		transaccion.setTipoTransaccion(transaccionDto.getTipoTransaccion());
		transaccion.setEstadoTransaccion(transaccionDto.getEstadoTransaccion());

		Transaccion updatedTransaccion = transaccionRepository.save(transaccion);
		log.info("Se ha actualizado la Transaccion: " + id);
		return mapToResponseDto(updatedTransaccion);
	}

	@Override
	@Transactional
	public TransaccionResponseDto eliminarPorId(UUID id) {
		Transaccion transaccion = transaccionRepository.findById(id)
				.orElseThrow(() -> {
					log.info("NO se ha encontrado la Transaccion: " + id);
					return new RecursoNoEncontradoException(id, "Transaccion");
				});
		log.info("Se ha encontrado la Transaccion: " + transaccion.getId());

		transaccionRepository.delete(transaccion);

		log.info("Se ha borrado la Transaccion: " + id);
		return mapToResponseDto(transaccion);
	}

	@Override
	public List<TransaccionResponseDto> findByFechaHoraBetween(LocalDateTime desde, LocalDateTime hasta) {
		return transaccionRepository.findByFechaHoraBetween(desde, hasta)
				.stream()
				.map(this::mapToResponseDto)
				.toList();
	}

	@Override
	public List<TransaccionResponseDto> findByEstadoTransaccion(EstadoTransaccion estadoTransaccion) {
		return transaccionRepository.findByEstadoTransaccion(estadoTransaccion)
				.stream()
				.map(this::mapToResponseDto)
				.toList();
	}

	@Override
	@Transactional
	public TransferenciaResponseDto realizarTransferenciaEntreCuentas(TransferenciaRequestDto transferenciaDto) {
		// Encontrar las cuentas
		CuentaFinanciera cuentaOrigen = findCuentaFinanciera(transferenciaDto.getCbuOrigen());
		CuentaFinanciera cuentaDestino = findCuentaFinanciera(transferenciaDto.getCbuDestino());

		// Generar las transacciones (TRANSFERENCIA_ENVIADA y TRANSFERENCIA_RECIBIDA)
		Transaccion transaccionTransferenciaEnviada = Transaccion.builder()
				.fechaHora(LocalDateTime.now())
				.monto(transferenciaDto.getMonto())
				.tipoTransaccion(TipoTransaccion.TRANSFERENCIA_ENVIADA)
				.estadoTransaccion(EstadoTransaccion.PENDIENTE)
				.cuentaFinanciera(cuentaOrigen)
				.build();

		Transaccion transaccionTransferenciaRecibida = Transaccion.builder()
				.fechaHora(LocalDateTime.now())
				.monto(transferenciaDto.getMonto())
				.tipoTransaccion(TipoTransaccion.TRANSFERENCIA_RECIBIDA)
				.estadoTransaccion(EstadoTransaccion.PENDIENTE)
				.cuentaFinanciera(cuentaDestino)
				.build();

		// Verificar saldo de cuenta
		cuentaFinancieraService.extraerSaldo(cuentaOrigen.getCbu(), transferenciaDto.getMonto());
		cuentaFinancieraService.ingresarSaldo(cuentaDestino.getCbu(), transferenciaDto.getMonto());

		// Guardar Transacciones
		transaccionTransferenciaEnviada.setEstadoTransaccion(EstadoTransaccion.COMPLETADA);
		transaccionTransferenciaRecibida.setEstadoTransaccion(EstadoTransaccion.COMPLETADA);
		transaccionRepository.save(transaccionTransferenciaEnviada);
		transaccionRepository.save(transaccionTransferenciaRecibida);

		// Confirmar la transferencia
		TransferenciaResponseDto transferencia = TransferenciaResponseDto.builder()
				.cuentaFinancieraOrigen(cuentaOrigen.getId())
				.cbuOrigen(cuentaOrigen.getCbu())
				.cuentaFinancieraDestino(cuentaDestino.getId())
				.cbuDestino(cuentaDestino.getCbu())
				.fechaHora(transaccionTransferenciaEnviada.getFechaHora())
				.monto(transferenciaDto.getMonto())
				.saldoCuentaOrigen(cuentaOrigen.getSaldo())
				.saldoCuentaDestino(cuentaDestino.getSaldo())
				.build();
		return transferencia;
	}

	private TransaccionResponseDto mapToResponseDto(Transaccion transaccion) {
		return TransaccionResponseDto.builder()
				.id(transaccion.getId())
				.fechaHora(transaccion.getFechaHora())
				.monto(transaccion.getMonto())
				.tipoTransaccion(transaccion.getTipoTransaccion())
				.estadoTransaccion(transaccion.getEstadoTransaccion())
				.cuentaFinanciera(transaccion.getCuentaFinanciera().getId())
				.fechaCreacion(transaccion.getFechaCreacion())
				.fechaUltimaActualizacion(transaccion.getFechaUltimaActualizacion())
				.build();
	}

	private CuentaFinanciera findCuentaFinanciera(UUID id) {
		CuentaFinanciera cuentaFinanciera = cuentaFinancieraRepository.findById(id)
				.orElseThrow(() -> {
					log.info("NO se ha encontrado la Cuenta Financiera: " + id);
					return new RecursoNoEncontradoException(id, "Cuenta Financiera");
				});
		log.info("Se ha encontrado la Cuenta Financiera: " + cuentaFinanciera.getId());
		return cuentaFinanciera;
	}

	private CuentaFinanciera findCuentaFinanciera(Long cbu) {
		CuentaFinanciera cuentaFinanciera = cuentaFinancieraRepository.findByCbu(cbu)
				.orElseThrow(() -> {
					log.info("NO se ha encontrado la Cuenta Financiera: " + cbu);
					return new RecursoNoEncontradoException(cbu, "Cuenta Financiera");
				});
		log.info("Se ha encontrado la Cuenta Financiera: " + cuentaFinanciera.getId());
		return cuentaFinanciera;
	}

	@Transactional
	public TransaccionResponseDto realizarExtraccion(
			BigDecimal monto,
			UUID cuentaId,
			UUID adherenteId) {

		// Buscar la cuenta
		CuentaFinanciera cuenta = cuentaFinancieraRepository.findById(cuentaId)
				.orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

		// Buscar el adherente
		Adherente adherente = adherenteRepository.findById(adherenteId)
				.orElseThrow(() -> new RuntimeException("Adherente no encontrado"));

		// Verificar que el adherente pertenezca al titular de la cuenta
		if (!adherente.getTitular().getId().equals(cuenta.getCliente().getId())) {
			throw new RuntimeException(
					"El adherente no pertenece al titular de la cuenta");
		}

		// Obtener el comienzo y final del día
		LocalDateTime inicioDia = LocalDate.now().atStartOfDay();
		LocalDateTime finDia = inicioDia.plusDays(1).minusNanos(1);

		// Buscar las extracciones realizadas hoy por el adherente
		List<Transaccion> extraccionesHoy = transaccionRepository
				.findByAdherenteIdAndTipoTransaccionAndFechaHoraBetween(
						adherenteId,
						TipoTransaccion.EXTRACCION,
						inicioDia,
						finDia);

		// Calcular cuánto lleva extraído hoy
		BigDecimal totalExtraido = extraccionesHoy.stream()
				.map(Transaccion::getMonto)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		// Límite diario del adherente
		BigDecimal limiteDiario = new BigDecimal("70000");

		// Verificar límite
		if (totalExtraido.add(monto).compareTo(limiteDiario) > 0) {
			throw new RuntimeException(
					"El adherente supera el límite diario de extracción");
		}

		// Verificar saldo suficiente
		if (cuenta.getSaldo().compareTo(monto) < 0) {
			throw new RuntimeException("Saldo insuficiente");
		}

		// Descontar el monto de la cuenta
		cuenta.setSaldo(cuenta.getSaldo().subtract(monto));

		cuentaFinancieraRepository.save(cuenta);

		// Crear la transacción
		Transaccion transaccion = Transaccion.builder()
				.fechaHora(LocalDateTime.now())
				.monto(monto)
				.tipoTransaccion(TipoTransaccion.EXTRACCION)
				.estadoTransaccion(EstadoTransaccion.COMPLETADA)
				.cuentaFinanciera(cuenta)
				.adherente(adherente)
				.build();

		// Guardar la transacción
		transaccionRepository.save(transaccion);

		// Devolver DTO sin relaciones JPA completas
		return TransaccionResponseDto.builder()
				.id(transaccion.getId())
				.fechaHora(transaccion.getFechaHora())
				.monto(transaccion.getMonto())
				.tipoTransaccion(transaccion.getTipoTransaccion())
				.estadoTransaccion(transaccion.getEstadoTransaccion())
				.cuentaFinanciera(transaccion.getCuentaFinanciera().getId())
				.fechaCreacion(transaccion.getFechaCreacion())
				.fechaUltimaActualizacion(transaccion.getFechaUltimaActualizacion())
				.build();
	}

}