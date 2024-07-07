package com.i2i.sms.dto;

import java.util.List;

public class AddStudentToClubResponseDto {
    private List<ClubResponseDto> addedClubs;
    private List<String> noClubsExist;

    public List<ClubResponseDto> getAddedClubs() {
        return addedClubs;
    }

    public void setAddedClubs(List<ClubResponseDto> addedClubs) {
        this.addedClubs = addedClubs;
    }

    public List<String> getNoClubsExist() {
        return noClubsExist;
    }

    public void setNoClubsExist(List<String> noClubsExist) {
        this.noClubsExist = noClubsExist;
    }
}
