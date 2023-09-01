package com.carrot.market.member.presentation;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;

import com.carrot.market.global.exception.ApiException;
import com.carrot.market.global.exception.domain.MemberException;
import com.carrot.market.support.ControllerTestSupport;

class MemberControllerTest extends ControllerTestSupport {

	@Test
	void checkDuplicateNickname() throws Exception {
		// given
		when(memberService.checkDuplicateNickname(any())).thenReturn(true);

		// when & then
		mockMvc.perform(
				get("/api/users").param("nickname", "jun")
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value("true"));
	}

	@Test
	void checkDuplicateNicknameInvokeExistMemberExcption() throws Exception {
		// given
		when(memberService.checkDuplicateNickname(any())).thenThrow(
			new ApiException(MemberException.EXIST_MEMBER));

		// when & then
		mockMvc.perform(
				get("/api/users").param("nickname", "jun")
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value("false"))
			.andExpect(jsonPath("$.errorCode.status").value(MemberException.EXIST_MEMBER.getHttpStatus().value()))
			.andExpect(jsonPath("$.errorCode.message").value(MemberException.EXIST_MEMBER.getMessage()));
	}
}