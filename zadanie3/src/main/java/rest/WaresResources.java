package rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import domain.Category;
import domain.Comment;
import domain.Ware;

@Path("/wares")
@Stateless
public class WaresResources {

	
	@PersistenceContext
	EntityManager em;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ware> getAll(){
		return em.createNamedQuery("ware.all", Ware.class)
				.getResultList();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(Ware ware) {
		em.persist(ware);
		return Response.ok(ware.getId()).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") int id) {
		List<Ware> result = em.createNamedQuery("ware.id", Ware.class)
				.setParameter("wareId", id)
				.getResultList();
		if(result.isEmpty()) {
			return Response.status(404).build();
		}
		return Response.ok(result.get(0)).build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") int id, Ware w) {
		List<Ware> result = em.createNamedQuery("ware.id", Ware.class)
				.setParameter("wareId", id)
				.getResultList();
		if(result.isEmpty()) {
			return Response.status(404).build();
		}
		result.get(0).setName(w.getName());
		result.get(0).setPrice(w.getPrice());
		result.get(0).setCategory(w.getCategory());
		em.persist(result.get(0));
		return Response.ok().build();
	}
	
	
	@GET
	@Path("/{wareId}/comments")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getComments(@PathParam("wareId") int wareId){
		List<Ware> result = em.createNamedQuery("ware.id", Ware.class)
				.setParameter("wareId", wareId)
				.getResultList();
		if(result.isEmpty()) {
			return null;
		}
		return result.get(0).getComments();
	}
	
	@POST
	@Path("/{id}/comments")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addComment(@PathParam("id") int wareId,Comment comment) {
		List<Ware> result = em.createNamedQuery("ware.id", Ware.class)
				.setParameter("wareId", wareId)
				.getResultList();
		if(result.isEmpty()) {
			return Response.status(404).build();
		}
		result.get(0).getComments().add(comment);
		comment.setWare(result.get(0));
		em.persist(comment);
		return Response.ok().build();
		
	}
	
	@DELETE
	@Path("/{wareId}/comments/{comId}")
	public Response deleteCom(@PathParam("comId") int comId,@PathParam("wareId") int wareId) {
		 List<Ware> result = em.createNamedQuery("ware.id", Ware.class)
				.setParameter("wareId", wareId)
				.getResultList();
		 List<Comment> result2 = em.createNamedQuery("ware.com", Comment.class)
					.setParameter("comId", comId)
					.getResultList();
		if(result.isEmpty() || result2.isEmpty()) {
			return Response.status(404).build();
		}
		Ware ware = result.get(0);
		ware.getComments().removeIf(comment -> comment.getId()==comId);
		em.remove(result2.get(0));
		em.persist(ware);
		return Response.ok().build();
	}
		
	
	
	@GET
	@Path("/cat={category}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ware> getCat(@PathParam("category") Category category){
		return em.createNamedQuery("ware.cat", Ware.class)
				.setParameter("category", category)
				.getResultList();
	}
	
	@GET
	@Path("/price={num1}-{num2}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ware> getPrice(@PathParam("num1") int num1,@PathParam("num2") int num2){
		return em.createNamedQuery("ware.price", Ware.class)
				.setParameter("num1", num1).setParameter("num2", num2)
				.getResultList();
	}
	
	@GET
	@Path("/name={name}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ware> getName(@PathParam("name") String name){
		return em.createNamedQuery("ware.name", Ware.class)
				.setParameter("name", name)
				.getResultList();
	}
	
}
