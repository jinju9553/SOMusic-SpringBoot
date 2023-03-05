package com.example.SOMusic.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.SOMusic.domain.GroupPurchase;
import com.example.SOMusic.domain.Join;
import com.example.SOMusic.domain.ShippingCost;
import com.example.SOMusic.repository.JoinRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@ExtendWith(SpringExtension.class)
class JoinServiceTest {

    private static final int JOIN_ID = 67492;
    private static final int GP_ID = 568;
    private static final int GP_PRICE = 10_000;
    private static final int SHIPPING_METHOD = 1;
    private static final int SHIPPING_COST = ShippingCost.findCostByCode(SHIPPING_METHOD);
    private static final int TOTAL_AMOUNT = 51_800;
    private static final String CONSUMER_ID = "amy1234";

    @InjectMocks
    JoinService joinService = new JoinServiceImpl();

    @Mock
    JoinRepository joinRepository;

    Join join1 = createTestJoin();
    Join join2 = createTestJoin();

    @Test
    void registerJoin() {
        //TODO: 리턴타입이 없어서 Mockito.when을 쓸 수 없음 => verify()
    }

    @Test
    void modifyJoin() {
        //TODO: 함수를 새로 설계하고 그 이후 테스트코드 작성
    }

    @Test
    void findJoinByJoinId() {
        int joinId = join1.getJoinId();

        Mockito.when(joinRepository.findJoinByJoinId(joinId))
                .thenReturn(join1);

        Join result = joinService.findJoinByJoinId(joinId);

        assertThat(result.getJoinId()).isEqualTo(joinId);
    }

    @Test
    void findAllByUserId() {
        String userId = join1.getConsumerId();

        List<Join> joins = initJoinList();

        Mockito.when(joinRepository.findAllByConsumerId(userId))
                .thenReturn(joins);

        List<Join> result = joinService.findAllByUserId(userId);

        assertThat(result.size()).isEqualTo(joins.size());
    }

    @Test
    void findAllByGroupPurchaseGpId() {
        GroupPurchase gp = join1.getGroupPurchase();
        int gpId = gp.getGpId();

        List<Join> joins = initJoinList();

        Mockito.when(joinRepository.findAllByGroupPurchaseGpId(gpId)).thenReturn(joins);

        List<Join> result = joinService.findAllByGroupPurchaseGpId(gpId);

        assertThat(result.size()).isEqualTo(joins.size());
    }

    List<Join> initJoinList() {
        joinRepository.save(join1);
        joinRepository.save(join2);

        List<Join> joins = new LinkedList<>();
        joins.add(join1);
        joins.add(join2);

        return joins;
    }

    @Test
    void calculateTotal() {
        GroupPurchase gp = join1.getGroupPurchase();

        int price = gp.getPrice();
        int quantity = join1.getQuantity();
        int shippingCost = join1.getShippingCost();

        int total = (price * quantity) + shippingCost;

        int result = joinService.calculateTotal(gp, join1);

        assertThat(result).isEqualTo(total);
    }

    @Test
    void initShippingCost() {
        int shippingMethod = join1.getShippingMethod();
        int shippingCost = ShippingCost.findCostByCode(shippingMethod);

        int result = joinService.initShippingCost(join1);

        assertThat(result).isEqualTo(shippingCost);
    }

    @Test
    void updateTotal() {
        int newShippingMethod = 2;

        join1.setShippingMethod(newShippingMethod);

        int newShippingCost = ShippingCost.findCostByCode(newShippingMethod);
        int newTotalPrice = (TOTAL_AMOUNT - SHIPPING_COST) + newShippingCost;

        int result = joinService.updateTotal(join1, newShippingCost);

        assertThat(result).isEqualTo(newTotalPrice);
    }

    @Test
    void updateAllStatus() {
        //TODO: 함수를 새로 설계하고 그 이후 테스트코드 작성
    }

    @Test
    void updateStatus() {
        //TODO: 함수를 새로 설계하고 그 이후 테스트코드 작성
    }

    public Join createTestJoin() {
        Join join = new Join();

        join.setJoinId(JOIN_ID);
        join.setConsumerId(CONSUMER_ID);
        join.setConsumerName("amy");
        join.setConsumerBank("BankName");
        join.setTotalAmount(TOTAL_AMOUNT);
        join.setShippingMethod(SHIPPING_METHOD);
        join.setShippingCost(SHIPPING_COST);

        join.setRegDate(new Date());

        GroupPurchase groupPurchase = new GroupPurchase();

        groupPurchase.setGpId(GP_ID);
        groupPurchase.setPrice(GP_PRICE);
        join.setGroupPurchase(groupPurchase);

        return join;
    }
}