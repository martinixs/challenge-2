package com.tests.sys.social.repository;

import com.tests.sys.social.entity.Person;
import com.tests.sys.social.entity.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, Long> {

    @Query("SELECT rel.friend FROM Relationship rel WHERE rel.person.id = :id and rel.relation = 'FRIEND'")
    List<Person> findFriendsById(@Param("id") Long id);

    @Query("SELECT rel.friend FROM Relationship rel WHERE rel.person.id = :id and rel.relation = 'FOLLOWER'")
    List<Person> findFollowersById(@Param("id") Long id);
}
