package com.vsta.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Voyage on the real-time
 * information about vessel berthing times,
 * retrieved from the retrieveByBerthingDate
 * API provided by PORTNET.
 */

@Entity
@Table(name = "vessel")
public class Vessel {

    /**
     * The ID to uniquely identify a Voyage object
     * uniqueId is from fullInVoyN + inVoyN
     */
    @JsonProperty("uniqueId") @Id
    @Column(nullable = false, name = "uniqueId")
    private String uniqueId;

    /**
     * The IMO number of a Voyage object.
     * This is part of the response retrieved
     * from retrieveByBerthingDate API.
     */
    @JsonProperty("imoN")
    @Column(nullable = true, name = "imoN")
    private String imoN;

    /**
     * The Full vessel name of a Voyage object.
     * This is part of the response retrieved
     * from retrieveByBerthingDate API.
     */
    @JsonProperty("fullVslM")
    @Column(nullable = false, name = "fullVslM")
    private String fullVslM;

    /**
     * The Abbreviated vessel name of a Voyage object.
     * This is part of the response retrieved
     * from retrieveByBerthingDate API.
     */
    @JsonProperty("abbrVslM")
    @Column(nullable = false, name = "abbrVslM")
    private String abbrVslM;

    /**
     * The Full Inwards voyage number of a Voyage object.
     * This is part of the response retrieved
     * from retrieveByBerthingDate API.
     */
    @JsonProperty("fullInVoyN")
    @Column(nullable = true, name = "fullInVoyN")
    private String fullInVoyN;

    /**
     * The Inwards voyage number of a Voyage object.
     * This is part of the response retrieved
     * from retrieveByBerthingDate API.
     */
    @JsonProperty("inVoyN")
    @Column(nullable = false, name = "inVoyN")
    private String inVoyN;

    /**
     * The Outwards voyage number of a Voyage object.
     * This is part of the response retrieved
     * from retrieveByBerthingDate API.
     */
    @JsonProperty("outVoyN")
    @Column(nullable = false, name = "outVoyN")
    private String outVoyN;

    /**
     * The Full Outwards voyage number of a Voyage object.
     * This is part of the response retrieved
     * from retrieveByBerthingDate API.
     */
    @JsonProperty("fullOutVoyN")
    @Column(nullable = true, name = "fullOutVoyN")
    private String fullOutVoyN;

    /**
     * The Shift sequence number of a Voyage object.
     * This is part of the response retrieved
     * from retrieveByBerthingDate API.
     */
    @JsonProperty("shiftSeqN")
    @Column(nullable = false, name = "shiftSeqN")
    private String shiftSeqN;

    /**
     * The Berthing date of a Voyage object.
     * This is part of the response retrieved
     * from retrieveByBerthingDate API.
     */
    @JsonProperty("bthgDt")
    @Column(nullable = false, name = "bthgDt")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private String bthgDt;

    /**
     * The Unberthing date of a Voyage object.
     * This is part of the response retrieved
     * from retrieveByBerthingDate API.
     */
    @JsonProperty("unbthgDt")
    @Column(nullable = false, name = "unbthgDt")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private String unbthgDt;

    /**
     * The Berth number of a Voyage object.
     * This is part of the response retrieved
     * from retrieveByBerthingDate API.
     */
    @JsonProperty("berthN")
    @Column(nullable = true, name = "berthN")
    private String berthN;

    /**
     * The Status of a Voyage object.
     * This is part of the response retrieved
     * from retrieveByBerthingDate API.
     */
    @JsonProperty("status")
    @Column(nullable = false, name = "status")
    private String status;

    /**
     * The Abbr Terminal Name of a Voyage object.
     * This is part of the response retrieved
     * from retrieveByBerthingDate API.
     */
    @JsonProperty("abbrTerminalM")
    @Column(nullable = true, name = "abbrTerminalM")
    private String abbrTerminalM;

    /**
     * Gets the ID of this Voyage object.
     * @return  This Voyage's ID.
     */
    public String getUniqueId() {
        return uniqueId;
    }

    /**
     * Overwrites the ID of this Voyage object.
     * @param uniqueId  This Voyage's ID.
     */
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    /**
     * Gets the IMO number of this Voyage object.
     * @return  This Voyage's IMO number.
     */
    public String getImoN() {
        return imoN;
    }

    /**
     * Overwrites the IMO number of this Voyage object.
     * @param imoN This Voyage's IMO number.
     */
    public void setImoN(String imoN) {
        this.imoN = imoN;
    }

    /**
     * Gets the Full Voyage name of this Voyage object.
     * @return  This Voyage's Full Voyage name.
     */
    public String getfullVslM() {
        return fullVslM;
    }

    /**
     * Overwrites the Full Voyage name of this Voyage object.
     * @param fullVslM This Voyage's Full Voyage name.
     */
    public void setFullVs1M(String fullVslM) {
        this.fullVslM = fullVslM;
    }

    /**
     * Gets the Abbreviated Voyage name of this Voyage object.
     * @return  This Voyage's Abbreviated Voyage name.
     */
    public String getAbbrVslM() {
        return abbrVslM;
    }

    /**
     * Overwrites the Abbreviated Voyage name of this Voyage object.
     * @param abbrVslM  This Voyage's Abbreviated Voyage name.
     */
    public void setAbbrVslM(String abbrVslM) {
        this.abbrVslM = abbrVslM;
    }

    /**
     * Gets the Full Inwards Voyage number of this Voyage object.
     * @return  This Voyage's Full Inwards Voyage number.
     */
    public String getFullInVoyN() {
        return fullInVoyN;
    }

