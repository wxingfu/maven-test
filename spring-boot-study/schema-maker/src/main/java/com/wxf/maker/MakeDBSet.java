package com.wxf.maker;


import com.wxf.table.Column;
import com.wxf.table.Key;
import com.wxf.table.Table;
import com.wxf.utility.DBConst;
import com.wxf.utility.SQLTypes;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @author weixf
 * @since 2022-01-21
 */
@Component
public class MakeDBSet {

    private String packageName;
    private String schemaOutputPATH;
    private String outputPackagePATH;

    private int DBType = DBConst.DB_UnSupported;
    private static final String space4_1 = "    ";
    private static final String space4_2 = space4_1 + space4_1;
    private static final String space4_3 = space4_2 + space4_1;
    private static final String space4_4 = space4_3 + space4_1;
    private static final String space4_5 = space4_4 + space4_1;
    private static final String space4_6 = space4_5 + space4_1;
    private String DBName;
    private boolean UserInfo = false;
    private boolean MultiCloseConn = false;

    public void setUserInfo(boolean UserInfo) {
        this.UserInfo = UserInfo;
    }

    public void isMultiCloseConn(boolean MultiCloseConn) {
        this.MultiCloseConn = MultiCloseConn;
    }

    public MakeDBSet() {

    }

    public MakeDBSet(String packageName, String schemaOutputPATH, String outputPackagePATH, String DBName) {
        this.packageName = packageName;
        this.schemaOutputPATH = schemaOutputPATH;
        this.outputPackagePATH = outputPackagePATH;
        this.DBName = DBName;
        this.DBType = DBConst.DB_Oracle;
    }

    private String getTimestamp() {
        String pattern = "yyyy-MM-dd HH:mm:ss SSS";
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Date today = new Date();
        return df.format(today);
    }

