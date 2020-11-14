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
     * Custom method to find favourites by the user's ID
     * @param userId Auto-generated ID of the user
     * @return Favourite array (empty list if no matching Favourite found)
     */
    List<Favourite> findByUserId(int userId);

    /**
     * Custom method to delete favourites by the user's ID and voyage's ID
     * @param userId Auto-generated ID of the user
     * @param voyageId Unique ID of the voyage
     */
    @Transactional
    void deleteByUserIdAndVoyageId(int userId, String voyageId);

    /**
     * Custom method to find if Favourite is in database
     * @param userId Auto-generated ID of the user
     * @param voyageId Unique ID of the voyage
     * @return list of favourite objects
     */
    @Query("select f from Favourite f where f.userId = :userId and f.voyageId = :voyageId")
    List<Favourite> findFavouriteByUserIdAndVoyageId(@Param("userId") Integer userId, @Param("voyageId") String voyageId);
}