    /**
     * Overwrites the Full Inwards Voyage number of this Voyage object.
     * @param fullInVoyN  This Voyage's Full Inwards Voyage number.
     */
    public void setFullInVoyN(String fullInVoyN) {
        this.fullInVoyN = fullInVoyN;
    }

    /**
     * Gets the Inwards Voyage number of this Voyage object.
     * @return  This Voyage's Inwards Voyage number.
     */
    public String getInVoyN() {
        return inVoyN;
    }

    /**
     * Overwrites the Inwards Voyage number of this Voyage object.
     * @param inVoyN  This Voyage's Inwards Voyage number.
     */
    public void setInVoyN(String inVoyN) {
        this.inVoyN = inVoyN;
    }

    /**
     * Gets the Outwards Voyage number of this Voyage object.
     * @return  This Voyage's Outwards Voyage number.
     */
    public String getOutVoyN() {
        return outVoyN;
    }

    /**
     * Overwrites the Outwards Voyage number of this Voyage object.
     * @param outVoyN  This Voyage's Outwards Voyage number.
     */
    public void setOutVoyN(String outVoyN) {
        this.outVoyN = outVoyN;
    }

    /**
     * Gets the Full Outwards Voyage number of this Voyage object.
     * @return  This Voyage's Full Outwards Voyage number.
     */
    public String getFullOutVoyN() {
        return fullOutVoyN;
    }

    /**
     * Gets the Full Outwards Voyage number of this Voyage object.
     * @param fullOutVoyN  This Voyage's Full Outwards Voyage number.
     */
    public void setFullOutVoyN(String fullOutVoyN) {
        this.fullOutVoyN = fullOutVoyN;
    }

    /**
     * Gets the Shift sequence number of this Voyage object.
     * @return  This Voyage's Shift sequence number.
     */
    public String getShiftSeqN() {
        return shiftSeqN;
    }

    /**
     * Overwrites the Shift sequence number of this Voyage object.
     * @param shiftSeqN  This Voyage's Shift sequence number.
     */
    public void setShiftSeqN(String shiftSeqN) {
        this.shiftSeqN = shiftSeqN;
    }

    /**
     * Gets the Berthing date of this Voyage object.
     * @return  This Voyage's Berthing date.
     *          Date format in ISO8601 - $YYYY-MM-DDThh:mm.
     */
    public String getBthgDt() {
        return bthgDt;
    }

    /**
     * Overwrites the Berthing date of this Voyage object.
     * @param bthgDt  This Voyage's Berthing date.
     *                Date format in ISO8601 - $YYYY-MM-DDThh:mm.
     */
    public void setBthgDt(String bthgDt) {
        this.bthgDt = bthgDt;
    }

    /**
     * Gets the Unberthing date of this Voyage object.
     * @return  This Voyage's Unberthing date.
     *          Date format in ISO8601 - $YYYY-MM-DDThh:mm.
     */
    public String getUnbthgDt() {
        return unbthgDt;
    }

    /**
     * Overwrites the Unberthing date of this Voyage object.
     * @param unbthgDt  This Voyage's Unberthing date.
     *                  Date format in ISO8601 - $YYYY-MM-DDThh:mm.
     */
    public void setUnbthgDt(String unbthgDt) {
        this.unbthgDt = unbthgDt;
    }

    /**
     * Gets the Berth number assigned of this Voyage object.
     * @return  This Voyage's Berth number assigned.
     */
    public String getBerthN() {
        return berthN;
    }

    /**
     * Overwrites the Berth number assigned of this Voyage object.
     * @param berthN  This Voyage's Berth number assigned.
     */
    public void setBerthN(String berthN) {
        this.berthN = berthN;
    }

    /**
     * Gets the Status of this Voyage object.
     * @return  This Voyage's Status.
     *          <p>
     *          The possible values are:
     *          <br>
     *          - BERTHING which means Voyage is on the way to the berth<br>
     *          - ALONGSIDE which means Voyage is at the berth<br>
     *          - UNBERTHED which means Voyage left the berth
     */
    public String getStatus() {
        return status;
    }

    /**
     * Overwrites the Status of this Voyage object.
     * @param status  This Voyage's Status.
     *                <p>
     *                The possible values are:
     *                <br>
     *                - BERTHING which means Voyage is on the way to the berth<br>
     *                - ALONGSIDE which means Voyage is at the berth<br>
     *                - UNBERTHED which means Voyage left the berth
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the Abbr Terminal Name of this Voyage object.
     * @return  This Voyage's Abbr Terminal Name.
     */
    public String getAbbrTerminalM() {
        return abbrTerminalM;
    }

    /**
     * Overwrites the Abbr Terminal Name of this Voyage object.
     * @param abbrTerminalM  This Voyage's Abbr Terminal Name.
     */
    public void setAbbrTerminalM(String abbrTerminalM) {
        this.abbrTerminalM = abbrTerminalM;
    }

    /**
     * Override toString method to encapsulate
     * all elements in string representation.
     * @return string representation of Voyage object
     */
    @Override
    public String toString() {
        return "Vessel [abbrTerminalM=" + abbrTerminalM + ", abbrVslM=" + abbrVslM + ", berthN=" + berthN + ", bthgDt="
                + bthgDt + ", fullInVoyN=" + fullInVoyN + ", fullOutVoyN=" + fullOutVoyN + ", fullVslM=" + fullVslM
                + ", imoN=" + imoN + ", inVoyN=" + inVoyN + ", outVoyN=" + outVoyN + ", shiftSeqN=" + shiftSeqN
                + ", status=" + status + ", unbthgDt=" + unbthgDt + ", uniqueId=" + uniqueId + "]";
    }

}
