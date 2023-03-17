package com.openclassrooms.safetynetalert.dto.childAlert;

import lombok.Data;

import java.util.List;

@Data
public class ChildAlertDTO {
    List<MinorPersonDTO> minorPersonDTOList;
    List<MajorPersonDTO> majorPersonDTOList;

    public ChildAlertDTO(List<MinorPersonDTO> minorPersonDTOList, List<MajorPersonDTO> majorPersonDTOList){
        this.minorPersonDTOList = minorPersonDTOList;
        this.majorPersonDTOList = majorPersonDTOList;
    }
}
