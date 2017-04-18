package com.mws.web.net.service;

import com.google.common.collect.Lists;
import com.mws.model.Terminal;
import com.mws.model.Vote;
import com.mws.model.VoteResult;
import com.mws.web.net.bo.Command;
import com.mws.web.net.bo.CommandContent;
import com.mws.web.net.bo.Constant;
import com.mws.web.net.bo.GlobalCache;
import com.mws.web.net.dto.*;
import com.mws.web.net.dto.client.*;
import com.mws.web.net.dto.client.StartVoteClientDto.Items;
import com.mws.web.service.TerminalService;
import com.mws.web.service.VoteService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 控制主机和代表主机通讯成功后, 代表主机需要对终端做业务指令派发
 * <p/>
 * 本service主要是用来桥接主控机和代表终端之间的通讯
 * <p/>
 * <p/>
 * Created by ranfi on 2/23/16.
 */

@Service
public class DispatchService {

    private static Logger logger = LoggerFactory.getLogger(DispatchService.class);

    @Resource
    private SendService sendService;

    @Resource
    private VoteService voteService;

    @Resource
    private TerminalService terminalService;


    /**
     * 清楚终端的座位信息
     *
     * @param seatId
     */
    public void cleanMember(Integer seatId) {
        try {

            Command cmd = Command.CLEAR_SEAT;
            //检测是否有发送权限
            if (!hasPermissionSend(cmd)) {
                return;
            }
//            GlobalCache.addMeetingContents(cmd, seatId);
            ClearSeatClientDto seatClientDto = new ClearSeatClientDto();
            seatClientDto.setSeatId(seatId);
            send(cmd, Constant.commandRequestType, seatId, seatClientDto, true);

        } catch (Exception e) {
            logger.error("下发清除指令出现异常", e);
        }
    }


    /**
     * 开始播放视屏
     *
     * @param playVideoParamDto
     */
    public void startPlayVideo(PlayVideoParamDto playVideoParamDto) {
        try {

            Command cmd = Command.START_PLAY_VIDEO;
            //检测是否有发送权限
            if (!hasPermissionSend(cmd)) {
                return;
            }
            send(cmd, Constant.commandRequestType, playVideoParamDto.getSeatId(), playVideoParamDto, true);

        } catch (Exception e) {
            logger.error("下发开始播放指令出现异常", e);
        }
    }

    /**
     * 停止播放视屏
     *
     * @param param
     */
    public void stopPlayVideo(BaseDto param) {
        try {

            Command cmd = Command.STOP_PLAY_VIDEO;
            //检测是否有发送权限
            if (!hasPermissionSend(cmd)) {
                return;
            }
            send(cmd, Constant.commandRequestType, param.getSeatId(), param, true);

        } catch (Exception e) {
            logger.error("下发停止指令出现异常", e);
        }
    }

    /**
     * 所有终端设置座位信息
     *
     * @param member
     */
    public void sendSeats(MemberDto member) {
        try {
            Command cmd = Command.SET_SEAT;
            //检测是否有发送权限
            if (!hasPermissionSend(cmd)) {
                return;
            }
//            GlobalCache.addMeetingContents(cmd, member);

            List<SeatDto> seats = member.getMembers();
            if (CollectionUtils.isEmpty(seats)) {
                logger.error("主控机下发座位信息指令已接受,数据为空");
                return;
            }

            for (SeatDto seat : seats) {
                Integer seatId = seat.getSeatId();  //座位号
                seat.setRegistered(member.getRegistered());
                seat.setTitle(member.getTitle());
                sendService.send(cmd, Constant.commandRequestType, seatId, seat, true);
            }

        } catch (Exception e) {
            logger.error("下发设置座位指令出现异常", e);
        }
    }


    /**
     * 设置主席位置
     *
     * @param param 主席位置参数
     */
    public void setChairman(ChairmanDto param) {
        try {
            Command cmd = Command.SET_CHAIRMAN;
            //检测是否有发送权限
            if (!hasPermissionSend(cmd)) {
                return;
            }

            GlobalCache.addMeetingContents(cmd, param);

            ChairmanClientDto chairmanClientDto = new ChairmanClientDto();
            chairmanClientDto.setSeatId(param.getSeatId());
            chairmanClientDto.setVideoUrl(param.getVideoUrl());

            sendService.broadcastForSeat(cmd, Constant.commandRequestType, chairmanClientDto);

        } catch (Exception e) {
            logger.error("下发设置座位指令出现异常", e);
        }
    }

