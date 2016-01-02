package checker.web;

import checker.web.ws.schema.GetCheckerRequest;
import checker.web.ws.schema.GetCheckerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ws.client.core.WebServiceTemplate;

/**
 * Created by david on 1/01/16.
 */
@Controller
public class AccessController {

    private static final Logger logger = LoggerFactory.getLogger(AccessController.class);

    private Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

    @RequestMapping(value = "/pene", method = RequestMethod.GET)
    public String doSomething(){
        logger.info("Se ha hecho peticion");
        marshaller.setPackagesToScan(ClassUtils.getPackageName(GetCheckerRequest.class));
        try {
            marshaller.afterPropertiesSet();
        } catch (Exception e) {}
        GetCheckerRequest request = new GetCheckerRequest();
        request.setUrl("http://www.unizar.es");
        Object response = new WebServiceTemplate(marshaller).marshalSendAndReceive("http://localhost:"
                + "8080" + "/ws", request);
        logger.info("respuesta recibida por el Web Service: "+((GetCheckerResponse) response).getResultCode());
        return "index";
    }

}
