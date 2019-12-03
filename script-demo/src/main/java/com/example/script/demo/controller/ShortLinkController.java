package com.example.script.demo.controller;

import com.example.script.demo.shortlink.ConversionUtils;
import com.example.script.demo.shortlink.ShortLink;
import com.example.script.demo.shortlink.ShortLinkRepository;
import com.example.script.demo.shortlink.ShortLinkResult;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Api(description = "短链")
@RestController
@RequestMapping(value = "/script-demo/v1/link/short/", produces = "application/json")
@Slf4j
public class ShortLinkController {


    @Autowired
    private ShortLinkRepository shortLinkRepository;


    @PostMapping("/create")
    public ShortLinkResult createShortLink(@RequestBody String url, HttpServletRequest request) throws UnknownHostException {
        Preconditions.checkArgument(StringUtils.isNotBlank(url));
        Instant instant = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
        ShortLink shortLink = shortLinkRepository.save(new ShortLink(url, Date.from(instant)));
        String shortStr = ConversionUtils.encode(shortLink.getId(), 4);
        log.info("生成短链 {}  ------>  {}", url, shortStr);
        return new ShortLinkResult(shortLink.getId(), url, getServerUrl(request) + "/script-demo/v1/link/short/to/" + shortStr);
    }


    @GetMapping("/to/{shortUrl}")
    public void redirectToSourceUrl(@PathVariable("shortUrl") String shortUrl, HttpServletResponse response) throws IOException {
        long id = ConversionUtils.decode(shortUrl);
        Optional<ShortLink> shortLinkOpt = Optional.ofNullable(shortLinkRepository.findOne(id));
        String url = shortLinkOpt.orElseGet(null).getUrl();
        response.sendRedirect(url);
    }


    /**
     * 获取当前应用服务器域名和端口
     * @return String
     */
    private String getServerUrl(HttpServletRequest request) throws UnknownHostException {
        StringBuilder sb = new StringBuilder();
        //获取服务器域名
        String serverName = request.getServerName();
        //获取服务器端口
        int serverPort = request.getServerPort();
        //获取服务器IP地址;
        String hostAddress = InetAddress.getByName(request.getServerName()).getHostAddress();

        return sb.append("http://").append(serverName).append(":").append(serverPort).toString();
    }
}
