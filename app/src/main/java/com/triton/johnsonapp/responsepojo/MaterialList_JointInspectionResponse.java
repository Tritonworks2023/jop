package com.triton.johnsonapp.responsepojo;

import java.util.List;

public class MaterialList_JointInspectionResponse {

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
        private Integer CNT;
        private Integer SCANNED_COUNT;
        private String MATL_DESC;

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

        public Integer getCNT() {
            return CNT;
        }

        public void setCNT(Integer CNT) {
            this.CNT = CNT;
        }

        public Integer getSCANNED_COUNT() {
            return SCANNED_COUNT;
        }

        public void setSCANNED_COUNT(Integer SCANNED_COUNT) {
            this.SCANNED_COUNT = SCANNED_COUNT;
        }

        public String getMATL_DESC() {
            return MATL_DESC;
        }

        public void setMATL_DESC(String MATL_DESC) {
            this.MATL_DESC = MATL_DESC;
        }
    }
}
