package com.weixf.wordexport.word.plugin;

import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.policy.DynamicTableRenderPolicy;
import com.deepoove.poi.policy.TableRenderPolicy;
import com.deepoove.poi.util.TableTools;
import com.weixf.wordexport.entity.DetailData;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.util.List;


public class DetailTablePolicy extends DynamicTableRenderPolicy {

    // 货品填充数据所在行数
    int goodsStartRow = 2;
    // 人工费填充数据所在行数
    int laborsStartRow = 5;

    @Override
    public void render(XWPFTable table, Object data) throws Exception {
        if (null == data) {
            return;
        }
        DetailData detailData = (DetailData) data;

        List<RowRenderData> labors = detailData.getLabors();
        if (null != labors) {
            table.removeRow(laborsStartRow);
            // 循环插入行
            for (int i = 0; i < labors.size(); i++) {
                XWPFTableRow insertNewTableRow = table.insertNewTableRow(laborsStartRow);
                for (int j = 0; j < 7; j++) insertNewTableRow.createCell();

                // 合并单元格
                TableTools.mergeCellsHorizonal(table, laborsStartRow, 0, 3);
                TableRenderPolicy.Helper.renderRow(table.getRow(laborsStartRow), labors.get(i));
            }
        }

        List<RowRenderData> goods = detailData.getGoods();
        if (null != goods) {
            table.removeRow(goodsStartRow);
            for (int i = 0; i < goods.size(); i++) {
                XWPFTableRow insertNewTableRow = table.insertNewTableRow(goodsStartRow);
                for (int j = 0; j < 7; j++) insertNewTableRow.createCell();
                TableRenderPolicy.Helper.renderRow(table.getRow(goodsStartRow), goods.get(i));
            }
        }
    }

}
