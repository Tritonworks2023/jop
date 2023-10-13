package com.triton.johnsonapp.responsepojo;

import java.util.List;

public class QRCodeListResponse {

    private String Status;
    private String Message;
    private List<Datum> Data = null;
    private Integer Code;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<Datum> getData() {
        return Data;
    }

    public void setData(List<Datum> data) {
        Data = data;
    }

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        Code = code;
    }

    public class Datum {

        private String JOBNO;
        private Integer MATL_ID;
        private String QRCODE;

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

        public String getQRCODE() {
            return QRCODE;
        }

        public void setQRCODE(String QRCODE) {
            this.QRCODE = QRCODE;
        }
    }
}
