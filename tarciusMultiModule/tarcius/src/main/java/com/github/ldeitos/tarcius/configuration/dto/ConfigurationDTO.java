package com.github.ldeitos.tarcius.configuration.dto;

import static org.apache.commons.lang.builder.ToStringStyle.SHORT_PREFIX_STYLE;
import static org.apache.commons.lang3.StringUtils.isBlank;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.github.ldeitos.tarcius.configuration.Constants;

/**
 * DTO to load configuration from {@link Constants#CONFIGURATION_FILE}
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
public class ConfigurationDTO {
	private String formatterClass;

	private String dispatcherClass;

	private boolean interruptOnError;

	public String getFormatterClass() {
		return formatterClass;
	}

	public void setFormatterClass(String formatterClass) {
		this.formatterClass = formatterClass;
	}

	public String getDispatcherClass() {
		return dispatcherClass;
	}

	public void setDispatcherClass(String dispatcherClass) {
		this.dispatcherClass = dispatcherClass;
	}

	public boolean isInterruptOnError() {
		return interruptOnError;
	}

	public void setInterruptOnError(boolean interruptOnError) {
		this.interruptOnError = interruptOnError;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, SHORT_PREFIX_STYLE);
	}

	public boolean isEmpty() {
		return isBlank(dispatcherClass) || isBlank(formatterClass);
	}
}
