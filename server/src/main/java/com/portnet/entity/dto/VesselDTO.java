package com.portnet.entity.dto;

/**
 * An domain model of a Vessel that represents the joined database table
 * 
 */

public class VesselDTO {
    
    private String uniqueId;
    private String imoN;
    private String fullVslM;
    private String abbrVslM;
    private String fullInVoyN;
    private String inVoyN;
    private String outVoyN;
    private String fullOutVoyN;
    private String shiftSeqN;
    private String bthgDt;
    private String unbthgDt;
    private String berthN;
    private String status;
    private String abbrTerminalM;
    private String last_bthgDt;
    private String last_unbthgDt;
    private int bthgDt_change_count;
    private int unbthgDt_change_count;
    private String first_arrival;

    //auto generated
    public VesselDTO(String uniqueId, String imoN, String fullVslM, String abbrVslM, String fullInVoyN, String inVoyN,
            String outVoyN, String fullOutVoyN, String shiftSeqN, String bthgDt, String unbthgDt, String berthN,
            String status, String abbrTerminalM, String last_bthgDt, String last_unbthgDt, int bthgDt_change_count,
            int unbthgDt_change_count, String first_arrival) {
        this.uniqueId = uniqueId;
        this.imoN = imoN;
        this.fullVslM = fullVslM;
        this.abbrVslM = abbrVslM;
        this.fullInVoyN = fullInVoyN;
        this.inVoyN = inVoyN;
        this.outVoyN = outVoyN;
        this.fullOutVoyN = fullOutVoyN;
        this.shiftSeqN = shiftSeqN;
        this.bthgDt = bthgDt;
        this.unbthgDt = unbthgDt;
        this.berthN = berthN;
        this.status = status;
        this.abbrTerminalM = abbrTerminalM;
        this.last_bthgDt = last_bthgDt;
        this.last_unbthgDt = last_unbthgDt;
        this.bthgDt_change_count = bthgDt_change_count;
        this.unbthgDt_change_count = unbthgDt_change_count;
        this.first_arrival = first_arrival;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getImoN() {
        return imoN;
    }

    public String getFullVslM() {
        return fullVslM;
    }

    public String getAbbrVslM() {
        return abbrVslM;
    }

    public String getFullInVoyN() {
        return fullInVoyN;
    }

    public String getInVoyN() {
        return inVoyN;
    }

    public String getOutVoyN() {
        return outVoyN;
    }

    public String getFullOutVoyN() {
        return fullOutVoyN;
    }

    public String getShiftSeqN() {
        return shiftSeqN;
    }

    public String getBthgDt() {
        return bthgDt;
    }

    public String getUnbthgDt() {
        return unbthgDt;
    }

    public String getBerthN() {
        return berthN;
    }

    public String getStatus() {
        return status;
    }

    public String getAbbrTerminalM() {
        return abbrTerminalM;
    }

    public String getLast_bthgDt() {
        return last_bthgDt;
    }

    public String getLast_unbthgDt() {
        return last_unbthgDt;
    }

    public int getBthgDt_change_count() {
        return bthgDt_change_count;
    }

    public int getUnbthgDt_change_count() {
        return unbthgDt_change_count;
    }

    public String getFirst_arrival() {
        return first_arrival;
    }

}
