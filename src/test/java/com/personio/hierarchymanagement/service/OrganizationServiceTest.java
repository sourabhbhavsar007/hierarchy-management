package com.personio.hierarchymanagement.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.personio.hierarchymanagement.exception.PersonioCustomException;
import com.personio.hierarchymanagement.service.helper.OrganizationHelper;
import com.personio.hierarchymanagement.utils.Member;

@RunWith(BlockJUnit4ClassRunner.class)
public class OrganizationServiceTest {

    @Test
    public void gettMembers_success() throws Exception {
        OrganizationHelper organizationHelper = new OrganizationHelper();
        Member employee = new Member("employee");
        Member supervisor = new Member("supervisor");
        organizationHelper.getMembers(employee, supervisor);

        Member root = organizationHelper.getRoot();

        assertNotNull(root);
    }


    @Test(expected = PersonioCustomException.class)
    public void getMembers_twoRoots() throws Exception {
    	OrganizationHelper organizationHelper = new OrganizationHelper();
        Member employee1 = new Member("employee1");
        Member supervisor1 = new Member("supervisor1");
        Member employee2 = new Member("employee2");
        Member supervisor2 = new Member("supervisor2");
        organizationHelper.getMembers(employee1, supervisor1);
        organizationHelper.getMembers(employee2, supervisor2);

        organizationHelper.getRoot();
    }

    @Test(expected = PersonioCustomException.class)
    public void getMembers_twoSupervisors() throws Exception {
    	OrganizationHelper organizationHelper = new OrganizationHelper();
        Member employee = new Member("employee");
        Member supervisor1 = new Member("supervisor1");
        Member supervisor2 = new Member("supervisor2");
        organizationHelper.getMembers(employee, supervisor1);
        organizationHelper.getMembers(employee, supervisor2);
    }
}
