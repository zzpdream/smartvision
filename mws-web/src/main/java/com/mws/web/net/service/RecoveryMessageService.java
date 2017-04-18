package com.mws.web.net.service;

import com.google.common.collect.Lists;
import com.mws.web.net.bo.Command;
import com.mws.web.net.bo.Constant;
import com.mws.web.net.bo.GlobalCache;
import com.mws.web.net.dto.*;
import com.mws.web.net.dto.client.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 恢复消息实现,主要用户代表主机和代表终端消息不一致性
 * <p/>
 * 一旦发生不一致性,则以服务器为准
 * <p/>
 * Created by ranfi on 3/23/16.
 */
@Service
public class RecoveryMessageService {

    private static Logger logger = LoggerFactory.getLogger(RecoveryMessageService.class);

    @Resource
    private SendService sendService;

    /**
     * 根据命令调用不同消息处理,由代表主机发送给代表终端
     *
     * @param command  当前恢复的消息命令
     * @param content  当前恢复的最新消息内容
     * @param clientIp 当前终端IP
     */
    public void sendMessageByCommand(Command command, Object content, String clientIp) {
        switch (command) {
            case INITIALIZE: //终端初始化
                initialize(clientIp);
                break;
            case SHUTDOWN: //终端初始化
                shutdown(clientIp);
                break;
            case CLEAR_SEAT:
                cleanMember(clientIp);
                break;
            case DOWNLOAD_PHOTO:
                break;
            case SET_SEAT:
                sendSeats((MemberDto) content, clientIp);
                break;
            case SHOW_LOGO:
                showLogo((LogoDto) content, clientIp);
                break;
            case START_REGISTER:
                startRegister((Map<String, String>) content, clientIp);
                break;
            case SHOW_REGISTER:
                showRegister((RegisterDto) content, clientIp);
                break;
            case STOP_REGISTER:
                stopRegister(clientIp);
                break;
            case REGISTER:
                manualRegister((Map<String, Object>) content, clientIp);
                break;
            case UNREGISTER:
                unRegister((Map<String, Object>) content, clientIp);
                break;
            case SHOW_SUBJECT:
                showSubject((SubjectDto) content, clientIp);
                break;
            case START_VOTE:
                startVote((StartVoteDto) content, clientIp);
                break;
            case STOP_VOTE:
                stopVote((StopVoteDto) content, clientIp);
                break;
            case START_SPEAK:
                startSpeak(clientIp);
                break;
            case REQUEST_SPEAK:
                requestSpeak((Map<String, Integer>) content, clientIp);
                break;
            case SET_SPEAKING:
                speaking((Map<String, Object>) content, clientIp);
                break;
            case CANCEL_SPEAKING:
                cancelSpeak((Map<String, Object>) content, clientIp);
                break;
            default:
                break;
        }
    }

    /**
     * 清楚终端的座位信息
     *
     * @param clientIp 终端IP
     */
    public void cleanMember(String clientIp) {
        try {
            ClearSeatClientDto clearSeatClientDto = new ClearSeatClientDto();
            sendService.sendByIp(Command.CLEAR_SEAT, Constant.commandRequestType, clientIp, clearSeatClientDto);
        } catch (Exception e) {
            logger.error("下发清除指令出现异常", e);
        }
    }

    /**
     * 设置终端座位信息
     *
     * @param member
     * @param clientIp 终端IP
     */
    public void sendSeats(MemberDto member, String clientIp) {
        try {

            List<SeatDto> seats = member.getMembers();
            if (CollectionUtils.isEmpty(seats)) {
                logger.error("主控机下发座位信息指令已接受,数据为空");
                return;
            }
            HeartbeatDto heartbeatDto = GlobalCache.heartbeats.get(clientIp);
            for (SeatDto seat : seats) {
                //只恢复与自己一致的座位信息
                if (seat.getSeatId().intValue() == heartbeatDto.getSeatId().intValue()) {
                    sendService.sendByIp(Command.SET_SEAT, Constant.commandRequestType, clientIp, seat);
                    break;
                }
            }

        } catch (Exception e) {
            logger.error("下发设置座位指令出现异常", e);
        }
    }

    /**
     * 下发会标信息
     *
     * @param logo
     * @param clientIp 终端IP
     */

