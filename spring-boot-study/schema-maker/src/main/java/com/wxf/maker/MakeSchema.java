package com.wxf.maker;


import com.wxf.table.Column;
import com.wxf.table.Key;
import com.wxf.table.Table;
import com.wxf.utility.DBConst;
import com.wxf.utility.SQLTypes;
import com.wxf.utility.SysConst;
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
public class MakeSchema {

    private String packageName;
    private String schemaOutputPATH;
    private String outputPackagePATH;
    private final int DBType = DBConst.DB_UnSupported;
    private boolean mFlag = false;
    private static final String space4_1 = "    ";
    private static final String space4_2 = space4_1 + space4_1;
    private static final String space4_3 = space4_2 + space4_1;
    private static final String space4_4 = space4_3 + space4_1;
    private static final String space4_5 = space4_4 + space4_1;
    private static final String newline = "\r\n";
    private String DBName;
    private boolean UserInfo = false;

    public MakeSchema() {
    }

    public MakeSchema(String packageName, String schemaOutputPATH, String outputPackagePATH, String DBName) {
        this.packageName = packageName;
        this.schemaOutputPATH = schemaOutputPATH;
        this.outputPackagePATH = outputPackagePATH;
        this.DBName = DBName;
    }

    public void setUserInfo(boolean UserInfo) {
        this.UserInfo = UserInfo;
    }


    private String getTimestamp() {
        String pattern = "yyyy-MM-dd HH:mm:ss SSS";
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Date today = new Date();
        return df.format(today);
    }

