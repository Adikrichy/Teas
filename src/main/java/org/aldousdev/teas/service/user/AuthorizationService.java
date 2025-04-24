package org.aldousdev.teas.service.user;

import org.aldousdev.teas.dto.request.PasswordRequestDto;
import org.aldousdev.teas.dto.response.SigninResponse;
import org.aldousdev.teas.models.user.Account;

import java.util.List;
import java.util.Map;

public interface AuthorizationService {
    SigninResponse updateUser(Map<String, Object> userRequestDto, Account account);
    void updatePassword(PasswordRequestDto passwordRequestDto, Account account);
    List<SigninResponse> getAllAccounts();
    SigninResponse toggleAdminRole(Long id);
    void deleteAccount(Long id, Account authenticatedAccount);
    Account findByEmail(String email);
}