    public void showLogo(LogoDto logo, String clientIp) {
        try {
            MessageDto.Request request = new MessageDto.Request();
            request.setCmd(Command.SHOW_LOGO.value);
            request.setType(Constant.commandRequestType);

            ShowLogoClientDto showLogoClientDto = new ShowLogoClientDto();
            showLogoClientDto.setTitles(logo.getContents());
            showLogoClientDto.setPicUrl("");
            sendService.sendByIp(Command.SHOW_LOGO, Constant.commandRequestType, clientIp, showLogoClientDto);

        } catch (Exception e) {
            logger.error("恢复会标指令出现异常", e);
        }
    }


    /**
     * 下发会议议程
     *
     * @param subject  会议议程
     * @param clientIp
     */
    public void showSubject(SubjectDto subject, String clientIp) {
        try {
            MessageDto.Request request = new MessageDto.Request();
            request.setCmd(Command.SHOW_SUBJECT.value);
            request.setType(Constant.commandRequestType);

            ShowSubjectClientDto showSubjectClientDto = new ShowSubjectClientDto();
            showSubjectClientDto.setSubject(subject.getContent());
            showSubjectClientDto.setVertical(subject.getVertical());
            showSubjectClientDto.setHorizontal(subject.getHorizontal());
            sendService.sendByIp(Command.SHOW_SUBJECT, Constant.commandRequestType, clientIp, showSubjectClientDto);

        } catch (Exception e) {
            logger.error("下发会议议程指令出现异常", e);
        }
    }

    /**
     * 恢复开始报名
     *
     * @param register 报名内容
     * @param clientIp 终端IP
     */
    public void startRegister(Map<String, String> register, String clientIp) {
        try {
            StartRegisterClientDto startRegisterClientDto = new StartRegisterClientDto();
            startRegisterClientDto.setTitle(register.get("title"));
            sendService.sendByIp(Command.START_REGISTER, Constant.commandRequestType, clientIp, startRegisterClientDto);
        } catch (Exception e) {
            logger.error("下发开始报名指令出现异常", e);
        }
    }


    /**
     * 显示报名情况
     *
     * @param register 报名内容
     * @param clientIp 终端IP
     */
    public void showRegister(RegisterDto register, String clientIp) {
        try {
            MessageDto.Request request = new MessageDto.Request();
            request.setCmd(Command.SHOW_REGISTER.value);
            request.setType(Constant.commandRequestType);

            ShowRegisterClientDto showRegisterClientDto = new ShowRegisterClientDto();
            showRegisterClientDto.setAbsent(register.getAbsent());
            showRegisterClientDto.setExpected(register.getExpected());
            showRegisterClientDto.setLeave(register.getAskForLeave());
            showRegisterClientDto.setRegistered(register.getRegistered());
            sendService.sendByIp(Command.SHOW_REGISTER, Constant.commandRequestType, clientIp, showRegisterClientDto);

        } catch (Exception e) {
            logger.error("下发显示报名指令出现异常", e);
        }
    }

    /**
     * 结束报名
     *
     * @param clientIp 终端IP
     */
    public void stopRegister(String clientIp) {
        try {
            StopRegisterClientDto stopRegisterClientDto = new StopRegisterClientDto();
            sendService.sendByIp(Command.STOP_REGISTER, Constant.commandRequestType, clientIp, stopRegisterClientDto);
        } catch (Exception e) {
            logger.error("下发结束报名指令出现异常", e);
        }
    }

    /**
     * 手工报名
     *
     * @param clientIp 终端IP
     */
    public void manualRegister(Map<String, Object> register, String clientIp) {
        try {
            sendService.sendByIp(Command.REGISTER, Constant.commandRequestType, clientIp, new ManualRegisterClientDto());
        } catch (Exception e) {
            logger.error("下发手工报名指令出现异常", e);
        }
    }

    /**
     * 注销报名
     *
     * @param clientIp 终端IP
     */
    public void unRegister(Map<String, Object> register, String clientIp) {
        try {
            UnRegisterClientDto unRegisterClientDto = new UnRegisterClientDto();
            sendService.sendByIp(Command.UNREGISTER, Constant.commandRequestType, clientIp, unRegisterClientDto);
        } catch (Exception e) {
            logger.error("下发注销报名指令出现异常", e);
        }
    }


