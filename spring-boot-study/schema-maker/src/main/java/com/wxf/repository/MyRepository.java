package com.wxf.repository;

import com.wxf.utility.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/*
 *
 * @author weixf
 * @date 2022-06-17
 */
@Repository
public class MyRepository {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private FDate fDate;

    public Connection getConnection(){
        DataSource dataSource = jdbcTemplate.getDataSource();
        try {
            if (dataSource != null) {
                return dataSource.getConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public String getOneValue(String sql) {
        List<String> resultList = new ArrayList<>();
        jdbcTemplate.query(sql, rs -> {
            resultList.add(rs.getString(1));
        });
        return resultList.get(0);
    }

    public SSRS execSQL(String sql) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
        SqlRowSetMetaData sqlRowSetMetaData = sqlRowSet.getMetaData();
        int columnCount = sqlRowSetMetaData.getColumnCount();
        SSRS tSSRS = new SSRS(columnCount);
        while (sqlRowSet.next()) {
            for (int j = 1; j <= columnCount; j++) {
                tSSRS.SetText(getDataValue(sqlRowSetMetaData, sqlRowSet, j));
            }
        }
        return tSSRS;
    }

    public String getDataValue(SqlRowSetMetaData sqlRowSetMetaData, SqlRowSet sqlRowSet, int i) {
        String strValue = "";
        int dataType = sqlRowSetMetaData.getColumnType(i);
        int dataScale = sqlRowSetMetaData.getScale(i);
        int dataPrecision = sqlRowSetMetaData.getPrecision(i);
        // ?????????????????????
        if ((dataType == Types.CHAR)
                || (dataType == Types.VARCHAR)) {
            strValue = sqlRowSet.getString(i);
        }
        // ??????????????????????????????
        else if ((dataType == Types.TIMESTAMP)
                || (dataType == Types.DATE)) {
            strValue = fDate.getString(sqlRowSet.getDate(i));
        }
        // ?????????????????????
        else if ((dataType == Types.DECIMAL)
                || (dataType == Types.FLOAT)) {
            // ?????????????????????????????????????????????????????????????????????????????????
            strValue = String.valueOf(sqlRowSet.getBigDecimal(i));
            // ????????????
            strValue = PubFun.getInt(strValue);
        }
        // ?????????????????????
        else if ((dataType == Types.INTEGER)
                || (dataType == Types.SMALLINT)
                || dataType == Types.TINYINT) {
            strValue = String.valueOf(sqlRowSet.getInt(i));
            strValue = PubFun.getInt(strValue);
        }
        // ?????????????????????
        else if (dataType == Types.NUMERIC) {
            if (dataScale == 0) {
                if (dataPrecision == 0) {
                    // ?????????????????????????????????????????????????????????????????????????????????
                    strValue = String.valueOf(sqlRowSet.getBigDecimal(i));
                } else {
                    strValue = String.valueOf(sqlRowSet.getLong(i));
                }
            } else {
                // ?????????????????????????????????????????????????????????????????????????????????
                strValue = String.valueOf(sqlRowSet.getBigDecimal(i));
            }
            strValue = PubFun.getInt(strValue);
        }
        return StrTool.cTrim(strValue);
    }
}
