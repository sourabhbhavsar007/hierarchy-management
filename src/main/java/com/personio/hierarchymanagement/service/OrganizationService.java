package com.personio.hierarchymanagement.service;

import java.util.Collection;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import com.personio.hierarchymanagement.exception.PersonioCustomException;
import com.personio.hierarchymanagement.utils.Member;

@Service
public interface OrganizationService {

	public Member getRoot(Collection<Pair<Member, Member>> pairs) throws PersonioCustomException;

	public Member getHierarchyByName(String name) throws PersonioCustomException;
	
}