    /**
     * 开始表决
     *
     * @param clientIp 终端IP
     */
    public void startVote(StartVoteDto startVoteDto, String clientIp) {
        try {
            List<String> titles = startVoteDto.getTitles();
            StartVoteClientDto clientDto = new StartVoteClientDto();
            List<StartVoteClientDto.Items> items = Lists.newArrayList();
            if (CollectionUtils.isNotEmpty(titles)) {
                int i = 0;
                for (String title : titles) {
                    items.add(new StartVoteClientDto.Items(++i, title));
                }
            }

            clientDto.setSubject(startVoteDto.getSubject());
            clientDto.setHorizontal(startVoteDto.getHorizontal());
            clientDto.setVertical(startVoteDto.getVertical());
            clientDto.setType(titles.size() > 0 ? 1 : 0);
            clientDto.setTitle("");//表决议题暂不考虑
            clientDto.setItems(items);

            sendService.sendByIp(Command.START_VOTE, Constant.commandRequestType, clientIp, clientDto);

        } catch (Exception e) {
            logger.error("下发开始表决指令出现异常", e);
        }
    }


    /**
     * 结束表决
     *
     * @param clientIp 座位编号
     */
    public void stopVote(StopVoteDto stopVoteDto, String clientIp) {
        try {
            List<VoteResultDto> voteResults = stopVoteDto.getResults();
            StopVoteClientDto stopVoteClientDto = new StopVoteClientDto();
            //列表为1是单向表决，否则是多项表决
            stopVoteClientDto.setType(voteResults.size() > 1 ? 1 : 0);
            stopVoteClientDto.setTitle("");
            stopVoteClientDto.setVotes(voteResults);
            sendService.sendByIp(Command.STOP_VOTE, Constant.commandRequestType, clientIp, stopVoteClientDto);
        } catch (Exception e) {
            logger.error("下发结束表决指令出现异常", e);
        }
    }


    /**
     * 开始申请发言
     */
    public void startSpeak(String clientIp) {
        try {

            sendService.sendByIp(Command.START_SPEAK, Constant.commandRequestType, clientIp, new StartSpeakClientDto());

        } catch (Exception e) {
            logger.error("开始申请发言出现异常", e);
        }
    }


    /**
     * 显示已申请发言, 仅发送给当前申请通过的座位
     *
     * @param clientIp 恢复的终端IP
     */
    public void requestSpeak(Map<String, Integer> requestSpeak, String clientIp) {
        try {

            sendService.sendByIp(Command.REQUEST_SPEAK, Constant.commandRequestType, clientIp, new RequestSpeakClientDto());

        } catch (Exception e) {
            logger.error("显示已申请发言出现异常", e);
        }
    }

    /**
     * 显示正在发言人姓名, 仅发送给当前发言的人员
     *
     * @param data
     * @param clientIp
     */
    public void speaking(Map<String, Object> data, String clientIp) {
        try {
            sendService.sendByIp(Command.SET_SPEAKING, Constant.commandRequestType, clientIp, new SpeakingClientDto());
        } catch (Exception e) {
            logger.error("显示正在发言信息出现异常", e);
        }
    }

    /**
     * 取消或者结束发言,仅有发言的终端界面改变状态，其他主机不变
     *
     * @param clientIp
     */
    public void cancelSpeak(Map<String, Object> data, String clientIp) {
        try {
            if (null == data.get("seatId")) {
                logger.error("取消或者结束发言座位号为空");
                return;
            }

            sendService.sendByIp(Command.CANCEL_SPEAKING, Constant.commandRequestType, clientIp, new CancelSpeakClientDto());

        } catch (Exception e) {
            logger.error("取消或者结束发言时出现异常", e);
        }
    }


    /**
     * 初始化终端
     *
     * @param clientIp
     */

    public void initialize(String clientIp) {
        try {
            sendService.sendByIp(Command.INITIALIZE, Constant.commandRequestType, clientIp, new InitializeClientDto());
        } catch (Exception e) {
            logger.error("执行初始化命令出现异常", e);
        }
    }

    /**
     * 关闭所有终端
     *
     * @param clientIp
     */
    public void shutdown(String clientIp) {
        try {
            sendService.sendByIp(Command.SHUTDOWN, Constant.commandRequestType, clientIp, new ShutdownClientDto());
        } catch (Exception e) {
            logger.error("执行关闭终端命令出现异常", e);
        }
    }

}

