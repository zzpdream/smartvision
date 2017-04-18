package com.mws.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ranfi on 3/7/16.
 */

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable(true)
@Table(name = "vote")
@Entity
public class Vote extends IdEntity<Integer> {

	private static final long serialVersionUID = -6731680480406949491L;

	private Integer seatId;

	private Integer type; // 表决类型： 1 单向表决， 2 多项表决

	private List<VoteResult> votes;

	public Integer getSeatId() {
		return seatId;
	}

	public void setSeatId(Integer seatId) {
		this.seatId = seatId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 获取子菜单
	 *
	 * @return 子菜单
	 */
	@OneToMany(mappedBy = "vote", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	public List<VoteResult> getVotes() {
		return votes;
	}

	public void setVotes(List<VoteResult> votes) {
		this.votes = votes;
	}

}
