package com.yadong.bugly;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * @packInfo:com.yadong.bugly
 * @author: yadong.qiu
 * Created by 邱亚东
 * Date: 2017/12/19
 * Time: 15:59
 */

public class DongApplication extends TinkerApplication {

    public DongApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL,
                "com.yadong.bugly.DongAppLicationLike",
                "com.tencent.tinker.loader.TinkerLoader",
                false);
    }
}
