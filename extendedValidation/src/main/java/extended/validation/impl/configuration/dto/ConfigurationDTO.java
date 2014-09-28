package extended.validation.impl.configuration.dto;

import static extended.constants.Constants.DEFAULT_MESSAGE_SOURCE;
import static javax.xml.bind.annotation.XmlAccessType.FIELD;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="extended-validation")
@XmlAccessorType(FIELD)
public class ConfigurationDTO {
	@XmlElement(type= String.class, name="message-source", defaultValue = DEFAULT_MESSAGE_SOURCE)
	private String messageSource;
	
	@XmlElement(name="message-files")
	private List<MessageFileDTO> messageFiles = new ArrayList<MessageFileDTO>();
	
	public String getMessageSource() {
		return messageSource;
	}
	
	public void setMessageSource(String messageSource) {
		this.messageSource = messageSource;
	}
	
	public List<MessageFileDTO> getMessageFiles() {
		return messageFiles;
	}
}
