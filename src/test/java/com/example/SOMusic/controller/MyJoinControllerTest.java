package com.example.SOMusic.controller;

import com.example.SOMusic.domain.Join;
import com.example.SOMusic.domain.ShippingCost;
import com.example.SOMusic.domain.TestJoin;
import com.example.SOMusic.service.JoinServiceImpl;
import com.example.SOMusic.service.JoinValidator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.validation.BindingResult;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MyJoinController.class)
class MyJoinControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    JoinServiceImpl joinService;

    @MockBean
    JoinValidator validator;

    private static final String URI_TEMPLATE = "/join";
    private static final String JOIN_INFO = "join/myJoinInfo";

    Join join = TestJoin.createTestJoin();

    Object[] shippingOption = ShippingCost.getCodeList();
    Object[] shippingText = ShippingCost.getNameList();
    Object[] shippingCost = ShippingCost.getCostList();

    @Test
    void showForm() throws Exception {
        int joinId = join.getJoinId();

        mvc.perform(MockMvcRequestBuilders.get(URI_TEMPLATE + "/info/" + joinId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name(JOIN_INFO));
    }

    @Test
    void formBackingInfo() throws Exception {
        int joinId = join.getJoinId();

        Mockito.when(joinService.findJoinByJoinId(joinId)).thenReturn(join);

        mvc.perform(MockMvcRequestBuilders.get(URI_TEMPLATE + "/info/" + joinId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("shippingOption", shippingOption))
                .andExpect(model().attribute("shippingText", shippingText))
                .andExpect(model().attribute("shippingCost", shippingCost))
                .andExpect(model().attributeExists("joinInfoReq"));
    }

    @Test
    void update() throws Exception {
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        int joinId = join.getJoinId();

        Mockito.doNothing().when(validator).validate(join, bindingResult);
        Mockito.doNothing().when(joinService).modifyJoin(join, join);

        mvc.perform(MockMvcRequestBuilders.post(URI_TEMPLATE + "/info/" + joinId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void delete() throws Exception {
        int joinId = join.getJoinId();

        Mockito.when(joinService.findJoinByJoinId(joinId)).thenReturn(join);
        Mockito.doNothing().when(joinService).deleteJoin(joinId);

        mvc.perform(MockMvcRequestBuilders.get(URI_TEMPLATE + "/delete/" + joinId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection());
    }
}