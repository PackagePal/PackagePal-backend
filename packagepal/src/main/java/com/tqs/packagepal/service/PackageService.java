package com.tqs.packagepal.service;

import com.tqs.packagepal.model.Package;
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

    public Optional<Package> findPackageById(String packageId) {
        return packageRepository.findByPackageId(packageId);
    }

    public List<Package> findPackagesByUserEmail(String userEmail) {
        return packageRepository.findByUserEmail(userEmail);
    }

    public Package addPackage(Package pack) {
        Optional<Package> packageOptional = packageRepository.findByPackageId(pack.getPackageId());
        if (packageOptional.isPresent()) {
            return packageOptional.get();
        }
        return packageRepository.save(pack);
    }

    public boolean deletePackage(String packageId) {
        Optional<Package> packageOptional = packageRepository.findByPackageId(packageId);
        if (packageOptional.isPresent()) {
            packageRepository.delete(packageOptional.get());
            return true;
        }
        return false;
    }

}
