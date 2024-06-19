package dk.dtu.compute.se.pisd.roborally.client.Data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Register {

    @JsonProperty("register_id")
    private int registerId;
    @JsonProperty("register_name")
    private String registerName;

    @JsonProperty("register_no")
    private String registerNo;

    public int getRegisterId() {
        return registerId;
    }

    public void setRegisterId(int registerId) {
        this.registerId = registerId;
    }

    public String getRegisterName() {
        return registerName;
    }

    public void setRegisterName(String registerName) {
        this.registerName = registerName;
    }

    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }
}
