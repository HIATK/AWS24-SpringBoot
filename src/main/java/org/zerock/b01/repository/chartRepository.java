package org.zerock.b01.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.b01.domain.Chartest;


@Repository
public interface chartRepository extends JpaRepository<Chartest,Long> {

}
