package com.personio.hierarchymanagement.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import com.personio.hierarchymanagement.exception.PersonioCustomException;
import com.personio.hierarchymanagement.model.Employee;
import com.personio.hierarchymanagement.repository.EmployeeRepository;
import com.personio.hierarchymanagement.service.helper.OrganizationHelper;
import com.personio.hierarchymanagement.utils.Member;

@Service
public class OrganizationServiceImpl implements OrganizationService {

	private final EmployeeRepository employeeRepository;

    public OrganizationServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    
	@Override
	public Member getRoot(Collection<Pair<Member, Member>> pairs) throws PersonioCustomException {
		
		OrganizationHelper organizationHelper = new OrganizationHelper();
        for (Pair<Member, Member> pair : pairs) {
        	organizationHelper.getMembers(pair.getLeft(), pair.getRight());
        }
        Member root = organizationHelper.getRoot();
        employeeRepository.deleteAll();
        saveMembers(root, null);
        return root;
	}

	@Override
	public Member getHierarchyByName(String name) throws PersonioCustomException {
		
		Employee searchedEmployee = employeeRepository.findByName(name);
        if (searchedEmployee.getName() == null) {
            return null;
        }

        Employee currentSupervisorEntity = searchedEmployee.getSupervisor();
        Member currentSupervisor = new Member(currentSupervisorEntity.getName());
        Member result = currentSupervisor;
        
        while (currentSupervisorEntity.getSupervisor() != null) {
            Employee parentSupervisorEntity = currentSupervisorEntity.getSupervisor();
            Member parentSupervisor = new Member(parentSupervisorEntity.getName());
            currentSupervisor.setSupervisor(parentSupervisor);
            currentSupervisor = parentSupervisor;
            currentSupervisorEntity = parentSupervisorEntity;
        }
        return result;
		
	}
	
	private void saveMembers(Member member, Employee supervisor) {
		
		Employee employee = new Employee();
		employee.setName(member.getName());
		employee.setSupervisor(supervisor);
        employeeRepository.save(employee);
        
        for (Member childEmployee : member.getEmployees()) {
            saveMembers(childEmployee, employee);
        }
    }

}
