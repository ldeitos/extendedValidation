package extended.validation.impl.configuration;

import static extended.constants.Constants.CONFIGURATION_FILE;
import static extended.constants.Constants.DEFAULT_MESSAGE_SOURCE;
import static java.lang.ClassLoader.getSystemResourceAsStream;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import extended.validation.impl.configuration.dto.ConfigurationDTO;

class ConfigurationLoader {
	private static ConfigurationDTO configuration;
	
	static ConfigurationDTO loadConfiguration(){
		if(configuration == null){
			load();
		}
		
		return configuration;
	}
	
	private static void load(){
		InputStream xmlCongiruation = 
			getSystemResourceAsStream(CONFIGURATION_FILE);
		if(xmlCongiruation != null){
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(ConfigurationDTO.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				configuration = (ConfigurationDTO) jaxbUnmarshaller.unmarshal(xmlCongiruation);
			} catch (JAXBException e) {
				configuration = getDefaultConfiguration();
			}			
		} else {
			configuration = getDefaultConfiguration();
		}
	}

	private static ConfigurationDTO getDefaultConfiguration() {
		ConfigurationDTO configuration = new ConfigurationDTO();
		
		configuration.setMessageSource(DEFAULT_MESSAGE_SOURCE);
		
		return configuration;
	}

}
