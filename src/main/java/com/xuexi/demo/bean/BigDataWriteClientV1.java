package com.xuexi.demo.bean;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ruihua.rpc.bigDataServiceV1.BigDataServiceV1Grpc;
import ruihua.rpc.bigDataServiceV1.DataValueItem;
import ruihua.rpc.bigDataServiceV1.WriteDataInBatchesRequest;
import ruihua.rpc.bigDataServiceV1.WriteDataInBatchesResponse;

public class BigDataWriteClientV1 {
    private static final Logger logger = LoggerFactory.getLogger(BigDataWriteClientV1.class);
    private BigDataServiceV1Grpc.BigDataServiceV1BlockingStub bigDataServiceV1BlockingStub;
    public void writeDataInBatches() {
        WriteDataInBatchesRequest data = WriteDataInBatchesRequest.newBuilder()
//                .addDataItems(DataValueItem.newBuilder().setClient("HJJC01FT01").setClientDatetime("3634634").setSystemDatetime("3634634"))
//                .addDataItems(DataValueItem.newBuilder().setClient("HJJC01FT01").setClientDatetime("3634634").setSystemDatetime("3634634").putAllValues(new HashMap<>()))
                .addDataItems(DataValueItem.newBuilder().setClient("HJJC01FT03").setClientDatetime("2021-04-31 00:00:00").setSystemDatetime("2021-04-31 00:00:00").putValues("2", "3"))
                .build();
        WriteDataInBatchesResponse opcs1 = this.writeDataInBatches(data);
    }


    /**
     * gRPC单数据写入
     *
     //     * @param writeDataRequest
     * @return
     */
    public WriteDataInBatchesResponse writeDataInBatches(WriteDataInBatchesRequest writeDataRequest) {

        String host = "10.238.251.20";
        int port = 1124;
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();

        bigDataServiceV1BlockingStub = BigDataServiceV1Grpc.newBlockingStub(managedChannel);

        WriteDataInBatchesResponse oResponse = bigDataServiceV1BlockingStub.writeDataInBatches(writeDataRequest);

        return oResponse;
    }

    public static void main(String[] args) {
        BigDataWriteClientV1 writeClientV1 = new BigDataWriteClientV1();
        writeClientV1.writeDataInBatches();
    }
}