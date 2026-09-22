package ar.edu.unju.fi.tp2.services.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unju.fi.tp2.models.CajaAhorro;
import ar.edu.unju.fi.tp2.repositories.CajaAhorroRepository;
import ar.edu.unju.fi.tp2.services.ICajaAhorroService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CajaAhorroServiceIMP implements ICajaAhorroService {

    @Autowired
    private CajaAhorroRepository cajaAhorroRepository;

    @Override
    @Transactional
    public CajaAhorro saveCajaAhorro(CajaAhorro cajaAhorro) {
        return cajaAhorroRepository.save(cajaAhorro);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CajaAhorro> findById(UUID id) throws Exception {
        return cajaAhorroRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CajaAhorro> findAll() {
        return cajaAhorroRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<CajaAhorro> updateCajaAhorro(UUID id, CajaAhorro cajaAhorroDetalle) {

        Optional<CajaAhorro> cajaAhorroOptional = cajaAhorroRepository.findById(id);

        if (cajaAhorroOptional.isPresent()) {

            CajaAhorro cajaAhorroDb = cajaAhorroOptional.orElseThrow();

            cajaAhorroDb.setCbu(cajaAhorroDetalle.getCbu());
            cajaAhorroDb.setAlias(cajaAhorroDetalle.getAlias());
            cajaAhorroDb.setSaldo(cajaAhorroDetalle.getSaldo());
            cajaAhorroDb.setEstadoCuenta(cajaAhorroDetalle.getEstadoCuenta());
            cajaAhorroDb.setCliente(cajaAhorroDetalle.getCliente());
            cajaAhorroDb.setMargenDescuento(cajaAhorroDetalle.getMargenDescuento());
            cajaAhorroDb.setComisionMantenimientoMensual(
                    cajaAhorroDetalle.getComisionMantenimientoMensual());

            return Optional.of(cajaAhorroRepository.save(cajaAhorroDb));
        }

        return cajaAhorroOptional;
    }

    @Override
    @Transactional
    public Optional<CajaAhorro> eliminarPorId(UUID id) {

        Optional<CajaAhorro> cajaAhorroOptional = cajaAhorroRepository.findById(id);

        cajaAhorroOptional.ifPresent(cajaAhorroDb -> {
            cajaAhorroRepository.delete(cajaAhorroDb);
        });

        return cajaAhorroOptional;
    }

    @Override
    public List<CajaAhorro> findByMargenDescuentoGreaterThan(BigDecimal margenDescuento) {
        return cajaAhorroRepository.findByMargenDescuentoGreaterThan(margenDescuento);
    }

    @Override
    public List<CajaAhorro> findByComisionMantenimientoMensualLessThan(BigDecimal comisionMantenimientoMensual) {
        return cajaAhorroRepository.findByComisionMantenimientoMensualLessThan(comisionMantenimientoMensual);
    }
}