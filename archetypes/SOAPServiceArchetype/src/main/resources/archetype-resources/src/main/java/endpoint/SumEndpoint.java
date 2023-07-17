package ${package}.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ${package}.wsdltypes.*;

@Endpoint
public class SumEndpoint {

	private static final Logger LOGGER = LoggerFactory.getLogger(SumEndpoint.class);

	@PayloadRoot(namespace = "${wsdlNamespace}", localPart = "sum")
	@ResponsePayload
	public SumResponse sum(@RequestPayload Sum request) {

		LOGGER.info("**** 'SumEndpoint' RECEIVED A REQUEST FOR 'sum(arg0={},arg1={})'", request.getArg0(), request.getArg1());

		int res = request.getArg0() + request.getArg1();

		ObjectFactory factory = new ObjectFactory();
		SumResponse response = factory.createSumResponse();
		response.setReturn(res);

		LOGGER.info("**** 'SumEndpoint' IS GOING TO SEND THE RESULT OF THE 'sum()' OPERATION ='{}'",
				response.getReturn());

		return response;
	}
}
