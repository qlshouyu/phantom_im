package com.iflytek.phantom.im.core.clusters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrpcClient {

//    public void rpc(RpcRequest request, StreamObserver<RpcResponse> responseObserver) {
//        this.getAsyncStub().rpc(request, responseObserver);
//    }
//
//    private ManagedChannel channel;
//
//
//    private GrpcClient(ManagedChannel channel) {
//        this.channel = channel;
//    }
//
//    public static GrpcClient build(String host, Integer port) {
//        LOGGER.info("New grpc {}:{}", host, port);
//        ManagedChannel channel = NettyChannelBuilder.forAddress(host, port)
//                .negotiationType(NegotiationType.PLAINTEXT)
//                .perRpcBufferLimit(2 << 20)// 1M
//                .retryBufferSize(2 << 19) // 512k
//                // keepAlive 2 hours
//                .keepAliveTime(1, TimeUnit.HOURS)
//                .keepAliveTimeout(40, TimeUnit.SECONDS)
//                .keepAliveWithoutCalls(true)
//                .maxRetryAttempts(3)
//                .build();
//        return new GrpcClient(channel);
//    }
//
//
//    public XConferenceGrpc.XConferenceStub getAsyncStub() {
//        return XConferenceGrpc.newStub(this.channel)
//                .withDeadline(Deadline.after(3, TimeUnit.SECONDS));
//    }
//
//    /**
//     * Check the connection
//     *
//     * @return
//     */
//    public boolean isOk() {
//        return !this.channel.isTerminated()
//                && !this.channel.isShutdown();
//    }
//
//    public void shutdown() {
//        try {
//            if (!this.channel.isShutdown()) {
//                LOGGER.info("Shutdown RPC");
//                this.channel.shutdown();
//            }
//        } catch (Exception e) {
//            LOGGER.warn("Failed to shutdown：{}", e.getMessage());
//        }
//    }
}
