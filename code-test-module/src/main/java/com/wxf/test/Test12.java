package com.wxf.test;


import org.jdom2.CDATA;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.json.JSONObject;
import org.json.XML;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/*
 *
 * @author weixf
 * @date 2022-06-28
 */
public class Test12 {

    public static void main(String[] args) throws IOException, JDOMException {

        // String id_18 = "^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}(\\d|([Xx]))";
        // String id_15 = "^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}\\d";
        // String id_valid = "(" + id_18 + ")" + "|" + "(" + id_15 + ")";
        // Pattern pattern = Pattern.compile(id_valid);
        // Matcher matcher = pattern.matcher("91234520120131123X");
        // System.out.println(matcher.matches());

        Document document = new Document();
        Element tranData = new Element("TranData");
        document.addContent(tranData);

        Element head = new Element("Head");
        Element body = new Element("Body");
        tranData.addContent(head);
        tranData.addContent(body);

        Element Flag = new Element("Flag");
        Flag.setText("000000");

        Element Desc = new Element("Desc");
        Desc.setText("成功");

        Element TranNo = new Element("TranNo");
        String tranNo = "1640425963071131068";
        TranNo.addContent(new CDATA(tranNo));

        Element TranDate = new Element("TranDate");
        TranDate.setText("1656490402664");

        Element Version = new Element("Version");
        Version.setText("");

        Element RequestType = new Element("RequestType");
        RequestType.setText("");

        Element ComId = new Element("ComId");
        ComId.setText("");

        head.addContent(Flag);
        head.addContent(Desc);
        head.addContent(TranNo);
        head.addContent(TranDate);
        head.addContent(Version);
        head.addContent(RequestType);
        head.addContent(ComId);

        Element ContNo = new Element("ContNo");
        String contNo = "120077000000122";
        ContNo.addContent(new CDATA(contNo));
        body.addContent(ContNo);

        Element ProposalPrtNo = new Element("ProposalPrtNo");
        String proposalPrtNo = "120077000000122";
        ProposalPrtNo.addContent(new CDATA(proposalPrtNo));
        body.addContent(ProposalPrtNo);

        Element Prem = new Element("Prem");
        Prem.setText("123");
        body.addContent(Prem);

        Element HealthImageNo = new Element("HealthImageNo");
        body.addContent(HealthImageNo);

        Element OrderId = new Element("OrderId");
        String orderId = "1640425963071131068";
        OrderId.addContent(new CDATA(orderId));
        body.addContent(OrderId);

        Element A = new Element("A");

        Element policyList = new Element("policyList");
        Element policyInfo = new Element("policyInfo");

        Element policyId = new Element("policyId");
        Element proposalNo = new Element("proposalNo");
        Element policyPrice = new Element("policyPrice");
        Element policyPayPrice = new Element("policyPayPrice");
        Element policyNum = new Element("policyNum");
        Element proposalEffectiveDate = new Element("proposalEffectiveDate");

        Element insuredInfoList = new Element("insuredInfoList");
        Element insuredInfo = new Element("insuredInfo");
        Element insuredRelation = new Element("insuredRelation");
        Element insuredNum = new Element("insuredNum");
        Element isSuccess = new Element("isSuccess");
        Element failCode = new Element("failCode");
        Element failReason = new Element("failReason");

        insuredInfo.addContent(insuredRelation);
        insuredInfo.addContent(insuredNum);
        insuredInfo.addContent(isSuccess);
        insuredInfo.addContent(failCode);
        insuredInfo.addContent(failReason);
        insuredInfoList.addContent(insuredInfo);

        policyInfo.addContent(policyId);
        policyInfo.addContent(proposalNo);
        policyInfo.addContent(policyPrice);
        policyInfo.addContent(policyPayPrice);
        policyInfo.addContent(policyNum);
        policyInfo.addContent(proposalEffectiveDate);
        policyInfo.addContent(insuredInfoList);
        policyList.addContent(policyInfo);

        A.addContent(policyList);
        A.addContent(new Element("policyList"));


        Element orderTotalPrice = new Element("orderTotalPrice");
        orderTotalPrice.setText("25751");

        Element orderTotalPayPrice = new Element("orderTotalPayPrice");
        orderTotalPayPrice.setText("25751");

        Element insuranceNum = new Element("insuranceNum");
        insuranceNum.setText("1");


        A.addContent(orderTotalPrice);
        A.addContent(orderTotalPayPrice);
        A.addContent(insuranceNum);

        body.addContent(A);

        byte[] xmlBytes = XMLGetBytes(document);
        String xml = new String(xmlBytes != null ? xmlBytes : new byte[0]);
        System.out.println(xml);

        JSONObject jsonObject = XML.toJSONObject(xml);
        System.out.println(jsonObject.toString());
    }

    public static byte[] XMLGetBytes(Document doc) {
        String encoding = "UTF-8";
        return XMLGetBytes(doc, encoding);
    }

    public static byte[] XMLGetBytes(Document doc, String strEncoding) {
        XMLOutputter xmlOutputter;
        try {
            Format format = Format.getPrettyFormat();
            format.setEncoding(strEncoding);
            format.setIndent("  ");
            format.setExpandEmptyElements(true);
            xmlOutputter = new XMLOutputter(format);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            xmlOutputter.output(doc, baos);
            baos.close();
            return baos.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
