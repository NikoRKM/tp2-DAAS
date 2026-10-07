package ar.edu.unju.fi.tp2.services.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import ar.edu.unju.fi.tp2.repositories.ConfiguracionRepository;
import ar.edu.unju.fi.tp2.services.IConfiguracionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfiguracionServiceIMP implements IConfiguracionService {
	
	private final ConfiguracionRepository configuracionRepository;

	@Override
	public BigDecimal obtenerMontoComision(String clave) {
		return configuracionRepository.findById(clave)
				.map(configuracion -> new BigDecimal(configuracion.getValor()))
				.orElse(new BigDecimal(0));
	}

}
