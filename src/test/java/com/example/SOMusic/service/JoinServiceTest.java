package com.example.SOMusic.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.SOMusic.dao.JoinDao;
import com.example.SOMusic.domain.*;
import com.example.SOMusic.repository.JoinRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedList;
import java.util.List;

@ExtendWith(SpringExtension.class)
class JoinServiceTest {

    private final ArgumentCaptor<Join> captor = ArgumentCaptor.forClass(Join.class);

    @InjectMocks
    JoinService joinService = new JoinServiceImpl();

    @Mock
    JoinRepository joinRepository;

    @Mock
    JoinDao joinDao;

    Join join1 = TestJoin.createTestJoin();
    Join join2 = TestJoin.createTestJoin();

    @Test
    void registerJoin() {
        int joinId = join1.getJoinId();

        joinDao.createJoin(join1);

        Mockito.verify(joinDao).createJoin(captor.capture());
        int result = captor.getValue().getJoinId();

        assertThat(result).isEqualTo(joinId);
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
        int totalAmount = join1.getTotalAmount();
        int shippingCost = join1.getShippingCost();

        join1.setShippingMethod(newShippingMethod);

        int newShippingCost = ShippingCost.findCostByCode(newShippingMethod);
        int newTotalPrice = (totalAmount - shippingCost) + newShippingCost;

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
}