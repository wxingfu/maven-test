package com.wxf.utils.test;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.Base64;

/*
 *
 * @author weixf
 * @date 2022-06-22
 */
public class Test {

    public static void main(String[] args) throws IOException {
        BASE64Encoder();
        Base64();
    }

    private static void Base64() {
        String str = "{\"body\":{\"channelType\":\"JT\",\"expandInfo\":{\"operTraceSessionId\":\"a2a12069e79557e37e8389b341c0b833\"},\"holderInfo\":{\"holderType\":1,\"holderName\":\"王洪涛\",\"holderSex\":1,\"holderBirthday\":\"1993-03-07\",\"holderCardType\":1,\"holderCardNo\":\"11010119930307265X\",\"holderCardEndDate\":\"2022-07-26\",\"holderMobile\":\"15000095783\",\"holderEmail\":\"vccz@163.com\",\"holderAnnualIncome\":\"11\",\"holderProvinceId\":\"110000\",\"holderCityId\":\"110100\",\"holderAreaId\":\"110101\",\"holderAddress\":\"哒哒哒哒哒哒111\",\"holderJobCode\":\"2010101\",\"isInsured\":1},\"orderTotalPayPrice\":311700,\"orderTotalPrice\":311700,\"policyList\":[{\"insuredInfoList\":[{\"insuredNum\":1,\"insuredPrice\":311700,\"insuredRelation\":0,\"insuredType\":1,\"insuredName\":\"王洪涛\",\"insuredSex\":1,\"insuredBirthday\":\"1993-03-07\",\"insuredCardType\":1,\"insuredCardNo\":\"11010119930307265X\",\"insuredCardEndDate\":\"2022-07-26\",\"insuredMobile\":\"15000095761\",\"insuredEmail\":\"vccz@163.com\",\"insuredHeight\":\"178\",\"insuredWeight\":\"68\",\"insuredAnnualIncome\":\"11\",\"insuredProvinceId\":\"110000\",\"insuredCityId\":\"110100\",\"insuredAreaId\":\"110101\",\"insuredAddress\":\"哒哒哒哒哒哒111\",\"insuredJobCode\":\"2010101\",\"insuredMedical\":1,\"benefitType\":1}],\"policyInfo\":{\"policyId\":\"16556957839591300480000\",\"policyPayPrice\":311700,\"policyPrice\":311700,\"antiMoneyLaunder\":0,\"bankNo\":\"105\",\"cardId\":\"6227002190466716191\",\"insuredNum\":1},\"productSchemeList\":[{\"mainInsuranceFlag\":1,\"productCode\":\"B27D01\",\"productSchemeInfo\":{\"insurancePeriod\":\"10\",\"insurancePeriodType\":\"Y\",\"paymentType\":\"Y\",\"policyBeginDate\":\"2022-06-21 00:00:00\",\"policyEndDate\":\"2032-06-21 00:00:00\",\"productName\":\"华贵大麦2022定期寿险（互联网专属）\",\"schemeCode\":\"2025955593992795\",\"schemeCoverage\":100000000,\"schemeName\":\"100万\",\"schemePremium\":103900},\"renewalInfo\":{\"paymentFrequency\":\"1\",\"paymentFrequencyType\":\"Y\",\"paymentPeriod\":\"5\",\"paymentPeriodType\":\"Y\"},\"riskList\":[{\"deductibleAmount\":0,\"dutyCoverage\":100000000,\"riskExplain\":\"身故及全残-100万\",\"riskName\":\"身故及全残\",\"riskType\":\"0\"}],\"waitPeriodDays\":90},{\"mainInsuranceFlag\":2,\"productCode\":\"B27D03\",\"productSchemeInfo\":{\"insurancePeriod\":\"10\",\"insurancePeriodType\":\"Y\",\"paymentType\":\"Y\",\"policyBeginDate\":\"2022-06-21 00:00:00\",\"policyEndDate\":\"2032-06-21 00:00:00\",\"productName\":\"水陆意外责任\",\"schemeCode\":\"2025955139993908\",\"schemeCoverage\":100000000,\"schemeName\":\"100万\",\"schemePremium\":103900},\"renewalInfo\":{\"paymentFrequency\":\"1\",\"paymentFrequencyType\":\"Y\",\"paymentPeriod\":\"5\",\"paymentPeriodType\":\"Y\"},\"riskList\":[{\"deductibleAmount\":0,\"dutyCoverage\":100000000,\"riskExplain\":\"身故及全残-100万\",\"riskName\":\"身故及全残\",\"riskType\":\"1\"}],\"waitPeriodDays\":90},{\"mainInsuranceFlag\":2,\"productCode\":\"B27D02\",\"productSchemeInfo\":{\"insurancePeriod\":\"10\",\"insurancePeriodType\":\"Y\",\"paymentType\":\"Y\",\"policyBeginDate\":\"2022-06-21 00:00:00\",\"policyEndDate\":\"2032-06-21 00:00:00\",\"productName\":\"航空意外责任\",\"schemeCode\":\"2025955130993844\",\"schemeCoverage\":100000000,\"schemeName\":\"100万\",\"schemePremium\":103900},\"renewalInfo\":{\"paymentFrequency\":\"1\",\"paymentFrequencyType\":\"Y\",\"paymentPeriod\":\"5\",\"paymentPeriodType\":\"Y\"},\"riskList\":[{\"deductibleAmount\":0,\"dutyCoverage\":100000000,\"riskExplain\":\"身故及全残-100万\",\"riskName\":\"身故及全残\",\"riskType\":\"1\"}],\"waitPeriodDays\":90}],\"renewInfo\":{}}],\"subChannel\":\"JDJR\"},\"header\":{\"comId\":\"470002\",\"licenseTag\":\"jintou\",\"requestType\":\"P01\",\"sendTime\":1655695783647,\"uuid\":\"1655695783959130048\",\"version\":\"1.2.1.0\"}}";
        // JDK1.8 Base64加密
        String encode = Base64.getEncoder().encodeToString(str.getBytes());
        System.out.println(encode);

        // JDK1.8 Base64解密
        // byte[] bytes = Base64.getDecoder().decode(encode);
        // String res = new String(bytes);
        // System.out.println(res);
    }

