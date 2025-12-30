package com.info.router;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.info.model.Person;
import com.info.service.PersonServiceImpl;

@Path("/users")
public class RestRouter {

    PersonServiceImpl ps = new PersonServiceImpl();

    /**
     * GET - Afficher toutes les personnes
     * URL: http://localhost:8080/tp333/api/users/affiche
     */
    @GET
    @Path("/affiche")
    @Produces(MediaType.APPLICATION_JSON)
    public Person[] getAllUsers() {
        return ps.getAllPersons();
    }

    /**
     * GET - Rechercher une personne par ID
     * URL: http://localhost:8080/tp333/api/users/{id}
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") int id) {
        Person person = ps.getPerson(id);
        
        if (person == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("state", "error");
            error.put("message", "Personne introuvable avec ID: " + id);
            return Response.status(404).entity(error).build();
        }
        
        return Response.ok(person).build();
    }

    /**
     * GET - Rechercher une personne par nom
     * URL: http://localhost:8080/tp333/api/users/search?name=Amira
     */
    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByName(@QueryParam("name") String name) {
        if (name == null || name.isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("state", "error");
            error.put("message", "Le paramètre 'name' est requis");
            return Response.status(400).entity(error).build();
        }
        
        Person person = ps.getPersonByName(name);
        
        if (person == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("state", "error");
            error.put("message", "Personne introuvable avec le nom: " + name);
            return Response.status(404).entity(error).build();
        }
        
        return Response.ok(person).build();
    }

    /**
     * POST - Ajouter une nouvelle personne (avec JSON dans le body)
     * URL: http://localhost:8080/tp333/api/users/add
     * Body: {"name": "Ahmed", "age": 25}
     */
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUserJson(Person person) {
        Map<String, Object> response = new HashMap<>();
        
        if (person.getName() == null || person.getName().isEmpty()) {
            response.put("state", "error");
            response.put("message", "Le nom est requis");
            return Response.status(400).entity(response).build();
        }
        
        if (person.getAge() <= 0) {
            response.put("state", "error");
            response.put("message", "L'âge doit être positif");
            return Response.status(400).entity(response).build();
        }
        
        boolean success = ps.addPerson(person);
        
        if (success) {
            response.put("state", "success");
            response.put("message", "Personne ajoutée avec succès");
            response.put("person", person);
            return Response.status(201).entity(response).build();
        } else {
            response.put("state", "error");
            response.put("message", "Erreur lors de l'ajout");
            return Response.status(500).entity(response).build();
        }
    }

    /**
     * PUT - Ajouter via URL (pour compatibilité)
     * URL: http://localhost:8080/tp333/api/users/add/{age}/{name}
     */
    @PUT
    @Path("/add/{age}/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUserUrl(@PathParam("age") int age, @PathParam("name") String name) {
        Map<String, Object> response = new HashMap<>();
        
        Person person = new Person();
        person.setAge(age);
        person.setName(name);
        
        boolean success = ps.addPerson(person);
        
        if (success) {
            response.put("state", "success");
            response.put("message", "Personne ajoutée avec succès");
            return Response.ok(response).build();
        } else {
            response.put("state", "error");
            response.put("message", "Erreur lors de l'ajout");
            return Response.status(500).entity(response).build();
        }
    }

    /**
     * PUT - Modifier une personne existante
     * URL: http://localhost:8080/tp333/api/users/update/{id}
     * Body: {"name": "Ahmed", "age": 30}
     */
    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") int id, Person person) {
        Map<String, Object> response = new HashMap<>();
        
        // Vérifier que la personne existe
        Person existingPerson = ps.getPerson(id);
        if (existingPerson == null) {
            response.put("state", "error");
            response.put("message", "Personne introuvable avec ID: " + id);
            return Response.status(404).entity(response).build();
        }
        
        // Mettre à jour
        person.setId(id);
        boolean success = ps.updatePerson(person);
        
        if (success) {
            response.put("state", "success");
            response.put("message", "Personne modifiée avec succès");
            response.put("person", person);
            return Response.ok(response).build();
        } else {
            response.put("state", "error");
            response.put("message", "Erreur lors de la modification");
            return Response.status(500).entity(response).build();
        }
    }

    /**
     * DELETE - Supprimer une personne
     * URL: http://localhost:8080/tp333/api/users/remove/{id}
     */
    @DELETE
    @Path("/remove/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") int id) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean success = ps.deletePerson(id);
            
            if (success) {
                response.put("state", "success");
                response.put("message", "Personne supprimée avec succès");
                return Response.ok(response).build();
            } else {
                response.put("state", "error");
                response.put("message", "Personne introuvable avec ID: " + id);
                return Response.status(404).entity(response).build();
            }
        } catch (Exception e) {
            response.put("state", "error");
            response.put("message", e.getMessage());
            return Response.status(500).entity(response).build();
        }
    }
}