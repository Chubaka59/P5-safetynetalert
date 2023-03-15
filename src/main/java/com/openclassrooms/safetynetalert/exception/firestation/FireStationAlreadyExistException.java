package com.openclassrooms.safetynetalert.exception.firestation;

public class FireStationAlreadyExistException extends RuntimeException {
    public FireStationAlreadyExistException(String address){
        super("This address = " + address + " is already covered by a fireStation");
    }
}
