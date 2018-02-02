package com.yala.sushant.ehr_backend.model;

/**
 * Created by sushant on 2/2/18.
 */

public class Presciption {

    String patientComplain,physicalExamination,recommendation,doctorId;

    long time;
    public Presciption(){}

    public String getPatientComplain() {
        return patientComplain;
    }

    public void setPatientComplain(String patientComplain) {
        this.patientComplain = patientComplain;
    }

    public String getPhysicalExamination() {
        return physicalExamination;
    }

    public void setPhysicalExamination(String physicalExamination) {
        this.physicalExamination = physicalExamination;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Presciption(String patientComplain, String physicalExamination, String recommendation, String doctorId, long time) {

        this.patientComplain = patientComplain;
        this.physicalExamination = physicalExamination;
        this.recommendation = recommendation;
        this.doctorId = doctorId;
        this.time = time;
    }
}
