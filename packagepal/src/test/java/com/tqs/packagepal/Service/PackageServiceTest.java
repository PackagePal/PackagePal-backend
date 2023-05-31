package com.tqs.packagepal.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tqs.packagepal.model.DeliveryStatus;
import com.tqs.packagepal.model.Package;
import com.tqs.packagepal.model.PickupPoint;
import com.tqs.packagepal.model.Store;
import com.tqs.packagepal.repository.PackageRepository;
import com.tqs.packagepal.service.PackageService;

class PackageServiceTest {

    @Mock
    private PackageRepository packageRepository;

    @InjectMocks
    private PackageService packageService;

    private List<Package> Packages;
    private Package Package1;
    private Package Package2;
    private PickupPoint PickupPoint;
    private Store Store;

    @BeforeEach
    void setUp() {
        Packages = new ArrayList<>();
        PickupPoint = new PickupPoint();
        Store = new Store();

        Package1 = new Package("packageId1", "user1", "email1", DeliveryStatus.PENDING, PickupPoint,
                Store);
        Package2 = new Package("packageId2", "user2", "email2", DeliveryStatus.DELIVERED, PickupPoint,
                Store);

        Packages.add(Package1);
        Packages.add(Package2);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetAllPackages_thenReturnAllPackages() {
        when(packageRepository.findAll()).thenReturn(Packages);

        List<Package> returnedPackages = packageService.getAllPackages();

        assertEquals(Packages, returnedPackages);
        verify(packageRepository, times(1)).findAll();
    }

    @Test
    void whenFindPackageById_thenReturnPackage() {
        String packageId = "packageId1";

        when(packageRepository.findByPackageId(packageId)).thenReturn(Optional.of(Package1));

        Optional<Package> returnedPackage = packageService.findPackageById(packageId);

        assertTrue(returnedPackage.isPresent());
        assertEquals(Package1, returnedPackage.get());
        verify(packageRepository, times(1)).findByPackageId(packageId);
    }

    @Test
    void whenFindPackageByInvalidId_thenReturnEmptyOptional() {
        String invalidPackageId = "invalidPackageId";

        when(packageRepository.findByPackageId(invalidPackageId)).thenReturn(Optional.empty());

        Optional<Package> returnedPackage = packageService.findPackageById(invalidPackageId);

        assertFalse(returnedPackage.isPresent());
        verify(packageRepository, times(1)).findByPackageId(invalidPackageId);
    }

    @Test
    void whenFindPackagesByUserEmail_thenReturnPackages() {
        String userEmail = "email1";
        when(packageRepository.findByUserEmail(userEmail)).thenReturn(Packages);

        List<Package> returnedPackages = packageService.findPackagesByUserEmail(userEmail);

        assertEquals(Packages, returnedPackages);
        verify(packageRepository, times(1)).findByUserEmail(userEmail);
    }

    @Test
    void whenAddPackage_withExistingPackageId_thenReturnExistingPackage() {
        String existingPackageId = "packageId1";

        when(packageRepository.findByPackageId(existingPackageId)).thenReturn(Optional.of(Package1));

        Package returnedPackage = packageService.addPackage(Package1);

        assertEquals(Package1, returnedPackage);
        verify(packageRepository, times(1)).findByPackageId(existingPackageId);
        verify(packageRepository, times(0)).save(Package1);
    }

    @Test
    void whenAddPackage_withNonExistingPackageId_thenReturnSavedPackage() {
        String nonExistingPackageId = "nonExistingPackageId";
    
        Package packageWithNonExistingId = new Package(nonExistingPackageId, "user1", "email1", DeliveryStatus.PENDING, PickupPoint, Store);
    
        when(packageRepository.findByPackageId(nonExistingPackageId)).thenReturn(Optional.empty());
        when(packageRepository.save(packageWithNonExistingId)).thenReturn(packageWithNonExistingId);
    
        Package returnedPackage = packageService.addPackage(packageWithNonExistingId);
    
        assertEquals(packageWithNonExistingId, returnedPackage);
        verify(packageRepository, times(1)).findByPackageId(nonExistingPackageId);
        verify(packageRepository, times(1)).save(packageWithNonExistingId);
    }

    @Test
    void whenDeletePackage_withExistingPackageId_thenReturnTrue() {
        String existingPackageId = "packageId1";

        when(packageRepository.findByPackageId(existingPackageId)).thenReturn(Optional.of(Package1));

        boolean isDeleted = packageService.deletePackage(existingPackageId);

        assertTrue(isDeleted);
        verify(packageRepository, times(1)).findByPackageId(existingPackageId);
        verify(packageRepository, times(1)).delete(Package1);
    }

    @Test
    void whenDeletePackage_withNonExistingPackageId_thenReturnFalse() {
        String nonExistingPackageId = "nonExistingPackageId";

        when(packageRepository.findByPackageId(nonExistingPackageId)).thenReturn(Optional.empty());

        boolean isDeleted = packageService.deletePackage(nonExistingPackageId);

        assertFalse(isDeleted);
        verify(packageRepository, times(1)).findByPackageId(nonExistingPackageId);
        verify(packageRepository, times(0)).delete(Package1);
    }
    
}