    public void create(Table tTable) {

        String TableName = tTable.getCode();
        PrintWriter out = null;
        String Path = null;
        Path = schemaOutputPATH + outputPackagePATH + "schema/";
        String ClassName = TableName + "Schema";
        String DBOperName = TableName + "DB";
        String FileName = ClassName + ".java";

        try {
            Key t_PK = tTable.getPrimaryKey();
            //????????????
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
            out.println("package " + packageName + ".schema;");
            out.println();
            // @Import
            out.println("import java.sql.ResultSet;");
            out.println("import java.sql.SQLException;");
            out.println("import java.sql.Types;");
            out.println("import java.io.InputStream;");
            out.println("import java.util.Date;");
            out.println();
            out.println("import com.sinosoft.utility.ChgData;");
            out.println("import com.sinosoft.utility.SQLString;");
            out.println("import com.sinosoft.utility.StrTool;");
            out.println("import com.sinosoft.utility.SysConst;");
            out.println("import com.sinosoft.utility.Schema;");
            out.println("import com.sinosoft.utility.CError;");
            out.println("import com.sinosoft.utility.CErrors;");
            out.println("import com.sinosoft.lis.pubfun.Arith;");
            out.println("import com.sinosoft.lis.pubfun.FDate;");
            //out.println("import " + packageName + ".db." + DBOperName + ";");
            out.println();
            // ?????????
            out.println("/**");
            out.println(" * <p>?????????????????????????????????????????????</p>");
            out.println(" * <p>ClassName: " + ClassName + " </p>");
            out.println(" * <p>Description: DB??? Schema ????????? </p>");
            out.println(" * <p>Company: Sinosoft Co.,LTD </p>");
            out.println(" * @Database: " + DBName);
            out.println(" * @author: Makerx");
            out.println(" * @CreateDatetime: " + getTimestamp());
            if (UserInfo) {
                Properties props = System.getProperties();
                out.println(" * @vm: " + props.getProperty("java.vm.name") + "(build " + props.getProperty("java.vm.version") + ", " + props.getProperty("java.vm.vendor") + ")");
                out.println(" * @o: " + props.getProperty("os.name") + "(" + props.getProperty("os.arch") + ")");
                out.println(" * @creator: " + props.getProperty("user.name") + "(" + props.getProperty("user.country") + ")");
            }
            out.println(" */");
            // @Begin
            out.println("public class " + ClassName + " implements Schema, Cloneable{");
            // ????????????????????????
            Field(out, tTable);
            out.println();
            out.println(space4_1 + "public static final int FIELDNUM = " + tTable.getColumnNum() + "; // ???????????????????????????");
            if (t_PK != null && t_PK.getColumnNum() > 0) {
                out.println();
                out.println(space4_1 + "private static String[] PK; // ??????");
            }
            if (mFlag) {
                out.println();
                out.println(space4_1 + "private FDate fDate = new FDate(); // ????????????");
            }
            out.println();
            out.println(space4_1 + "public CErrors mErrors; // ????????????");
            out.println();
            // ???????????????
            Constructor(out, tTable, ClassName);
            out.println();
            // ??????clone??????
            clone(out, ClassName);
            out.println();
            // ?????? getPK ??????
            getPK(out, tTable);
            out.println();
            // ?????? set???get ???????????????
            getANDset(out, tTable);
            // ?????? setSchema ??????
            //out.println();
            SetSchemaBySchema(out, tTable, ClassName);
            out.println();
            //?????? setSchema ??????
            setSchemaByRS(out, tTable, ClassName);
            out.println();
            //?????? getSchema ??????,???????????????????????????????????????
            getSchema(out, ClassName);
            out.println();
            //getDB????????????????????????Schema???DB??????????????????????????????????????????????????? 2012-03-19
            //?????? getDB ??????,???????????????????????????????????????
            //getDB(out, DBOperName);
            out.println();
            // ?????? encode ??????
            encode(out, tTable);
            out.println();
            // ?????? decode ??????
            decode(out, tTable, ClassName);
            out.println();
            // ?????? getV ??????
            getVByCode(out, tTable);
            out.println();
            // ?????? getV ??????
            getVByIndex(out, tTable);
            out.println();
            // if (false) {
            //     // ?????? getVx ??????
            //     getVxByCode(out, tTable);
            //     out.println();
            //     // ?????? getVx ??????
            //     getVxByIndex(out, tTable);
            //     out.println();
            // }
            // ?????? setV ??????
            setV(out, tTable);
            out.println();
            // ?????? equals ??????
            equals(out, tTable, ClassName);
            out.println();
            // ?????? getFieldCount ??????
            getFieldCount(out);
            out.println();
            // ?????? int getFieldIndex(String strFieldName) ??????
            getFieldIndex(out, tTable);
            out.println();
            // ?????? String getFieldName(int nFieldIndex) ??????
            getFieldName(out, tTable);
            out.println();
            // ?????? int getFieldType(String strFieldName) ??????
            getFieldTypeByName(out, tTable);
            out.println();
            // ?????? int getFieldType(int nFieldIndex) ??????
            getFieldTypeByIndex(out, tTable);
            // ????????????
            out.println("}");
            mFlag = false;
        } // end of try
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void Field(PrintWriter out, Table tTable) {
        out.println(space4_1 + "// @Field");
        for (int i = 0; i < tTable.getColumnNum(); i++) {
            Column f = tTable.getColumn(i);
            String FieldName = f.getName();
            String FieldCode = f.getCode();
            String FieldType = f.getDataType();
            if (FieldType.equals("Date")) {
                mFlag = true;
            }
            out.println(space4_1 + "/** " + FieldName + " */");
            out.println(space4_1 + "private " + FieldType + " " + FieldCode + ";");
        }
    }

    private void Constructor(PrintWriter out, Table tTable, String ClassName) {
        Key tPK = tTable.getPrimaryKey();
        out.println(space4_1 + "// @Constructor");
        out.println(space4_1 + "public " + ClassName + "()");
        out.println(space4_1 + "{");
        out.println(space4_2 + "mErrors = new CErrors();");
        if (tPK != null && tPK.getColumnNum() > 0) {
            out.println(space4_2 + "String[] pk = new String[" +
                    tPK.getColumnNum() + "];");
            for (int i = 0; i < tPK.getColumnNum(); i++) {
                out.println(space4_2 + "pk[" + i + "] = \"" + tPK.getColumn(i) +
                        "\";");
            }
            out.println(space4_2 + "PK = pk;");
        }
        out.println(space4_1 + "}");
    }

    private void clone(PrintWriter out, String ClassName) {
        out.println(space4_1 + "/**");
        out.println(space4_1 + " * Schema??????");
        out.println(space4_1 + " * @return Object");
        out.println(space4_1 + " * @throws CloneNotSupportedException");
        out.println(space4_1 + " */");
        out.println(space4_1 +
                "public Object clone() throws CloneNotSupportedException{");
        out.println(space4_2 + ClassName + " cloned = (" + ClassName +
                ")super.clone();");
        if (mFlag) {
            out.println(space4_2 + "cloned.fDate = (FDate) fDate.clone();");
        }
        out.println(space4_2 + "cloned.mErrors = (CErrors) mErrors.clone();");
        out.println(space4_2 + "return cloned;");
        out.println(space4_1 + "}");
    }

    private void getPK(PrintWriter out, Table tTable) {
        Key tPK = tTable.getPrimaryKey();
        out.println(space4_1 + "// @Method");
        out.println(space4_1 + "public String[] getPK(){");
        if (tPK != null && tPK.getColumnNum() > 0) {
            out.println(space4_2 + "return PK;");
        } else {
            out.println(space4_2 + "return null;");
        }
        out.println(space4_1 + "}");
    }

    private void getANDset(PrintWriter out, Table tTable) {
        for (int i = 0; i < tTable.getColumnNum(); i++) {
            Column f = tTable.getColumn(i);
            String FieldCode = f.getCode();
            String FieldType = f.getDataType();
            int FieldPrecision = f.getPrecision();
            // get??????
            if (FieldType.equals("Date")) {
                // ????????????
                out.println(space4_1 + "public String get" + FieldCode + "(){");
                out.println(space4_2 + "if(" + FieldCode + " != null)");
                out.println(space4_3 + "return fDate.getString(" + FieldCode + ");");
                out.println(space4_2 + "else");
                out.println(space4_3 + "return null;");
                out.println(space4_1 + "}");
                out.println();
            } else if (FieldType.equals("InputStream")) {
                // ????????????
                out.println(space4_1 + "public InputStream get" + FieldCode + "(){");
                out.println(space4_2 + "return " + FieldCode + ";");
                out.println(space4_1 + "}");
                out.println();
            } else {
                out.println(space4_1 + "public " + FieldType + " get" + FieldCode + "(){");
                if (FieldType.equals("String")) {
                    if (SysConst.CHANGECHARSET) {
                        out.println(space4_2 + "if(SysConst.CHANGECHARSET && " + FieldCode + " != null && !" + FieldCode + ".equals(\"\")){");
                        out.println(space4_3 + "" + FieldCode + " = StrTool.unicodeToGBK(" + FieldCode + ");");
                        out.println(space4_2 + "}");
                    }
                }
                out.println(space4_2 + "return " + FieldCode + ";");
                out.println(space4_1 + "}");
                out.println();
            }
            // set??????
            out.println(space4_1 + "public void set" + FieldCode + "(" + FieldType + " a" + FieldCode + "){");
//            if (FieldType.equals("String") && f.getLength() > 0)
//            {
//              out.println("\t\tif(a" + FieldCode + "!=null && a" + FieldCode + ".length()>" + f.getLength() + ")");
//              out.println("\t\t\tthrow new IllegalArgumentException(\"" + f.getName() + FieldCode + "???\"+a" + FieldCode + "+\"?????????\"+a" + FieldCode + ".length()+\"???????????????" + f.getLength() + "\");");
//            }
            // ?????????????????????????????????????????????????????????????????????????????????
            if (FieldType.equals("double")) {
                // ?????????????????????????????????????????????????????????
                if (DBType == DBConst.DB_Oracle || DBType == DBConst.DB_SQLServer) {
                    out.println(space4_2 + FieldCode + " = a" + FieldCode + ";");
                } else {
                    out.println(space4_2 + FieldCode + " = Arith.round(a" + FieldCode + "," + FieldPrecision + ");");
                }
            } else {
                out.println(space4_2 + FieldCode + " = a" + FieldCode + ";");
            }
            out.println(space4_1 + "}");
            out.println();
            if (FieldType.equals("int")) {
                out.println(space4_1 + "public void set" + FieldCode + "(Integer a" + FieldCode + "){");
                out.println(space4_2 + "if(a" + FieldCode + " != null){");
                out.println(space4_3 + FieldCode + " = a" + FieldCode + ".intValue();");
                out.println(space4_2 + "}");
                out.println(space4_1 + "}");
                out.println();
            }
            if (FieldType.equals("double")) {
                out.println(space4_1 + "public void set" + FieldCode + "(Double a" + FieldCode + "){");
                out.println(space4_2 + "if(a" + FieldCode + " != null){");
                out.println(space4_3 + FieldCode + " = a" + FieldCode + ".doubleValue();");
                out.println(space4_2 + "}");
                out.println(space4_1 + "}");
                out.println();
            }
            if (FieldType.equals("Integer")) {
                out.println(space4_1 + "public void set" + FieldCode + "(int a" + FieldCode + "){");
                out.println(space4_2 + FieldCode + " = new Integer(a" + FieldCode + ");");
                out.println(space4_1 + "}");
                out.println();
            }
            if (FieldType.equals("Double")) {
                out.println(space4_1 + "public void set" + FieldCode + "(double a" + FieldCode + "){");
                out.println(space4_2 + FieldCode + " = new Double(a" + FieldCode + ");");
                out.println(space4_1 + "}");
                out.println();
            }
            // ??????????????? String ?????? Set ?????????
            if (!FieldType.equals("String")) {
                out.println(space4_1 + "public void set" + FieldCode + "(String a" + FieldCode + "){");

                if (FieldType.equals("float")) {
                    out.println(space4_2 + "if(a" + FieldCode + " != null && !a" + FieldCode + ".equals(\"\")){");
                    out.println(space4_3 + "Float tFloat = new Float(a" + FieldCode + ");");
                    out.println(space4_3 + "float f = tFloat.floatValue();");
                    out.println(space4_3 + FieldCode + " = f;");
                    out.println(space4_2 + "}");
                }
                if (FieldType.equals("double")) {
                    // add by yt 2003-6-20
                    out.println(space4_2 + "if(a" + FieldCode + " != null && !a" + FieldCode + ".equals(\"\")){");
                    out.println(space4_3 + "Double tDouble = new Double(a" + FieldCode + ");");
                    // ?????????????????????????????????????????????????????????????????????????????????
                    // ?????????????????????????????????????????????????????????
                    if (DBType == DBConst.DB_Oracle || DBType == DBConst.DB_SQLServer) {
                        out.println(space4_3 + FieldCode + " = tDouble.doubleValue();");
                    } else {
                        out.println(space4_3 + "double d = tDouble.doubleValue();");
                        out.println(space4_3 + FieldCode + " = Arith.round(d," + FieldPrecision + ");");
                    }
                    out.println(space4_2 + "}");
                }
                if (FieldType.equals("int")) {
                    out.println(space4_2 + "if(a" + FieldCode + " != null && !a" + FieldCode + ".equals(\"\")){");
                    out.println(space4_3 + "Integer tInteger = new Integer(a" + FieldCode + ");");
                    out.println(space4_3 + "int i = tInteger.intValue();");
                    out.println(space4_3 + "" + FieldCode + " = i;");
                    out.println(space4_2 + "}");
                }
                if (FieldType.equals("Date")) {
                    out.println(space4_2 + "if(a" + FieldCode + " != null && !a" + FieldCode + ".equals(\"\")){");
                    out.println(space4_3 + FieldCode + " = fDate.getDate(a" + FieldCode + ");");
                    out.println(space4_2 + "}");
                    out.println(space4_2 + "else");
                    out.println(space4_3 + FieldCode + " = null;");
                }
                if (FieldType.equals("Integer")) {
                    out.println(space4_2 + "if(a" + FieldCode + " != null && !a" + FieldCode + ".equals(\"\")){");
                    out.println(space4_3 + FieldCode + " = new Integer(a" + FieldCode + ");");
                    out.println(space4_2 + "}");
                }
                if (FieldType.equals("Double")) {
                    // add by yt 2003-6-20
                    out.println(space4_2 + "if(a" + FieldCode + " != null && !a" + FieldCode + ".equals(\"\")){");
                    out.println(space4_3 + FieldCode + " = new Double(a" + FieldCode + ");");
                    out.println(space4_2 + "}");
                }
                out.println(space4_1 + "}");
                out.println();
            }
        }
    }

    private void SetSchemaBySchema(PrintWriter out, Table tTable, String ClassName) {
        out.println(space4_1 + "/**");
        out.println(space4_1 + " * ?????????????????? " + ClassName + " ????????? Schema ??????");
        out.println(space4_1 + " * @param: a" + ClassName + " " + ClassName);
        out.println(space4_1 + " **/");
        out.println(space4_1 + "public void setSchema(" + ClassName + " a" + ClassName + "){");
        for (int i = 0; i < tTable.getColumnNum(); i++) {
            Column f = tTable.getColumn(i);
            String FieldCode = f.getCode();
            String FieldType = f.getDataType();
            if (FieldType.equals("Date")) {
                out.println(space4_2 + "this." + FieldCode + " = fDate.getDate(a" + ClassName + ".get" + FieldCode + "());");
            } else {
                out.println(space4_2 + "this." + FieldCode + " = a" + ClassName + ".get" + FieldCode + "();");
            }
        }
        out.println(space4_1 + "}");
    }

    private void setSchemaByRS(PrintWriter out, Table tTable, String ClassName) {
        out.println(space4_1 + "/**");
        out.println(space4_1 + " * ?????? ResultSet ????????? i ?????? Schema ??????");
        out.println(space4_1 + " * @param: rs ResultSet");
        out.println(space4_1 + " * @param: i int");
        out.println(space4_1 + " * @return: boolean");
        out.println(space4_1 + " **/");
        out.println(space4_1 + "public boolean setSchema(ResultSet rs, int i){");
        out.println(space4_2 + "try{");
        out.println(space4_3 + "//rs.absolute(i); // ???????????????");
        for (int i = 0; i < tTable.getColumnNum(); i++) {
            // ????????????????????????????????????????????????????????????
            Column f = tTable.getColumn(i);
            String FieldCode = f.getCode();
            String FieldType = f.getDataType();
            if (FieldType.equals("float")) {
                out.println(space4_3 + "this." + FieldCode + " = rs.getFloat(" + String.valueOf(i + 1) + ");");
            }
            if (FieldType.equals("double")) {
                out.println(space4_3 + "this." + FieldCode + " = rs.getDouble(" + String.valueOf(i + 1) + ");");
            }
            if (FieldType.equals("int")) {
                out.println(space4_3 + "this." + FieldCode + " = rs.getInt(" + String.valueOf(i + 1) + ");");
            }
            if (FieldType.equals("String")) {
                if (DBType == DBConst.DB_Oracle && f.getDBSqlType() == SQLTypes.LONGVARCHAR) { //?????????????????????????????????
                    out.println(space4_3 + "this." + FieldCode + " = rs.getString(" + String.valueOf(i + 1) + ");");
                } else {
                    out.println(space4_3 + "if(rs.getString(" + String.valueOf(i + 1) + ") == null)");
                    out.println(space4_4 + "this." + FieldCode + " = null;");
                    out.println(space4_3 + "else");
                    out.println(space4_4 + "this." + FieldCode + " = rs.getString(" + String.valueOf(i + 1) + ").trim();");
                }
            }
            if (FieldType.equals("Date")) {
                out.println(space4_3 + "this." + FieldCode + " = rs.getDate(" + String.valueOf(i + 1) + ");");
            }
            if (FieldType.equals("InputStream")) {
                out.println(space4_3 + "this." + FieldCode + " = rs.getBinaryStream(" + String.valueOf(i + 1) + ");");
            }
        }

        // end of for
        out.println(space4_2 + "}");
        out.println(space4_2 + "catch(SQLException sqle){");
        out.println(space4_3 + "System.out.println(\"???????????????" + tTable.getCode() + "???????????????Schema???????????????????????????????????????db.executeQuery??????????????????select * from tables\");");
        out.println(space4_3 + "// @@????????????");
        out.println(space4_3 + "CError tError = new CError();");
        out.println(space4_3 + "tError.moduleName = \"" + ClassName + "\";");
        out.println(space4_3 + "tError.functionName = \"setSchema\";");
        out.println(space4_3 + "tError.errorMessage = sqle.toString();");
        out.println(space4_3 + "this.mErrors.addOneError(tError);");
        out.println(space4_3 + "return false;");
        out.println(space4_2 + "}");
        out.println(space4_2 + "return true;");
        out.println(space4_1 + "}");
    }

    private void getSchema(PrintWriter out, String ClassName) {
        out.println(space4_1 + "public " + ClassName + " getSchema(){");
        out.println(space4_2 + ClassName + " a" + ClassName + " = new " + ClassName + "();");
        out.println(space4_2 + "a" + ClassName + ".setSchema(this);");
        out.println(space4_2 + "return a" + ClassName + ";");
        out.println(space4_1 + "}");
    }

    private void getDB(PrintWriter out, String DBOperName) {
        out.println(space4_1 + "public " + DBOperName + " getDB(){");
        out.println(space4_2 + DBOperName + " aDBOper = new " + DBOperName +
                "();");
        out.println(space4_2 + "aDBOper.setSchema(this);");
        out.println(space4_2 + "return aDBOper;");
        out.println(space4_1 + "}");
    }

    private void encode(PrintWriter out, Table tTable) {
        out.println(space4_1 + "/**");
        out.println(space4_1 + " * ?????????????????? XML ???????????????????????????<A href ={@docRoot}/dataStructure/tb.html#Prp" + tTable.getCode() + "??????/A>?????????");
        out.println(space4_1 + " * @return: String ????????????????????????");
        out.println(space4_1 + " **/");
        out.println(space4_1 + "public String encode(){");
        out.println(space4_2 + "StringBuffer strReturn = new StringBuffer(256);");
        boolean b = false;
        for (int i = 0; i < tTable.getColumnNum(); i++) {
            Column f = tTable.getColumn(i);
            String FieldCode = f.getCode();
            String FieldType = f.getDataType();
            if (i == (tTable.getColumnNum() - 1)) {
                b = true;
            }
            if (i == 0) {
                out.println(space4_2 + "strReturn.append(" + getEnCodeStringLine(b, FieldCode, FieldType));
            } else {
                out.println(space4_2 + "strReturn.append(" + getEnCodeStringLine(b, FieldCode, FieldType));
            }
        }
        out.println(space4_2 + "return strReturn.toString();");
        out.println(space4_1 + "}");
    }

    private void decode(PrintWriter out, Table tTable, String ClassName) {
        out.println(space4_1 + "/**");
        out.println(space4_1 + " * ?????????????????????????????????<A href ={@docRoot}/dataStructure/tb.html#Prp" + tTable.getCode() + ">??????????????????????????????</A>?????????");
        out.println(space4_1 + " * @param: strMessage String ????????????????????????????????????");
        out.println(space4_1 + " * @return: boolean");
        out.println(space4_1 + " **/");
        out.println(space4_1 + "public boolean decode(String strMessage){");
        out.println(space4_2 + "try{");
        for (int i = 0; i < tTable.getColumnNum(); i++) {
            Column f = tTable.getColumn(i);
            String FieldCode = f.getCode();
            String FieldType = f.getDataType();
            out.println(space4_3 + getDeCodeStringLine(i, FieldCode, FieldType));
        }
        out.println(space4_2 + "}");
        out.println(space4_2 + "catch(NumberFormatException ex){");
        out.println(space4_3 + "// @@????????????");
        out.println(space4_3 + "CError tError = new CError();");
        out.println(space4_3 + "tError.moduleName = \"" + ClassName + "\";");
        out.println(space4_3 + "tError.functionName = \"decode\";");
        out.println(space4_3 + "tError.errorMessage = ex.toString();");
        out.println(space4_3 + "this.mErrors.addOneError(tError);");
        out.println(space4_3 + "return false;");
        out.println(space4_2 + "}");
        out.println(space4_2 + "return true;");
        out.println(space4_1 + "}");
    }

    private void getVByCode(PrintWriter out, Table tTable) {
        out.println(space4_1 + "/**");
        out.println(space4_1 + " * ???????????????????????????String??????????????????");
        out.println(space4_1 + " * @param: FCode String ????????????????????????");
        out.println(space4_1 + " * @return: String");
        out.println(space4_1 + " * ????????????????????????????????????\"\"");
        out.println(space4_1 + " * ??????????????????????????????\"null\"");
        out.println(space4_1 + " **/");
        out.println(space4_1 + "public String getV(String FCode){");
        out.println(space4_2 + "String strReturn = \"\";");
        for (int i = 0; i < tTable.getColumnNum(); i++) {
            Column f = tTable.getColumn(i);
            String FieldCode = f.getCode();
            String FieldType = f.getDataType();
            out.println(space4_2 + "if(FCode.equals(\"" + FieldCode + "\")){");
            if (FieldType.equals("Date")) {
                out.println(space4_3 + "strReturn = StrTool.GBKToUnicode(this.get" + FieldCode + "());");
            } else if (FieldType.equals("int") || FieldType.equals("Integer") || FieldType.equals("double") || FieldType.equals("Double")) {
                out.println(space4_3 + "strReturn = String.valueOf(" + FieldCode + ");");
            } else if (FieldType.equals("String")) {
                out.println(space4_3 + "strReturn = StrTool.GBKToUnicode(" + FieldCode + ");");
            } else {
                out.println(space4_3 + "strReturn = StrTool.GBKToUnicode(String.valueOf(" + FieldCode + "));");
            }
            out.println(space4_2 + "}");
        }
        out.println(space4_2 + "if(strReturn.equals(\"\")){");
        out.println(space4_3 + "strReturn = \"null\";");
        out.println(space4_2 + "}");
        out.println(space4_2 + "return strReturn;");
        out.println(space4_1 + "}");
    }

    private void getVByIndex(PrintWriter out, Table tTable) {
        out.println(space4_1 + "/**");
        out.println(space4_1 + " * ??????Schema???????????????????????????????????????");
        out.println(space4_1 + " * @param: nFieldIndex int ????????????????????????");
        out.println(space4_1 + " * @return: String");
        out.println(space4_1 + " * ????????????????????????????????????\"\"");
        out.println(space4_1 + " * ??????????????????????????????\"null\"");
        out.println(space4_1 + " **/");
        out.println(space4_1 + "public String getV(int nFieldIndex){");
        out.println(space4_2 + "String strFieldValue = \"\";");
        out.println(space4_2 + "switch(nFieldIndex){");
        for (int i = 0; i < tTable.getColumnNum(); i++) {
            Column f = tTable.getColumn(i);
            String FieldCode = f.getCode();
            String FieldType = f.getDataType();
            out.println(space4_3 + "case " + String.valueOf(i) + ":");
            if (FieldType.equals("Date")) {
                out.println(space4_4 + "strFieldValue = StrTool.GBKToUnicode(this.get" + FieldCode + "());");
            } else if (FieldType.equals("String")) {
                out.println(space4_4 + "strFieldValue = StrTool.GBKToUnicode(" + FieldCode + ");");
            } else if (FieldType.equals("int") || FieldType.equals("Integer") || FieldType.equals("double") || FieldType.equals("Double")) {
                out.println(space4_4 + "strFieldValue = String.valueOf(" + FieldCode + ");");
            } else {
                out.println(space4_4 + "strFieldValue = String.valueOf(" + FieldCode + ");");
            }
            out.println(space4_4 + "break;");
        }
        out.println(space4_3 + "default:");
        out.println(space4_4 + "strFieldValue = \"\";");
        out.println(space4_2 + "}");
        out.println(space4_2 + "if(strFieldValue.equals(\"\")){");
        out.println(space4_3 + "strFieldValue = \"null\";");
        out.println(space4_2 + "}");
        out.println(space4_2 + "return strFieldValue;");
        out.println(space4_1 + "}");
    }

    private void getVxByCode(PrintWriter out, Table tTable) {
        out.println(space4_1 + "/**");
        out.println(space4_1 + " * ???????????????????????????String??????????????????");
        out.println(space4_1 + " * @param: FCode String ????????????????????????");
        out.println(space4_1 + " * @return: String");
        out.println(space4_1 + " * ????????????????????????????????????\"\"");
        out.println(space4_1 + " * ??????????????????????????????\"null\"");
        out.println(space4_1 + " **/");
        out.println(space4_1 + "public String getVx(String FCode){");
        out.println(space4_2 + "String strReturn = null;");
        for (int i = 0; i < tTable.getColumnNum(); i++) {
            Column f = tTable.getColumn(i);
            String FieldCode = f.getCode();
            String FieldType = f.getDataType();
            out.println(space4_2 + "if(FCode.equals(\"" + FieldCode + "\")){");
            out.println(space4_3 + "if(" + FieldCode + " != null){");
            if (FieldType.equals("Date")) {
                out.println(space4_4 +
                        "strReturn = StrTool.GBKToUnicode(this.get" +
                        FieldCode + "());");
            } else if (FieldType.equals("Integer") || FieldType.equals("int") ||
                    FieldType.equals("Double") || FieldType.equals("double")) {
                out.println(space4_4 + "strReturn = String.valueOf(" +
                        FieldCode +
                        ");");
            } else if (FieldType.equals("String")) {
                out.println(space4_4 + "strReturn = StrTool.GBKToUnicode(" +
                        FieldCode + ");");
            } else {
                out.println(space4_4 + "strReturn = String.valueOf(" +
                        FieldCode + ");");
            }
            out.println(space4_3 + "}");
            out.println(space4_2 + "}");
        }
        out.println(space4_2 + "return strReturn;");
        out.println(space4_1 + "}");
    }

    private void getVxByIndex(PrintWriter out, Table tTable) {
        out.println(space4_1 + "/**");
        out.println(space4_1 + " * ??????Schema???????????????????????????????????????");
        out.println(space4_1 + " * @param: nFieldIndex int ????????????????????????");
        out.println(space4_1 + " * @return: String");
        out.println(space4_1 + " * ????????????????????????????????????\"\"");
        out.println(space4_1 + " * ??????????????????????????????\"null\"");
        out.println(space4_1 + " **/");
        out.println(space4_1 + "public String getVx(int nFieldIndex){");
        out.println(space4_2 + "String strFieldValue = null;");
        out.println(space4_2 + "switch(nFieldIndex){");
        for (int i = 0; i < tTable.getColumnNum(); i++) {
            Column f = tTable.getColumn(i);
            String FieldCode = f.getCode();
            String FieldType = f.getDataType();
            out.println(space4_3 + "case " + String.valueOf(i) + ":");
            out.println(space4_4 + "if(" + FieldCode + " != null){");
            if (FieldType.equals("Date")) {
                out.println(space4_5 +
                        "strFieldValue = StrTool.GBKToUnicode(this.get" +
                        FieldCode + "());");
            } else if (FieldType.equals("String")) {
                out.println(space4_5 + "strFieldValue = StrTool.GBKToUnicode(" +
                        FieldCode + ");");
            } else if (FieldType.equals("Integer") || FieldType.equals("int") ||
                    FieldType.equals("Double") || FieldType.equals("double")) {
                out.println(space4_5 + "strFieldValue = String.valueOf(" +
                        FieldCode +
                        ");");
            } else {
                out.println(space4_5 + "strFieldValue = String.valueOf(" +
                        FieldCode + ");");
            }
            out.println(space4_4 + "}");
            out.println(space4_4 + "break;");
        }
        out.println(space4_3 + "default:");
        out.println(space4_4 + "strFieldValue = null;");
        out.println(space4_2 + "}");
        out.println(space4_2 + "return strFieldValue;");
        out.println(space4_1 + "}");
    }


    private void setV(PrintWriter out, Table tTable) {
        out.println(space4_1 + "/**");
        out.println(space4_1 + " * ???????????????????????????String??????????????????");
        out.println(space4_1 + " * @param: FCode String ?????????????????????");
        out.println(space4_1 + " * @param: FValue String ????????????");
        out.println(space4_1 + " * @return: boolean");
        out.println(space4_1 + " **/");
        out.println(space4_1 + "public boolean setV(String FCode, String FValue){");
        out.println(space4_2 + "if(StrTool.cTrim(FCode).equals(\"\")){");
        out.println(space4_3 + "return false;");
        out.println(space4_2 + "}");
        for (int i = 0; i < tTable.getColumnNum(); i++) {
            Column f = tTable.getColumn(i);
            String FieldCode = f.getCode();
            String FieldType = f.getDataType();
            out.println(space4_2 + "if(FCode.equalsIgnoreCase(\"" + FieldCode + "\")){");
            if (FieldType.equals("Date")) {
                out.println(space4_3 + "if(FValue != null && !FValue.equals(\"\")){");
                out.println(space4_4 + FieldCode + " = fDate.getDate(FValue);");
                out.println(space4_3 + "}");
                out.println(space4_3 + "else");
                out.println(space4_4 + FieldCode + " = null;");
            }
            if (FieldType.equals("float")) {
                out.println(space4_3 + "if(FValue != null && !FValue.equals(\"\")){");
                out.println(space4_4 + "Float tFloat = new Float(FValue);");
                out.println(space4_4 + "float f = tFloat.floatValue();");
                out.println(space4_4 + FieldCode + " = f;");
                out.println(space4_3 + "}");
            }
            if (FieldType.equals("double")) { // add by yt 2003-6-20
                out.println(space4_3 + "if(FValue != null && !FValue.equals(\"\")){");
                out.println(space4_4 + "Double tDouble = new Double(FValue);");
                out.println(space4_4 + "double d = tDouble.doubleValue();");
                out.println(space4_4 + FieldCode + " = d;");
                out.println(space4_3 + "}");
            }
            if (FieldType.equals("int")) {
                out.println(space4_3 + "if(FValue != null && !FValue.equals(\"\")){");
                out.println(space4_4 + "Integer tInteger = new Integer(FValue);");
                out.println(space4_4 + "int i = tInteger.intValue();");
                out.println(space4_4 + FieldCode + " = i;");
                out.println(space4_3 + "}");
            }
            if (FieldType.equals("String")) {
                out.println(space4_3 + "if(FValue != null && !FValue.equals(\"\")){");
                out.println(space4_4 + FieldCode + " = FValue.trim();");
                out.println(space4_3 + "}");
                out.println(space4_3 + "else{");
                out.println(space4_4 + FieldCode + " = null;");
                out.println(space4_3 + "}");
            }
            if (FieldType.equals("Integer")) {
                out.println(space4_3 + "if(FValue != null && !FValue.equals(\"\")){");
                out.println(space4_4 + FieldCode + " = new Integer(FValue);");
                out.println(space4_3 + "}");
                out.println(space4_3 + "else{");
                out.println(space4_4 + FieldCode + " = null;");
                out.println(space4_3 + "}");
            }
            if (FieldType.equals("Double")) {
                out.println(space4_3 + "if(FValue != null && !FValue.equals(\"\")){");
                out.println(space4_4 + FieldCode + " = new Double(FValue);");
                out.println(space4_3 + "}");
                out.println(space4_3 + "else{");
                out.println(space4_4 + FieldCode + " = null;");
                out.println(space4_3 + "}");
            }
            out.println(space4_2 + "}");
        }
        out.println(space4_2 + "return true;");
        out.println(space4_1 + "}");
    }

    private void equals(PrintWriter out, Table tTable, String ClassName) {
        out.println(space4_1 + "public boolean equals(Object otherObject){");
        out.println(space4_2 + "if(this == otherObject)");
        out.println(space4_3 + "return true;");
        out.println(space4_2 + "if(otherObject == null)");
        out.println(space4_3 + "return false;");
        out.println(space4_2 + "if(getClass() != otherObject.getClass())");
        out.println(space4_3 + "return false;");
        out.println(space4_2 + ClassName + " other = (" + ClassName + ") otherObject;");
        out.println(space4_2 + "return");
        for (int i = 0; i < tTable.getColumnNum(); i++) {
            Column f = tTable.getColumn(i);
            String FieldCode = f.getCode();
            String FieldType = f.getDataType();
            String relation = "";
            String endChar = "";
            if (i != 0) {
                relation = "&& ";
            }
            if (i == tTable.getColumnNum() - 1) {
                endChar = ";";
            }
            if (FieldType.equals("Date")) {
                out.println(space4_4 + relation + "(" + FieldCode + " == null ? other.get" + FieldCode + "() == null : " + "fDate.getString(" + FieldCode + ")" + ".equals(other.get" + FieldCode + "()))" + endChar);
            }
            if (FieldType.equals("String") || FieldType.equals("Integer") || FieldType.equals("Double")) {
                out.println(space4_4 + relation + "(" + FieldCode + " == null ? other.get" + FieldCode + "() == null : " + FieldCode + ".equals(other.get" + FieldCode + "()))" + endChar);
            }
            if (FieldType.equals("float") || FieldType.equals("int") || FieldType.equals("double")) { // modify by yt 2003-6-20
                out.println(space4_4 + relation + FieldCode + " == other.get" + FieldCode + "()" + endChar);
            }
            if (FieldType.equals("InputStream")) {
                out.println(space4_4 + endChar);
            }
        } // end of for
        out.println(space4_1 + "}");
    }

    private void getFieldCount(PrintWriter out) {
        out.println(space4_1 + "/**");
        out.println(space4_1 + " * ??????Schema?????????????????????");
        out.println(space4_1 + " * @return: int");
        out.println(space4_1 + " **/");
        out.println(space4_1 + "public int getFieldCount(){");
        out.println(space4_2 + "return FIELDNUM;");
        out.println(space4_1 + "}");
    }

    private void getFieldIndex(PrintWriter out, Table tTable) {
        out.println(space4_1 + "/**");
        out.println(space4_1 + " * ??????Schema???????????????????????????????????????");
        out.println(space4_1 + " * ????????????????????????????????????-1");
        out.println(space4_1 + " * @param: strFieldName String");
        out.println(space4_1 + " * @return: int");
        out.println(space4_1 + " **/");
        out.println(space4_1 + "public int getFieldIndex(String strFieldName){");
        for (int i = 0; i < tTable.getColumnNum(); i++) {
            Column f = tTable.getColumn(i);
            String FieldCode = f.getCode();
            out.println(space4_2 + "if(strFieldName.equals(\"" + FieldCode + "\")){");
            out.println(space4_3 + "return " + String.valueOf(i) + ";");
            out.println(space4_2 + "}");
        }
        out.println(space4_2 + "return -1;");
        out.println(space4_1 + "}");
    }

    private void getFieldName(PrintWriter out, Table tTable) {
        out.println(space4_1 + "/**");
        out.println(space4_1 + " * ??????Schema???????????????????????????????????????");
        out.println(space4_1 + " * ????????????????????????????????????\"\"");
        out.println(space4_1 + " * @param: nFieldIndex int");
        out.println(space4_1 + " * @return: String");
        out.println(space4_1 + " **/");
        out.println(space4_1 + "public String getFieldName(int nFieldIndex){");
        out.println(space4_2 + "String strFieldName = \"\";");
        out.println(space4_2 + "switch(nFieldIndex){");
        for (int i = 0; i < tTable.getColumnNum(); i++) {
            Column f = tTable.getColumn(i);
            String FieldCode = f.getCode();
            out.println(space4_3 + "case " + String.valueOf(i) + ":");
            out.println(space4_4 + "strFieldName = \"" + FieldCode + "\";");
            out.println(space4_4 + "break;");
        }
        out.println(space4_3 + "default:");
        out.println(space4_4 + "strFieldName = \"\";");
        out.println(space4_2 + "}");
        out.println(space4_2 + "return strFieldName;");
        out.println(space4_1 + "}");
    }

    private void getFieldTypeByName(PrintWriter out, Table tTable) {
        out.println(space4_1 + "/**");
        out.println(space4_1 + " * ??????Schema??????????????????????????????????????????");
        out.println(space4_1 + " * ????????????????????????????????????Schema.TYPE_NOFOUND");
        out.println(space4_1 + " * @param: strFieldName String");
        out.println(space4_1 + " * @return: int");
        out.println(space4_1 + " **/");
        out.println(space4_1 + "public int getFieldType(String strFieldName){");
        out.println(space4_2 + "if(strFieldName == null){");
        out.println(space4_3 + "return Schema.TYPE_NOFOUND;");
        out.println(space4_2 + "}");
        for (int i = 0; i < tTable.getColumnNum(); i++) {
            Column f = tTable.getColumn(i);
            String FieldCode = f.getCode();
            String FieldType = f.getDataType();
            String strFieldType = "Schema.TYPE_NOFOUND";
            out.println(space4_2 + "if(strFieldName.equals(\"" + FieldCode + "\")){");
            if (FieldType.equals("String")) {
                strFieldType = "Schema.TYPE_STRING";

            } else if (FieldType.equals("Date")) {
                strFieldType = "Schema.TYPE_DATE";

            } else if (FieldType.equals("int")) {
                strFieldType = "Schema.TYPE_INT";

            } else if (FieldType.equals("float")) {
                strFieldType = "Schema.TYPE_FLOAT";

            } else if (FieldType.equals("double")) {
                strFieldType = "Schema.TYPE_DOUBLE";

            } else if (FieldType.equals("Integer")) {
                strFieldType = "Schema.TYPE_INT";

            } else if (FieldType.equals("Double")) {
                strFieldType = "Schema.TYPE_DOUBLE";

            } else {
                strFieldType = "Schema.TYPE_NOFOUND";
            }
            out.println(space4_3 + "return " + strFieldType + ";");
            out.println(space4_2 + "}");
        }
        out.println(space4_2 + "return Schema.TYPE_NOFOUND;");
        out.println(space4_1 + "}");
    }

    private void getFieldTypeByIndex(PrintWriter out, Table tTable) {
        out.println(space4_1 + "/**");
        out.println(space4_1 + " * ??????Schema??????????????????????????????????????????");
        out.println(space4_1 + " * ????????????????????????????????????Schema.TYPE_NOFOUND");
        out.println(space4_1 + " * @param: nFieldIndex int");
        out.println(space4_1 + " * @return: int");
        out.println(space4_1 + " **/");
        out.println(space4_1 + "public int getFieldType(int nFieldIndex){");
        out.println(space4_2 + "int nFieldType = Schema.TYPE_NOFOUND;");
        out.println(space4_2 + "switch(nFieldIndex){");
        for (int i = 0; i < tTable.getColumnNum(); i++) {
            Column f = tTable.getColumn(i);
            String FieldType = f.getDataType();
            String strFieldType = "Schema.TYPE_NOFOUND";
            out.println(space4_3 + "case " + String.valueOf(i) + ":");
            if (FieldType.equals("String")) {
                strFieldType = "Schema.TYPE_STRING";
            } else if (FieldType.equals("Date")) {
                strFieldType = "Schema.TYPE_DATE";
            } else if (FieldType.equals("int")) {
                strFieldType = "Schema.TYPE_INT";
            } else if (FieldType.equals("float")) {
                strFieldType = "Schema.TYPE_FLOAT";
            } else if (FieldType.equals("double")) {
                strFieldType = "Schema.TYPE_DOUBLE";
            } else if (FieldType.equals("Integer")) {
                strFieldType = "Schema.TYPE_INT";
            } else if (FieldType.equals("Double")) {
                strFieldType = "Schema.TYPE_DOUBLE";
            } else {
                strFieldType = "Schema.TYPE_NOFOUND";
            }
            out.println(space4_4 + "nFieldType = " + strFieldType + ";");
            out.println(space4_4 + "break;");
        }
        out.println(space4_3 + "default:");
        out.println(space4_4 + "nFieldType = Schema.TYPE_NOFOUND;");
        out.println(space4_2 + "}");
        out.println(space4_2 + "return nFieldType;");
        out.println(space4_1 + "}");
    }

    // ??????encode????????????????????????????????????
    private static String getEnCodeStringLine(boolean b, String strColName, String strColType) {
        String str = ""; // ?????????????????????
        if (strColType.equals("String") || strColType.equals("Date")) {
            if (strColType.equals("Date")) {
                strColName = "fDate.getString(" + strColName + ")";
            }
            if (b) {
                str = "StrTool.cTrim(" + strColName + "));";
            } else {
                str = "StrTool.cTrim(" + strColName + "));" + newline + space4_2 + "strReturn.append(SysConst.PACKAGESPILTER);";
            }
        } else {
            if (b) {
                if (strColType.equals("InputStream")) {
                    str = " 1 );";
                } else {
                    str = "ChgData.chgData(" + strColName + "));";
                }
            } else {
                if (strColType.equals("InputStream")) {
                    str = " 1 );strReturn.append(SysConst.PACKAGESPILTER);";
                } else {
                    str = "ChgData.chgData(" + strColName + "));" + newline + space4_2 + "strReturn.append(SysConst.PACKAGESPILTER);";
                }
            }
        }
        return str;
    }

    // ?????? decode????????????????????????????????????
    private static String getDeCodeStringLine(int i, String strColName, String strColType) {
        String str = ""; // ?????????????????????
        if (strColType.equals("String")) {
            str = strColName + " = StrTool.getStr(StrTool.GBKToUnicode(strMessage), " + (i + 1) + ", SysConst.PACKAGESPILTER);";
        } else if (strColType.equals("int")) {
            str = strColName + " = new Integer(ChgData.chgNumericStr(StrTool.getStr(" + "strMessage, " + (i + 1) + ", SysConst.PACKAGESPILTER))).intValue();";
        } else if (strColType.equals("float")) {
            str = strColName + " = new Float(ChgData.chgNumericStr(StrTool.getStr(" + "strMessage, " + (i + 1) + ", SysConst.PACKAGESPILTER))).floatValue();";
        } else if (strColType.equals("double")) { // add by yt 2003-6-20
            str = strColName + " = new Double(ChgData.chgNumericStr(StrTool.getStr(" + "strMessage, " + (i + 1) + ", SysConst.PACKAGESPILTER))).doubleValue();";
        } else if (strColType.equals("Date")) {
            str = strColName + " = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), " + (i + 1) + ", SysConst.PACKAGESPILTER));";
        } else if (strColType.equals("Integer")) {
            str = strColName + " = new Integer(ChgData.chgNumericStr(StrTool.getStr(" + "strMessage, " + (i + 1) + ", SysConst.PACKAGESPILTER)));";
        } else if (strColType.equals("Double")) {
            str = strColName + " = new Double(ChgData.chgNumericStr(StrTool.getStr(" + "strMessage, " + (i + 1) + ", SysConst.PACKAGESPILTER)));";
        }
        return str;
    }

}
