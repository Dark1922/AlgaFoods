package com.algaworks.algafood.infrastructure.service.report;

import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.domain.service.VendaReportService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PdfVendaReportService implements VendaReportService {//infraestrutura n tem a ver com dominio negocio ou api
 
	@Autowired
	private VendaQueryService vendaQueryService;
	
	@Override
	public byte[] emitirVendasDiaria(VendaDiariaFilter filtro, String timeOfset) {//inputstream dados do nosso relatório
		try {
		var inputStream = this.getClass().getResourceAsStream("/relatorios/venda-diaria.jasper"); //quer buscar um arquivo fluxo de dados
		
		var parametros = new HashMap<String, Object>();
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));//passa o local br pt
		
		//dataSource daonde vem os dados para preencher esse relatorio
		var vendasDiarias = vendaQueryService.consultarVendasDiaria(filtro, timeOfset); //é uma coleção de dados de java beans
		var dataSource = new JRBeanCollectionDataSource(vendasDiarias);
		var jasperPrint = JasperFillManager.fillReport(inputStream, parametros, dataSource);
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
		
		}catch(Exception e) {
			throw new ReportException("Não foi possível emitir relatório de vendas diárias", e); //e causa da exception
		}
	} 

}
