package vn.noron.api.wio.rest;


import io.reactivex.rxjava3.core.Single;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.noron.api.service.momo.MomoService;
import vn.noron.api.service.ordermomo.OrderMomoService;
import vn.noron.apiconfig.config.bind.annotation.MomoAnnotation;
import vn.noron.apiconfig.model.DfResponse;
import vn.noron.data.request.order.OrderRequest;
import vn.noron.data.request.order_momo.MomoResult;
import vn.noron.data.response.MessageResponse;
import vn.noron.data.response.momo.MomoResponse;
import vn.noron.data.response.order.OrderResponse;
import vn.noron.utils.authentication.AuthenticationUtils;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class OrderMomoController {
    private final OrderMomoService orderMomoService;
    private final MomoService momoService;

    public OrderMomoController(OrderMomoService orderMomoService, MomoService momoService) {
        this.orderMomoService = orderMomoService;
        this.momoService = momoService;
    }

    @Operation(summary = "luu thong tin thanh toan bang momo", tags = "Order")
    @ApiResponse(responseCode = "200", content =
            {@Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))})
    @ApiResponse(responseCode = "400", description = "bad-request", content = @Content)
    @PostMapping("/orders/momo")
    public Single<ResponseEntity<DfResponse<MomoResponse>>> insertOrder(
            @RequestBody OrderRequest orderRequest,
            @RequestParam("callback_url") String callBackUrl,
            Authentication authentication) {
        orderRequest.setUserId(AuthenticationUtils.loggedUserId(authentication));
        return orderMomoService.createOrder(orderRequest,callBackUrl)
                .map(DfResponse::okEntity);
    }

    @Operation(summary = "cap nhat ket qua momo tra ve", tags = "Order")
    @ApiResponse(responseCode = "200", content =
            {@Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))})
    @ApiResponse(responseCode = "400", description = "bad-request", content = @Content)
    @PostMapping("/momo/update-result")
    public Single<ResponseEntity<DfResponse<OrderResponse>>> momoReturnBe(
            @MomoAnnotation MomoResult momoResult) {
        return momoService.momoReturnResult(momoResult)
                .map(DfResponse::okEntity);
    }

    @Operation(summary = "FrontEnd tra ve ket qua", tags = "Order")
    @ApiResponse(responseCode = "200", content =
            {@Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))})
    @ApiResponse(responseCode = "400", description = "bad-request", content = @Content)
    @PostMapping("/momo/check-result")
    public Single<ResponseEntity<DfResponse<OrderResponse>>> UpdateResult(
            @RequestBody MomoResult momoResult) {
        return momoService.updateResult(momoResult)
                .map(DfResponse::okEntity);
    }
}

