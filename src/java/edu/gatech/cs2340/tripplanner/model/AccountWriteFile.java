package java.edu.gatech.cs2340.tripplanner.model;

import java.io.PrintWriter;

/**
 * Created by Charlie on 6/5/2014.
 */
public class AccountWriteFile {
    private int latest;
    private Integer fileNumber = (latest + 1);
    private String fileName = fileNumber.toString();
    PrintWriter writer = new PrintWriter(fileName, "UTF-8");

    //todo: a lot
}
