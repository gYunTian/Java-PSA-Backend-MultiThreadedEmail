package com.vsta.dao;

import com.vsta.model.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Data Access Objects for "favourite" table.
 * Used to perform various operations on the database
 * including retrieval and modification.
 */

@Repository
public interface FavouriteDAO extends JpaRepository<Favourite, Integer> {
    /**
     * Custom method to find favourites by the user's ID.
     * @param userId ID to uniquely identify a User.
     * @return Favourite array (empty list if no matching Favourite found)
     */
    List<Favourite> findByUserId(int userId);

    /**
     * Custom method to delete favourites by the user's ID and voyage's ID.
     * @param userId ID to uniquely identify a User.
     * @param voyageId ID to uniquely identify a Voyage.
     */
    @Transactional
    void deleteByUserIdAndVoyageId(int userId, String voyageId);

    /**
     * Custom method to find if Favourite is in database.
     * @param userId ID to uniquely identify a User.
     * @param voyageId ID to uniquely identify a Voyage.
     * @return list of Favourite objects of indicated userId and voyageId.
     */
    @Query("select f from Favourite f where f.userId = :userId and f.voyageId = :voyageId")
    List<Favourite> findFavouriteByUserIdAndVoyageId(@Param("userId") Integer userId, @Param("voyageId") String voyageId);
}
