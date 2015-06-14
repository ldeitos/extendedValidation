package com.github.ldeitos.tarcius.audit.resolver;

import static com.github.ldeitos.tarcius.config.ConfigConstants.FORMATTED_DATE_RESOLVER_ID;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.ldeitos.tarcius.qualifier.CustomResolver;

@CustomResolver(FORMATTED_DATE_RESOLVER_ID)
public class DateFormattedStringResolver extends FormattedStringResolver<Date> {

	@Override
	public String resolve(Date input) {
		DateFormat formatter = new SimpleDateFormat(getFormat());
		return formatter.format(input);
	}

}
