
package com.vozye.sms.dogbreeds.Models;

import java.util.List;
public class BreedsListModel {

    private String status;
    private List<String> message = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

}
