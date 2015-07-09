package com.github.ldeitos.tarcius.audit.factory;

import java.lang.annotation.Annotation;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import com.github.ldeitos.tarcius.api.ParameterFormattedResolver;
import com.github.ldeitos.tarcius.api.ParameterResolver;
import com.github.ldeitos.tarcius.qualifier.CustomResolver;

@ApplicationScoped
public class ResolverFactory {

	@Inject
	@Any
	private Instance<ParameterResolver<?>> resolvers;

	@Inject
	@Any
	private Instance<ParameterFormattedResolver<?>> formattedResolvers;

	public ParameterResolver<?> getResolver(String qualifier) {
		return getResolver(new CustomResolverLiteral(qualifier));
	}

	public ParameterFormattedResolver<?> getFormattedResolver(String qualifier) {
		return getFormattedResolver(new CustomResolverLiteral(qualifier));
	}

	public ParameterResolver<?> getResolver(Annotation qualifier) {
		Instance<ParameterResolver<?>> instance = resolvers.select(qualifier);

		return instance.get();
	}

	public ParameterFormattedResolver<?> getFormattedResolver(Annotation qualifier) {
		Instance<ParameterFormattedResolver<?>> instance = formattedResolvers.select(qualifier);

		return instance.get();
	}

	static class CustomResolverLiteral extends AnnotationLiteral<CustomResolver> {
		private static final long serialVersionUID = 1L;

		private String value;

		CustomResolverLiteral(String value) {
			this.value = value;
		}

		public String value() {
			return value;
		}
	}

}
