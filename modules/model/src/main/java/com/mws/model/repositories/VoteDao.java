package com.mws.model.repositories;

import com.mws.model.BaseDao;
import com.mws.model.Vote;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**O
 * Created by ranfi on 3/7/16.
 */
public interface VoteDao extends BaseDao<Vote, Integer> {


    @Query("from Vote")
    public List<Vote> findVotes();


    @Query("from Vote where seatId = ?1")
    public Vote findVoteBySeatId(int seatId);

}