    /**
     * 下发会标信息
     *
     * @param logo
     */
    public void showLogo(LogoDto logo) {
        try {
            Command cmd = Command.SHOW_LOGO;
            //检测是否有发送权限
            if (!hasPermissionSend(cmd)) {
                return;
            }

            GlobalCache.addMeetingContents(cmd, logo);

            //为零就发送广播,否则单个座位发送
            Integer seatId = null == logo.getSeatId() ? 0 : logo.getSeatId();

            ShowLogoClientDto showLogoClientDto = new ShowLogoClientDto();
            showLogoClientDto.setTitles(logo.getContents());
            showLogoClientDto.setPicUrl("");

            send(cmd, Constant.commandRequestType, seatId, showLogoClientDto);

        } catch (Exception e) {
            logger.error("下发会标指令出现异常", e);
        }
    }


    /**
     * 下发会议议程
     *
     * @param subject 会议议程
     */
    public void showSubject(SubjectDto subject) {
        try {

            Command cmd = Command.SHOW_SUBJECT;
            //检测是否有发送权限
            if (!hasPermissionSend(cmd)) {
                return;
            }
            //为零就发送广播,否则单个座位发送
            Integer seatId = null == subject.getSeatId() ? 0 : subject.getSeatId();
            GlobalCache.addMeetingContents(cmd, subject);

            ShowSubjectClientDto showSubjectClientDto = new ShowSubjectClientDto();
            showSubjectClientDto.setSubject(subject.getContent());
            showSubjectClientDto.setHorizontal(subject.getHorizontal());
            showSubjectClientDto.setVertical(subject.getVertical());

            sendForSeat(cmd, Constant.commandRequestType, seatId, showSubjectClientDto);

        } catch (Exception e) {
            logger.error("下发会议议程指令出现异常", e);
        }
    }

    /**
     * 开始报名
     *
     * @param register 报名内容
     */
    public void startRegister(Map<String, String> register) {
        try {
            Command cmd = Command.START_REGISTER;
            //检测是否有发送权限
            if (!hasPermissionSend(cmd)) {
                return;
            }
            GlobalCache.addMeetingContents(cmd, register);

            StartRegisterClientDto startRegisterClientDto = new StartRegisterClientDto();
            startRegisterClientDto.setTitle(register.get("title"));
            //仅发送给有表决权的终端
//            sendService.broadcastForVotingRights(cmd, Constant.commandRequestType, startRegisterClientDto);

            sendService.broadcastForSeat(cmd, Constant.commandRequestType, startRegisterClientDto);

        } catch (Exception e) {
            logger.error("下发开始报名指令出现异常", e);
        }
    }

    /**
     * 开始报名
     *
     * @param showMem 报名内容
     */
    public void showMember(Map<String, String> showMem) {
        try {
            Command cmd = Command.SHOW_MEMBER;
            //检测是否有发送权限
            if (!hasPermissionSend(cmd)) {
                return;
            }
            GlobalCache.addMeetingContents(cmd, showMem);
            ShowMemClientDto showMemClientDto = new ShowMemClientDto();
            showMemClientDto.setTitle(showMem.get("title"));
            //仅发送给有表决权的终端
//            sendService.broadcastForVotingRights(cmd, Constant.commandRequestType, startRegisterClientDto);

            sendService.broadcastForSeat(cmd, Constant.commandRequestType, showMemClientDto);

        } catch (Exception e) {
            logger.error("下发开始报名指令出现异常", e);
        }
    }


    /**
     * 显示报名情况, 只针对排了座位的终端
     *
     * @param register 报名内容
     */
    public void showRegister(RegisterDto register) {
        try {
            Command cmd = Command.SHOW_REGISTER;
            //检测是否有发送权限
            if (!hasPermissionSend(cmd)) {
                return;
            }
            GlobalCache.addMeetingContents(cmd, register);

            ShowRegisterClientDto showRegisterClientDto = new ShowRegisterClientDto();
            showRegisterClientDto.setAbsent(register.getAbsent());
            showRegisterClientDto.setExpected(register.getExpected());
            showRegisterClientDto.setLeave(register.getAskForLeave());
            showRegisterClientDto.setRegistered(register.getRegistered());
            sendService.broadcastForSeat(cmd, Constant.commandRequestType, showRegisterClientDto);

        } catch (Exception e) {
            logger.error("下发显示报名指令出现异常", e);
        }
    }


