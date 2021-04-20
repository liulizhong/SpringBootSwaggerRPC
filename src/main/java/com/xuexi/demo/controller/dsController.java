package com.xuexi.demo.controller;

import com.xuexi.demo.bean.BigDataWriteClientV1;
import com.xuexi.demo.utils.JDBCClientUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ruihua.rpc.bigDataServiceV1.DataValueItem;
import ruihua.rpc.bigDataServiceV1.WriteDataInBatchesRequest;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @author
 * @version TODO
 * @class 通过大数据调度系统，调用的数据采集任务接口
 * @CalssName UserController
 * @create 2020-07-24 17:49
 * @Des TODO
 */
@Api(tags = "数据采集项目后台任务接口管理")
@RestController
public class dsController {
    @ApiOperation(value = "MySQL读数写入通用接口", notes = "输入数据源、数据库ip、端口、用户名、密码、库名、表名、字段名及sql语句，来实现MySQL数据源传输到大数据平台")
    @GetMapping(value = "/dsToWriteDataInBatches")
    public Boolean dsToWriteDataInBatches(@ApiParam(value = "数据源ID", required = true, defaultValue = "1") @RequestParam String dsID,
                                          @ApiParam(value = "数据库IP", required = true, defaultValue = "10.238.251.3") @RequestParam String dbIP,
                                          @ApiParam(value = "数据库端口", required = true, defaultValue = "3306") @RequestParam String dbPort,
                                          @ApiParam(value = "用户名", required = true, defaultValue = "bigdata_connect") @RequestParam String userName,
                                          @ApiParam(value = "密码", required = true, defaultValue = "bigdata_connect") @RequestParam String password,
                                          @ApiParam(value = "数据库名", required = true, defaultValue = "bigdata_connect") @RequestParam String dbName,
                                          @ApiParam(value = "表名", required = true, defaultValue = "T_TASK_DETAIL") @RequestParam String tableName,
                                          @ApiParam(value = "字段名", required = true, defaultValue = "USER_NAME") @RequestParam String fieldName,
                                          @ApiParam(value = "点表名", required = true, defaultValue = "0") @RequestParam String pointName,
                                          @ApiParam(value = "sql语句", required = true, defaultValue = "select USER_NAME from T_TASK_DETAIL order by  ID limit 1;") @RequestParam String sql) throws Exception {
        System.out.println(sql);
        if ("1".equals(dsID)) {
            System.out.println("111111111111111111111111111111111111111111");
            //// 【1】获取源数据
            Statement mysqlStatement = JDBCClientUtils.getMysqlClient(dbIP, dbPort, userName, password, dbName);
            if (!sql.contains("limit")) {
                throw new Exception("sql语句应包含'limit 1'");
            }
            ResultSet resultSet = mysqlStatement.executeQuery(sql);
            resultSet.next();
            String value = resultSet.getObject(1) + "";
            System.out.println("本次抽取数据" + pointName + "值为：" + value);
            String timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            // rowkey命名规则：String rowKey = (dbIP + "^" + dbName + "^" + tableName + "^" + fieldName + "^" + timeFormat).toLowerCase();
            //// 【2】写入景建通用接口
            WriteDataInBatchesRequest.Builder builder = WriteDataInBatchesRequest.newBuilder();
            BigDataWriteClientV1 bigDataWriteClientV1 = new BigDataWriteClientV1();
            HashMap<String, String> valuesMap = new HashMap<>();
            //valuesMap.put("POINT_NAME", pointName);
            //valuesMap.put("POINT_QUALITY", "good");
            valuesMap.put("POINT_VALUE", value);
            builder.addDataItems(DataValueItem.newBuilder()
                    .setClient(pointName)           //点表名
                    .setClientDatetime(timeFormat)  // 设备时间
                    //.setSystemDatetime("3634634") // 拉取时间
                    .putAllValues(valuesMap));
            bigDataWriteClientV1.writeDataInBatches(builder.build());
            // 【3】关闭资源
            resultSet.close();
            mysqlStatement.close();
            return true;
        } else if ("2".equals(dsID)) {
            System.out.println("22222222222222222222222222222222222222222222222222222");
            return true;
        } else if ("3".equals(dsID)) {
            System.out.println("333333333333333333333333333333333333333333");
            return true;
        }
        System.out.println("44444444444444444444444444444444");
        return true;
    }
}
