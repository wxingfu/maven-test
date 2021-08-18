package com.wxf.client;


import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Body;
import com.dtflys.forest.annotation.Post;

@BaseRequest(
        baseURL = "http://172.16.4.68/StockService/PlanService.asmx?wsdl",
        contentType = "text/xml; charset=utf-8"
)
public interface MyClient2 {

    // @Post(headers = {"SOAPAction: http://tempuri.org/GetStockAcidRolling"})
    @Post(headers = {"SOAPAction: http://tempuri.org/GetStockAcidRolling"})
    String GetStockAcidRolling(@Body String param);
}
