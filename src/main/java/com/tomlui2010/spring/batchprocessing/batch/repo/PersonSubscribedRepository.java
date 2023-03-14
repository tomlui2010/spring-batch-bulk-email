package com.tomlui2010.spring.batchprocessing.batch.repo;

import com.tomlui2010.spring.batchprocessing.batch.model.PersonDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/*
interface extends JpaRepository, which provides generic CRUD (Create, Read, Update, Delete) methods for
interacting with the PersonDTO entity in a database.The interface is annotated with @Repository, which indicates to
Spring that it is a repository component that should be managed by the Spring container.The interface has a generic type
 parameter <PersonDTO, Long>, where PersonDTO is the type of the entity and Long is the type of its primary key.
 The interface does not define any additional methods beyond those provided by JpaRepository, but it inherits
 all the methods of JpaRepository, such as save(), findById(), findAll(), and delete(), which can be used to
 interact with the PersonDTO entity in the database.
 */
@Repository
@Qualifier("personsubscribedrepo")
public interface PersonSubscribedRepository extends JpaRepository<PersonDTO, Long> {
}
