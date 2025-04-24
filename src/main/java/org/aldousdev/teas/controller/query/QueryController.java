package org.aldousdev.teas.controller.query;

import org.aldousdev.teas.dto.response.BestSellerDto;
import org.aldousdev.teas.service.query.CustomQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/query")
public class QueryController {

    private final CustomQueryService customQueryService;

    public QueryController(CustomQueryService customQueryService) {
        this.customQueryService = customQueryService;

    }
    @GetMapping("/allSales")
    public List<BestSellerDto> getAllSales(){
        return customQueryService.getAllSales();
//        if(count == 0)
//            return bestSellerDtoList;
//        return bestSellerDtoList.subList(0, Math.min(count, bestSellerDtoList.size()));
    }
    @GetMapping("/milk")
    public List<Object[]> getSalesByMilk(){
        return customQueryService.getMilkByPurchaseLineitem();

    }
    @GetMapping("/bestSellers")
    public List<BestSellerDto> getBestSellers(){
        return customQueryService.getMonthlyBestSellers();

    }
}
