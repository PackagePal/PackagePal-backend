package com.tqs.packagepal.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.tqs.packagepal.model.PickupPoint;
import com.tqs.packagepal.repository.PickupPointRepository;
import com.tqs.packagepal.service.PickupPointService;

class PickupPointServiceTest {

    @Mock
    private PickupPointRepository pickupPointRepository;

    @InjectMocks
    private PickupPointService pickupPointService;

    private List<PickupPoint> PickupPoints;
    private PickupPoint PickupPoint1;
    private PickupPoint PickupPoint2;

    @BeforeEach
    void setUp() {
        PickupPoints = new ArrayList<>();
        PickupPoint1 = new PickupPoint("name1", "address1", "city1", "postalCode1", 1.0, 2.0);
        PickupPoint2 = new PickupPoint("name2", "address2", "city2", "postalCode2", 3.0, 4.0);

        PickupPoints.add(PickupPoint1);
        PickupPoints.add(PickupPoint2);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetAllPickupPoints_thenReturnAllPickupPoints() {
        when(pickupPointRepository.findAll()).thenReturn(PickupPoints);

        List<PickupPoint> returnedPickupPoints = pickupPointService.getAllPickupPoints();

        assertEquals(PickupPoints, returnedPickupPoints);
        verify(pickupPointRepository, times(1)).findAll();
    }

    @Test
    void whenGetPickupPointById_thenReturnPickupPoint() {
        when(pickupPointRepository.findById(PickupPoint2.getId())).thenReturn(Optional.of(PickupPoint2));

        Optional<PickupPoint> returnedPickupPoint = pickupPointService.getPickupPointById(PickupPoint2.getId());

        assertEquals(PickupPoint2, returnedPickupPoint.orElse(null));
        verify(pickupPointRepository, times(1)).findById(PickupPoint2.getId());
    }

    @Test
    void whenGetPickupPointByInvalidId_thenReturnNull() {
        Long invalidId = 200L;

        when(pickupPointRepository.findById(invalidId)).thenReturn(Optional.empty());

        Optional<PickupPoint> returnedPickupPoint = pickupPointService.getPickupPointById(invalidId);

        assertNull(returnedPickupPoint.orElse(null));
        verify(pickupPointRepository, times(1)).findById(invalidId);
    }

    @Test
    void whenAddValidPickupPoint_thenReturnCreatedPickupPoint() {
        when(pickupPointRepository.findByName(PickupPoint1.getName())).thenReturn(Optional.empty());
        when(pickupPointRepository.save(PickupPoint1)).thenReturn(PickupPoint1);

        PickupPoint createdPickupPoint = pickupPointService.addPickupPoint(PickupPoint1);

        assertEquals(PickupPoint1, createdPickupPoint);
        verify(pickupPointRepository, times(1)).findByName(PickupPoint1.getName());
        verify(pickupPointRepository, times(1)).save(PickupPoint1);
    }

    @Test
    void whenAddInvalidPickupPointName_thenThrowException() {
        when(pickupPointRepository.findByName(PickupPoint1.getName())).thenReturn(Optional.of(PickupPoint1));
    
        Throwable exception = assertThrows(IllegalStateException.class, () -> {
            pickupPointService.addPickupPoint(PickupPoint1);
        });
    
        assertEquals(IllegalStateException.class, exception.getClass());
        verify(pickupPointRepository, times(1)).findByName(PickupPoint1.getName());
        verify(pickupPointRepository, times(0)).save(PickupPoint1);
    }

    @Test
    void whenAddInvalidPickupPointEmail_thenThrowException() {
        when(pickupPointRepository.findByName(PickupPoint1.getName())).thenReturn(Optional.empty());
        when(pickupPointRepository.save(PickupPoint1)).thenReturn(PickupPoint1);

        PickupPoint createdPickupPoint = pickupPointService.addPickupPoint(PickupPoint1);

        assertEquals(PickupPoint1, createdPickupPoint);
        verify(pickupPointRepository, times(1)).findByName(PickupPoint1.getName());
        verify(pickupPointRepository, times(1)).save(PickupPoint1);
    }

}
