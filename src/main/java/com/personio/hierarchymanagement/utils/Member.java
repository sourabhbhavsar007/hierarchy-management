package com.personio.hierarchymanagement.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import com.personio.hierarchymanagement.exception.PersonioCustomException;

public class Member {

    private final String name;

    private Member supervisor;

    private Collection<Member> employees = new ArrayList<>();

    public Member(String name) throws PersonioCustomException {
    	
        if (name == null) {
            throw new PersonioCustomException("Name cannot be null, please try again with valid name!");
        }
        this.name = name;
    }

    public void addEmployee(Member employee) {
        employees.add(employee);
    }

    @Override
    public boolean equals(Object o) {
    	
        if (this == o) 
        	return true;
        
        if (o == null || getClass() != o.getClass()) 
        	return false;
        
        Member member = (Member) o;
        return name.equals(member.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

	public Member getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Member supervisor) {
		this.supervisor = supervisor;
	}

	public Collection<Member> getEmployees() {
		return employees;
	}

	public void setEmployees(Collection<Member> employees) {
		this.employees = employees;
	}

	public String getName() {
		return name;
	}

	
}