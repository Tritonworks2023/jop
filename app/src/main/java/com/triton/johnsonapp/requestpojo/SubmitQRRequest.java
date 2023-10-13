package com.triton.johnsonapp.requestpojo;

import java.util.List;

public class SubmitQRRequest {

    private String job_id;
    private String mat_id;
    private List<String> qrcode = null;

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getMat_id() {
        return mat_id;
    }

    public void setMat_id(String mat_id) {
        this.mat_id = mat_id;
    }

    public List<String> getQrcode() {
        return qrcode;
    }

    public void setQrcode(List<String> qrcode) {
        this.qrcode = qrcode;
    }
}
