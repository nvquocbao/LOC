package resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import model.Lottery;


// Will map the resource to the URL todos
@Path("/get")
public class LotteryResource {

	// Allows to insert contextual objects into the class,
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Lottery getLottery() {
		Lottery lottery = new Lottery("You must enter date", "", "", "", "", "", "", "", "", "");
		return lottery;
	}
	
	@GET
	@Path("{date}")
	@Produces(MediaType.APPLICATION_JSON)
	public Lottery getLotteryWithDate(@PathParam("date") String date) {
		
		Lottery lottery = new Lottery(date, "Not Found", "", "", "", "", "", "", "", "");
		if (date.equals("22-01-2015")) {
			lottery = new Lottery(date, "68", "123", "9981", "1357", "21044", "54033", "93313", "02450", "761133");
		} else if (date.equals("24-01-2015")){
			lottery = new Lottery(date, "68", "777", "6666", "1568", "44444", "33333", "22222", "11111", "31568");
		}
		
		return lottery;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Lottery consumeJSON(Lottery lotery){
		System.out.println(lotery.getDate() + " == " + lotery.getEight());
		Lottery lottery = new Lottery("Not Found", "Not Found", "", "", "", "", "", "", "", "");
		return lottery;
	}
}