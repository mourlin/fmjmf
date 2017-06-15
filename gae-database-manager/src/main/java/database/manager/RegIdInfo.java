package database.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class RegIdInfo {
	final static Logger logger = Logger.getLogger(RegIdInfo.class.getName());
	private static Properties props;

	static {
		props					= new Properties();
		InputStream inStream	= Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
		try {
			if (inStream != null)
				props.load(inStream);	//FIXME null pointer exception
			else
				props.put("url.to.regid.registry", System.getProperty("url.to.regid.registry"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<RegId> call() {
		ClientResource cr 	= new ClientResource(props.getProperty("url.to.regid.registry"));
		System.out.println("url.to.regid.registry = "+props.getProperty("url.to.regid.registry"));
		cr.setMethod(Method.GET);
		cr.setRequestEntityBuffering(false);
		logger.info("call to RegIdRegistry search");
		cr.accept(MediaType.APPLICATION_JSON);
				Representation answer 					= cr.get();
	    
		JacksonRepresentation<List> regIdJSON	= new JacksonRepresentation<List>(answer, List.class );
		try {
			ObjectMapper objectMapper			= regIdJSON.getObjectMapper();
			List<RegId> list = objectMapper.readValue(regIdJSON.getText(), new TypeReference<List<RegId>>(){});
			return list;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
