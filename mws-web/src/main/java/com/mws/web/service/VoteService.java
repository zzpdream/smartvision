package com.mws.web.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mws.model.Vote;
import com.mws.model.repositories.VoteDao;

/**
 * Created by ranfi on 3/7/16.
 */

@Service
public class VoteService {

	@Resource
	private VoteDao voteDao;

	@Transactional
	public void saveVote(Vote vote) {
		voteDao.save(vote);

	}

	@Transactional(readOnly = true)
	public List<Vote> findAllVotes() {
		return voteDao.findVotes();
	}

	@Transactional(readOnly = true)
	public Vote findVoteBySeatId(int seatId) {
		return voteDao.findVoteBySeatId(seatId);
	}

	@Transactional
	public void deleteVotes() {
		voteDao.deleteAll();
	}

}
