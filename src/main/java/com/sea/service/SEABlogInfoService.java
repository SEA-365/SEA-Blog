package com.sea.service;

import com.sea.model.dto.WebsiteConfigDTO;
import com.sea.model.vo.WebsiteConfigVO;

public interface SEABlogInfoService {

//    void report();

//    AuroraHomeInfoDTO getAuroraHomeInfo();
//
//    AuroraAdminInfoDTO getAuroraAdminInfo();

    void updateWebsiteConfig(WebsiteConfigVO websiteConfigVO);

    WebsiteConfigDTO getWebsiteConfig();

//    void updateAbout(AboutVO aboutVO);
//
//    AboutDTO getAbout();

}
