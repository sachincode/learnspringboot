package com.example.script.demo.controller;

import com.example.script.demo.qrcodelink.QRCodeResult;
import com.example.script.demo.qrcodelink.QRCodeUtils;
import com.example.script.demo.shortlink.ConversionUtils;
import com.example.script.demo.shortlink.ShortLink;
import com.example.script.demo.shortlink.ShortLinkRepository;
import com.example.script.demo.shortlink.ShortLinkResult;
import com.google.common.base.Preconditions;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static com.example.script.demo.config.DemoConfig.TEMP_FILE_PATH;

@Api(description = "二维码链接")
@RestController
@RequestMapping(value = "/script-demo/v1/link/qrcode/", produces = "application/json")
@Slf4j
public class QRCodeLinkController {




    @PostMapping(value = "/create", headers = "content-type=multipart/form-data", consumes = "multipart/form-data")
    public void createQRCodeLink(@RequestParam String url, @RequestParam(value = "logoFile", required = false) MultipartFile logoFile, HttpServletResponse response) throws IOException, WriterException {
        Preconditions.checkArgument(StringUtils.isNotBlank(url));

        File file = (logoFile != null && !logoFile.isEmpty()) ? multipartFileToFile(logoFile): null;
        QRCodeUtils.QREncode(url, file, response.getOutputStream());
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "multipartFile", paramType="form", value = "临时文件", dataType="file", required = true),
    })
    @PostMapping("/read")
    public QRCodeResult redirectToSourceUrl(@RequestParam("multipartFile") MultipartFile multipartFile, HttpServletResponse response) throws IOException, NotFoundException {
        File file = multipartFileToFile(multipartFile);
        return QRCodeUtils.QRReader(file);
    }



    private File multipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(TEMP_FILE_PATH + "/" + multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        return file;
    }
}
