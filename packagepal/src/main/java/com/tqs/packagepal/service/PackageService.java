package com.tqs.packagepal.service;

import com.tqs.packagepal.model.Package;
import com.tqs.packagepal.model.PickupPoint;
import com.tqs.packagepal.model.Store;
import com.tqs.packagepal.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PackageService {

    @Autowired
    private PackageRepository packageRepository;

    public List<Package> getAllPackages() {
        return packageRepository.findAll();
    }

    public Optional<Package> findPackageById(Long id) {
        return packageRepository.findByPackageId(id);
    }

    public List<Package> findPackagesByPickupPoint(PickupPoint pickupPoint) {
        return packageRepository.findByPickupPoint(pickupPoint);
    }

    public List<Package> findPackagesByStore(Store store) {
        return packageRepository.findByStore(store);
    }

    public Package savePackage(Package pack) {
        return packageRepository.save(pack);
    }

    public void deletePackage(Long id) {
        packageRepository.deleteById(id);
    }

}
