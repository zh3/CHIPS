package com.chips.datarecord;

public class DataRecord {
    public DataRecord(String newId) {
        id = Integer.parseInt(newId.trim());
    }
    
    public int getId() {
        return id;
    }
    
    protected int id;
}
