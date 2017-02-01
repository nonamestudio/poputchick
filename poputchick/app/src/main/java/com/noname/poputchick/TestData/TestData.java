package com.noname.poputchick.TestData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestData {

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phone")
    @Expose
    private String phone;

//    public TestData (String id, String name, String email, String phone){
//        this.id = id;
//        this.name = name;
//        this.email = email;
//        this.phone = phone;
//    }

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name Users name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Users email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email Users email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return Users phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone Users phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
