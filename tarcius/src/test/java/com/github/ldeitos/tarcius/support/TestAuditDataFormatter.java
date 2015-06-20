package com.github.ldeitos.tarcius.support;

import com.github.ldeitos.tarcius.api.AuditDataContainer;
import com.github.ldeitos.tarcius.api.AuditDataFormatter;
import com.github.ldeitos.tarcius.audit.AuditDataSource;

public class TestAuditDataFormatter implements AuditDataFormatter<AuditData> {

	@Override
	public AuditDataContainer<AuditData> format(AuditDataSource preAuditData) {
		AuditData auditData = new AuditData();
		StringBuilder messageBuilder = auditData.getMessage();
		String quebraLinha = System.getProperty("line.separator");

		messageBuilder.append("Método auditado: ");
		messageBuilder.append(preAuditData.getAuditReference());

		for (String entrada : preAuditData.getAuditParameterReferences()) {
			messageBuilder.append(quebraLinha).append("Parâmetro auditado: ");
			messageBuilder.append(entrada).append(quebraLinha);
			messageBuilder.append("Valor: ").append(preAuditData.getResolvedParameterValues().get(entrada));
		}

		return new AuditDataContainer<AuditData>(auditData);
	}
}
