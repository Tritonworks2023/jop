package com.triton.johnsonapp.requestpojo;

public class QRCodeListRequest {

    private String JOBNO;
    private Integer MATL_ID;

    public String getJOBNO() {
        return JOBNO;
    }

    public void setJOBNO(String JOBNO) {
        this.JOBNO = JOBNO;
    }

    public Integer getMATL_ID() {
        return MATL_ID;
    }

    public void setMATL_ID(Integer MATL_ID) {
        this.MATL_ID = MATL_ID;
    }
}