    public void create(Table tTable) throws Exception {
        String TableName = tTable.getCode();
        PrintWriter out = null;
        String Path = null;
        Path = schemaOutputPATH + outputPackagePATH + "vdb/";
        String ClassName = TableName + "DBSet";
        String FileName = ClassName + ".java";
        String SetName = TableName + "Set";
        String SchemaName = TableName + "Schema";
        try {
            // ????????????
            File dir = new File(Path);
            if (!dir.exists() || !dir.isDirectory()) {
                boolean b = dir.mkdirs();
            }
            // out = new PrintWriter(new FileWriter(new File(Path + FileName)), true);
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    Files.newOutputStream(new File(Path + FileName).toPath()), "GBK")),
                    true);
            // ???????????????
            out.println("/**");
            out.println(" * Copyright (c) " + getTimestamp().substring(0, 4) + " Sinosoft Co.,LTD.");
            out.println(" * All right reserved.");
            out.println(" */");
            out.println();
            // @Package
            out.println("package " + packageName + ".vdb;");
            out.println();
            // @Import
            out.println("import java.io.StringReader;");
            out.println("import java.sql.Connection;");
            out.println("import java.sql.ResultSet;");
            out.println("import java.sql.Statement;");
            out.println("import java.sql.PreparedStatement;");
            out.println("import java.sql.Date;");

            out.println("import java.util.logging.Logger;");

            out.println();
            //out.println("import " + packageName + ".schema." + SchemaName +
            //            ";");
            out.println("import " + packageName + ".vschema." + SetName + ";");
            out.println("import com.sinosoft.utility.DBOper;");
            out.println("import com.sinosoft.utility.DBConnPool;");
            out.println("import com.sinosoft.utility.SQLString;");
            out.println("import com.sinosoft.utility.StrTool;");
            out.println("import com.sinosoft.utility.CError;");
            out.println("import com.sinosoft.utility.CErrors;");
            out.println();
            // ?????????
            out.println("/**");
            out.println(" * <p>?????????????????????????????????????????????</p>");
            out.println(" * <p>ClassName: " + ClassName + " </p>");
            out.println(" * <p>Description: DB???????????????????????????????????? </p>");
            out.println(" * <p>Company: Sinosoft Co.,LTD</p>");
            out.println(" * @Database: " + DBName);
            out.println(" * @author: Makerx");
            out.println(" * @CreateDatetime??? " + getTimestamp());
            if (UserInfo) {
                Properties props = System.getProperties();
                out.println(" * @vm: " + props.getProperty("java.vm.name") + "(build " + props.getProperty("java.vm.version") + ", " + props.getProperty("java.vm.vendor") + ")");
                out.println(" * @os: " + props.getProperty("os.name") + "(" + props.getProperty("os.arch") + ")");
                out.println(" * @creator: " + props.getProperty("user.name") + "(" + props.getProperty("user.country") + ")");
            }
            out.println(" */");
            // @Begin
            out.println("public class " + ClassName + " extends " + SetName + "{");
            // ????????????????????????
            field(out);
            out.println();
            // ???????????????
            Constructor(out, TableName, ClassName);
            out.println();
            // ????????????
            out.println(space4_1 + "// @Method");
            //deleteSQL??????
            if (!tTable.haveLargeObjInPK()) {
                deleteSQL(out, ClassName);
                out.println();
            }
            // 2005-11-11 Kevin
            // ????????????????????????WHERE????????????????????????????????????????????????????????????
            StringBuffer sbPKWhereClause = new StringBuffer(50);
            Key t_PK = tTable.getPrimaryKey();
            if (t_PK != null) {
                for (int i = 0; i < t_PK.getColumnNum(); i++) {
                    String f = t_PK.getColumn(i);
                    sbPKWhereClause.append(" AND ").append(f).append(" = ?");
                }
                sbPKWhereClause.delete(0, 4);// get rid of the
                // first " AND"
                // part.
            }
            if (sbPKWhereClause.length() == 0) {
                sbPKWhereClause.append("1 = 1");
            }
            // ??????UPDATE???????????????????????????????????????????????????ContNo = ? , PolNo = ?
            StringBuffer sbInsertColumnClause = new StringBuffer(300);
            StringBuffer sbUpdateColumnClause = new StringBuffer(300);
            for (int i = 0; i < tTable.getColumnNum(); i++) {
                Column f = tTable.getColumn(i);
                sbInsertColumnClause.append(" , ?");
                sbUpdateColumnClause.append(" , ").append(f.getCode()).append(" = ?");
            }
            sbInsertColumnClause.delete(0, 2);// get rid of the first " ," part.
            sbUpdateColumnClause.delete(0, 2);// get rid of the first " ," part.
            // 2005-11-14 Kevin
            // ??????insert???????????????preparedStatement?????????
            if (!tTable.haveLargeObj()) {
                insert(out, tTable, ClassName, sbInsertColumnClause);
                out.println();
            }
            //2006-05-25 xijiahui?????????????????????????????????Java????????????????????????
            if (t_PK != null && t_PK.getColumnNum() > 0) {
                // 2005-11-14 Kevin
                // ??????delete???????????????preparedStatement?????????
                if (!tTable.haveLargeObjInPK()) {
                    delete(out, tTable, ClassName, sbPKWhereClause);
                    out.println();
                }
                // 2005-11-14 Kevin
                // ??????update???????????????preparedStatement?????????
                if (!tTable.haveLargeObj()) {
                    update(out, tTable, ClassName, sbPKWhereClause, sbUpdateColumnClause);
                }
            }
            // ????????????
            out.println("}");
        } // end of try
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void field(PrintWriter out) {

        out.println(space4_1 + "private final Logger logger = Logger.getLogger(getClass().toString());");

        out.println(space4_1 + "// @Field");
        out.println(space4_1 + "private Connection con;");
        out.println(space4_1 + "private DBOper db;");
        out.println(space4_1 + "private static final int BATCH_SIZE = 300;");
        out.println(space4_1 + "/**");
        out.println(space4_1 + " * flag = true: ??????Connection");
        out.println(space4_1 + " * flag = false: ?????????Connection");
        out.println(space4_1 + " */");
        out.println(space4_1 + "private boolean mflag = false;");
    }


    private void Constructor(PrintWriter out, String TableName, String ClassName) {
        out.println(space4_1 + "// @Constructor");
        out.println(space4_1 + "public " + ClassName + "(Connection cConnection){");
        out.println(space4_2 + "con = cConnection;");
        out.println(space4_2 + "db = new DBOper(con, \"" + TableName + "\");");
        out.println(space4_2 + "mflag = true;");
        out.println(space4_1 + "}");
        out.println();
        out.println(space4_1 + "public " + ClassName + "(){");
        out.println(space4_2 + "db = new DBOper(\"" + TableName + "\");");
        out.println(space4_1 + "}");
    }


    private void deleteSQL(PrintWriter out, String ClassName) {
        out.println(space4_1 + "public boolean deleteSQL(){");
        out.println(space4_2 + "if(db.deleteSQL(this)){");
        out.println(space4_3 + "return true;");
        out.println(space4_2 + "}");
        out.println(space4_2 + "else{");
        out.println(space4_3 + "// @@????????????");
        out.println(space4_3 + "this.mErrors.copyAllErrors(db.mErrors);");
        out.println(space4_3 + "CError tError = new CError();");
        out.println(space4_3 + "tError.moduleName = \"" + ClassName + "\";");
        out.println(space4_3 + "tError.functionName = \"deleteSQL\";");
        out.println(space4_3 + "tError.errorMessage = \"????????????!\";");
        out.println(space4_3 + "this.mErrors.addOneError(tError);");
        out.println(space4_3 + "return false;");
        out.println(space4_2 + "}");
        out.println(space4_1 + "}");
    }


    private void delete(PrintWriter out, Table tTable, String ClassName, StringBuffer sbPKWhereClause) throws Exception {
        int nParamIndex = 1;
        out.println(space4_1 + "/**");
        out.println(space4_1 + " * ????????????");
        out.println(space4_1 + " * ?????????????????????");
        out.println(space4_1 + " * @return boolean");
        out.println(space4_1 + " */");
        out.println(space4_1 + "public boolean delete(){");
        operHead(out);
        out.println(space4_3 + "String sql = \"DELETE FROM " + tTable.getCode() + " WHERE " + sbPKWhereClause + "\";");
        out.println(space4_3 + "pstmt = con.prepareStatement(sql);");

        out.println(space4_3 + "StringBuilder paramStringBuilder = new StringBuilder();");
        out.println(space4_3 + "paramStringBuilder.append(\"(\");");

        out.println(space4_3 + "for(int i = 1; i <= tCount; i++)");
        out.println(space4_3 + "{");
        Key t_PK = tTable.getPrimaryKey();
        if (t_PK != null) {
            // ???????????????????????????
            nParamIndex = 1;
            for (int i = 0; i < t_PK.getColumnNum(); i++) {
                String f_PK = t_PK.getColumn(i);
                for (int j = 0; j < tTable.getColumnNum(); j++) {
                    if (f_PK.equals(tTable.getColumn(j).getCode())) {
                        Column f = tTable.getColumn(j);
                        genSetParamString(f, nParamIndex++, "pstmt", out, space4_4, true);
                        break;
                    }
                }
            }
        }
        operTail(out, ClassName, "delete", tTable.getCode());
    }


    private void update(PrintWriter out, Table tTable, String ClassName,
                        StringBuffer sbPKWhereClause, StringBuffer sbUpdateColumnClause) throws Exception {
        out.println(space4_1 + "/**");
        out.println(space4_1 + " * ????????????");
        out.println(space4_1 + " * ?????????????????????");
        out.println(space4_1 + " * @return boolean");
        out.println(space4_1 + " */");
        out.println(space4_1 + "public boolean update(){");
        operHead(out);
        out.println(space4_3 + "String sql = \"UPDATE " + tTable.getCode() + " SET " + sbUpdateColumnClause + " WHERE " + sbPKWhereClause + "\";");
        out.println(space4_3 + "pstmt = con.prepareStatement(sql);");

        out.println(space4_3 + "StringBuilder paramStringBuilder = new StringBuilder();");
        out.println(space4_3 + "paramStringBuilder.append(\"(\");");

        out.println(space4_3 + "for(int i = 1; i <= tCount; i++){");
        // ??????????????????????????????
        int nParamIndex = 1;
        for (int i = 0; i < tTable.getColumnNum(); i++) {
            Column f = tTable.getColumn(i);
            genSetParamString(f, nParamIndex++, "pstmt", out, space4_4, false);
        }
        Key t_PK = tTable.getPrimaryKey();
        if (t_PK != null) {
            for (int i = 0; i < t_PK.getColumnNum(); i++) {
                String f_PK = t_PK.getColumn(i);
                for (int j = 0; j < tTable.getColumnNum(); j++) {
                    if (f_PK.equals(tTable.getColumn(j).getCode())) {
                        Column f = tTable.getColumn(j);
                        genSetParamString(f, nParamIndex++, "pstmt", out, space4_4, true);
                        break;
                    }
                }
            }
        }
        operTail(out, ClassName, "update", tTable.getCode());
    }


    private void insert(PrintWriter out, Table tTable, String ClassName, StringBuffer sbInsertColumnClause) throws Exception {
        out.println(space4_1 + "/**");
        out.println(space4_1 + " * ????????????");
        out.println(space4_1 + " * @return boolean");
        out.println(space4_1 + " */");
        out.println(space4_1 + "public boolean insert(){");
        operHead(out);
        out.println(space4_3 + "String sql = \"INSERT INTO " + tTable.getCode() + " VALUES (" + sbInsertColumnClause + ")\";");
        out.println(space4_3 + "pstmt = con.prepareStatement(sql);");

        out.println(space4_3 + "StringBuilder paramStringBuilder = new StringBuilder();");
        out.println(space4_3 + "paramStringBuilder.append(\"(\");");

        out.println(space4_3 + "for(int i = 1; i <= tCount; i++){");

        // ??????????????????????????????
        int nParamIndex = 1;
        for (int i = 0; i < tTable.getColumnNum(); i++) {
            Column f = tTable.getColumn(i);
            genSetParamString(f, nParamIndex++, "pstmt", out, space4_4, false);
        }
        operTail(out, ClassName, "insert", tTable.getCode());

    }


    private void operHead(PrintWriter out) {
        out.println(space4_2 + "PreparedStatement pstmt = null;");
        out.println(space4_2 + "if(!mflag){");
        out.println(space4_3 + "con = DBConnPool.getConnection();");
        out.println(space4_2 + "}");
        out.println(space4_2 + "try{");
        out.println(space4_3 + "if(!mflag){");
        out.println(space4_4 + "// ?????????????????????????????????????????????Commit??????");
        out.println(space4_4 + "con.setAutoCommit(false);");
        out.println(space4_3 + "}");
        out.println(space4_3 + "int tCount = this.size();");
        out.println(space4_3 + "int hasExeCount = 0;");
    }


    private void operTail(PrintWriter out, String ClassName, String oper, String TableName) {
        out.println(space4_4 + "pstmt.addBatch();");
        out.println(space4_4 + "hasExeCount++;");
        out.println(space4_4 + "if (hasExeCount % BATCH_SIZE == 0){");

        out.println(space4_5 + "String params = paramStringBuilder.substring(0, paramStringBuilder.lastIndexOf(\",\"));");
        out.println(space4_5 + "params += \")\";");
        out.println(space4_5 + "logger.info(\"exeSql:\" + sql + \"\\n\" + \"exeSqlParam:\" + params);");

        out.println(space4_5 + "pstmt.executeBatch();");
        out.println(space4_4 + "}");
        out.println(space4_3 + "}");

        out.println(space4_3 + "String params = paramStringBuilder.substring(0, paramStringBuilder.lastIndexOf(\",\"));");
        out.println(space4_3 + "params += \")\";");
        out.println(space4_3 + "logger.info(\"exeSql: \" + sql + \"\\n\" + \"exeSqlParam: \" + params);");

        out.println(space4_3 + "pstmt.executeBatch();");
        out.println(space4_3 + "pstmt.close();");
        out.println(space4_3 + "if(!mflag){");
        out.println(space4_4 + "// ????????????????????????????????????????????????????????????Commit");
        out.println(space4_4 + "con.commit();");
        out.println(space4_4 + "con.close();");
        out.println(space4_3 + "}");
        out.println(space4_2 + "}");
        out.println(space4_2 + "catch(Exception ex){");
        out.println(space4_3 + "// @@????????????");
        out.println(space4_3 + "ex.printStackTrace();");
        out.println(space4_3 + "this.mErrors.copyAllErrors(db.mErrors);");
        out.println(space4_3 + "CError tError = new CError();");
        out.println(space4_3 + "tError.moduleName = \"" + ClassName + "\";");
        out.println(space4_3 + "tError.functionName = \"" + oper + "()\";");
        out.println(space4_3 + "tError.errorMessage = ex.toString();");
        out.println(space4_3 + "this.mErrors.addOneError(tError);");
        out.println(space4_3 + "int tCount = this.size();");
        out.println(space4_3 + "SQLString sqlObj = new SQLString(\"" + TableName + "\");");
        out.println(space4_3 + "for(int i = 1; i <= tCount; i++){");
        out.println(space4_4 + "// ????????????Sql??????");
        if (oper.equals("delete")) {
            out.println(space4_4 + "sqlObj.setSQL(4, this.get(i));");
        } else if (oper.equals("update")) {
            out.println(space4_4 + "sqlObj.setSQL(2, this.get(i));");
        } else {
            out.println(space4_4 + "sqlObj.setSQL(1, this.get(i));");
        }
        out.println(space4_4 + "System.out.println(\"??????Sql??????\" + sqlObj.getSQL());");
        out.println(space4_3 + "}");
        out.println(space4_3 + "try{pstmt.close();}catch(Exception e){e.printStackTrace();}");
        out.println(space4_3 + "if(!mflag){");
        out.println(space4_4 + "//??????????????????????????????????????????????????????RollBack");
        out.println(space4_4 + "try{");
        out.println(space4_5 + "con.rollback();");
        out.println(space4_5 + "con.close();");
        out.println(space4_4 + "}");
        out.println(space4_4 + "catch(Exception e){");
        out.println(space4_5 + "e.printStackTrace();");
        out.println(space4_4 + "}");
        out.println(space4_3 + "}");
        out.println(space4_3 + "return false;");
        out.println(space4_2 + "}");
        out.println(space4_2 + "finally{");
        out.println(space4_3 + "if(!mflag){");
        out.println(space4_4 + "try{");
        //xijiahui 2006-07-07 finally???????????????????????????????????????JDBC???????????????????????????Sql Server???jDts??????
        if (MultiCloseConn) {
            out.println(space4_5 + "con.close();");
        } else {
            out.println(space4_5 + "if(con.isClosed() == false){");
            out.println(space4_6 + "con.close();");
            out.println(space4_5 + "}");
        }
        out.println(space4_4 + "}");
        out.println(space4_4 + "catch(Exception e){");
        out.println(space4_5 + "e.printStackTrace();");
        out.println(space4_4 + "}");
        out.println(space4_3 + "}");
        out.println(space4_2 + "}");
        out.println(space4_2 + "return true;");
        out.println(space4_1 + "}");
    }


    private void genSetParamString(Column ColumnInfo, int nParamIndex, String strStatementVar,
                                   PrintWriter out, String strAppend, boolean bWherePart) throws Exception {
        int nColumnType = ColumnInfo.getDBSqlType();

        if (strStatementVar == null || strStatementVar.equals("")) {
            strStatementVar = "pstmt";
        }
        switch (nColumnType) {
            case SQLTypes.CHAR:
                out.println(strAppend + "if(this.get(i).get" + ColumnInfo.getCode() + "() == null || this.get(i).get" + ColumnInfo.getCode() + "().equals(\"null\")){");
                out.println(strAppend + space4_1 + strStatementVar + ".setString(" + String.valueOf(nParamIndex) + ", null);");

                out.println(strAppend + space4_1 + "paramStringBuilder.append(\"null\").append(\",\");");

                out.println(strAppend + "}");
                out.println(strAppend + "else{");
                if (bWherePart) {
                    out.println(strAppend + space4_1 + strStatementVar + ".setString(" + String.valueOf(nParamIndex) + ", StrTool.space(this.get(i).get" + ColumnInfo.getCode() + "(), " + ColumnInfo.getDBLength() + "));");

                    out.println(strAppend + space4_1 + "paramStringBuilder.append(StrTool.space(this.get(i).get" + ColumnInfo.getCode() + "()," + ColumnInfo.getDBLength() + ")).append(\",\");");
                } else {
                    out.println(strAppend + space4_1 + strStatementVar + ".setString(" + String.valueOf(nParamIndex) + ", this.get(i).get" + ColumnInfo.getCode() + "());");

                    out.println(strAppend + space4_1 + "paramStringBuilder.append(this.get(i).get" + ColumnInfo.getCode() + "()).append(\",\");");
                }
                out.println(strAppend + "}");
                break;
            case SQLTypes.VARCHAR:
            case SQLTypes.TIMESTAMP: //For SQL Server
                out.println(strAppend + "if(this.get(i).get" + ColumnInfo.getCode() + "() == null || this.get(i).get" + ColumnInfo.getCode() + "().equals(\"null\")){");
                out.println(strAppend + space4_1 + strStatementVar + ".setString(" + String.valueOf(nParamIndex) + ", null);");

                out.println(strAppend + space4_1 + "paramStringBuilder.append(\"null\").append(\",\");");

                out.println(strAppend + "}");
                out.println(strAppend + "else{");
                out.println(strAppend + space4_1 + strStatementVar + ".setString(" + String.valueOf(nParamIndex) + ", this.get(i).get" + ColumnInfo.getCode() + "());");

                out.println(strAppend + space4_1 + "paramStringBuilder.append(this.get(i).get" + ColumnInfo.getCode() + "()).append(\",\");");

                out.println(strAppend + "}");
                break;
            case SQLTypes.LONGVARCHAR: //For SQL Server???Text??????(MS SQL Server JDBC 2005?????????)???Oracle??????Long??????
            case SQLTypes.CLOB: //For SQL Server???Text??????(jTds?????????)
                out.println(strAppend + "if(this.get(i).get" + ColumnInfo.getCode() + "() == null || this.get(i).get" + ColumnInfo.getCode() + "().equals(\"null\")){");
                out.println(strAppend + space4_1 + strStatementVar + ".setString(" + String.valueOf(nParamIndex) + ", null);");

                out.println(strAppend + space4_1 + "paramStringBuilder.append(\"null\").append(\",\");");

                out.println(strAppend + "}");
                out.println(strAppend + "else{");
                if (DBType == DBConst.DB_SQLServer) {
                    out.println(strAppend + space4_1 + strStatementVar + ".setString(" + String.valueOf(nParamIndex) + ", this.get(i).get" + ColumnInfo.getCode() + "());");

                    out.println(strAppend + space4_1 + "paramStringBuilder.append(this.get(i).get" + ColumnInfo.getCode() + "()).append(\",\");");

                } else if (DBType == DBConst.DB_Oracle) {
                    out.println(strAppend + space4_1 + "StringReader strReader = new StringReader(this.get(i).get" + ColumnInfo.getCode() + "());");
                    out.println(strAppend + space4_1 + strStatementVar + ".setCharacterStream(" + String.valueOf(nParamIndex) + ",strReader,this.get(i).get" + ColumnInfo.getCode() + "().length());");

                    out.println(strAppend + space4_1 + "paramStringBuilder.append(this.get(i).get" + ColumnInfo.getCode() + "()).append(\",\");");
                }
                out.println(strAppend + "}");
                break;
            case SQLTypes.DATE:
                out.println(strAppend + "if(this.get(i).get" + ColumnInfo.getCode() + "() == null || this.get(i).get" + ColumnInfo.getCode() + "().equals(\"null\")){");
                out.println(strAppend + space4_1 + strStatementVar + ".setDate(" + String.valueOf(nParamIndex) + ", null);");

                out.println(strAppend + space4_1 + "paramStringBuilder.append(\"null\").append(\",\");");

                out.println(strAppend + "}");
                out.println(strAppend + "else{");
                out.println(strAppend + space4_1 + strStatementVar + ".setDate(" + String.valueOf(nParamIndex) + ", Date.valueOf(this.get(i).get" + ColumnInfo.getCode() + "()));");

                out.println(strAppend + space4_1 + "paramStringBuilder.append(Date.valueOf(this.get(i).get" + ColumnInfo.getCode() + "())).append(\",\");");

                out.println(strAppend + "}");
                break;
            case SQLTypes.INTEGER:
            case SQLTypes.SMALLINT:
            case SQLTypes.TINYINT:
                out.println(strAppend + strStatementVar + ".setInt(" + String.valueOf(nParamIndex) + ", this.get(i).get" + ColumnInfo.getCode() + "());");

                out.println(strAppend + "paramStringBuilder.append(this.get(i).get" + ColumnInfo.getCode() + "()).append(\",\");");

                break;
            case SQLTypes.NUMERIC:
            case SQLTypes.DOUBLE:
            case SQLTypes.DECIMAL:
            case SQLTypes.REAL:
                out.println(strAppend + strStatementVar + ".setDouble(" + String.valueOf(nParamIndex) + ", this.get(i).get" + ColumnInfo.getCode() + "());");

                out.println(strAppend + "paramStringBuilder.append(this.get(i).get" + ColumnInfo.getCode() + "()).append(\",\");");

                break;
            default:
                System.out.println(ColumnInfo.getCode() + " is unsupported Column type");
        }
    }

}
