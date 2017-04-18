package com.mws.model.repositories;

import com.mws.model.BaseDao;
import com.mws.model.Terminal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
 * Created by ranfi on 3/13/16.
 */
public interface TerminalDao extends BaseDao<Terminal, Integer> {


    @Query("from Terminal where seatId = ?1 and terminalType=?2")
    Terminal findTerminalBySeatIdAndType(int seatId, int terminalType);

    @Query("from Terminal where ip=?1")
    Terminal findTerminalByIp(String ip);

    @Query("from Terminal where ip=?1")
    List<Terminal> findTerminalListByIp(String ip);

    @Query("select ip from Terminal where seatId=?1")
    Set<String> findTermialIpBySeatId(int seatId);

    @Query("select count(a) from Terminal a")
    Long getTotalTerminals();

    @Query("select count(a) from Terminal a where connectStatus= ?1")
    Long getTerminalsByConnectStatus(int connectStatus);

    @Query("select a from Terminal a ")
    Page<Terminal> findTerminalAsPage(Specification<Terminal> spec, Pageable pageable);
}
