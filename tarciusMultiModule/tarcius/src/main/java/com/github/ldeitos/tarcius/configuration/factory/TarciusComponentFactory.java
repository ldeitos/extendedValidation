package com.github.ldeitos.tarcius.configuration.factory;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import com.github.ldeitos.tarcius.api.AuditDataDispatcher;
import com.github.ldeitos.tarcius.api.AuditDataFormatter;

public class TarciusComponentFactory {
	@Inject
	@Any
	private Instance<AuditDataFormatter<?>> dataFormaters;

	@Inject
	@Any
	private Instance<AuditDataDispatcher<?>> dataDispatchers;

	@SuppressWarnings("unchecked")
	public <C> C get(Class<C> type) {
		C bean = null;

		if (type.equals(AuditDataDispatcher.class)) {
			bean = (C) dataDispatchers.get();
		} else {
			bean = (C) dataFormaters.get();
		}

		return bean;
	}

}
