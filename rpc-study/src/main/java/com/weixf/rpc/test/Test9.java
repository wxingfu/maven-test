package com.weixf.rpc.test;

import com.dtflys.forest.config.ForestConfiguration;
import com.weixf.rpc.client.MyClient;
import com.weixf.rpc.entity.common.ReqParams;
import com.weixf.rpc.entity.dept.Department;
import com.weixf.rpc.entity.dept.PageDepartment;
import com.weixf.rpc.entity.dms.DmsOpenRecord;
import com.weixf.rpc.entity.dms.PageDmsOpenRecord;
import com.weixf.rpc.entity.emp.Employee;
import com.weixf.rpc.entity.emp.PageEmployee;
import com.weixf.rpc.entity.invehicle.InVehicle;
import com.weixf.rpc.entity.invehicle.InVehicleRecord;
import com.weixf.rpc.entity.outvehicle.OutVehicle;
import com.weixf.rpc.entity.outvehicle.OutVehicleRecord;

import java.util.List;

public class Test9 {

    public static void main(String[] args) {

        // 实例化Forest配置对象
        ForestConfiguration configuration = ForestConfiguration.configuration();
        // 通过Forest配置对象实例化Forest请求接口
        MyClient client = configuration.createInstance(MyClient.class);

        ReqParams empReqParam = new ReqParams(2, 1, "StaffNO",
                false, "", "", 0);
        PageEmployee employees = client.getEmployees(empReqParam);
        List<Employee> empList = employees.getRecords();
        System.out.println(empList);

        String param = "\"Rid!='' and ParentId='f9cca47b-31eb-4c03-9a16-0f4626ae2025'\"";
        PageDepartment pageDepartment = client.getDepartments(param);
        List<Department> deptList = pageDepartment.getModels();
        System.out.println(deptList);

        param = "\"Rid='311f51cf-60d0-4711-8411-ebe0c30a1236' and ParentId='f9cca47b-31eb-4c03-9a16-0f4626ae2025'\"";
        PageDepartment department = client.getDepartmentById(param);
        Department dept = department.getModel();
        System.out.println(dept);


        param = "\"InTime >='2021-06-02 00:00:00' order by InTime desc\"";
        InVehicleRecord inVehicleRecord = client.getInVehicleRecord(param);
        List<InVehicle> inVehicleList = inVehicleRecord.getModels();
        System.out.println(inVehicleList);


        // ReqParams vehicleReqParam = new ReqParams(
        //         2, 1, "InTime", true,
        //         "InTime>='2021-06-01 00:00:00'", "", 0);
        // PageInVehicleRecord pageInVehicleRecord = client.pageInVehicleRecord(vehicleReqParam);
        // List<InVehicle> pageInVehicleRecordList = pageInVehicleRecord.getRecords();
        // System.out.println(pageInVehicleRecordList);


        ReqParams dmsReqParam = new ReqParams(
                2, 1, "StaffNo", false,
                "StaffNo<>'' and OpenDate >= '2021-06-01' and OpenDate <= '2021-06-30'",
                "", 0);
        PageDmsOpenRecord pageDmsOpenRecord = client.pageDmsOpenRecord(dmsReqParam);
        List<DmsOpenRecord> dmsOpenRecordList = pageDmsOpenRecord.getRecords();
        System.out.println(dmsOpenRecordList);


        param = "\"InTime >='2021-06-01 00:00:00' order by OutTime desc\"";
        OutVehicleRecord outVehicleRecord = client.getOutVehicleRecord(param);
        List<OutVehicle> outVehicle = outVehicleRecord.getModels();
        System.out.println(outVehicle);


        // ReqParams outVehicleReqParam = new ReqParams(
        //         2, 1, "OutTime", true,
        //         "OutTime>='2021-06-01 00:00:00'", "", 0);
        // PageOutVehicleRecord pageOutVehicleRecord = client.pageOutVehicleRecord(outVehicleReqParam);
        // List<OutVehicle> outVehicleList = pageOutVehicleRecord.getRecords();
        // System.out.println(outVehicleList);

    }
}