    /**
     * 结束报名
     */
    public void stopRegister() {
        try {
            Command cmd = Command.STOP_REGISTER;
            //检测是否有发送权限
            if (!hasPermissionSend(cmd)) {
                return;
            }
            GlobalCache.addMeetingContents(cmd, null);

            StopRegisterClientDto stopRegisterClientDto = new StopRegisterClientDto();
            sendService.broadcastForVotingRights(cmd, Constant.commandRequestType, stopRegisterClientDto);

        } catch (Exception e) {
            logger.error("下发结束报名指令出现异常", e);
        }
    }


    /**
     * 查询报名情况
     */
    public List<RegisterResultDto> getRegisters() {
        try {
            List<RegisterResultDto> registerResult = Lists.newArrayList();
            Map<String, HeartbeatDto> heartbeats = GlobalCache.heartbeats;
            if (heartbeats.isEmpty()) {
                return null;
            }
            for (Map.Entry<String, HeartbeatDto> entry : heartbeats.entrySet()) {
                HeartbeatDto heartbeatDto = entry.getValue();

                //如果是列席终端不统计报名结果
                if (null == heartbeatDto.getTerminalType() || heartbeatDto.getTerminalType().intValue() == Terminal.TerminalType.ATTEND.value) {
                    continue;
                }
                RegisterResultDto registerResultDto = new RegisterResultDto();
                registerResultDto.setSeatId(heartbeatDto.getSeatId());
                registerResultDto.setStatus(heartbeatDto.getRegister());
                registerResult.add(registerResultDto);
            }
            return registerResult;
        } catch (Exception e) {
            logger.error("查询所有终端报名情况出现异常", e);
        }
        return null;
    }


    /**
     * 手工补到
     * 只针对表决权和排了座位的终端
     *
     * @param register
     */
    public void manualRegister(Map<String, Object> register) {
        try {
            Command cmd = Command.REGISTER;
            //检测是否有发送权限
            if (!hasPermissionSend(cmd)) {
                return;
            }

            GlobalCache.addMeetingContents(cmd, register);

            ManualRegisterClientDto manualRegisterClientDto = new ManualRegisterClientDto();
            Object seatObj = register.get("seatId");
            List<Integer> seatIds = Lists.newArrayList();
            if (seatObj instanceof String || seatObj instanceof Integer) {
                seatIds.add(Integer.parseInt(seatObj.toString()));
            } else if (seatObj instanceof List) {
                seatIds = (List<Integer>) seatObj;
            }
            for (Integer seatId : seatIds) {
                manualRegisterClientDto.setSeatId(seatId);
                boolean isSuccess = sendService.sendForVotingRights(cmd, Constant.commandRequestType, seatId, manualRegisterClientDto);
                if (isSuccess) {
                    //更新终端报名状态
                    GlobalCache.updateHeartbeats(seatId, 1);
                }
            }
        } catch (Exception e) {
            logger.error("下发手工报名指令出现异常", e);
        }
    }

    /**
     * 注销报名
     */
    public void unRegister(Map<String, Object> register) {
        try {
            Command cmd = Command.UNREGISTER;
            //检测是否有发送权限
            if (!hasPermissionSend(cmd)) {
                return;
            }

            GlobalCache.addMeetingContents(cmd, register);

            UnRegisterClientDto unRegisterClientDto = new UnRegisterClientDto();
            Object seatObj = register.get("seatId");
            List<Integer> seatIds = Lists.newArrayList();
            if (seatObj instanceof String || seatObj instanceof Integer) {
                seatIds.add(Integer.parseInt(seatObj.toString()));
            } else if (seatObj instanceof List) {
                seatIds = (List<Integer>) seatObj;
            }
            for (Integer seatId : seatIds) {
                boolean isSuccess = sendService.sendForVotingRights(cmd, Constant.commandRequestType, seatId, unRegisterClientDto);
                if (isSuccess) {
                    //更新终端报名状态
                    GlobalCache.updateHeartbeats(seatId, 0);
                }
            }
        } catch (Exception e) {
            logger.error("下发注销报名指令出现异常", e);
        }
    }


