package vn.noron.api.service.order;


import io.reactivex.rxjava3.core.Single;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class OrderServiceImpl implements IOrderService {

//    private final IOrderRepository orderRepository;
//    private final IOrderProductRepository orderProductRepository;
//    private final OrderProductMapper orderProductMapper;
//    private final IReportRepository reportRepository;
//    private final ReportMapper reportMapper;
//    private final IDocumentTypeRepository documentTypeRepository;
//    private final OrderMapper orderMapper;
//    private final IReportImageRepository reportImageRepository;
//    private final IOrderEmail orderEmail;
//    private final OrderCommonService orderCommonService;
//
//
//    public OrderServiceImpl(OrderRepositoryImpl orderRepository,
//                            IOrderProductRepository orderProductRepository,
//                            OrderProductMapper orderProductMapper,
//                            IReportRepository reportRepository,
//                            ReportMapper reportMapper,
//                            IDocumentTypeRepository documentTypeRepository,
//                            OrderMapper orderMapper,
//                            IReportImageRepository reportImageRepository,
//                            IOrderEmail orderEmail,
//                            OrderCommonService orderCommonService) {
//        this.orderRepository = orderRepository;
//        this.orderProductRepository = orderProductRepository;
//        this.orderProductMapper = orderProductMapper;
//        this.reportRepository = reportRepository;
//        this.reportMapper = reportMapper;
//        this.documentTypeRepository = documentTypeRepository;
//        this.orderMapper = orderMapper;
//        this.reportImageRepository = reportImageRepository;
//        this.orderEmail = orderEmail;
//        this.orderCommonService = orderCommonService;
//    }
//
//    @Override
//    public Single<OrderResponse> saveOrder(OrderRequest orderRequest) {
//        Long loggedUserId = getLoggedUserId();
//        if (loggedUserId == null) return Single.error(new ApiException(NOT_PERMISSION));
//        if (orderRequest.getPhoneNumber() != null && !checkPhone(orderRequest.getPhoneNumber())) {
//            return Single.error(new ApiException(INVALID_PHONE, HttpStatus.SC_BAD_REQUEST));
//        }
//        if (orderRequest.getEmail() != null && !checkEmail(orderRequest.getEmail())) {
//            return Single.error(new ApiException(INVALID_EMAIL, HttpStatus.SC_BAD_REQUEST));
//        }
//        Order order = orderMapper.toPojo(orderRequest);
//        order.setOwnerId(loggedUserId);
//        if (orderRequest.getTotal() < 0.1) {
//            return orderRepository.saveOrder(order, orderRequest.getOrderProducts())
//                    .flatMap(orderReturn -> orderCommonService.toOrderResponse(orderReturn));
//        }
//        return orderRepository.saveOrder(order, orderRequest.getOrderProducts())
//                .flatMap(orderReturn -> orderCommonService.toOrderResponse(orderReturn))
//                .flatMap(orderResponse -> orderEmail.sendOrderByEmail(orderResponse.getId(), order, orderRequest.getOrderProducts(), loggedUserId)
//                        .map(aboolean -> orderResponse));
//
//    }
//
//    @Override
//    public Single<PageResponse<OrderResponse>> getOrderHistory(Pageable pageable, Long userId, Integer status) {
//        return orderRepository.getOrderOfUser(userId, pageable, status)
//                .flatMap(orders -> {
//                    List<Integer> orderIds = orders.stream().map(Order::getId).collect(Collectors.toList());
//                    return Single.zip(
//                            reportRepository.findAll().flatMap(this::toShortReportResponse),
//                            documentTypeRepository.findAll(),
//                            orderProductRepository.getList(ORDER_PRODUCT.ORDER_ID.in(orderIds)),
//                            orderRepository.countNumOrderOfUser(userId, status),
//                            (reports, documentTypes, orderProducts, numOrder) -> {
//                                List<OrderProductResponse> orderProductResponse = convertOrderProductResponse(orderProducts, reports, documentTypes);
//                                List<OrderResponse> orderResponses = convertToOrderResponse(orders, orderProductResponse);
//                                return new PageResponse<OrderResponse>()
//                                        .setItems(orderResponses)
//                                        .setTotal(numOrder);
//                            });
//                });
//    }
//
//    private List<OrderResponse> convertToOrderResponse(List<Order> orders, List<OrderProductResponse> orderProductResponse) {
//        List<OrderResponse> orderResponses = orderMapper.toListResponse(orders);
//        Map<Integer, List<OrderProductResponse>> mapOrderProducts = orderProductResponse
//                .stream().collect(Collectors.groupingBy(OrderProductResponse::getOrderId));
//        orderResponses.forEach(orderResponse -> orderResponse
//                .setOrderProducts(mapOrderProducts.getOrDefault(orderResponse.getId(), new ArrayList<>())));
//        return orderResponses;
//    }
//
//    private List<OrderProductResponse> convertOrderProductResponse(List<OrderProduct> orderProducts, List<ShortReportResponse> reports, List<DocumentType> documentTypes) {
//        Map<Integer, ShortReportResponse> shortReportResponses = reports
//                .stream().collect(Collectors.toMap(ShortReportResponse::getId, Function.identity()));
//
//        Map<Integer, PackageResponse> documentTypeResponses = reportMapper.toTypeResponse(documentTypes)
//                .stream().collect(Collectors.toMap(PackageResponse::getId, Function.identity()));
//        List<OrderProductResponse> orderProductResponses = orderProductMapper.toListResponse(orderProducts);
//
//        orderProductResponses.forEach(orderProductResponse -> {
//            orderProductResponse.setReportResponse(shortReportResponses.get(orderProductResponse.getReportId()));
//            orderProductResponse.setPackageResponse(documentTypeResponses.get(orderProductResponse.getProductId()));
//        });
//
//        return orderProductResponses;
//    }
//
//    //Todo duplicate function in report service
//    private Single<List<ShortReportResponse>> toShortReportResponse(List<Report> reports) {
//        Set<Integer> ids = collectToSet(reports, Report::getId);
//        return reportImageRepository.getList(REPORT_IMAGE.REPORT_ID.in(ids))
//                .map(reportImages -> {
//                    Map<Integer, List<ReportImage>> mediaMap = reportImages.stream()
//                            .collect(groupingBy(ReportImage::getReportId));
//                    return reportMapper.toListShortResponse(reports, mediaMap);
//                });
//    }
}
