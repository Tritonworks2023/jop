package com.triton.johnsonapp.responsepojo;

import java.util.List;

public class WorksheetEmpactivityResponse {


    /**
     * Status : Success
     * Message : Job_no_managment List
     * Data : [{"_id":"61c1e5e09934282617679543","activedetail__id":"61c1e43497057923644090bd","job_detail_no":"JOB_01","job_detail_created_at":"23-10-2021 11:00 AM","job_detail_update_at":"23-10-2021 11:00 AM","job_detail_created_by":"Admin","job_detail_updated_by":"Admin","update_reason":"","__v":0},{"_id":"61c1e5e79934282617679545","activedetail__id":"61c1e43497057923644090bd","job_detail_no":"JOB_02","job_detail_created_at":"23-10-2021 11:00 AM","job_detail_update_at":"23-10-2021 11:00 AM","job_detail_created_by":"Admin","job_detail_updated_by":"Admin","update_reason":"","__v":0},{"_id":"61c1e5ea9934282617679547","activedetail__id":"61c1e43497057923644090bd","job_detail_no":"JOB_03","job_detail_created_at":"23-10-2021 11:00 AM","job_detail_update_at":"23-10-2021 11:00 AM","job_detail_created_by":"Admin","job_detail_updated_by":"Admin","update_reason":"","__v":0}]
     * Code : 200
     */

    private String Status;
    private String Message;
    private int Code;

    private List<DataBean> Data;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {

        private String act_name;
        private String desc;

        public String getAct_name() {
            return act_name;
        }

        public void setAct_name(String act_name) {
            this.act_name = act_name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

    }
}
