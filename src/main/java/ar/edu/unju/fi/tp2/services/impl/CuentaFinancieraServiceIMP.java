package ar.edu.unju.fi.tp2.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unju.fi.tp2.enums.EstadoCuenta;
import ar.edu.unju.fi.tp2.models.CuentaFinanciera;
import ar.edu.unju.fi.tp2.repositories.CuentaFinancieraRepository;
import ar.edu.unju.fi.tp2.services.ICuentaFinancieraService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CuentaFinancieraServiceIMP implements ICuentaFinancieraService {

    @Autowired
    private CuentaFinancieraRepository cuentaFinancieraRepository;

    @Override
    @Transactional
    public CuentaFinanciera saveCuentaFinanciera(
            CuentaFinanciera cuentaFinanciera) {

        return cuentaFinancieraRepository.save(cuentaFinanciera);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CuentaFinanciera> findById(UUID id) throws Exception {
        return cuentaFinancieraRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuentaFinanciera> findAll() {
        return cuentaFinancieraRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<CuentaFinanciera> updateCuentaFinanciera(
            UUID id,
            CuentaFinanciera cuentaFinancieraDetalle) {

        Optional<CuentaFinanciera> cuentaFinancieraOptional =
                cuentaFinancieraRepository.findById(id);

        if (cuentaFinancieraOptional.isPresent()) {

            CuentaFinanciera cuentaFinancieraDb =
                    cuentaFinancieraOptional.orElseThrow();

            cuentaFinancieraDb.setCbu(cuentaFinancieraDetalle.getCbu());
            cuentaFinancieraDb.setAlias(cuentaFinancieraDetalle.getAlias());
            cuentaFinancieraDb.setSaldo(cuentaFinancieraDetalle.getSaldo());
            cuentaFinancieraDb.setEstadoCuenta(
                    cuentaFinancieraDetalle.getEstadoCuenta());
            cuentaFinancieraDb.setCliente(
                    cuentaFinancieraDetalle.getCliente());

            return Optional.of(
                    cuentaFinancieraRepository.save(cuentaFinancieraDb));
        }

        return cuentaFinancieraOptional;
    }

    @Override
    @Transactional
    public Optional<CuentaFinanciera> eliminarPorId(UUID id) {

        Optional<CuentaFinanciera> cuentaFinancieraOptional =
                cuentaFinancieraRepository.findById(id);

        cuentaFinancieraOptional.ifPresent(cuentaFinancieraDb -> {
            cuentaFinancieraRepository.delete(cuentaFinancieraDb);
        });

        return cuentaFinancieraOptional;
    }

    @Override
    public Optional<CuentaFinanciera> findByCbu(Long cbu) throws Exception {
        
        return cuentaFinancieraRepository.findByCbu(cbu);
    }

    @Override
    public List<CuentaFinanciera> findByEstadoCuenta(EstadoCuenta estadoCuenta) {
        // TODO Auto-generated method stub
        return cuentaFinancieraRepository.findByEstadoCuenta(estadoCuenta);
    }
}