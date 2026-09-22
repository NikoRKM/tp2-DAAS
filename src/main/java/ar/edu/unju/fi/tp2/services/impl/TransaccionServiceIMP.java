package ar.edu.unju.fi.tp2.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unju.fi.tp2.enums.EstadoTransaccion;
import ar.edu.unju.fi.tp2.models.Transaccion;
import ar.edu.unju.fi.tp2.repositories.TransaccionRepository;
import ar.edu.unju.fi.tp2.services.ITransaccionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransaccionServiceIMP implements ITransaccionService {

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Override
    @Transactional
    public Transaccion saveTransaccion(Transaccion transaccion) {
        return transaccionRepository.save(transaccion);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Transaccion> findById(UUID id) throws Exception {
        return transaccionRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaccion> findAll() {
        return transaccionRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Transaccion> updateTransaccion(
            UUID id,
            Transaccion transaccionDetalle) {

        Optional<Transaccion> transaccionOptional =
                transaccionRepository.findById(id);

        if (transaccionOptional.isPresent()) {

            Transaccion transaccionDb =
                    transaccionOptional.orElseThrow();

            transaccionDb.setFechaHora(
                    transaccionDetalle.getFechaHora());

            transaccionDb.setMonto(
                    transaccionDetalle.getMonto());

            transaccionDb.setTipoTransaccion(
                    transaccionDetalle.getTipoTransaccion());

            transaccionDb.setEstadoTransaccion(
                    transaccionDetalle.getEstadoTransaccion());

            transaccionDb.setCuentaFinanciera(
                    transaccionDetalle.getCuentaFinanciera());

            return Optional.of(
                    transaccionRepository.save(transaccionDb));
        }

        return transaccionOptional;
    }

    @Override
    @Transactional
    public Optional<Transaccion> eliminarPorId(UUID id) {

        Optional<Transaccion> transaccionOptional =
                transaccionRepository.findById(id);

        transaccionOptional.ifPresent(transaccionDb -> {
            transaccionRepository.delete(transaccionDb);
        });

        return transaccionOptional;
    }

    @Override
    public List<Transaccion> findByFechaHoraBetween(LocalDateTime desde, LocalDateTime hasta) {
        // TODO Auto-generated method stub
        return transaccionRepository.findByFechaHoraBetween(desde, hasta);
    }

    @Override
    public List<Transaccion> findByEstadoTransaccion(EstadoTransaccion estadoTransaccion) {
        // TODO Auto-generated method stub
        return transaccionRepository.findByEstadoTransaccion(estadoTransaccion);
    }
}