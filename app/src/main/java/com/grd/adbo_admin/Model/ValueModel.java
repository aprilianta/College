package com.grd.adbo_admin.Model;

import java.util.List;

public class ValueModel {
    String value;
    String message;
    List<ResultMahasiswaModel> result;

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public List<ResultMahasiswaModel> getResult() {
        return result;
    }
}
