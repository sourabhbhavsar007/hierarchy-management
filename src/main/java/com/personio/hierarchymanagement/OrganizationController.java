package com.personio.hierarchymanagement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.personio.hierarchymanagement.exception.PersonioCustomException;
import com.personio.hierarchymanagement.service.OrganizationService;
import com.personio.hierarchymanagement.utils.Member;

@RestController
public class OrganizationController {
	
	Logger log = LoggerFactory.getLogger(OrganizationController.class);
	
	private final static JsonObject EMPTY_OBJECT = new JsonObject();
	
	@Autowired
    private OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping("/updateHierarchy")
    public String updateHierarchy(HttpEntity<String> httpEntity) throws PersonioCustomException {
    	
    	log.info("Request call for updating hierarchy...");
    	
        String json = httpEntity.getBody();
        
        if (json == null) {
            throw new PersonioCustomException("Request cannot be null, please try again!");
        }

        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entries = object.entrySet();
        Collection<Pair<Member, Member>> pairs = new ArrayList<>();
        
        for (Map.Entry<String, JsonElement> currentEntry : entries) {
        	
            String employeeName = currentEntry.getKey();
            String supervisorName = currentEntry.getValue().getAsString();
            
            Member employee = new Member(employeeName);
            Member supervisor = new Member(supervisorName);
            
            pairs.add(Pair.of(employee, supervisor));
        }
        
        try {
        	
            Member root = organizationService.getRoot(pairs);
            return generateJson(root);
        
        } catch (RuntimeException e) {
            throw new PersonioCustomException(e.getMessage());
        }
    }

    @GetMapping("/getHierarchy/{name}")
    public String getHierarchy(@PathVariable String name) throws PersonioCustomException {
    	
    	log.info("Request call for getting hierarchy with input name : " + name);
    	
        Member firstSupervisor = organizationService.getHierarchyByName(name);
        
        if (firstSupervisor == null) {
            return EMPTY_OBJECT.toString();
        }

        JsonObject result = new JsonObject();
        Member currentSupervisor = firstSupervisor;
        result.add(currentSupervisor.getName(), EMPTY_OBJECT);
        
        while (currentSupervisor.getSupervisor() != null) {
            Member supervisor = currentSupervisor.getSupervisor();
            JsonObject newJsonObject = new JsonObject();
            newJsonObject.add(supervisor.getName(), result);
            result = newJsonObject;
            currentSupervisor = supervisor;
        }
        return result.toString();
    }

    private String generateJson(Member root) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add(root.getName(), generateEmployees(root));
        return jsonObject.toString();
    }

    private JsonElement generateEmployees(Member supervisor) {
        if (supervisor.getEmployees().isEmpty()) {
            return EMPTY_OBJECT;
        }
        
        JsonObject result = new JsonObject();
        for (Member employee : supervisor.getEmployees()) {
            result.add(employee.getName(), generateEmployees(employee));
        }
        
        return result;
    }

}
