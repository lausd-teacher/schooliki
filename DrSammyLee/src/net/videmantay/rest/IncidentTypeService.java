package net.videmantay.rest;

import static net.videmantay.server.util.DB.db;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.videmantay.rest.dto.IncidentTypeDTO;
import net.videmantay.server.entity.IncidentType;
import net.videmantay.server.util.DB;

@Path("/incidenttype")
public class IncidentTypeService {
	
	DB<IncidentType> incidentTypeDB = new DB<IncidentType>(IncidentType.class);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getIncidentTypes(){
		List<IncidentType> types =  db().load().type(IncidentType.class).list();
		List<IncidentTypeDTO> incidentTypeDTOList = new ArrayList<IncidentTypeDTO>();
		for(IncidentType type: types){
			incidentTypeDTOList.add(new IncidentTypeDTO(type));
		}
		return Response.ok().entity(incidentTypeDTOList).build();
	}
	
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response creteIncidentType(IncidentTypeDTO typeDTO){
		IncidentType incidentType = IncidentType.createFromDTO(typeDTO);
		Long id = incidentTypeDB.save(incidentType).getId();
		return Response.ok().entity(id).build();
	}
	
	
	

}
