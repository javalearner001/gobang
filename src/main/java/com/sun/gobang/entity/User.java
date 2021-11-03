package com.sun.gobang.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = -1195355065989100970L;

    private int userId;

    private String username;

    private String userAddress;

    private long flagBin;

    private Boolean isFromCopy;

}
