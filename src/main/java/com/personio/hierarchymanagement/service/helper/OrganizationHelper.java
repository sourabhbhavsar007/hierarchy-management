package com.personio.hierarchymanagement.service.helper;



import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import com.personio.hierarchymanagement.exception.PersonioCustomException;
import com.personio.hierarchymanagement.utils.Member;
import com.sun.tools.sjavac.Log;

public class OrganizationHelper {
    private Collection<Member> allMembers = new ArrayList<>();

    public void getMembers(Member employee, Member supervisor) throws PersonioCustomException {
        if (employee == null || supervisor == null) {
        	Log.error("Employee and Supervisor cannot be null...");
            throw new PersonioCustomException("Employee and Supervisor cannot be null...");
        }
        
        Member existedEmployee = null;
        Member existedSupervisor = null;
        for (Member currentMember : allMembers) {
            if (employee.equals(currentMember)) {
                existedEmployee = currentMember;
            }
            if (supervisor.equals(currentMember)) {
                existedSupervisor = currentMember;
            }
        }
        if (existedEmployee == null) {
            allMembers.add(employee);
        }
        if (existedSupervisor == null) {
            allMembers.add(supervisor);
        }

        if (existedEmployee != null) {
            if (existedEmployee.getSupervisor() != null) {
                String message = "Cannot add supervisor %s to %s, because it already has %s as supervisor";
                throw new PersonioCustomException(String.format(
                        message,
                        employee.getName(),
                        supervisor.getName(),
                        employee.getSupervisor().getName()
                ));
            }
            
            //check for loops
            if (existedSupervisor != null) {
                loopValidation(existedEmployee, existedSupervisor);
                existedEmployee.setSupervisor(existedSupervisor);
                existedSupervisor.addEmployee(existedEmployee);
                return;
            }
            existedEmployee.setSupervisor(supervisor);
            supervisor.addEmployee(existedEmployee);
            return;
        }
        
        // if everything fine, do the assignment
        if (existedSupervisor == null) {
            employee.setSupervisor(supervisor);
            supervisor.addEmployee(employee);
            return;
        }
        
        employee.setSupervisor(existedSupervisor);
        existedSupervisor.addEmployee(employee);
    }

    public Member getRoot() throws PersonioCustomException {
        Member result = null;
        for (Member currentMember : allMembers) {
            if (currentMember.getSupervisor() == null) {
                if (result == null) {
                    result = currentMember;
                } else {
                    String message = "There exist at least two roots %s and %s";
                    throw new PersonioCustomException(String.format(message, result, currentMember));
                }
            }
        }
        return result;
    }

    public Collection<Member> getAllMembers() {
        return allMembers;
    }

    private void loopValidation(Member existedEmployee, Member existedSupervisor) throws PersonioCustomException {
        Collection<Member> memberHierarchy = new ArrayList<>();
        memberHierarchy.add(existedSupervisor);
        Member currentSuperVisorOfSupervisor = existedSupervisor.getSupervisor();
        
        while (currentSuperVisorOfSupervisor != null) {
            memberHierarchy.add(currentSuperVisorOfSupervisor);
            if (currentSuperVisorOfSupervisor.equals(existedEmployee)) {
                memberHierarchy.add(existedSupervisor);
                String loop = memberHierarchy.stream().map(Member::getName).collect(Collectors.joining(" -> "));
                Log.error("There is a loop of supervisors...");
                throw new PersonioCustomException("There is loop of supervisors : " + loop);
            }
            currentSuperVisorOfSupervisor = currentSuperVisorOfSupervisor.getSupervisor();
        }
    }

}
