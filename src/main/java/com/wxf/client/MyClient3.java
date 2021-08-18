package com.wxf.client;

import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Body;
import com.dtflys.forest.annotation.Post;

@BaseRequest(
        baseURL = "http://172.16.4.68/bak_p/PlanService.asmx?wsdl",
        contentType = "text/xml; charset=utf-8"
)
public interface MyClient3 {

    @Post(headers = {"SOAPAction: http://tempuri.org/GetMaterialForDate"})
    String GetMaterialForDate(@Body String param);

}