    /**
     * 开始表决,只针对排了座位的终端
     *
     * @param startVoteDto
     */
    public void startVote(StartVoteDto startVoteDto) {
        try {
            Command cmd = Command.START_VOTE;

            //清除表决信息
            voteService.deleteVotes();

            //检测是否有发送权限
            if (!hasPermissionSend(cmd)) {
                return;
            }

            GlobalCache.addMeetingContents(cmd, startVoteDto);

            List<String> titles = startVoteDto.getTitles();
            StartVoteClientDto clientDto = new StartVoteClientDto();
            clientDto.setSubject(startVoteDto.getSubject());
            clientDto.setVertical(startVoteDto.getVertical());
            clientDto.setHorizontal(startVoteDto.getHorizontal());
            List<Items> items = Lists.newArrayList();
            if (CollectionUtils.isNotEmpty(titles)) {
                int i = 0;
                for (String title : titles) {
                    items.add(new Items(++i, title));
                }
            }
            clientDto.setType(titles.size() > 0 ? 1 : 0);
            clientDto.setTitle("");//表决议题暂不考虑
            clientDto.setItems(items);

//            sendService.broadcastForVotingRightsAndRegisted(cmd, Constant.commandRequestType, clientDto);
            sendService.broadcastForSeat(cmd, Constant.commandRequestType, clientDto);

        } catch (Exception e) {
            logger.error("下发开始表决指令出现异常", e);
        }
    }


    /**
     * 结束表决
     */
    public void stopVote(StopVoteDto stopVoteDto) {
        try {

            Command cmd = Command.STOP_VOTE;
            //检测是否有发送权限
            if (!hasPermissionSend(cmd)) {
                return;
            }

            GlobalCache.addMeetingContents(cmd, stopVoteDto);

            List<VoteResultDto> voteResults = stopVoteDto.getResults();

            StopVoteClientDto stopVoteClientDto = new StopVoteClientDto();
            //列表为1是单向表决，否则是多项表决
            stopVoteClientDto.setType(voteResults.size() > 1 ? 1 : 0);
            stopVoteClientDto.setTitle("");
            stopVoteClientDto.setVotes(voteResults);
//            sendService.broadcastForVotingRightsAndRegisted(cmd, Constant.commandRequestType, stopVoteClientDto);
            sendService.broadcastForSeat(cmd, Constant.commandRequestType, stopVoteClientDto);

        } catch (Exception e) {
            logger.error("下发结束表决指令出现异常", e);
        }
    }


    /**
     * 查询所有终端的表决情况
     */
    public List<VoteQueryDto> getVotes() {
        List<VoteQueryDto> voteQueryDtos = Lists.newArrayList();
        try {
            List<Vote> votes = voteService.findAllVotes(); //查询数据库
            if (CollectionUtils.isEmpty(votes)) {
                return voteQueryDtos;
            }
            for (Vote vote : votes) {
                VoteQueryDto voteQueryDto = new VoteQueryDto();
                voteQueryDto.setSeatId(vote.getSeatId());
                List<VoteQueryItemDto> voteQueryItemDtos = Lists.newArrayList();
                List<VoteResult> voteResults = vote.getVotes();
                if (CollectionUtils.isNotEmpty(voteResults)) {
                    for (VoteResult voteResult : voteResults) {
                        VoteQueryItemDto voteQueryItemDto = new VoteQueryItemDto();
                        voteQueryItemDto.setId(voteResult.getSubjectId());
                        voteQueryItemDto.setName(voteResult.getSubject());
                        voteQueryItemDto.setKey(voteResult.getValue());
                        voteQueryItemDtos.add(voteQueryItemDto);
                    }
                }
                voteQueryDto.setResults(voteQueryItemDtos);
                voteQueryDtos.add(voteQueryDto);
            }
        } catch (Exception e) {
            logger.error("查询所有终端表决情况出现异常", e);
        }
        return voteQueryDtos;
    }


