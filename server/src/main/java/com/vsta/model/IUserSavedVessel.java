package com.vsta.model;

/**
 * Represents a vessel saved by a user
 */

public interface IUserSavedVessel {

    /**
     * Gets the ID of the a UserSavedVessel.
     * @return  This UserSavedVessel's ID.
     */
    int getId();

    /**
     * Overwrites the ID of this UserSavedVessel.
     * @param id  This UserSavedVessel's ID.
     */
    void setId(int id);

    /**
     * Gets the ID of the User.
     * @return ID of User who saved the voyage.
     */
    int getUserId();

    /**
     * Overwrites the ID of the User.
     * @param userId ID of User who saved the voyage.
     */
    void setUserId(int userId);

    /**
     * Gets the ID of the Voyage.
     * @return  ID of Voyage that User saved.
     */
    String getVoyageId();

}
