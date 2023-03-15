package com.openclassrooms.safetynetalert.exception.firestation;

public class FireStationNotFoundException extends RuntimeException {
    public FireStationNotFoundException(String address) {
        super("fireStation not found with address = " + address);
    }
}
