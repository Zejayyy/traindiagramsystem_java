package com.itsymion.Algorithm.utils;

import com.itsymion.Algorithm.LineMsg;
import com.itsymion.Algorithm.Ratio;
import com.itsymion.Algorithm.Train;
import com.itsymion.Algorithm.Trip;

import java.io.*;
import java.util.ArrayList;

public class CopyUtils
{

    public static ArrayList<Trip> CopyTrip(ArrayList<Trip> upTrips) throws IOException, ClassNotFoundException
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(upTrips);
        oos.flush();
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));

        return (ArrayList<Trip>) ois.readObject();

    }
    public static ArrayList<Train> CopyTrain(ArrayList<Train> trainSet) throws IOException, ClassNotFoundException
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(trainSet);
        oos.flush();
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));

        return (ArrayList<Train>) ois.readObject();

    }



    //单独写函数复制集合
    public static ArrayList<ArrayList<Ratio>> CopyRatio(ArrayList<ArrayList<Ratio>> arrayList1) throws IOException, ClassNotFoundException
    {//1-被复制的集合
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(arrayList1);
        oos.flush();
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));

        return (ArrayList<ArrayList<Ratio>>) ois.readObject();
    }

    public static ArrayList<LineMsg> CopyMsg(ArrayList<LineMsg> arrayList1) throws IOException, ClassNotFoundException
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(arrayList1);
        oos.flush();
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));

        return (ArrayList<LineMsg>) ois.readObject();


    }
}
