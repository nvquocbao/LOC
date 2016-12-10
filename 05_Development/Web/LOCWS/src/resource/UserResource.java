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
import model.bean.Output;
import model.bean.User;
import model.dao.UserDAO;


// Will map the resource to the URL todos
@Path("/user")
public class UserResource {

	// Allows to insert contextual objects into the class,
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public Output getAllUser() {
		Output output = null;
		ArrayList<User> listUser = null;
		try {
			listUser = UserDAO.getAll();
			output = new Output(Common.RESULT_OK, listUser);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			output = new Output(Common.RESULT_ERROR);
		}
		return output;
	}
	
	@GET
	@Path("/getById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Output getUserById(@PathParam("id") int id) {
		Output output = null;
		User user = null;
		try {
			user = UserDAO.getUserById(id);
			output = new Output(Common.RESULT_OK, user);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			output = new Output(Common.RESULT_ERROR);
		}
		return output;
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Output login(User user){
		Output output = null;
		User tempUser = null;
		try {
			tempUser = UserDAO.login(user.getEmail(), user.getPassword());
			if (tempUser != null) {
				user = tempUser;
				output = new Output(Common.RESULT_OK, user);
			} else {
				output = new Output(Common.RESULT_ERROR);
			}
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
	public Output createUser(User user){
		Output output = null;
		try {
			UserDAO.insert(user);
			output = new Output(Common.RESULT_OK);
		} catch (ClassNotFoundException | SQLException e) {
			output = new Output(Common.RESULT_OK);
			e.printStackTrace();
		}
		return output;
	}
	
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Output updateUser(User user){
		Output output = null;
		try {
			UserDAO.insert(user);
			output = new Output(Common.RESULT_OK);
		} catch (ClassNotFoundException | SQLException e) {
			output = new Output(Common.RESULT_OK);
			e.printStackTrace();
		}
		return output;
	}
}