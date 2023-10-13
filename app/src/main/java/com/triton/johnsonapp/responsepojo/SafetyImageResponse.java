package com.triton.johnsonapp.responsepojo;

import java.util.List;

public class SafetyImageResponse {
    private String Status;
    private String Message;
    private List<ImageData> Data;
    private int Code;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

    public List<ImageData> getData() {
        return Data;
    }

    public void setData(List<ImageData> data) {
        this.Data = data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        this.Code = code;
    }



    public class ImageData {
        private String imageUrl;
        private String date;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

    }
}