    /**
     * 开始申请发言
     * <p/>
     * 只针对排座人员的终端
     */
    public void startSpeak() {
        try {

            Command cmd = Command.START_SPEAK;
            //检测是否有发送权限
            if (!hasPermissionSend(cmd)) {
                return;
            }

            GlobalCache.addMeetingContents(cmd, null);

            StartSpeakClientDto startSpeakClientDto = new StartSpeakClientDto();
            sendService.broadcastForSeat(cmd, Constant.commandRequestType, startSpeakClientDto);

        } catch (Exception e) {
            logger.error("开始申请发言出现异常", e);
        }
    }


    /**
     * 显示已申请发言, 仅发送给当前申请通过的座位
     */
    public void requestSpeak(Map<String, Integer> requestSpeak) {
        try {
            Command cmd = Command.REQUEST_SPEAK;
            //检测是否有发送权限
            if (!hasPermissionSend(cmd)) {
                logger.info("不符合申请发言条件....");
                return;
            }
            GlobalCache.addMeetingContents(cmd, requestSpeak);

            sendService.sendForSeat(cmd, Constant.commandRequestType, requestSpeak.get("seatId"), new RequestSpeakClientDto());

        } catch (Exception e) {
            logger.error("显示已申请发言出现异常", e);
        }
    }

    /**
     * 显示正在发言人姓名, 仅发送给当前发言的人员
     */
    public void speaking(Map<String, Object> data) {
        try {
            Command cmd = Command.SET_SPEAKING;
            //检测是否有发送权限
            if (!hasPermissionSend(cmd)) {
                logger.info("不符合正在发言条件....");
                return;
            }
            GlobalCache.addMeetingContents(cmd, data);

            int seatId = Integer.parseInt(data.get("seatId").toString());
            sendService.sendForSeat(cmd, Constant.commandRequestType, seatId, new SpeakingClientDto());
        } catch (Exception e) {
            logger.error("显示正在发言信息出现异常", e);
        }
    }

    /**
     * 取消或者结束发言,仅有发言的终端界面改变状态，其他主机不变
     */
    public void cancelSpeak(Map<String, Object> data) {
        try {
            Command cmd = Command.CANCEL_SPEAKING;
            //检测是否有发送权限
            if (!hasPermissionSend(cmd)) {
                return;
            }
            GlobalCache.addMeetingContents(cmd, data);
            if (null == data.get("seatId")) {
                logger.error("取消或者结束发言座位号为空");
                return;
            }

            int seatId = Integer.parseInt(data.get("seatId").toString());
            sendService.sendForSeat(cmd, Constant.commandRequestType, seatId, new CancelSpeakClientDto());

        } catch (Exception e) {
            logger.error("取消或者结束发言时出现异常", e);
        }
    }


    /**
     * 初始化所有终端
     */

    public void showImage(ShowImageDto param) {
        try {
            Command cmd = Command.SHOW_IMAGE;
            //检测是否有发送权限
            if (!hasPermissionSend(cmd)) {
                return;
            }
            sendService.sendForSeat(cmd, Constant.commandRequestType, param.getSeatId(), param, false);
        } catch (Exception e) {
            logger.error("执行显示图片命令出现异常", e);
        }
    }


    /**
     * 初始化所有终端
     */

    public void initialize(InitializeDto param) {
        try {
            Command cmd = Command.INITIALIZE;
            //检测是否有发送权限
            if (!hasPermissionSend(cmd)) {
                return;
            }
//            GlobalCache.addMeetingContents(cmd, null);

            InitializeClientDto initializeClientDto = new InitializeClientDto();
            if (null != param) {
                initializeClientDto.setBrightness(param.getBrightness());
                initializeClientDto.setSeatId(param.getSeatId());
            }
            sendService.broadcast(cmd, Constant.commandRequestType, initializeClientDto, false);

            //初始化心跳
            GlobalCache.initHeartbeats();

            //清除表决信息
            voteService.deleteVotes();

        } catch (Exception e) {
            logger.error("执行初始化命令出现异常", e);
        }
    }

    /**
     * 重启终端
     *
     * @param param 基础座位参数
     */
    public void reboot(BaseDto param) {
        try {
            Command cmd = Command.REBOOT;
            //检测是否有发送权限
            if (!hasPermissionSend(cmd)) {
                return;
            }

            send(cmd, Constant.commandRequestType, param.getSeatId(), new RebootClientDto(), false);

        } catch (Exception e) {
            logger.error("执行关闭终端命令出现异常", e);
        }
    }

