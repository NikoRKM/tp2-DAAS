package ar.edu.unju.fi.tp2.schedulers;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ar.edu.unju.fi.tp2.services.IComisionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ComisionScheduler {
	
	private final IComisionService comisionService;
	
	@Scheduled(cron = "${app.comisiones.cron}")
	public void ejecutarComisionMensual() {
		log.info("Iniciando cobro de comisiones mensuales...");
		
		comisionService.debitarCostoMantenimiento();

		log.info("Finalizando cobro de comisiones mensuales...");
	}
	
}
