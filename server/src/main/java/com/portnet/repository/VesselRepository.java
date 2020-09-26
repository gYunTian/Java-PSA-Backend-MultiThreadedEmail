package com.portnet.repository;

import com.portnet.entity.storage.Vessel;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VesselRepository extends CrudRepository<Vessel, String> { 
}
