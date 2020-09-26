package com.portnet.service.storage;

import java.util.List;

import com.portnet.entity.storage.Vessel;

public interface IVesselService {
    List<Vessel> findAll();
}
