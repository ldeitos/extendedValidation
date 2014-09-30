package com.github.ldeitos.validation.impl.configuration;

import static com.github.ldeitos.constants.Constants.MESSAGE_FILES_ENVIRONMENT_PROPERTY;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static org.apache.commons.collections15.CollectionUtils.collect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections15.Transformer;

import com.github.ldeitos.exception.InvalidConfigurationException;
import com.github.ldeitos.util.ManualContext;
import com.github.ldeitos.validation.MessagesSource;
import com.github.ldeitos.validation.impl.configuration.dto.ConfigurationDTO;
import com.github.ldeitos.validation.impl.configuration.dto.MessageFileDTO;

public class Configuration {
	private final static Configuration instance = new Configuration();;
	
	private ConfigurationDTO configuration;
	
	private MessagesSource messagesSource;
	
	private Configuration(){
		configuration = ConfigurationLoader.loadConfiguration();
	};
	
	public static Configuration getConfiguration() {
		return instance;
	}
	
	public List<String> getConfituredMessageFiles(){
		ArrayList<String> messageFiles = new ArrayList<String>(getMessageFilesFromXML());
		
		messageFiles.addAll(getMessageFilesFromEnvironmentProperty());
		
		return unmodifiableList(messageFiles);
	}
	
	private Collection<String> getMessageFilesFromEnvironmentProperty() {
		Transformer<String, String> toTrimString = new Transformer<String, String>() {
			public String transform(String arg0) {
				return arg0.trim();
			}
		};
		String configuredFiles = System.getProperty(MESSAGE_FILES_ENVIRONMENT_PROPERTY);
		Collection<String> fileNames = new ArrayList<String>();
		
		if(configuredFiles != null){;
			fileNames.addAll(collect(asList(configuredFiles.split(",")), toTrimString));
		}
		
		return fileNames;
	}

	private Collection<String> getMessageFilesFromXML(){
		Transformer<MessageFileDTO, String> toFileName = new Transformer<MessageFileDTO, String>() {
			public String transform(MessageFileDTO arg0) {
				return arg0.toString();
			}
		};
		
		return collect(configuration.getMessageFiles(), toFileName);
	}
	
	
	
	public MessagesSource getConfiguredMessagesSource() {
		if(messagesSource ==  null) {
			try {
				@SuppressWarnings("unchecked")
				Class<? extends MessagesSource> type = 
					(Class<? extends MessagesSource>) Class.forName(configuration.getMessageSource(), true, ClassLoader.getSystemClassLoader());
				messagesSource = ManualContext.lookupCDI(type);
			} catch (ClassNotFoundException e) {
				InvalidConfigurationException.throwNew(e.getMessage(), e);
			} 
		}
		
		return messagesSource;
	}	
}
