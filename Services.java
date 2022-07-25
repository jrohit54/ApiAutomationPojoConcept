package services;

import com.google.gson.Gson;


import com.example.types.Appointment;
import com.example.types.Session;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;


public class Services extends BaseClass {


    public Response getAppointments(String districtId, String date) {

        try {
            setup();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
            //logger to be added , that says execption in loading the file
        }


        RequestSpecification spec = RestAssured.given();
        Response response = spec.baseUri(prop.get("host").toString()).basePath(prop.get("path").toString())
                                .queryParam("district_id", districtId)
                                .queryParam("date", date)
                                .header("Accept-Language", "hi_IN")
                                .get();


        return response;
    }

    public static void main(String[] args) {


        Services services = new Services();
        Response response = services.getAppointments("265", "23-07-2022");

        Gson gson = new Gson();

        Appointment example = gson.fromJson(response.asString(), Appointment.class);

        System.out.println(response.asString());
        List<Session> sessions = example.getSessions();
        int numberOfHospitals = 0;
        int numberOfSlotsTotal=0;
        System.out.println("total size is "+sessions.size());
        for(Session session:sessions)
        {
            if(session.getFeeType()!=null &&session.getVaccine()!=null)
            if(session.getFeeType().equals("Free") && session.getVaccine().equals("COVISHIELD"))
            {
                numberOfHospitals++;
                numberOfSlotsTotal+=session.getSlots().size();
                System.out.println("hospital name is   "+session.getName()+ "  and number of slots are  "+session.getSlots().size());
            }
        }
        System.out.println("Total hospitals are "+numberOfHospitals);
        System.out.println("number of slots are "+numberOfSlotsTotal);


    }









}