package com.triton.johnsonapp.requestpojo;

public class AttendanceHelperListRequest {
    String JLS_OA_ENGGCODE;
    String JLS_OA_ATTDATE;
    String phone_number;

    public String getJLS_HA_ENGGCODE() {
        return JLS_OA_ENGGCODE;
    }

    public void setJLS_OA_ENGGCODE(String JLS_OA_ENGGCODE) {
        this.JLS_OA_ENGGCODE = JLS_OA_ENGGCODE;
    }

    public String getJLS_HA_ATTDATE() {
        return JLS_OA_ATTDATE;
    }

    public void setJLS_OA_ATTDATE(String JLS_OA_ATTDATE) {
        this.JLS_OA_ATTDATE = JLS_OA_ATTDATE;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
