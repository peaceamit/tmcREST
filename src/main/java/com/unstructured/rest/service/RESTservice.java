package com.unstructured.rest.service;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.unstructured.rest.dataobject.Cab;
import com.unstructured.rest.dataobject.Location;
import com.unstructured.rest.init.LoadService;

@javax.ws.rs.Path("REST")
public class RESTservice {

	boolean isServiceRunning;
	LoadService loadService = LoadService.getSingleInstance();
	ObjectMapper mapper = new ObjectMapper(); 


	@Path("/statusCheck")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getStatus() {

		return "{" +
				"\"appPropertyAvailable\":\"" + loadService.getStatus().get(0).toString() + "\"" +
				",\"CabDataAvailable\":\"" + loadService.getStatus().get(1).toString() + "\"" +
				",\"pickUpLocationAvailable\":\"" + loadService.getStatus().get(2).toString() + "\"" +
				",\"routeDataAvailable\":\"" + loadService.getStatus().get(3).toString() + "\"" +
				"}";
	}

	@Path("/cabList")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getCabList()
	{
		String jsonData = null;
		try {
			jsonData =  mapper.writeValueAsString(loadService.getCabList());
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{No file present}";
		}

		return jsonData;
	}

	@Path("/routeList")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getRouteList()
	{
		String jsonData = null;

		try {
			jsonData = mapper.writeValueAsString(loadService.getRouteList());
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonData;
	}

	@Path("/cab/{cabno}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getCabInfo(@PathParam("cabno") String cabno)
	{
		String jsonData = null;

		try {
			jsonData = mapper.writeValueAsString(loadService.getCabList().get(cabno));
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonData;
	}

	@Path("/route/{routeNo}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getRouteInfo(@PathParam("routeNo") String routeNo)
	{
		String jsonData = null;
		try {
			jsonData = mapper.writeValueAsString(loadService.getRouteList().get(routeNo));
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonData;
	}

	@Path("/cab/{cabNo}/{lattitude}/{longitude}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public String setCabLocation(@PathParam ("cabNo") String cabNo, @PathParam("lattitude") double lattitude, @PathParam("longitude") double longitude)
	{
		String successfulUpdate = "false";
		Cab cab = loadService.getCabList().get(cabNo);
		if (cab != null)
		{
			synchronized(this){
				loadService.getCabList().get(cabNo).setCurrentLocation(new Location(lattitude, longitude));
				successfulUpdate = "true";
			}
		}
		
		return successfulUpdate;
	}

}
