package com.wxf.utility;

import org.springframework.stereotype.Component;

import java.util.Vector;

/**
 * @author weixf
 * @since 2022-01-21
 */
@Component
public class CErrors implements Cloneable {

    private Vector<CError> vErrors = new Vector<>();

    private int errorCount = 0;

    private String flag = "";

    private String content = "";

    private String result = "";


    public Object clone() throws CloneNotSupportedException {
        // clone the mutable fields of this class
        return super.clone();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String f) {
        flag = f;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String c) {
        content = c;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String c) {
        result = c;
    }

    public CErrors() {
    }

    /**
     * 向错误容器类中增加一个错误，错误个数加1
     *
     * @param cError CError
     */
    public void addOneError(CError cError) {
        this.vErrors.add(cError);
        this.errorCount++;
    }

    /**
     * 向错误容器类中增加一个错误，错误个数加1
     *
     * @param cErrorString String
     */
    public void addOneError(String cErrorString) {
        CError tError = new CError(cErrorString.trim());
        this.vErrors.add(tError);
        this.errorCount++;
    }

    /**
     * 移出最后的错误
     */
    public void removeLastError() {
        if (errorCount > 0) {
            this.vErrors.removeElementAt(errorCount - 1);
            this.errorCount--;
        }
    }

    /**
     * 移出指定的错误信息
     *
     * @param pos int
     */
    public void removeError(int pos) {
        if ((errorCount > 0) && (pos < errorCount)) {
            this.vErrors.removeElementAt(pos);
            this.errorCount--;
        }
    }

    /**
     * 将错误容器内的错误信息清空，计数重置为0
     */
    public void clearErrors() {
        this.vErrors.clear();
        this.errorCount = 0;
    }

    /**
     * 得到容器中的错误的个数
     *
     * @return int
     */
    public int getErrorCount() {
        return this.errorCount;
    }

    /**
     * 得到容器中指定位置的错误对象
     *
     * @param indexError int
     * @return CError
     */
    public CError getError(int indexError) {
        return vErrors.get(indexError);
    }

    /**
     * 得到最早一个错误的错误信息,如果没有错误则返回空字符串""
     *
     * @return String
     */
    public String getFirstError() {
        try {
            CError tError = this.vErrors.get(0);
            String strMsg = tError.errorMessage;
            strMsg = strMsg.replace((char) (10), ' ');
            strMsg = strMsg.replace('"', ' ');
            strMsg = strMsg.replace('\'', ' ');
            return strMsg;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 得到最后一个错误的错误信息,如果没有错误则返回空字符串""
     *
     * @return String
     */
    public String getLastError() {
        if (errorCount < 1) {
            return "";
        }

        try {
            CError tError = this.vErrors.get(errorCount - 1);

            /*
             * kevin 2002-10-15 保证错误信息的最后一个字符不是回车。否则，这样的信息会造成javascript的错误。 替换一些特殊的字符。否则，会造成javascript的错误。
             */
            String strMsg = tError.errorMessage;

            strMsg = strMsg.replace((char) (10), ' ');
            strMsg = strMsg.replace('"', ' ');
            strMsg = strMsg.replace('\'', ' ');

            return strMsg;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 判断产生的错误是否严重到需要处理
     *
     * @return boolean
     */
    public boolean needDealError() {
        try {
            if (this.getErrorCount() > 0) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    /**
     * 复制所有的错误信息到本类中
     *
     * @param cSourceErrors CErrors
     */
    public void copyAllErrors(CErrors cSourceErrors) {
        int iMax = cSourceErrors.getErrorCount();
        for (int i = 0; i < iMax; i++) {
            this.addOneError(cSourceErrors.getError(i));
        }
    }

    /**
     * 获取错误严重级别
     *
     * @return String
     */
    public String getErrType() {
        int forbitNum = 0;
        int needSelectNum = 0;
        int allowNum = 0;
        int unknowNum = 0;

        for (CError vError : vErrors) {
            switch (vError.errorType) {
                case CError.TYPE_FORBID:
                    forbitNum++;
                    break;
                case CError.TYPE_NEEDSELECT:
                    needSelectNum++;
                    break;
                case CError.TYPE_ALLOW:
                    allowNum++;
                    break;
                default:
                    unknowNum++;
                    break;
            }
        }

        if (forbitNum > 0) {
            return CError.TYPE_FORBID;
        } else if (needSelectNum > 0) {
            return CError.TYPE_NEEDSELECT;
        } else if (allowNum > 0) {
            return CError.TYPE_ALLOW;
        } else if (unknowNum > 0) {
            return CError.TYPE_UNKNOW;
        } else {
            return CError.TYPE_NONEERR;
        }
    }

    /**
     * 获取错误内容,并分类,以提供界面显示
     *
     * @return String
     */
    public String getErrContent() {
        content = "（一共发生 " + vErrors.size() + " 个错误）\n";
        StringBuilder forbitContent = new StringBuilder();
        StringBuilder needSelectContent = new StringBuilder();
        StringBuilder allowContent = new StringBuilder();
        StringBuilder unknowContent = new StringBuilder();

        for (CError vError : vErrors) {
            switch (vError.errorType) {
                case CError.TYPE_FORBID:
                    forbitContent.append("  ").append("（错误编码：").append(vError.errorNo).append("）").append(vError.errorMessage).append("\n");
                    break;
                case CError.TYPE_NEEDSELECT:
                    needSelectContent.append("  ").append("（错误编码：").append(vError.errorNo).append("）").append(vError.errorMessage).append("\n");
                    break;
                case CError.TYPE_ALLOW:
                    allowContent.append("  ").append("（错误编码：").append(vError.errorNo).append("）").append(vError.errorMessage).append("\n");
                    break;
                default:
                    unknowContent.append("  ").append("（错误编码：意外错误）").append(vError.errorMessage).append("\n");
                    break;
            }
        }

        if (!forbitContent.toString().equals("")) {
            content = content + "严重错误如下:\n" + forbitContent;
        }

        if (!needSelectContent.toString().equals("")) {
            content = content + "需要选择的错误如下:\n" + needSelectContent;
        }

        if (!allowContent.toString().equals("")) {
            content = content + "允许出现的错误如下:\n" + allowContent;
        }

        if (!unknowContent.toString().equals("")) {
            content = content + "意外错误如下:\n" + unknowContent;
        }
        return content;
    }

    /**
     * 校验错误
     */
    public void checkErrors(CErrors cerrors) {
        if (cerrors.getErrType().equals(CError.TYPE_NONE)) {
            content = "操作成功";
            flag = "Success";
        } else {
            if (cerrors.getErrType().equals(CError.TYPE_ALLOW)) {
                flag = "Success";
            } else {
                flag = "Fail";
            }
        }
    }
}
