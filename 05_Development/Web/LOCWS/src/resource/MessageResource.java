package resource;

import java.sql.SQLException;
import java.util.ArrayList;

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

import model.Common;
import model.bean.Message;
import model.bean.Output;
import model.dao.MessageDAO;


// Will map the resource to the URL todos
@Path("/message")
public class MessageResource {

	// Allows to insert contextual objects into the class,
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Path("/list/{childId}/{parentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Output listMessage(@PathParam("childId") int childId, @PathParam("parentId") int parentId) {
		Output output = null;
		ArrayList<Message> listMessage = null;
		try {
			listMessage = MessageDAO.getAllMessageOfConversation(childId, parentId);
			output = new Output(Common.RESULT_OK, listMessage);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			output = new Output(Common.RESULT_ERROR);
		}
		return output;
	}
	
	@GET
	@Path("/list/{childId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Output listMessageOfChild(@PathParam("childId") int childId) {
		Output output = null;
		ArrayList<Message> listMessage = null;
		try {
			listMessage = MessageDAO.getAllMessageOfChild(childId);
			output = new Output(Common.RESULT_OK, listMessage);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			output = new Output(Common.RESULT_ERROR);
		}
		return output;
	}
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Output createMessage(Message message){
		Output output = null;
		try {
			MessageDAO.insert(message);
			output = new Output(Common.RESULT_OK);
		} catch (ClassNotFoundException | SQLException e) {
			output = new Output(Common.RESULT_OK);
			e.printStackTrace();
		}
		return output;
	}
}