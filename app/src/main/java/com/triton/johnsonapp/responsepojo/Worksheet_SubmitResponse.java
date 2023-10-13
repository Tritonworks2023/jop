package com.triton.johnsonapp.responsepojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Worksheet_SubmitResponse {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private List<Datum> data;
    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("hrs_count")
    @Expose
    private Integer hrs_count;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getHrs_count() {
        return hrs_count;
    }

    public void setHrs_count(Integer hrs_count) {
        this.hrs_count = hrs_count;
    }

    public class Datum {

        @SerializedName("JLS_EWD_WKDATE")
        @Expose
        private String JLS_EWD_WKDATE;
        @SerializedName("JLS_EWD_BRCODE")
        @Expose
        private String JLS_EWD_BRCODE;
        @SerializedName("JLS_EWD_JOBNO")
        @Expose
        private String JLS_EWD_JOBNO;
        @SerializedName("JLS_EWD_EMPNO")
        @Expose
        private String JLS_EWD_EMPNO;
        @SerializedName("JLS_EWD_ACTIVITY")
        @Expose
        private String JLS_EWD_ACTIVITY;
        @SerializedName("JLS_EWD_WRKHOUR")
        @Expose
        private String JLS_EWD_WRKHOUR;
        @SerializedName("JLS_EWD_PREPBY")
        @Expose
        private String JLS_EWD_PREPBY;
        @SerializedName("JLS_EWD_PREPDT")
        @Expose
        private String JLS_EWD_PREPDT;
        @SerializedName("JLS_EWD_STATUS")
        @Expose
        private String JLS_EWD_STATUS;
        @SerializedName("JLS_EWD_MODBY")
        @Expose
        private String JLS_EWD_MODBY;
        @SerializedName("JLS_EWD_MODDT")
        @Expose
        private String JLS_EWD_MODDT;
        @SerializedName("JLS_EWD_DISTANCE")
        @Expose
        private String JLS_EWD_DISTANCE;

        public String getJLS_EWD_WKDATE() {
            return JLS_EWD_WKDATE;
        }

        public void setJLS_EWD_WKDATE(String JLS_EWD_WKDATE) {
            this.JLS_EWD_WKDATE = JLS_EWD_WKDATE;
        }

        public String getJLS_EWD_BRCODE() {
            return JLS_EWD_BRCODE;
        }

        public void setJLS_EWD_BRCODE(String JLS_EWD_BRCODE) {
            this.JLS_EWD_BRCODE = JLS_EWD_BRCODE;
        }

        public String getJLS_EWD_JOBNO() {
            return JLS_EWD_JOBNO;
        }

        public void setJLS_EWD_JOBNO(String JLS_EWD_JOBNO) {
            this.JLS_EWD_JOBNO = JLS_EWD_JOBNO;
        }

        public String getJLS_EWD_EMPNO() {
            return JLS_EWD_EMPNO;
        }

        public void setJLS_EWD_EMPNO(String JLS_EWD_EMPNO) {
            this.JLS_EWD_EMPNO = JLS_EWD_EMPNO;
        }

        public String getJLS_EWD_ACTIVITY() {
            return JLS_EWD_ACTIVITY;
        }

        public void setJLS_EWD_ACTIVITY(String JLS_EWD_ACTIVITY) {
            this.JLS_EWD_ACTIVITY = JLS_EWD_ACTIVITY;
        }

        public String getJLS_EWD_WRKHOUR() {
            return JLS_EWD_WRKHOUR;
        }

        public void setJLS_EWD_WRKHOUR(String JLS_EWD_WRKHOUR) {
            this.JLS_EWD_WRKHOUR = JLS_EWD_WRKHOUR;
        }

        public String getJLS_EWD_PREPBY() {
            return JLS_EWD_PREPBY;
        }

        public void setJLS_EWD_PREPBY(String JLS_EWD_PREPBY) {
            this.JLS_EWD_PREPBY = JLS_EWD_PREPBY;
        }

        public String getJLS_EWD_PREPDT() {
            return JLS_EWD_PREPDT;
        }

        public void setJLS_EWD_PREPDT(String JLS_EWD_PREPDT) {
            this.JLS_EWD_PREPDT = JLS_EWD_PREPDT;
        }

        public String getJLS_EWD_STATUS() {
            return JLS_EWD_STATUS;
        }

        public void setJLS_EWD_STATUS(String JLS_EWD_STATUS) {
            this.JLS_EWD_STATUS = JLS_EWD_STATUS;
        }

        public String getJLS_EWD_MODBY() {
            return JLS_EWD_MODBY;
        }

        public void setJLS_EWD_MODBY(String JLS_EWD_MODBY) {
            this.JLS_EWD_MODBY = JLS_EWD_MODBY;
        }

        public String getJLS_EWD_MODDT() {
            return JLS_EWD_MODDT;
        }

        public void setJLS_EWD_MODDT(String JLS_EWD_MODDT) {
            this.JLS_EWD_MODDT = JLS_EWD_MODDT;
        }

        public String getJLS_EWD_DISTANCE() {
            return JLS_EWD_DISTANCE;
        }

        public void setJLS_EWD_DISTANCE(String JLS_EWD_DISTANCE) {
            this.JLS_EWD_DISTANCE = JLS_EWD_DISTANCE;
        }

    }

}
