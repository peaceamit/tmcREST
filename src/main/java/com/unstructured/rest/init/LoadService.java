package com.unstructured.rest.init;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.unstructured.rest.dataobject.Cab;
import com.unstructured.rest.dataobject.PickUpPoint;
import com.unstructured.rest.dataobject.Route;
import com.unstructured.rest.dataobject.RouteList;

public class LoadService {

	private String appPropertyLocation = "/opt/apache-tomcat-6.0.39/webapps/trackmycab/resources/setting/tmc.properties";
	private String routeListLocation = null;
	private String routeDataLocation = null;
	private String cabListLocation = null;
	private String stopListLocation = null;
	
	private static ArrayList<Boolean> status;

	Map<String,RouteList> routeList;
	Map<String,Cab> cabList;
	Map<String,PickUpPoint> pickUpPoints;


	private LoadService(){}

	public static LoadService serviceData;

	public static LoadService getSingleInstance()
	{
		if (serviceData == null) {
			status = new ArrayList<Boolean>();
			synchronized (LoadService.class) {
				if (serviceData == null) {
					serviceData = new LoadService();
					serviceData.loadData();
				}
			}
		}
		return serviceData;
	}

	/**
	 * method that loads initial set of data for cab, route, pickup points etc
	 */
	public void loadData(){
		/*
		 * app status variable
		 */

		status.add(readAppProperty());
		status.add(populateCabList());
		status.add(populateLocationList());
		status.add(populateRouteList());
	}

	public boolean readAppProperty()
	{
		boolean readPropertyFileSuccess = false;

		try {

			//FileReader reader = new FileReader(appPropertyLocation);
			FileReader reader = new FileReader(appPropertyLocation);
			Properties appProperty = new Properties();
			appProperty.load(reader);

			routeListLocation = appProperty.getProperty("ROUTE_LIST_RESOURCE");
			routeDataLocation = appProperty.getProperty("ROUTE_RESOURCE_LOCATION");
			cabListLocation = appProperty.getProperty("CAB_LIST_RESOURCE");
			stopListLocation = appProperty.getProperty("STOP_LIST_RESOURCE");

			System.out.println("Woha, read the property file : One of the data is "+ routeListLocation);

			readPropertyFileSuccess = true;
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return readPropertyFileSuccess;
	}

	public boolean populateCabList()
	{
		boolean cabListReadSuccess = false;
		ObjectMapper mapper = new ObjectMapper();

		cabList = new HashMap<String,Cab>();

		try {
			FileReader reader = new FileReader(cabListLocation);
			cabList = mapper.readValue(reader, new TypeReference<HashMap<String,Cab>>() {});

			cabListReadSuccess = true;
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cabListReadSuccess;
	}

	public boolean populateLocationList()
	{
		boolean locationListReadSuccess = false;

		ObjectMapper mapper = new ObjectMapper();

		pickUpPoints = new HashMap<String,PickUpPoint>();

		try {
			FileReader reader = new FileReader(stopListLocation);
			pickUpPoints = mapper.readValue(reader, new TypeReference<HashMap<String,PickUpPoint>>(){});	

			locationListReadSuccess = true;
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return locationListReadSuccess;
	}

	public boolean populateRouteList()
	{
		boolean routeListReadSuccess = false;

		ObjectMapper mapper = new ObjectMapper();

		routeList = new HashMap<String,RouteList>();

		try {
			FileReader reader = new FileReader(routeListLocation);
			routeList = mapper.readValue(reader, new TypeReference<HashMap<String,RouteList>>(){});	
			reader.close();


			//For each of the routes, get the route details
			for (String key:routeList.keySet())
			{
				Route route = populateRouteData(routeList.get(key).getRouteNo());
				
				for (String key2:route.getStopList().keySet())
				{
					int pickupNo = route.getStopList().get(key2).getStopNo();
					route.getStopList().get(key2).setLocation(pickUpPoints.get(Integer.toString(pickupNo)).getLocation());
					route.getStopList().get(key2).setStopName(pickUpPoints.get(Integer.toString(pickupNo)).getStopName());
				}
				routeList.get(key).setRoute(route);
			}

			routeListReadSuccess = true;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return routeListReadSuccess;
	}

	public Route populateRouteData(String routeNo)
	{
		ObjectMapper mapper = new ObjectMapper();

		Route route = null;

		try{
			FileReader reader = new FileReader(routeDataLocation + '/' + routeNo + ".json");
			route = mapper.readValue(reader, new TypeReference<Route>(){});
			
			reader.close();
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return route;
	}

	public Map<String, RouteList> getRouteList() {
		return routeList;
	}

	public void setRouteList(Map<String, RouteList> routeList) {
		this.routeList = routeList;
	}

	public Map<String, Cab> getCabList() {
		return cabList;
	}

	public void setCabList(Map<String, Cab> cabList) {
		this.cabList = cabList;
	}

	public Map<String, PickUpPoint> getPickUpPoints() {
		return pickUpPoints;
	}

	public void setPickUpPoints(Map<String, PickUpPoint> pickUpPoints) {
		this.pickUpPoints = pickUpPoints;
	}

	public ArrayList<Boolean> getStatus() {
		return status;
	}
	
}
