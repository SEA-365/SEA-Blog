package com.sea.controller;


import com.alibaba.fastjson.JSON;
import com.sea.enums.StatusCodeEnum;
import com.sea.model.dto.AboutDTO;
import com.sea.model.dto.ResponseDataDTO;
import com.sea.model.dto.WebsiteConfigDTO;
import com.sea.model.vo.AboutVO;
import com.sea.service.SEABlogInfoService;
import com.sea.util.BeanCopyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static com.sea.constant.OptTypeConstant.UPDATE;
import static com.sea.constant.OptTypeConstant.UPLOAD;

@Api(tags = "seablog信息")
@RestController
@RequestMapping("/seablog")
public class SEABlogInfoController {

    @Autowired
    private SEABlogInfoService seaBlogInfoService;

//    @Autowired
//    private UploadStrategyContext uploadStrategyContext;

//    @ApiOperation(value = "上报访客信息")
//    @PostMapping("/report")
//    public ResultVO<?> report() {
//        auroraInfoService.report();
//        return ResultVO.ok();
//    }
//
//    @ApiOperation(value = "获取系统信息")
//    @GetMapping("/")
//    public ResultVO<AuroraHomeInfoDTO> getBlogHomeInfo() {
//        return ResultVO.ok(auroraInfoService.getAuroraHomeInfo());
//    }
//
//    @ApiOperation(value = "获取系统后台信息")
//    @GetMapping("/admin")
//    public ResultVO<AuroraAdminInfoDTO> getBlogBackInfo() {
//        return ResultVO.ok(auroraInfoService.getAuroraAdminInfo());
//    }

//    @OptLog(optType = UPDATE)
//    @ApiOperation(value = "更新网站配置")
//    @PutMapping("/admin/website/config")
//    public ResultVO<?> updateWebsiteConfig(@Valid @RequestBody WebsiteConfigVO websiteConfigVO) {
//        auroraInfoService.updateWebsiteConfig(websiteConfigVO);
//        return ResultVO.ok();
//    }

    @ApiOperation(value = "获取网站配置")
    @GetMapping("/website/config")
    public ResponseDataDTO<WebsiteConfigDTO> getWebsiteConfig() {
        return new ResponseDataDTO<>(StatusCodeEnum.SUCCESS.getCode(), seaBlogInfoService.getWebsiteConfig());
    }

    @ApiOperation(value = "查看关于我信息")
    @GetMapping("/about")
    public ResponseDataDTO<AboutDTO> getAbout() {

        return new ResponseDataDTO<>(StatusCodeEnum.SUCCESS.getCode(), seaBlogInfoService.getAbout());
    }

    @ApiOperation(value = "修改关于我信息")
    @PutMapping("/admin/about")
    public ResponseDataDTO<?> updateAbout(@Valid @RequestBody AboutVO aboutVO) {
        seaBlogInfoService.updateAbout(aboutVO);
        return new ResponseDataDTO<>(StatusCodeEnum.SUCCESS.getCode(), "更新about内容成功！");
    }

//    @OptLog(optType = UPLOAD)
//    @ApiOperation(value = "上传博客配置图片")
//    @ApiImplicitParam(name = "file", value = "图片", required = true, dataType = "MultipartFile")
//    @PostMapping("/admin/config/images")
//    public ResultVO<String> savePhotoAlbumCover(MultipartFile file) {
//        return ResultVO.ok(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.CONFIG.getPath()));
//    }

}