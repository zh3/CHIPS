package com.chips.datarecord;

import java.io.Serializable;

public class DataRecord implements Serializable {
    private static final long serialVersionUID = 2173664914586334123L;

    public DataRecord(String newId) {
        id = Integer.parseInt(newId.trim());
    }
    
    public int getId() {
        return id;
    }
    
    protected int id;
}
