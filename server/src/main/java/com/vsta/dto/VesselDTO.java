package com.vsta.dto;

/**
 * Data Transfer Object Projection helper to support the conversion
 * of result from native query object to a temporary Vessel entity
 * Reference: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections
 */

public interface VesselDTO {

    /**
     * Gets the ID of this VesselDTO object.
     * @return  This VesselDTO's ID.
     */
    String getUniqueId();

    /**
     * Gets the IMO number of this VesselDTO object.
     * @return  This VesselDTO's IMO number.
     */
    String getImoN();

    /**
     * Gets the Full vessel name of this VesselDTO object.
     * @return  This VesselDTO's Full vessel name.
     */
    String getFullVslM();

    /**
     * Gets the Abbreviated vessel name of this VesselDTO object.
     * @return  This VesselDTO's Abbreviated vessel name.
     */
    String getAbbrVslM();

    /**
     * Gets the Full Inwards voyage number of this VesselDTO object.
     * @return  This VesselDTO's Full Inwards voyage number.
     */
    String getFullInVoyN();

    /**
     * Gets the Inwards voyage number of this VesselDTO object.
     * @return  This VesselDTO's Inwards voyage number.
     */
    String getInVoyN();

    /**
     * Gets the Outwards voyage number of this VesselDTO object.
     * @return  This VesselDTO's Outwards voyage number.
     */
    String getOutVoyN();

    /**
     * Gets the Full Outwards voyage number of this VesselDTO object.
     * @return  This VesselDTO's Full Outwards voyage number.
     */
    String getFullOutVoyN();

    /**
     * Gets the Shift sequence number of this VesselDTO object.
     * @return  This VesselDTO's Shift sequence number.
     */
    String getShiftSeqN();

    /**
     * Gets the Berthing date of this VesselDTO object.
     * @return  This VesselDTO's Berthing date.
     *          Date format in ISO8601 - $YYYY-MM-DDThh:mm.
     */
    String getBthgDt();

    /**
     * Gets the Unberthing date of this VesselDTO object.
     * @return  This VesselDTO's Unberthing date.
     *          Date format in ISO8601 - $YYYY-MM-DDThh:mm.
     */
    String getUnbthgDt();

    /**
     * Gets the Berth number assigned of this VesselDTO object.
     * @return  This VesselDTO's Berth number assigned.
     */
    String getBerthN();

    /**
     * Gets the Status of this VesselDTO object.
     * @return  This VesselDTO's Status.
     *          <p>
     *          The possible values are:
     *          <p>
     *          - BERTHING which means voyage is on the way to the berth
     *          - ALONGSIDE which means voyage is at the berth
     *          - UNBERTHED which means voyage left the berth
     */
    String getStatus();

    /**
     * Gets the Abbr Terminal Name of this VesselDTO object.
     * @return  This VesselDTO's Abbr Terminal Name.
     */
    String getAbbrTerminalM();

    /**
     * Gets the Last Berthing date of this VesselDTO object.
     * @return  This VesselDTO's Last Berthing date.
     */
    String getLast_bthgDt();

    /**
     * Gets the Last Unberthing date of this VesselDTO object.
     * @return  This VesselDTO's Last Unberthing date.
     */
    String getLast_unbthgDt();

    /**
     * Gets the Number of time the Berthing date of this VesselDTO object changes.
     * @return  This VesselDTO's Last Berthing date.
     */
    int getBthgDt_change_count();

    /**
     * Gets the Number of time the Unberthing date of this VesselDTO object changes.
     * @return  This VesselDTO's Last Unberthing date.
     */
    int getUnbthgDt_change_count();

    /**
     * Gets the First Berthing date of this VesselDTO object retrieved.
     * @return  This VesselDTO's First Berthing date.
     */
    String getFirst_arrival();

}