    private static void BASE64Encoder() throws IOException {
        String str = "{\"body\":{\"channelType\":\"JT\",\"expandInfo\":{\"operTraceSessionId\":\"a2a12069e79557e37e8389b341c0b833\"},\"holderInfo\":{\"holderType\":1,\"holderName\":\"王洪涛\",\"holderSex\":1,\"holderBirthday\":\"1993-03-07\",\"holderCardType\":1,\"holderCardNo\":\"11010119930307265X\",\"holderCardEndDate\":\"2022-07-26\",\"holderMobile\":\"15000095783\",\"holderEmail\":\"vccz@163.com\",\"holderAnnualIncome\":\"11\",\"holderProvinceId\":\"110000\",\"holderCityId\":\"110100\",\"holderAreaId\":\"110101\",\"holderAddress\":\"哒哒哒哒哒哒111\",\"holderJobCode\":\"2010101\",\"isInsured\":1},\"orderTotalPayPrice\":311700,\"orderTotalPrice\":311700,\"policyList\":[{\"insuredInfoList\":[{\"insuredNum\":1,\"insuredPrice\":311700,\"insuredRelation\":0,\"insuredType\":1,\"insuredName\":\"王洪涛\",\"insuredSex\":1,\"insuredBirthday\":\"1993-03-07\",\"insuredCardType\":1,\"insuredCardNo\":\"11010119930307265X\",\"insuredCardEndDate\":\"2022-07-26\",\"insuredMobile\":\"15000095761\",\"insuredEmail\":\"vccz@163.com\",\"insuredHeight\":\"178\",\"insuredWeight\":\"68\",\"insuredAnnualIncome\":\"11\",\"insuredProvinceId\":\"110000\",\"insuredCityId\":\"110100\",\"insuredAreaId\":\"110101\",\"insuredAddress\":\"哒哒哒哒哒哒111\",\"insuredJobCode\":\"2010101\",\"insuredMedical\":1,\"benefitType\":1}],\"policyInfo\":{\"policyId\":\"16556957839591300480000\",\"policyPayPrice\":311700,\"policyPrice\":311700,\"antiMoneyLaunder\":0,\"bankNo\":\"105\",\"cardId\":\"6227002190466716191\",\"insuredNum\":1},\"productSchemeList\":[{\"mainInsuranceFlag\":1,\"productCode\":\"B27D01\",\"productSchemeInfo\":{\"insurancePeriod\":\"10\",\"insurancePeriodType\":\"Y\",\"paymentType\":\"Y\",\"policyBeginDate\":\"2022-06-21 00:00:00\",\"policyEndDate\":\"2032-06-21 00:00:00\",\"productName\":\"华贵大麦2022定期寿险（互联网专属）\",\"schemeCode\":\"2025955593992795\",\"schemeCoverage\":100000000,\"schemeName\":\"100万\",\"schemePremium\":103900},\"renewalInfo\":{\"paymentFrequency\":\"1\",\"paymentFrequencyType\":\"Y\",\"paymentPeriod\":\"5\",\"paymentPeriodType\":\"Y\"},\"riskList\":[{\"deductibleAmount\":0,\"dutyCoverage\":100000000,\"riskExplain\":\"身故及全残-100万\",\"riskName\":\"身故及全残\",\"riskType\":\"0\"}],\"waitPeriodDays\":90},{\"mainInsuranceFlag\":2,\"productCode\":\"B27D03\",\"productSchemeInfo\":{\"insurancePeriod\":\"10\",\"insurancePeriodType\":\"Y\",\"paymentType\":\"Y\",\"policyBeginDate\":\"2022-06-21 00:00:00\",\"policyEndDate\":\"2032-06-21 00:00:00\",\"productName\":\"水陆意外责任\",\"schemeCode\":\"2025955139993908\",\"schemeCoverage\":100000000,\"schemeName\":\"100万\",\"schemePremium\":103900},\"renewalInfo\":{\"paymentFrequency\":\"1\",\"paymentFrequencyType\":\"Y\",\"paymentPeriod\":\"5\",\"paymentPeriodType\":\"Y\"},\"riskList\":[{\"deductibleAmount\":0,\"dutyCoverage\":100000000,\"riskExplain\":\"身故及全残-100万\",\"riskName\":\"身故及全残\",\"riskType\":\"1\"}],\"waitPeriodDays\":90},{\"mainInsuranceFlag\":2,\"productCode\":\"B27D02\",\"productSchemeInfo\":{\"insurancePeriod\":\"10\",\"insurancePeriodType\":\"Y\",\"paymentType\":\"Y\",\"policyBeginDate\":\"2022-06-21 00:00:00\",\"policyEndDate\":\"2032-06-21 00:00:00\",\"productName\":\"航空意外责任\",\"schemeCode\":\"2025955130993844\",\"schemeCoverage\":100000000,\"schemeName\":\"100万\",\"schemePremium\":103900},\"renewalInfo\":{\"paymentFrequency\":\"1\",\"paymentFrequencyType\":\"Y\",\"paymentPeriod\":\"5\",\"paymentPeriodType\":\"Y\"},\"riskList\":[{\"deductibleAmount\":0,\"dutyCoverage\":100000000,\"riskExplain\":\"身故及全残-100万\",\"riskName\":\"身故及全残\",\"riskType\":\"1\"}],\"waitPeriodDays\":90}],\"renewInfo\":{}}],\"subChannel\":\"JDJR\"},\"header\":{\"comId\":\"470002\",\"licenseTag\":\"jintou\",\"requestType\":\"P01\",\"sendTime\":1655695783647,\"uuid\":\"1655695783959130048\",\"version\":\"1.2.1.0\"}}";
        BASE64Encoder base64Encoder = new BASE64Encoder();
        // JDK1.8 BASE64Encoder 加密
        String encode = base64Encoder.encode(str.getBytes());
        System.out.println(encode);

        BASE64Decoder base64Decoder = new BASE64Decoder();
        // JDK1.8 BASE64Decoder 解密
        // byte[] bytes = base64Decoder.decodeBuffer(encode);
        // String res = new String(bytes);
        // System.out.println(res);
    }
}
