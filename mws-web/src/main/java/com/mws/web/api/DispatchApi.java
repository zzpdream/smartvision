package com.mws.web.api;

import com.mws.web.net.dto.*;
import com.mws.web.net.service.DispatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 主控机轮询代表主机的指令分发api接口,接受到主控机消息后再分发到所有终端机器
 * <p/>
 * Created by ranfi on 2/23/16.
 */

@Controller
@RequestMapping(value = "/api")
public class DispatchApi extends BaseApi {

    private static Logger logger = LoggerFactory.getLogger(DispatchApi.class);


    @Resource
    private DispatchService dispatchService;


    /**
     * 主控机清除座位上的人员信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/clearmember", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto clearMember(@RequestBody Map<String, String> request) {
        logger.info("---------清除座位Begin--------");
        ResponseDto response = new ResponseDto();
        Integer seatId = Integer.valueOf(request.get("seatId"));
        dispatchService.cleanMember(seatId);
        logger.info("---------清除座位End----------");
        return response;
    }

    /**
     * 主控机设置座位信息
     *
     * @param member 座位信息
     * @return
     */
    @RequestMapping(value = "/setmembers", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto setMembers(@RequestBody MemberDto member) {
        logger.info("---------设置座位Begin--------");
        ResponseDto response = new ResponseDto();
        dispatchService.sendSeats(member);
        logger.info("---------设置座位End----------");
        return response;
    }

    /**
     * 设置主席位置
     *
     * @param param 座位信息
     * @return
     */
    @RequestMapping(value = "/setchairman", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto setMembers(@RequestBody ChairmanDto param) {
        logger.info("---------设置主席座位Begin--------");
        ResponseDto response = new ResponseDto();
        dispatchService.setChairman(param);
        logger.info("---------设置主席座位End----------");
        return response;
    }

    /**
     * 显示会标
     *
     * @param logo 会标信息
     * @return
     */
    @RequestMapping(value = "/logo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto showLogo(@RequestBody LogoDto logo) {
        logger.info("---------显示徽标Begin--------");
        ResponseDto response = new ResponseDto();
        dispatchService.showLogo(logo);
        logger.info("---------显示徽标End----------");
        return response;
    }

    /**
     * 显示会议议程
     *
     * @param subject 会议议程内容
     * @return
     */
    @RequestMapping(value = "/subject", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto showSubject(@RequestBody SubjectDto subject) {
        logger.info("---------会议议程Begin--------");
        ResponseDto response = new ResponseDto();
        dispatchService.showSubject(subject);
        logger.info("---------会议议程End----------");
        return response;
    }

    /**
     * 开始报到
     *
     * @param register 报到内容
     * @return
     */
    @RequestMapping(value = "/startregister", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto startRegister(@RequestBody Map<String, String> register) {
        logger.info("---------开始报到Begin--------");
        ResponseDto response = new ResponseDto();
        dispatchService.startRegister(register);
        logger.info("---------开始报到End----------");
        return response;
    }

    /**
     * 获取成员信息
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/showmember", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto showmember(@RequestBody Map<String, String> register) {
        ResponseDto response = new ResponseDto();
        dispatchService.showMember(register);
        return response;
    }

    /**
     * 显示报到情况
     *
     * @param register 报到结果集
     * @return
     */
    @RequestMapping(value = "/showregister", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto showRegister(@RequestBody RegisterDto register) {
        logger.info("---------查询报到Begin--------");
        ResponseDto response = new ResponseDto();
        dispatchService.showRegister(register);
        logger.info("---------查询报到End----------");
        return response;
    }

    /**
     * 结束报到
     *
     * @return
     */
    @RequestMapping(value = "/stopregister", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto stopRegister() {
        logger.info("---------结束报到Begin--------");
        ResponseDto response = new ResponseDto();
        dispatchService.stopRegister();
        logger.info("---------结束报到End----------");
        return response;
    }


    /**
     * 查询报到情况
     *
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto getRegister() {
        logger.info("---------查询报到Begin--------");
        ResponseDto response = new ResponseDto();
        List<RegisterResultDto> registerResults = dispatchService.getRegisters();
        response.setResults(registerResults);
        logger.info("---------查询报到End----------");
        return response;
    }


    /**
     * 手工报到
     *
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto manualRegister(@RequestBody Map<String, Object> register) {
        logger.info("---------手工报到Begin--------");
        ResponseDto response = new ResponseDto();
        dispatchService.manualRegister(register);
        logger.info("---------手工报到End----------");
        return response;
    }

    /**
     * 手工销到
     *
     * @return
     */
    @RequestMapping(value = "/unregister", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto unRegister(@RequestBody Map<String, Object> register) {
        logger.info("---------手动销到Begin--------");
        ResponseDto response = new ResponseDto();
        dispatchService.unRegister(register);
        logger.info("---------手动销到End----------");
        return response;
    }


    /**
     * 开始表决
     *
     * @return
     */
    @RequestMapping(value = "/startvote", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto startVote(@RequestBody StartVoteDto startVoteDto) {
        logger.info("---------开始表决Begin--------");
        ResponseDto response = new ResponseDto();
        dispatchService.startVote(startVoteDto);
        logger.info("---------开始表决End----------");
        return response;
    }


    /**
     * 结束表决
     *
     * @return
     */
    @RequestMapping(value = "/stopvote", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto stopVote(@RequestBody StopVoteDto stopVoteDto) {
        logger.info("---------结束表决Begin--------");
        ResponseDto response = new ResponseDto();
        dispatchService.stopVote(stopVoteDto);
        logger.info("---------结束表决End----------");
        return response;
    }


    /**
     * 查询表决
     *
     * @return
     */
    @RequestMapping(value = "/vote", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto getVotes() {
        logger.info("---------查询表决结果Begin--------");
        ResponseDto response = new ResponseDto();
        response.setResults(dispatchService.getVotes());
        logger.info("---------查询表决结果End----------");
        return response;
    }

    /**
     * 开始申请发言
     *
     * @return
     */
    @RequestMapping(value = "/startspeak", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto startSpeak() {
        logger.info("---------开始申请发言Begin--------");
        ResponseDto response = new ResponseDto();
        dispatchService.startSpeak();
        logger.info("---------开始申请发言End----------");
        return response;
    }


    /**
     * 显示已申请发言
     *
     * @return
     */
    @RequestMapping(value = "/requestspeak", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto requestSpeak(@RequestBody Map<String, Integer> requestspeak) {
        logger.info("---------请求发言Begin--------");
        ResponseDto response = new ResponseDto();
        dispatchService.requestSpeak(requestspeak);
        logger.info("---------请求发言End----------");
        return response;
    }


    /**
     * 设置当前发言人
     *
     * @return
     */
    @RequestMapping(value = "/speaking", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto speaking(@RequestBody Map<String, Object> data) {
        logger.info("---------当前发言人Begin--------");
        ResponseDto response = new ResponseDto();
        dispatchService.speaking(data);
        logger.info("---------当前发言人End----------");
        return response;
    }

    /**
     * 取消和结束发言
     *
     * @return
     */
    @RequestMapping(value = "/cancelspeak", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto cancelSpeak(@RequestBody Map<String, Object> data) {
        logger.info("---------取消发言Begin--------");
        ResponseDto response = new ResponseDto();
        dispatchService.cancelSpeak(data);
        logger.info("---------取消发言End----------");
        return response;
    }


    /**
     * 初始化所有终端
     *
     * @return
     */
    @RequestMapping(value = "/initialize", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto initialize(@RequestBody(required = false) InitializeDto param) {
        logger.info("---------初始化Begin--------");
        ResponseDto response = new ResponseDto();
        dispatchService.initialize(param);
        logger.info("---------初始化End----------");
        return response;
    }


    /**
     * 显示所有
     *
     * @return
     */
    @RequestMapping(value = "/showimage", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto showImage(@RequestBody(required = false) ShowImageDto param) {
        ResponseDto response = new ResponseDto();
        dispatchService.showImage(param);
        return response;
    }

    /**
     * 关闭所有终端
     *
     * @return
     */
    @RequestMapping(value = "/shutdown", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto shutdown(@RequestBody BaseDto param) {
        logger.info("---------关机Begin--------");
        ResponseDto response = new ResponseDto();
        dispatchService.shutdown(param);
        logger.info("---------关机End----------");
        return response;
    }

    /**
     * 重启终端
     *
     * @return
     */
    @RequestMapping(value = "/reboot", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto reboot(@RequestBody BaseDto param) {
        logger.info("---------重启Begin--------");
        ResponseDto response = new ResponseDto();
        dispatchService.reboot(param);
        logger.info("---------重启End----------");
        return response;
    }

    /**
     * 检测代表主机状态是否正常
     *
     * @return
     */
    @RequestMapping(value = "/echo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto echo() {
        ResponseDto response = new ResponseDto();
        return response;
    }


    /**
     * 开始播放视频
     *
     * @return
     */
    @RequestMapping(value = "/startplayvideo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto startPlayVideo(@RequestBody PlayVideoParamDto playVideoParamDto) {
        ResponseDto response = new ResponseDto();
        dispatchService.startPlayVideo(playVideoParamDto);
        return response;
    }

    /**
     * 停止播放视频
     *
     * @return
     */
    @RequestMapping(value = "/stopplayvideo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto stopPlayVideo(@RequestBody BaseDto param) {
        ResponseDto response = new ResponseDto();
        dispatchService.stopPlayVideo(param);
        return response;
    }

}
