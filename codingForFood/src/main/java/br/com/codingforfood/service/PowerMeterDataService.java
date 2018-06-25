package br.com.codingforfood.service;
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.com.codingforfood.util.Util;
 
@Path("/powerMeterDataService")
public class PowerMeterDataService {
 
	@GET
	@Path("/{param}")
	public Response getData(@PathParam("param") String input) {
 
		String basePath = "c:\\";
		
		String output = Util.fileToString(basePath + input);
 
		return Response.status(200).entity(output).build();
 
	}
 
}