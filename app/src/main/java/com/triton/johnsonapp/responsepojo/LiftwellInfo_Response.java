package com.triton.johnsonapp.responsepojo;

public class LiftwellInfo_Response {

    private String Status;
    private String Message;
    private DataBean Data;
    private int Code;

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

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean data) {
        Data = data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public class DataBean {

        private String BRCODE;
        private String JOBNO;
        private String CUSNAME;
        private String ECNO;
        private String CUST_ADDRESS;
        private String INS_ADDRESS;
        private String NOUNITS;
        private String DMODEL;
        private String LOAD_SPEED;
        private String SPEED;
        private String FLOORS;
        private String NOCARENT;
        private String LIFTWELL;
        private String LAND_ENTRANCE;
        private String CLR_OPEN;
        private String FRAME_SIZE;
        private String CARTYPE;
        private String CARENTRANCE;
        private String FLRDISPCHAR;

        public String getBRCODE() {
            return BRCODE;
        }

        public void setBRCODE(String BRCODE) {
            this.BRCODE = BRCODE;
        }

        public String getJOBNO() {
            return JOBNO;
        }

        public void setJOBNO(String JOBNO) {
            this.JOBNO = JOBNO;
        }

        public String getCUSNAME() {
            return CUSNAME;
        }

        public void setCUSNAME(String CUSNAME) {
            this.CUSNAME = CUSNAME;
        }

        public String getECNO() {
            return ECNO;
        }

        public void setECNO(String ECNO) {
            this.ECNO = ECNO;
        }

        public String getCUST_ADDRESS() {
            return CUST_ADDRESS;
        }

        public void setCUST_ADDRESS(String CUST_ADDRESS) {
            this.CUST_ADDRESS = CUST_ADDRESS;
        }

        public String getINS_ADDRESS() {
            return INS_ADDRESS;
        }

        public void setINS_ADDRESS(String INS_ADDRESS) {
            this.INS_ADDRESS = INS_ADDRESS;
        }

        public String getNOUNITS() {
            return NOUNITS;
        }

        public void setNOUNITS(String NOUNITS) {
            this.NOUNITS = NOUNITS;
        }

        public String getDMODEL() {
            return DMODEL;
        }

        public void setDMODEL(String DMODEL) {
            this.DMODEL = DMODEL;
        }

        public String getLOAD_SPEED() {
            return LOAD_SPEED;
        }

        public void setLOAD_SPEED(String LOAD_SPEED) {
            this.LOAD_SPEED = LOAD_SPEED;
        }

        public String getSPEED() {
            return SPEED;
        }

        public void setSPEED(String SPEED) {
            this.SPEED = SPEED;
        }

        public String getFLOORS() {
            return FLOORS;
        }

        public void setFLOORS(String FLOORS) {
            this.FLOORS = FLOORS;
        }

        public String getNOCARENT() {
            return NOCARENT;
        }

        public void setNOCARENT(String NOCARENT) {
            this.NOCARENT = NOCARENT;
        }

        public String getLIFTWELL() {
            return LIFTWELL;
        }

        public void setLIFTWELL(String LIFTWELL) {
            this.LIFTWELL = LIFTWELL;
        }

        public String getLAND_ENTRANCE() {
            return LAND_ENTRANCE;
        }

        public void setLAND_ENTRANCE(String LAND_ENTRANCE) {
            this.LAND_ENTRANCE = LAND_ENTRANCE;
        }

        public String getCLR_OPEN() {
            return CLR_OPEN;
        }

        public void setCLR_OPEN(String CLR_OPEN) {
            this.CLR_OPEN = CLR_OPEN;
        }

        public String getFRAME_SIZE() {
            return FRAME_SIZE;
        }

        public void setFRAME_SIZE(String FRAME_SIZE) {
            this.FRAME_SIZE = FRAME_SIZE;
        }

        public String getCARTYPE() {
            return CARTYPE;
        }

        public void setCARTYPE(String CARTYPE) {
            this.CARTYPE = CARTYPE;
        }

        public String getCARENTRANCE() {
            return CARENTRANCE;
        }

        public void setCARENTRANCE(String CARENTRANCE) {
            this.CARENTRANCE = CARENTRANCE;
        }

        public String getFLRDISPCHAR() {
            return FLRDISPCHAR;
        }

        public void setFLRDISPCHAR(String FLRDISPCHAR) {
            this.FLRDISPCHAR = FLRDISPCHAR;
        }
    }
}