    /**
     * 关闭所有终端
     *
     * @param param 基础座位参数
     */
    public void shutdown(BaseDto param) {
        try {
            Command cmd = Command.SHUTDOWN;
            //检测是否有发送权限
            if (!hasPermissionSend(cmd)) {
                return;
            }
//            GlobalCache.addMeetingContents(cmd, null);

            send(cmd, Constant.commandRequestType, param.getSeatId(), new ShutdownClientDto(), false);

        } catch (Exception e) {
            logger.error("执行关闭终端命令出现异常", e);
        }
    }

    /**
     * 发送指令给代表终端,如果传入座位信息为0 则广播全部终端,否则发送该座位终端
     *
     * @param cmd    发送命令字
     * @param type   请求类型res或者req
     * @param seatId 终端座位号
     * @param param  发送传输的实体对象
     */
    private void send(Command cmd, String type, Integer seatId, Object param, boolean... isUpdateStatus) {
        if (null != seatId && seatId.intValue() > 0) {
            sendService.send(cmd, type, seatId, param, isUpdateStatus);
        } else {
            sendService.broadcast(cmd, type, param, isUpdateStatus);
        }

    }

    /**
     * 发送指令给代表终端,如果传入座位信息为0 则广播全部终端,否则发送该座位终端
     *
     * @param cmd    发送命令字
     * @param type   请求类型res或者req
     * @param seatId 终端座位号
     * @param param  发送传输的实体对象
     */
    private void sendForSeat(Command cmd, String type, Integer seatId, Object param) {
        if (null != seatId && seatId.intValue() > 0) {
            sendService.sendForSeat(cmd, type, seatId, param);
        } else {
            sendService.broadcastForSeat(cmd, type, param);
        }

    }


    /**
     * 发送指令给代表终端,如果传入座位信息为0 则广播全部终端,否则发送该座位终端
     * <p>
     * 只针对有表决权
     *
     * @param cmd    发送命令字
     * @param type   请求类型res或者req
     * @param seatId 终端座位号
     * @param param  发送传输的实体对象
     */
    private void sendForVotingRights(Command cmd, String type, Integer seatId, Object param) {
        if (null != seatId && seatId.intValue() > 0) {
            sendService.sendForVotingRights(cmd, type, seatId, param);
        } else {
            sendService.broadcastForVotingRights(cmd, type, param);
        }

    }


    /**
     * 当前发送的指令与代表主机存储的上次指令比较,判断是否允许发送?
     * <p/>
     * 如果在允许范围内则返回true,否则返回false
     *
     * @param sendCmd
     * @return
     */
    public boolean hasPermissionSend(Command sendCmd) {
        CommandContent commandContent = GlobalCache.getLatestCommandContent();
        if (null == commandContent) {
            return true;
        }
        Command cmd = commandContent.getCmd();
        int latestStatus = cmd.status;
        switch (sendCmd) {
            case SHUTDOWN:
            case REBOOT:
            case INITIALIZE:
            case CLEAR_SEAT:
            case SET_SEAT:
            case SET_CHAIRMAN:
            case SHOW_LOGO:
            case SHOW_SUBJECT:
                return true;

            //报名业务指令权限判断,暂时不做任何限制,由主控机决定
            case REGISTER:
            case UNREGISTER:
            case STOP_REGISTER:
//                if (latestStatus >= Command.START_REGISTER.status && latestStatus <= Command.UNREGISTER.status) {
//                    return true;
//                }
//                break;
                return true;

            //发言业务指令的权限判断
            case REQUEST_SPEAK:
            case SET_SPEAKING:
            case CANCEL_SPEAKING:
                if (latestStatus >= Command.START_SPEAK.status && latestStatus <= Command.CANCEL_SPEAKING.status) {
                    return true;
                }
                break;

            case STOP_VOTE:
            case VOTE:
                if (latestStatus >= Command.START_VOTE.status && latestStatus <= Command.STOP_VOTE.status) {
                    return true;
                }
                break;

            default:
                return true;
        }
        return false;
    }

}
