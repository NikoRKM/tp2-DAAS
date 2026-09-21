package ar.edu.unju.fi.tp2.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unju.fi.tp2.models.CuentaCorriente;
import ar.edu.unju.fi.tp2.repositories.CuentaCorrienteRepository;
import ar.edu.unju.fi.tp2.services.ICuentaCorrienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service 
@RequiredArgsConstructor 
@Slf4j 
public class CuentaCorrienteServiceIMP implements ICuentaCorrienteService {

    @Autowired 
    private CuentaCorrienteRepository cuentaCorrienteRepository;

    @Override
    @Transactional
    public CuentaCorriente saveCuentaCorriente(CuentaCorriente cuentaCorriente) {
        return cuentaCorrienteRepository.save(cuentaCorriente);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CuentaCorriente> findById(UUID id) throws Exception {
        return cuentaCorrienteRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuentaCorriente> findAll() {
        return cuentaCorrienteRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<CuentaCorriente> updateCuentaCorriente(
            UUID id,
            CuentaCorriente cuentaCorrienteDetalle) {

        Optional<CuentaCorriente> cuentaCorrienteOptional =
                cuentaCorrienteRepository.findById(id);

        if (cuentaCorrienteOptional.isPresent()) {

            CuentaCorriente cuentaCorrienteDb =
                    cuentaCorrienteOptional.orElseThrow();

            cuentaCorrienteDb.setCbu(cuentaCorrienteDetalle.getCbu());
            cuentaCorrienteDb.setAlias(cuentaCorrienteDetalle.getAlias());
            cuentaCorrienteDb.setSaldo(cuentaCorrienteDetalle.getSaldo());
            cuentaCorrienteDb.setEstadoCuenta(
                    cuentaCorrienteDetalle.getEstadoCuenta());
            cuentaCorrienteDb.setCliente(cuentaCorrienteDetalle.getCliente());

            cuentaCorrienteDb.setTasaInteresAnual(
                    cuentaCorrienteDetalle.getTasaInteresAnual());

            cuentaCorrienteDb.setCupoLimiteMensual(
                    cuentaCorrienteDetalle.getCupoLimiteMensual());

            return Optional.of(
                    cuentaCorrienteRepository.save(cuentaCorrienteDb));
        }

        return cuentaCorrienteOptional;
    }

    @Override
    @Transactional 
    public Optional<CuentaCorriente> eliminarPorId(UUID id) {

        Optional<CuentaCorriente> cuentaCorrienteOptional =
                cuentaCorrienteRepository.findById(id);

        cuentaCorrienteOptional.ifPresent(cuentaCorrienteDb -> {
            cuentaCorrienteRepository.delete(cuentaCorrienteDb);
        });

        return cuentaCorrienteOptional;
    }

}
