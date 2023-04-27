package com.example.SOMusic.service;

import com.example.SOMusic.dao.JoinDao;
import com.example.SOMusic.domain.GroupPurchase;
import com.example.SOMusic.domain.Join;
import com.example.SOMusic.domain.ShippingCost;
import com.example.SOMusic.domain.TestJoin;
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

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class JoinServiceTest {

    private static final int STATUS = 3;
    private final ArgumentCaptor<Join> captor = ArgumentCaptor.forClass(Join.class);

    @InjectMocks
    JoinService joinService = new JoinServiceImpl();

    @Mock
    JoinRepository joinRepository;

    @Mock
    JoinDao joinDao;

    Join join1 = TestJoin.createTestJoin();
    Join join2 = TestJoin.createAnotherTestJoin();

    @Test
    void registerJoin() {
        int joinId = join1.getJoinId();

        joinDao.createJoin(join1);

        Mockito.verify(joinDao).createJoin(captor.capture());
        int result = captor.getValue().getJoinId();

        assertThat(result).isEqualTo(joinId);
    }

    @Test
    void modifyJoin() { //TODO: 학교 DB 복구 후 테스트
        String initialName = join1.getConsumerName();
        String initialBank = join1.getConsumerBank();
        int initialShippingCost = join1.getShippingCost();
        int initialAmount = join1.getTotalAmount();

        joinService.modifyJoin(join1, join2);

        assertThat(join1.getConsumerBank()).isNotEqualTo(initialBank);
        assertThat(join1.getShippingCost()).isNotEqualTo(initialShippingCost);
        assertThat(join1.getTotalAmount()).isNotEqualTo(initialAmount);
        assertThat(join1.getConsumerName()).isEqualTo(initialName);
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
    void initShippingCost() {
        int shippingMethod = join1.getShippingMethod();
        int shippingCost = ShippingCost.findCostByCode(shippingMethod);

        int result = joinService.initShippingCost(join1);

        assertThat(result).isEqualTo(shippingCost);
    }

    @Test
    void calculateTotal() {
        int newShippingMethod = 2;
        int totalAmount = join1.getTotalAmount();
        int shippingCost = join1.getShippingCost();

        join1.setShippingMethod(newShippingMethod);

        int newShippingCost = ShippingCost.findCostByCode(newShippingMethod);
        int newTotalPrice = (totalAmount - shippingCost) + newShippingCost;

        int result = joinService.calculateUpdatedTotal(join1, newShippingCost);

        assertThat(result).isEqualTo(newTotalPrice);
    }

    @Test
    void updateAllStatus() {
        List<Join> joins = initSameJoinList();

        int initialStatus1 = joins.get(0).getStatus();
        int initialStatus2 = joins.get(1).getStatus();

        joinService.updateAllStatus(joins, STATUS);

        int result1 = joins.get(0).getStatus();
        int result2 = joins.get(1).getStatus();

        assertThat(result1).isNotEqualTo(initialStatus1);
        assertThat(result2).isNotEqualTo(initialStatus2);
    }

    List<Join> initSameJoinList() {
        joinRepository.save(join1);
        joinRepository.save(join1);

        List<Join> joins = new LinkedList<>();
        joins.add(join1);
        joins.add(join1);

        return joins;
    }

    @Test
    void updateStatus() {
        int initialStatus = join1.getStatus();

        joinService.updateStatus(join1, STATUS);

        int result = join1.getStatus();

        assertThat(result).isNotEqualTo(initialStatus);
    }
}