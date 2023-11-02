package com.itsymion.Algorithm;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineMsg implements Serializable
{
    private int ID;//trip序号
    private int type;//1上行,0下行
    private boolean isEmpty;
    private int onOrOut;//1上线，0-下线

    public LineMsg(int ID,int type,int onOrOut,boolean isEmpty)
    {
        this.ID = ID;
        this.type = type;
        this.onOrOut = onOrOut;
        this.isEmpty = isEmpty;



    }
}
