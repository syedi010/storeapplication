package com.store.storeapplication.DTO;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class fetchIdenticals<T> {
    
    private boolean success;
    private String message;
    private int statusCode;
    private Set<String> data;
    private int totalCount;

   
}
